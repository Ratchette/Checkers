package checkers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{
    GameInfo gameInfo() throws RemoteException;
    Object playGame(Client requestingClient) throws RemoteException;
    
    Object watch(Client requestingClient) throws RemoteException;
    String doNotWatch(Client requestingClient) throws RemoteException;

    Object considerGame(Client requestingClient, GameDesign aGame) throws RemoteException;
    void acceptGame(Player aPlayer) throws RemoteException;
    
    void move(Move playersMove) throws RemoteException;
    void resign(Player aPlayer, char code, String reason) throws RemoteException;
}
