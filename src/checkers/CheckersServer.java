/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;
/*
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CheckersServer implements Server{
	private static final String serverName = "Checkers_Server";
	
	private Registry registry;
//	private Compute engine;
//	private Compute stub;
	
	private GameInfo currentGame;
	private GameInfo stub;
	
	private ArrayList<Client> players;
	private ArrayList<Client> observers;
	private ArrayList<GameDesign> suggestions;
	private Boolean gameInProgress;
	
	
	CheckersServer(){
		// initialize variables
	}
	
	public void startup() throws Exception{
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
//		this.engine = new ComputeEngine();
//		
//		this.stub = (Compute) UnicastRemoteObject.exportObject(engine, 0); 
		
		this.players = new ArrayList<>();
		this.observers = new ArrayList<>();
		this.suggestions = new ArrayList<>();
		this.gameInProgress = false;
		
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

    public GameInfo gameInfo() throws RemoteException{
    	// TODO INCOMPLETE METHOD
    	return new GameInfo(currentGame);
    }

    Object playGame(Client requestingClient){
        if(gameInProgress){
        	// A game is already being played, so reject all clients until the game ends
        	return "Reject:Game in Progress";
        }
        else if(this.suggestions.size() == 0){
        	// add the first client to the clients list. Wineberg's use case [Scenario 1] 
        	players.add(requestingClient);
        	return "No Players";
        }
        else{
        	// One client has already suggested a game. Wineberg's use case [Scenario 2] 
        	players.add(requestingClient);
        	// TODO requires some complex logic
        }
    }

    public Object watch(Client requestingClient){
    	// See Wineberg Use Case [Scenario 0.2]
        if(!gameInProgress && suggestions.size() == 0){
        	return "No Game Being Played: No Players";
        }
        
        else if(suggestions.size() > 0){
        	return "No Game Being Played: Waiting for Second Player";
        }
        
        // Below here means that there is a game in progress
        else if(players.contains(requestingClient)){
        	return "Player: Already Observing Game";
        }
        
        else { 
        	observers.add(requestingClient);
        	return new GameInfo(currentGame);
        }
    }

    public String doNotWatch(Client requestingClient){
    	if(players.contains(requestingClient)){
    		return "Rejected: Players Must Watch";
    	}
    	else if(!observers.contains(requestingClient)){
    		
    	}
    }
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
*/