   // File Name GreetingServer.java

import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class GreetingServer extends Thread {
   private LinkedList<Socket> clients = new LinkedList<Socket>();
   private static ServerSocket serverSocket;

   public GreetingServer(int port) throws IOException {
      //insert missing line here for binding a port to a socket
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(1000000);
      this.start();
   }

   public void run() {
      new Thread() {
         public void run() {
            boolean connected = true;

            while(connected) {            
               try {
                  // Start accepting data from the ServerSocket //
                  //insert missing line for accepting connection from client here]
                  final Socket client = serverSocket.accept();
                  System.out.println("Just connected to " + client.getRemoteSocketAddress());
                  clients.add(client);
                 
                  new Thread(){
                     public void run(){
                        try{
                           while(true){
                              DataInputStream in = new DataInputStream(client.getInputStream());
                              /* Read data from the ClientSocket */
                              String message = in.readUTF();
                              System.out.println(message);
                              
                              for(Socket c : clients) {
                                 DataOutputStream out = new DataOutputStream(c.getOutputStream());
                                 /* Send data to the ClientSocket */
                                 out.writeUTF(message);
                                 out.flush();
                              }
                           }
                        } catch (Exception e){
                           System.out.print(e);
                        }
                     }
                  }.start();                  
               } catch(SocketTimeoutException s) {
                  System.out.println("Socket timed out!");
                  break;
               } catch(IOException e) {
                  System.out.println("Usage: java GreetingServer <port no.>");
                  break;
               }
            }
         } 

      }.start();

   }

   
}
