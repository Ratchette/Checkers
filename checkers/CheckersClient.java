/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CheckersClient implements Remote, GameObserver, Player, Serializable{
	private static final long serialVersionUID = 1L;
	private static final String granger = "131.104.48.15";
	private static final String serverName = "checkersServer";
	
	private GameObserver observer;
	private Player player;
	
	private GameInfo myGame;
	
	
	public CheckersClient(boolean isPlayer) throws RemoteException{
		// TODO - bind this method to an action listener INSTEAD of taking a string as a parameter
		// When client starts up, the GUI will have two buttons, one to be an observer, and one to be a client
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
	
	public CheckersClient() {
	
	}


	public GameInfo getGame() throws RemoteException{
		return new GameInfo(myGame);
	}

	@Override
	public Object considerGame(GameDesign aGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void startGame(PlayerInfo player, String gameType) throws RemoteException {
		
		
	}



	@Override
	public void opponentResigned(PlayerInfo player, char code, String message)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String resign() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void youWin() throws RemoteException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String move(Move playerMove) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String playerResigned(PlayerInfo aPlayer, char code, String aMessage)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void gameOver(PlayerInfo winner) throws RemoteException {
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
		Registry registry;
		
		System.out.println("Welcome to Checkers! Choose your game type:");
		System.out.println("Currently Available:\n\t\"British\"\n\t\"American\"\n\t\"International\"\n\t\"Canadian\"");
		System.out.println("\n");
		Scanner scanner = new Scanner (System.in);  
		String name = scanner.next();
		System.out.println("You've entered: " + name);
		
		//TODO: Implement connection to server
		try {
			CheckersPlayer p = new CheckersPlayer();
			p.startGame(new PlayerInfo("Player"), name);
			
			if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
			
			try{
//	            registry = LocateRegistry.getRegistry("127.0.0.1");    //enter the address of the remote machine here
//				Server server = (Server) registry.lookup(serverName);	
//				
////				 testing methods
//				System.out.println(server.testMethod("Hello Granger"));
			}
			catch(Exception e){
				System.out.println("Unable to connect to server " + serverName + " at this time");
				e.printStackTrace();
			}
			
			Gui window = new Gui(p.theBoard);
			window.drawBoard(p.theBoard);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scanner.close();
		//TODO: Wait until the server accepts client
		
		//TODO: When game approved, start game
	}

	public GameObserver getObserver() {
		return observer;
	}

	public void setObserver(GameObserver observer) {
		this.observer = observer;
	}
}
