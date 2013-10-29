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
	PlayerInfo getPlayerInfo() throws RemoteException;

	void startGame() throws RemoteException;
	
	String move(Move playersMove) throws RemoteException;
	    //The return String = (Accept, Invalid);
	
	void playerResigned(PlayerInfo aPlayer, char code, String aMessage) throws RemoteException;
	     //  for code values, see resign() under class CheckersServer)\
	
	String sendMove(Move playersMove) throws RemoteException;
}