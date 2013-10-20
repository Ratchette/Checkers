package checkers;

import java.rmi.RemoteException;

public class CheckersObserver implements GameObserver{
	private GameInfo myGame;

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
	public void gameOver(PlayerInfo winner) throws RemoteException {
		// TODO Display the message that the game is now over and who has won
		
		System.out.println("*** GAME OVER ***");
		System.out.println("Player [ " + winner.getName() + " ] has won!");
	}
}
