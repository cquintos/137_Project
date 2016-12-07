import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class GreetingClient {
   
   public static void main(String [] args) throws Exception{
      JFrame frame = new JFrame("CHAT MODULE");
      JPanel panel = new JPanel();
      final JTextArea textArea = new JTextArea(10,20);
      textArea.setBackground(Color.white);
      textArea.setLineWrap(true);
      JScrollPane scroll = new JScrollPane(textArea);
      final JTextField textField = new JTextField(20);
      textField.setBackground(Color.white);
      
      panel.add(textArea);
      panel.add(textField);
      
     Scanner sc = new Scanner(System.in);
     System.out.print("Enter username: ");
     final String username = sc.nextLine();
      
      frame.setSize(300,300);
      frame.add(panel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      
      try {
   	   String serverName = args[0]; //get IP address of server from first param
	      int port = Integer.parseInt(args[1]); //get port from second param
         final Scanner scanner = new Scanner(System.in);
         final Socket client = new Socket(serverName, port);
         
         /* Open a ClientSocket and connect to ServerSocket */
         System.out.println("Connecting to " + serverName + " on port " + port);
         //insert missing line here for creating a new socket for client and binding it to a port
	 
         new Thread() {
            public void run(){
      	      try {
                  OutputStream outToServer = client.getOutputStream();
                  final DataOutputStream out = new DataOutputStream(outToServer);
                  
                  textField.addActionListener(new ActionListener(){
                    
                      public void actionPerformed(ActionEvent e){
                        String message = username + ": " + textField.getText() + "\n";
                        try{
                          out.writeUTF(message);
                        }catch(IOException a){
                         System.out.println("ERROR!");
                        }
                        textField.setText("");
                      }
                     
                    });
                     
                  /*while(true) {
                      Send data to the ServerSocket 
                     String message = scanner.nextLine();
                     out.writeUTF(username+": " +message);
                  }*/
               } catch (Exception e){
                  System.out.println(e);
               };
            }
         }.start();

         new Thread() {
            public void run(){
               try {
                  /* Receive data from the ServerSocket */
                  InputStream inFromServer = client.getInputStream();
                  DataInputStream in = new DataInputStream(inFromServer);
            
                  while(true) {
                     /* Send data to the ServerSocket */
                     //System.out.println(in.readUTF());
                     int pos = textArea.getCaretPosition();
                     textArea.insert(in.readUTF(), pos);
                  }
               } catch (Exception e){
                  System.out.println(e);
               };
            }
         }.start();

      } catch(IOException e) {
         //e.printStackTrace();
      	System.out.println("Cannot find Server");
      } catch(ArrayIndexOutOfBoundsException e) {
         System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
      }
   }
}
