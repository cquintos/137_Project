import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.JLabel;

class Client implements Runnable, Constants{
	int team, clicks;
	int toMove =0;
	int start=300;

	String server;
	String name;

	boolean connected=false;
	DatagramSocket socket = new DatagramSocket();
	String serverData;

	BufferedImage offscreen;

	Thread t = new Thread(this);

	public Client(String server,String name, int team) throws Exception{
		this.server=server;
		this.name=name;
		this.team=team;
		
		//set some timeout for the socket
		socket.setSoTimeout(100);
		
		//tiime to play
		t.start();		
	}

    public void run(){
		while(true){
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			socket.receive(packet);
			}catch(Exception ioe){/*lazy exception handling :)*/}
			
			serverData=new String(buf);
			serverData=serverData.trim();

			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name+" "+team);
			}
			else if(connected && serverData.startsWith("STARTO")){
				GUI.waiting.setVisible(false);
				GUI.three.setVisible(true);
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
				GUI.three.setVisible(false);
				GUI.two.setVisible(true);
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
				GUI.two.setVisible(false);
				GUI.one.setVisible(true);
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
				GUI.one.setVisible(false);
				GUI.tugButton.setVisible(true);
				GUI.tugButton.setEnabled(true);
			}
			else if(connected && serverData.startsWith("TUG")){
				int newBallLocation = Integer.parseInt(serverData.split(" ")[1].trim());
				GUI.ball.setBounds(newBallLocation, 240, 60, 60); 
				GUI.frame.repaint();
				GUI.score.setText(Integer.toString(GUI.playerScore));
			}
			else if(connected && serverData.startsWith("FINISHED")){
				int winner = Integer.parseInt(serverData.split(" ")[1].trim());
				System.out.println(winner);
				if (team == winner){
					GUI.winner.setVisible(true);
				}
				else
					GUI.loser.setVisible(true);
			}
		}			
	}

	// public void paintComponent(Graphics g){
	// 	g.drawImage(offscreen, 0, 0, null);
	// }

	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        	socket.send(packet);
        }catch(Exception e){}
		
	}
}
