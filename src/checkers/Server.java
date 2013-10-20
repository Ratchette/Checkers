package checkers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{
	public static final String serverName = "CheckersServer";
	public static final String granger = "131.104.48.15";
	
	GameInfo gameInfo() throws RemoteException;

    Object playGame(CheckersClient requestingClient) throws RemoteException;
        //return Object could be String or GameDesign

    Object watch(CheckersClient requestingClient) throws RemoteException;
        //return Object could be String or GameInfo 

    String doNotWatch(CheckersClient requestingClient) throws RemoteException;
        //returns "Accept" if success or an error message such as
                //"Cannot be removed: not on Observers list" 

    Object considerGame(CheckersClient requestingClient, GameDesign aGame) throws RemoteException;
        //Object could be String or GameDesign

    void acceptGame(Player aPlayer) throws RemoteException;

    void move(Move playersMove) throws RemoteException;

    void resign(Player aPlayer, char code, String reason) throws RemoteException;
         // code = 'l' or 'L' for "lose"
         //     - i.e. player either:
         //           1) agrees that move that took the player's last piece was legal
         //           2) believes that they have no moves available
         // code = 'r' or 'R' for "rules disagreement" 
         //     - i.e. "move not legal by my rules"
         // code = 'o' or 'O' for "other reason
         //     - e.g. has to stop playing to get an assignment finished
    
    
    public String testMethod(String message) throws RemoteException;
}

