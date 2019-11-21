/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Sydnee
 */
public class Server 
{
    private int portNumber;
    private ServerSocket ss;
    static boolean chatRunning; 
    static Vector<ClientHandler> activeClientList = new Vector<>();
    static int i = 0;
    
    
    Server(int portNumber,JTextArea textArea) throws IOException
    {
        this.portNumber = portNumber;
        this.ss = new ServerSocket(this.portNumber);
        this.chatRunning = true;
        Socket socket;
        
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        
        // Runs infinte loop until you click the end button
        // It keeps searching for client request 
        while(chatRunning)
        {   
                System.out.println("Waiting for new clients to join....");
                socket = ss.accept();
                
                System.out.println("A new client has connected : " + socket);
                
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                
                System.out.println("Assigning a new thread for this client");
                ClientHandler newClient = new ClientHandler(socket,"client " + i ,dis, dos, textArea);
                Thread t = new Thread(newClient);
                System.out.println("Adding this client to active client list");
                activeClientList.add(newClient);
                t.start();
                i++;
            
        }
    }
    
    public void endChat()
    {
        this.chatRunning = false;
        System.exit(0);
    }
    
    public boolean getChatRunning()
    {
        return chatRunning;
    }
    
}

class ClientHandler implements Runnable
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;
    private String name;
    boolean isActive;
    JTextArea textArea;
    
    public ClientHandler(Socket socket, String name, DataInputStream dis, DataOutputStream dos, JTextArea textArea)
    {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.isActive = true;
        this.textArea = textArea;
    }

    @Override
    public void run() 
    {
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        String received;
        
        while(true)
        {
            try
            {
                Thread.sleep(4000);
                received = dis.readUTF();
                System.out.println(received);
                
                if(!Server.chatRunning)
                {
                    this.isActive = false;
                    this.socket.close();
                    break;
                }
                if(received == "USER_EXIT")
                {
                    this.isActive = false;
                    this.socket.close();
                    for(ClientHandler mc : Server.activeClientList)
                    {
                        if(mc.isActive == true)
                        {
                            mc.dos.writeUTF(this.name + "has logged out of the chat");
                        }
                    }
                }
                for(ClientHandler mc : Server.activeClientList)
                {
                    if(mc.isActive == true)
                    {
                        mc.dos.writeUTF(this.name + " : " + received);
                    }
                }
            }catch (IOException e) { 
                  
                e.printStackTrace(); 
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

        try { 
            this.dis.close();
            this.dos.close(); 
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }  
}
