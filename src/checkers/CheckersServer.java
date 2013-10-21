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
		
		this.gameInProgress = false;
		
		// TODO FIXTHIS
	}
	
	/*************************************************************************
	 * 						SERVER STARTUP AND SHUTDOWN
	 *************************************************************************/
	
	// initialize the server
	private void startup() throws Exception{
		printStatus("Server", "Startup initiated");
		
		try{
			LocateRegistry.createRegistry(4150);
			Naming.rebind("//localhost:4150/" + Server.serverName, this);
			
			printStatus("Server", "bound to port 4150 on localhost\n"); // FIXME - print your local IP address
		}
		
		catch(Exception e){
			printStatus("Server", "CRITICAL ERROR : unable to startup rmi server\n");
			e.printStackTrace();
			shutdown();
		}
	}

	private void restart(){
		this.players = new ArrayList<Player>();
		this.observers = new ArrayList<GameObserver>();
		this.suggestions = new ArrayList<GameDesign>();
		
		this.gameInProgress = false;
		
		printStatus("Server", "Server reset: Now accepting new players and clients\n");
	}
	
	// stop the server
    private void shutdown() throws Exception{
    	printStatus("Server", "Shutdown initiated\n");
    	
    	// FIXME - the server does not shut down yet XP (I implemented the below stupidity to compensate for it)
    	System.exit(0);
    }

    // getter for gameInfo
    public GameInfo gameInfo() throws RemoteException{
		printStatus("UNKNOWN", "Request to view Game Info\n");
		System.out.println();
		
    	return new GameInfo(currentGame);
    }

    
	/*************************************************************************
	 * 								OBSERVERS
	 *************************************************************************/
    
	@Override
	public Object watch(GameObserver requestingClient) throws RemoteException {	
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Request to watch");
		
		if(!gameInProgress){
			if(suggestions.size() == 0){
				printStatus(playerName, "Reject watch request: No game in progress\n");
				return "No Game Being Played: No Players";
			}
			else{
				printStatus(playerName, "Reject watch request: Waiting for another player\n");
				System.out.println();
				return "No Game Being Played: Waiting for Second Player";
			}
		}
		
		if(players.contains(requestingClient)){
			printStatus(playerName, "Reject watch request: Player is already on observer list\n");
			return "Player: Already Observing Game";
		}
		
		if(!observers.contains(requestingClient))
			observers.add(requestingClient);
		
		printStatus(playerName, "Successfully watching\n");
		
		return currentGame;
	}

	@Override
	public String doNotWatch(GameObserver requestingClient) throws RemoteException {
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Request to stop watching");
		
		if(players.contains(requestingClient)){
			printStatus(playerName, "Reject stop watching request: Players must watch the game\n");
			return "Rejected: Players Must Watch";
		}
		
		if(!observers.contains(requestingClient)){
			printStatus(playerName, "Reject stop watching request: Client did not previously request to watch game\n");
			return  "Rejected: Already Not Watching";
		}
		
		observers.remove(requestingClient);
		printStatus(playerName, "Successfully stopped watching\n");
		return "Success";
	}

	
	/*************************************************************************
	 * 								PLAYERS
	 *************************************************************************/
	
	@Override
    public Object playGame(Player requestingClient) throws RemoteException{
    	String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Request to play game");
    	
		if(gameInProgress){
			// A game is already being played, so reject all clients until the game ends
			printStatus(playerName, "Reject play request: A game is already in progress");
			System.out.println();
			
			return "Reject:Game in Progress";
		}
		
		if(suggestions.size() == 0){
			// add the first client to the clients list. Wineberg's use case [Scenario 1] 
			// TODO - implement this part
			printStatus(playerName, "Reject play request: First client added to players queue");
			System.out.println();
			
			players.add(requestingClient);
			return "No Players";
		}

		// One client has already suggested a game. Wineberg's use case [Scenario 2] 
		// TODO - implement this part
		players.add(requestingClient);
		
		return null; //FIXME Complete this function
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
	public void resign(Player aPlayer, char code, String reason) throws RemoteException {
		String playerName = aPlayer.getPlayerInfo().getName();
		printStatus(playerName, "Resignation request");
		
		if(!players.contains(aPlayer)){
			printStatus(playerName, "Rejected Resignation: The requester is not a player");
			return;
		}
		
		printStatus(playerName, "Resignation Accepted: [ " + playerName + "] has lost." );
		
		for(Player p : players){
			p.playerResigned(aPlayer.getPlayerInfo(), code, reason);
			
			if(!p.equals(aPlayer))	// FIXME - this line may be redundant
				p.youWin();
		}
		
		for(GameObserver o : observers){
			o.playerResigned(aPlayer.getPlayerInfo(), code, reason);
			o.gameOver(aPlayer.getPlayerInfo());	// FIXME - this line may be redundant
		}
		
		this.restart();
	}
	
	
	/*************************************************************************
	 * 							CUSTOM METHODS
	 *************************************************************************/
	
	public void printStatus( String requester, String message){
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println("\t" + date + " >> [ " + requester + " ] " + message);
	}
   
    // For making sure that RMI was implemented correctly. PLO!
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
