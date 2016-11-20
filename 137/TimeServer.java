import java.io.*;
import java.net.*;
import java.util.*;

public class TimeServer {
	final private static int DAYTIME_PORT = 9999;

	public static void main(String args[]) throws IOException {
		DatagramSocket socket = new DatagramSocket(DAYTIME_PORT);
		
		while (true) {
			System.out.println("Waiting for time requests...");
			byte buffer[] = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);

			System.out.print("Request received...sending time...");
			String date = new Date().toString();
			buffer = date.getBytes();

			/* Get response address/port for client from packet */
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			packet = new DatagramPacket(buffer, buffer.length, 
			address, port);
			socket.send(packet);
			System.out.println("Done");
		}
	}
}
