/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class CheckersClient implements Remote{
	private GameObserver observer;
	private Player player;
	private GameInfo myGame;
	
	/**
	 * @param args
	 */
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
	
	public static void main(String[] args) {
		// TODO start GUI
		
		
		System.out.println("It works!");

	}

}
