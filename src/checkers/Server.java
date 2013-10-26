package checkers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{
	public static final String serverName = "CheckersServer";
	public static final String granger = "131.104.48.15";
	
	Object gameInfo() throws RemoteException;
	
    Object watch(GameObserver requestingClient) throws RemoteException;
    String doNotWatch(GameObserver requestingClient) throws RemoteException;

    Object playGame(Player requestingClient) throws RemoteException;
    Object considerGame(Player requestingClient, GameDesign aGame) throws RemoteException;
    void acceptGame(Player aPlayer, GameDesign aGame) throws RemoteException;

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

