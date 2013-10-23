package checkers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CheckersObserver extends UnicastRemoteObject implements GameObserver{
	private static final long serialVersionUID = 1L;
	
	private Server server;
	private GameInfo myGame;
	private PlayerInfo myID;
	
	CheckersObserver(PlayerInfo myName) throws RemoteException{
		myID = myName;
	}
	
	@Override
	public void receiveMove(Move playerMove) throws RemoteException {
		// TODO Render the move on the board WITHOUT making it this client's turn (this code should be ripped from player)
	}

	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage) throws RemoteException {
		// TODO Display the message that the player has given up
		
		System.out.println("*** GAME OVER ***");
		System.out.println("Player [ " + aPlayer.getName() + " ] has resigned with code [ " + code + " ]");
		System.out.println("Message : " + aMessage);
	}

	@Override
	public PlayerInfo getPlayerInfo() throws RemoteException{
		return new PlayerInfo(myID);
	}
	
	public void setGame(GameInfo newGame) throws Exception{
		this.myGame = new GameInfo(newGame);
	}
}
