import java.io.*;
import java.net.*;

public class GetTime {
	final private static int DAYTIME_PORT = 9999;

	public static void main(String args[]) throws IOException {

		String host = "127.0.0.1";
		byte message[] = new byte[256];
		InetAddress address = InetAddress.getByName(host);

		System.out.println("Checking time at host " + address + "...please wait...");
		DatagramPacket packet = new DatagramPacket(message, message.length, address, DAYTIME_PORT);
		DatagramSocket socket = new DatagramSocket();
	
		socket.send(packet);
		packet = new DatagramPacket(message, message.length);

		/* receive() method of DatagramSocket will indefinitely block until
		   a UDP datagram is received */
		socket.receive(packet);

		String time = new String(packet.getData());
		System.out.println("The time at " + host + " is: " + time);
		socket.close();
	}
}
