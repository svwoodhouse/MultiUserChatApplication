package chatapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javafx.scene.layout.BorderPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatApp 
{
    public static void main(String[] args) 
    {
        // GUI Components for the server
        JFrame mainFrame = new JFrame("Server");
        JPanel serverPanel;
        JPanel serverLogPanel;
        JButton startServer;
        JButton endServer;
        JLabel portNumber;
        JTextArea serverLogs;
        JTextField portNumberEntry;
        JScrollPane scrollPane;
        BorderPane border = new BorderPane();
        
        
        mainFrame.setSize(600,500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
        serverPanel = new JPanel();
        serverLogPanel = new JPanel();
        serverPanel.setLayout(new BorderLayout());
        startServer = new JButton("Start Server");
        endServer = new JButton("End Server");
        portNumber = new JLabel("Port");
        serverLogs = new JTextArea();
        portNumberEntry = new JTextField();
        scrollPane = new JScrollPane(serverLogs);
        
        // Adds components to the panel and to the frame
        serverPanel.add(portNumber,BorderLayout.LINE_START);
        serverPanel.add(portNumberEntry, BorderLayout.CENTER);
        serverPanel.add(startServer, BorderLayout.CENTER);
        serverPanel.add(endServer, BorderLayout.LINE_END);
        serverLogPanel.add(scrollPane);

        mainFrame.getContentPane().add(serverPanel,BorderLayout.PAGE_START);
        mainFrame.getContentPane().add(serverLogPanel,BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
        
        
        
    }
    
}
