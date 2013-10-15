package checkers;

import java.rmi.RemoteException;

public class CheckersObserver implements GameObserver{
	private GameInfo myGame;

	@Override
	public String move(Move playerMove) throws RemoteException {
		// TODO Render the move on the board WITHOUT making it this client's turn (this code should be ripped from player)
		
		return null;
	}

	@Override
	public String playerResigned(PlayerInfo aPlayer, char code, String aMessage) throws RemoteException {
		// TODO Display the message that the player has given up
		
		System.out.println("*** GAME OVER ***");
		System.out.println("Player [ " + aPlayer.getName() + " ] has resigned with code [ " + code + " ]");
		System.out.println("Message : " + aMessage);
		return null;
	}

	@Override
	public void gameOver(PlayerInfo winner) throws RemoteException {
		// TODO Display the message that the game is now over and who has won
		
		System.out.println("*** GAME OVER ***");
		System.out.println("Player [ " + winner.getName() + " ] has won!");
	}

}
