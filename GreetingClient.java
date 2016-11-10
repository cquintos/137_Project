import java.net.*;
import java.io.*;
import java.util.Scanner;

public class GreetingClient {
   
   public static void main(String [] args) {
      try {
   	   String serverName = args[0]; //get IP address of server from first param
	      int port = Integer.parseInt(args[1]); //get port from second param
         final Scanner scanner = new Scanner(System.in);
         Socket client = new Socket(serverName, port);
         
         Scanner sc = new Scanner(System.in);
         System.out.print("Enter username: ");
         String username = sc.nextLine();

                  
         /* Open a ClientSocket and connect to ServerSocket */
         System.out.println("Connecting to " + serverName + " on port " + port);
         //insert missing line here for creating a new socket for client and binding it to a port
	 
         new Thread() {
            public void run(){
      	      try {
                  OutputStream outToServer = client.getOutputStream();
                  DataOutputStream out = new DataOutputStream(outToServer);
                     
                  while(true) {
                     /* Send data to the ServerSocket */
                     String message = scanner.nextLine();
                     out.writeUTF(username+": " +message);
                  }
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
                     System.out.println(in.readUTF());
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
