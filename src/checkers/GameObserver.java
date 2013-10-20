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

public interface GameObserver extends Remote{
	void receiveMove(Move playersMove) throws RemoteException;

	void playerResigned(PlayerInfo aPlayer, char code, String aMessage) throws RemoteException;

	void gameOver(PlayerInfo winner) throws RemoteException;
}
