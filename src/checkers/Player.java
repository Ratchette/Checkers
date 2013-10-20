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
	Object considerGame(GameDesign aGame) throws RemoteException; 
	//The return Object = {String, GameDesign}

void startGame() throws RemoteException;

String move(Move playersMove) throws RemoteException;
    //The return String = (“Accept”, “Invalid”);

void playerResigned(PlayerInfo aPlayer, char code, String aMessage) throws RemoteException;
     //  for code values, see resign() under class CheckersServer)

void youWin() throws RemoteException;
     // now redundant since the winning player will get the message 
     // playerResigned with an 'l' or 'L' code
}