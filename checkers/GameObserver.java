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
	
    public String move(Move playerMove)throws RemoteException;
	public String playerResigned(PlayerInfo aPlayer, char code, String aMessage)throws RemoteException;
	public void gameOver (PlayerInfo winner)throws RemoteException;
}
