import java.util.*;
import java.net.*;
import java.io.*;

class Server implements Runnable, Constants{

	String playerData;

	int playerCount=0;
	int roomCount=0;
	int chatPort;

	Thread greetingServer;

	DatagramSocket serverSocket = null;

	GameState game;

	int stage = WAITING_FOR_PLAYERS;

	int numPlayers;

	Thread t = new Thread(this);

	public Server(int numPlayers, int chatPort){
		this.numPlayers = numPlayers;
		this.chatPort = chatPort;
		try{
			serverSocket = new DatagramSocket(PORT);
			serverSocket.setSoTimeout(TIMEOUT);
		} catch(IOException e){
			System.err.println("Port failure "+PORT);
            System.exit(-1);
		} catch(Exception e){}

		try{	
			new GreetingServer(this.chatPort);
		}
		catch(Exception e){
			System.out.println(e);
		}
		

		game = new GameState();

		System.out.println("GAME CREATED");

		t.start();

	}

	public void broadcast(String msg){
		for(Iterator ite=game.getPlayers().keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			Player player=(Player)game.getPlayers().get(name);			
			send(player,msg);	
		}
	}

	public void send(Player player, String msg){
		DatagramPacket packet;	
		byte buf[] = msg.getBytes();		
		packet = new DatagramPacket(buf, buf.length, player.getAddress(),player.getPort());
		try{
			serverSocket.send(packet);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public void run(){
		boolean gameNotDone = true;
		int lastBallLocation = 510;
		while(true){

			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			serverSocket.receive(packet);
			}catch(Exception ioe){}

			playerData = new String(buf);

			playerData = playerData.trim();

			if(playerData.startsWith("JOINEDROOM")){
				roomCount++;
				System.out.println(roomCount + "waiting for room");
			}

			switch(stage){
				case WAITING_FOR_PLAYERS:
					if (playerData.startsWith("CONNECT")){
						String tokens[] = playerData.split(" ");
						Player player = new Player(tokens[1], Integer.parseInt(tokens[2]), packet.getAddress(),packet.getPort());
						System.out.println("Player connected: "+tokens[1]);
						game.update(tokens[1].trim(),player);
						broadcast("CONNECTED "+tokens[1]);
						broadcast("PORTNUMBER "+Integer.toString(chatPort));
						playerCount++;
						if (playerCount==numPlayers){
								stage=GAME_START;
							}
						}

					break;
				case GAME_START:
					if(roomCount == playerCount){
						System.out.println("Gameo Starto");
						broadcast("STARTO");
						stage=IN_PROGRESS;
						game.initializeBall();
						broadcast(game.toString());
						break;
					}
				case IN_PROGRESS:
					if (playerData.startsWith("CLICK")){
						//Tokenize:
						//The format: PLAYER <player name> <x> <y>
						String[] playerInfo = playerData.split(" ");					  
						String pname =playerInfo[1];
						int team = Integer.parseInt(playerInfo[2].trim());
						//Get the player from the game state
						Player player=(Player)game.getPlayers().get(pname);					  
						player.addClick();
						//Update the game state
						game.update(pname, player);
						int x = 0;
						if(team == TEAM_ONE)
							x=3;
						else 
							x=-3;
						game.setBallLocation(game.getBallLocation() + x);
						//Send to all the updated game state
						broadcast(game.toString());
						lastBallLocation = game.getBallLocation();
						System.out.println(lastBallLocation);
						System.out.println(game.getBallLocation());
						if(lastBallLocation < 410 || lastBallLocation > 610){
							stage = GAME_FINISHED;
						}
					  }
					  break;
				case GAME_FINISHED:
					if(lastBallLocation >= 410){
						broadcast("FINISHED 1");
						System.out.println("winner 1");
					}
					else if(lastBallLocation <= 610){
						broadcast("FINISHED 2");
						System.out.println("winner 2");
					}
					else{
						broadcast("NOWINNER");
						System.out.println("winner "+lastBallLocation);
					}
					stage = WAITING_GAME_RESTART;
					System.out.println(stage);
					roomCount = 0;
					break;
				case WAITING_GAME_RESTART:
					if (playerData.startsWith("REMATCH")){
						roomCount++;
						if(roomCount == playerCount)
							stage = GAME_START;
					}
					else if(playerData.startsWith("QUIT")){
						broadcast("DONEGAME");
						System.exit(1);
					}
					break;

			}

		}

	}
	
	/*public static void main(String args[]){
		if (args.length != 1){
			System.out.println("format: java Server <noofplayers>");
			System.exit(1);
		}
		
		new Server(Integer.parseInt(args[0]));
	}*/
}