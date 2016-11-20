import java.util.*;

class GameState{

	public static int ballLocation;
	private Map<String, Player> players=new HashMap();

	public GameState(){
		this.ballLocation = 510;
	}

	public void update(String name, Player player){
		players.put(name,player);
	}

	public String toString(){
		String retval="TUG ";
		retval+=this.ballLocation;
		return retval;
	}

	public Map getPlayers(){
		return players;
	}

	public void setBallLocation(int ballLocation){
		this.ballLocation = ballLocation;
	}

	public int getBallLocation(){
		return this.ballLocation;
	}

}