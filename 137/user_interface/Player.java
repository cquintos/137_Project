import java.net.InetAddress;

public class Player{

	private InetAddress address;
	
	private int port;
	
	private String name;
	
	private int clicks;

	private int team;

	public Player(String name, int team, InetAddress address, int port){
		this.address = address;
		this.port = port;
		this.name = name;
		this.team = team;
	}

	public InetAddress getAddress(){
		return this.address;
	}

	public int getPort(){
		return this.port;
	}

	public String getName(){
		return this.name;
	}
	
	public void addClick(){
		this.clicks++;
	}

	public int getTeam(){
		return this.team;
	}

	
	public int getClicks(){
		return this.clicks;
	}

	public String toString(){
		String retval="";
		retval+="PLAYER ";
		retval+=this.name+" ";
		retval+=this.clicks+" ";
		retval+=this.team;
		return retval;
	}	

}