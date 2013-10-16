/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.io.Console;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CheckersClient implements Remote, GameObserver, Player{
	private GameObserver observer;
	private Player player;
	
	private GameInfo myGame;
	
	
	CheckersClient(boolean Player) throws RemoteException{
		// TODO - bind this method to an action listener INSTEAD of taking a string as a parameter
		// When client starts up, the GUI will have two buttons, one to be an observer, and one to be a client
		if(Player){
			this.player = new CheckersPlayer();
			this.observer = null;
		}
		else{
			this.player = null;
			this.observer = new CheckersObserver();
		}
		
		this.myGame = null;
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
	public void startGame() throws RemoteException {
		
		
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
				&& this.observer.equals(otherClient.observer)
				&& this.myGame.equals(otherClient.getGame());
		}
		catch(Exception e){
			System.out.println("Excpetion Occured");
			e.printStackTrace();
			return false;
		}
		
	}

	
	public static void main(String[] args) {

		//TODO: Implement connection to server
		
		System.out.println("Welcome to Checkers! Choose your game type:");
		System.out.println("Currently Available:\n\t\"British\"");
		System.out.println("\n");
		Scanner scanner = new Scanner (System.in);  
		String name = scanner.next();
		
		System.out.println("You've entered: " + name);
		
		//TODO: Wait until the server accepts client
		
		//TODO: When game approved, start game
	}
}
