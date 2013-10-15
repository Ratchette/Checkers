/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CheckersServer implements Server{
	private static final String serverName = "Checkers_Server";
	
	private Registry registry;
//	private Compute engine;
//	private Compute stub;
	
	private GameInfo currentGame;
	private GameInfo stub;
	
	// list of clients
	// game that each wants to play
	
	CheckersServer(){
		// initialize variables
	}
	
	public void startup() throws Exception{
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		this.engine = new ComputeEngine();
		
		this.stub = (Compute) UnicastRemoteObject.exportObject(engine, 0); 
		
		this.registry = LocateRegistry.getRegistry();
		this.registry.rebind(serverName, stub);
		
		System.out.println("Server bound");
			
//		} catch (Exception e) {	
//			System.err.println("ComputeEngine exception:");
//			e.printStackTrace();
//		}
	}

    void shutdown(){
    	this.registry.unbind(serverName);
    	UnicastRemoteObject.unexportObject(engine, true);
    }

    GameInfo gameInfo() throws RemoteException{
    	return currentGame.clone();
    }

    Object playGame(Client requestingClient) 
        return Object could be String or GameDesign

    Object watch(Client requestingClient)
        return Object could be String or GameInfo 

    String doNotWatch(Client requestingClient)
        returns "Accept" if success or an error message such as
                "Cannot be removed: not on Observers list" 

    Object considerGame(Client requestingClient, GameDesign aGame)
        Object could be String or GameDesign

    void acceptGame(Player aPlayer)

    void move(Move playersMove)

    void resign(Player aPlayer, char code, String reason)
         // code = 'l' or 'L' for "lose"
         //     - i.e. player either:
         //           1) agrees that move that took the player's last piece was legal
         //           2) believes that they have no moves available
         // code = 'r' or 'R' for "rules disagreement" 
         //     - i.e. "move not legal by my rules"
         // code = 'o' or 'O' for "other reason
         //     - e.g. has to stop playing to get an assignment finished  
}