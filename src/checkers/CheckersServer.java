/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CheckersServer extends UnicastRemoteObject implements Server{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Player> players;
	private ArrayList<GameObserver> observers;
	private ArrayList<GameDesign> suggestions;
	
	private Boolean gameInProgress;
	private GameInfo currentGame;
	
	// constructor
	CheckersServer() throws RemoteException{
		this.players = new ArrayList<Player>();
		this.observers = new ArrayList<GameObserver>();
		this.suggestions = new ArrayList<GameDesign>();
		
		this.gameInProgress = true;
		
		// TODO FIXTHIS
	}
	
	// initialize the server
	public void startup() throws Exception{
	// THIS WAS THE SOURCE OF ALL OF MY PROBLEMS!
	//		if (System.getSecurityManager() == null) 
	//		System.setSecurityManager(new SecurityManager());
		
		try{
			LocateRegistry.createRegistry(4150);
			Naming.rebind("//localhost:4150/" + Server.serverName, this);
			
			System.out.println("Checkers Server Ready");
		}
		
		catch(Exception e){
			System.err.println("Checkers server startup exception:");
			e.printStackTrace();
		}
	}

	// stop the server
    void shutdown() throws Exception{
    	//Naming.unbind("//localhost:4150/" + Server.serverName);
    	System.out.println("Checkers server has shutdown");
    	
    	// FIXME - the server does not shut down yet XP (I implemented the below stupidity to compensate for it)
    	System.exit(0);
    }

    // getter for gameInfo
    public GameInfo gameInfo() throws RemoteException{
    	// TODO INCOMPLETE METHOD
    	return new GameInfo(currentGame);
    }

    @Override
    public Object playGame(Player requestingClient){
		if(gameInProgress){
			// A game is already being played, so reject all clients until the game ends
			return "Reject:Game in Progress";
		}
		
		else if(suggestions.size() == 0){
			// add the first client to the clients list. Wineberg's use case [Scenario 1] 
			players.add(requestingClient);
			return "No Players";
		}
		
		else{
			// TODO requires some complex logic
			// One client has already suggested a game. Wineberg's use case [Scenario 2] 
			players.add(requestingClient);
			
		}
		
		return null; //FIXME Complete this function
	}

	@Override
	public Object watch(GameObserver requestingClient) throws RemoteException {	
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus("Request to watch", playerName);
		
		if(!gameInProgress){
			if(suggestions.size() == 0){
				printStatus("Reject watch request: No game in progress", playerName);
				return "No Game Being Played: No Players";
			}
			else{
				printStatus("Reject watch request: Waiting for another player", playerName);
				return "No Game Being Played: Waiting for Second Player";
			}
		}
		
		if(players.contains(requestingClient)){
			printStatus("Reject watch request: Player is already on observer list", playerName);
			return "Player: Already Observing Game";
		}
		
		if(!observers.contains(requestingClient))
			observers.add(requestingClient);
		
		printStatus("Successfully watching", playerName);
		System.out.println();
		return currentGame;
	}

	@Override
	public String doNotWatch(GameObserver requestingClient) throws RemoteException {
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus("Request to stop watching", requestingClient.getPlayerInfo().getName());
		
		if(players.contains(requestingClient)){
			printStatus("Reject stop watching request: Players must watch the game", playerName);
			return "Rejected: Players Must Watch";
		}
		
		if(!observers.contains(requestingClient)){
			printStatus("Reject stop watching request: Client did not previously request to watch game", playerName);
			return  "Rejected: Already Not Watching";
		}
		
		observers.remove(requestingClient);
		printStatus("Successfully stopped watching", playerName);
		System.out.println();
		return "Success";
	}

	@Override
	public Object considerGame(Player requestingClient, GameDesign aGame)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void acceptGame(Player aPlayer) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Move playersMove) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resign(Player aPlayer, char code, String reason)
			throws RemoteException {
		// TODO Auto-generated method stub

	}
	
	public void printStatus(String message, String requester){
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println("\t" + date + " >> " + message + " by [ " + requester + " ]");
	}
    
//
//    public Object watch(Client requestingClient) throws RemoteException{
////    	// See Wineberg Use Case [Scenario 0.2]
////        if(!gameInProgress && suggestions.size() == 0){
////        	return "No Game Being Played: No Players";
////        }
////        
////        else if(suggestions.size() > 0){
////        	return "No Game Being Played: Waiting for Second Player";
////        }
////        
////        // Below here means that there is a game in progress
////        else if(players.contains(requestingClient)){
////        	return "Player: Already Observing Game";
////        }
////        
////        else { 
////        	observers.add(requestingClient);
////        	return new GameInfo(currentGame);
////        }
//    	
//    	return null;
//    }
//
//    public String doNotWatch(Client requestingClient){
////    	if(players.contains(requestingClient)){
////    		return "Rejected: Players Must Watch";
////    	}
//////    	else if(!observers.contains(requestingClient)){
//////    		
//////    	}
//    	return null; // FIXME Implement this function
//    }
////        returns "Accept" if success or an error message such as
////                "Cannot be removed: not on Observers list" 
//
//    
//    public Object considerGame(Client requestingClient, GameDesign aGame){
////      Object could be String or GameDesign
//		return null;
//	}
//
//
//    public void acceptGame(Player aPlayer){
//    	
//    }
//
//    public void move(Move playersMove){
//    	
//    }
//
//    public void resign(Player aPlayer, char code, String reason){
//    	
//    }
//         // code = 'l' or 'L' for "lose"
//         //     - i.e. player either:
//         //           1) agrees that move that took the player's last piece was legal
//         //           2) believes that they have no moves available
//         // code = 'r' or 'R' for "rules disagreement" 
//         //     - i.e. "move not legal by my rules"
//         // code = 'o' or 'O' for "other reason
//         //     - e.g. has to stop playing to get an assignment finished  
    
    public String testMethod(String message) throws RemoteException{
    	System.out.println("I got the message [ " + message + " ]");
    	return "Server says HEY THERE ";
    }
	
    public static void main(String[] args){
		CheckersServer server;
		
		try{
			server = new CheckersServer();
			
			// FIXME - Make the program find its own IP (print the IP of the machine that you have run the server on)
			//			Then print it to the command line
			server.startup();
			
			Scanner reader = new Scanner(System.in);
			reader.nextLine();
//			
//			// FIXME
//			GameObserver client = server.observers.get(0);
//			client.gameOver(new PlayerInfo("Player2"));
//			
//			reader.nextLine();
//			reader.close();
			
			server.shutdown();
		}
		catch (Exception e){
			System.out.println("An error occured?");
			e.printStackTrace();
		}
    }
}
