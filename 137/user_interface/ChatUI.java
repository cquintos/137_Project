import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class ChatUI extends JPanel{
  ChatUI(final String username, String serverName, int port){
    final JTextArea textArea = new JTextArea(10,20);
    textArea.setBackground(Color.white);
    textArea.setLineWrap(true);
    JScrollPane scroll = new JScrollPane(textArea);
    final JTextField textField = new JTextField(20);
    textField.setBackground(Color.white);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
    this.add(scroll);
    this.add(textField);
    try{
      final Socket client = new Socket(serverName, port);
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
            System.out.println("Error");
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
            System.out.println("Error");
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
