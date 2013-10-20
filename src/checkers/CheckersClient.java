/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CheckersClient implements GameObserver, Player{
	private GameObserver observer;
	private Player player;
	
	private GameInfo myGame;
	
	// constructor
	public CheckersClient(boolean isPlayer) throws RemoteException{
		if(isPlayer){
			this.player = new CheckersPlayer();
			this.setObserver(null);
		}
		else{
			this.player = null;
			this.setObserver(new CheckersObserver());
		}
		
		this.myGame = null;
	}
	
	// getter for game observer
	public GameObserver getObserver() {
		return observer;
	}

	// setter for game observer
	public void setObserver(GameObserver observer) {
		this.observer = observer;
	}

	// getter for gameInfo
	public GameInfo getGame() throws RemoteException{
		return new GameInfo(myGame);
	}

	@Override
	public Object considerGame(GameDesign aGame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void youWin() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String move(Move playersMove) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void gameOver(PlayerInfo winner) {
		// TODO Auto-generated method stub
	}

	@Override
	public void receiveMove(Move playersMove) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean equals (Object other){
		if(other == null){
			return false;
		}
		
		if(other.getClass() != this.getClass()){
			return false;
		}
		
		CheckersClient otherClient = (CheckersClient) other;
		try{
			return this.player.equals(otherClient.player)
				&& this.getObserver().equals(otherClient.getObserver())
				&& this.myGame.equals(otherClient.getGame());
		}
		catch(Exception e){
			System.out.println("Excpetion Occured");
			e.printStackTrace();
			return false;
		}
		
	}

	
	public static void main(String[] args) {
		// setup connection to server
		String hostname = Server.granger;
		
		if(args.length > 0)
			hostname = args[0];
		
		// choose a game type
		System.out.println("Welcome to Checkers! Choose your game type:");
		System.out.println("Currently Available:\n\t\"British\"\n\t\"American\"\n\t\"International\"\n\t\"Canadian\"");
		System.out.println("\n");
		Scanner scanner = new Scanner (System.in);  
		String name = scanner.next();
		System.out.println("You've entered: " + name);
		
		//TODO: Implement connection to server
		try {
			Server server = (Server) Naming.lookup("//" + hostname + "/" + Server.serverName);
			// ensure that you have connected to the server
			
			CheckersPlayer p = new CheckersPlayer();
			p.startGame();
			
			Gui window = new Gui(p.theBoard);
			window.drawBoard(p.theBoard);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scanner.close();
	}
}
