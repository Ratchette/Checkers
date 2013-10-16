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

public interface Player extends Remote{
	public Object considerGame (GameDesign aGame) throws RemoteException;
	public void startGame (PlayerInfo player) throws RemoteException;
	
	public String move (Move playersMove) throws RemoteException;
	
	public void opponentResigned (PlayerInfo player, char code, String message) throws RemoteException;
	public String resign () throws RemoteException;
	public void youWin () throws RemoteException;
}