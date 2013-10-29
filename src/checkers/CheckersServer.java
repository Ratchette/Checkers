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
	private ArrayList<GameInfo> requestedGames;
	
	private Boolean gameInProgress;
	private GameInfo currentGame;
	
	CheckersServer() throws RemoteException{
		this.players = new ArrayList<Player>();
		this.observers = new ArrayList<GameObserver>();
		this.requestedGames = new ArrayList<GameInfo>();
		
		this.gameInProgress = false;
		this.currentGame = null;
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
			
			printStatus("Server", "bound to port 4150 on localhost\n"); // FIXME print your local IP address instead
		}
		
		catch(Exception e){
			printStatus("Server", "CRITICAL ERROR : unable to startup rmi server\n");
			e.printStackTrace();
			shutdown();
		}
	}

	// End current game of checkers 
	private void restart(){
		this.players = new ArrayList<Player>();
		this.observers = new ArrayList<GameObserver>();
		this.requestedGames = new ArrayList<GameInfo>();
		
		this.gameInProgress = false;
		this.currentGame = null;
		
		printStatus("Server", "Server reset: Now accepting new players and clients\n");
	}
	
	// stop the server
    private void shutdown() throws Exception{
    	printStatus("Server", "Shutdown initiated\n");
    	
    	// FIXME - the server does not shut down yet XP (I implemented the below stupidity to compensate for it)
    	System.exit(0);
    }
    
    
	/*************************************************************************
	 * 								OBSERVERS
	 *************************************************************************/
    
	@Override
	public Object watch(GameObserver requestingClient) throws RemoteException {	
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Request to watch");
		
		if(!gameInProgress){
			if(requestedGames.size() == 0){
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
	public void doNotWatch(GameObserver requestingClient) throws RemoteException {
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Request to stop watching");
		
		if(players.contains(requestingClient)){
			printStatus(playerName, "Reject stop watching request: Players must watch the game\n");
//			return "Rejected: Players Must Watch";
		}
		
		if(!observers.contains(requestingClient)){
			printStatus(playerName, "Reject stop watching request: Client did not previously request to watch game\n");
//			return  "Rejected: Already Not Watching";
		}
		
		observers.remove(requestingClient);
		printStatus(playerName, "Successfully stopped watching\n");
//		return "Success";
	}

    public Object gameInfo() throws RemoteException{
		printStatus("UNKNOWN", "Request to view Game Info");
		
		if(gameInProgress){
			printStatus("UNKNOWN", "Sent current game information\n");
			return new GameInfo(currentGame);
		}
		
		if(requestedGames.size() == 0){
			printStatus("UNKNOWN", "No game suggestions yet\n");
			return "No Game Being Played: No Players";
		}
		
		printStatus("UNKNOWN", "Returned first suggestion: game " + currentGame.getCurrentBoard().getBoardDesign().getGameType()
				+ " suggested by [ " + currentGame.getPlayer1().getName()+ "\n");
		
    	return new GameInfo(requestedGames.get(0));
    }
	
    
	/*************************************************************************
	 * 								PLAYERS
	 *************************************************************************/
	
	@Override
	// Sends the player the game that is currently being considered
    public Object playGame(Player requestingClient) throws RemoteException{
    	String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Request to play game");
    	
		if(gameInProgress){
			printStatus(playerName, "Reject play request: A game is already in progress\n");
			return "Reject:Game in Progress";
		}

		// if no suggestions exist yet
		if(requestedGames.size() == 0){
			printStatus(playerName, "Reject play request: There has been no suggested game yet\n");
			return "Reject:No Players";
		}
		
		for(int i=0; i<requestedGames.size(); i++){
			
			// check that the requesting player is NOT the one that originally suggested the game
			if(!requestedGames.get(i).getPlayer1().equals(requestingClient.getPlayerInfo())){
				// move to the end of the Queue
				GameInfo temp = requestedGames.remove(i);
				temp.setPlayer2(requestingClient.getPlayerInfo());
				requestedGames.add(temp);
				
				printStatus(playerName, "Game offered: [ " + currentGame.getPlayer1().getName() + " ] suggested " 
						+ currentGame.getCurrentBoard().getBoardDesign().getGameType() + " checkers \n");
				return new GameDesign(temp.getTheGame());
			}
		}
		
		// the only suggested games were by this player
		printStatus(playerName, "Reject play request: There has been no suggested game yet\n");
		return "Reject:No Players";
	}
	
	@Override
	public Object considerGame(Player requestingClient, GameDesign aGame) throws RemoteException {
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Suggested game: " + aGame.getGameBoardDesign().getGameType() + " checkers");
		
		if(gameInProgress){
			printStatus(playerName, "Reject suggestion: There is already a game in progress\n");
			return "Reject: Game in progress";
		}
			
		// add suggestion to queue
		if(!players.contains(requestingClient))
			players.add(requestingClient);
		requestedGames.add(new GameInfo (aGame, requestingClient.getPlayerInfo(), new PlayerInfo("None")));

		
		// Find a game to offer them
		for(int i=0; i<requestedGames.size(); i++){
			
			// check that the requesting player is NOT the one that originally suggested the game
			if(!requestedGames.get(i).getPlayer1().equals(requestingClient.getPlayerInfo())){
				// move to the end of the Queue
				GameInfo temp = requestedGames.remove(i);
				temp.setPlayer2(requestingClient.getPlayerInfo());
				requestedGames.add(temp);
				
				printStatus(playerName, "Game offered: [ " + temp.getPlayer1().getName() + " ] suggested " 
						+ temp.getCurrentBoard().getBoardDesign().getGameType() + " checkers \n");
				return new GameDesign(temp.getTheGame());
			}
		}
		
		printStatus(playerName, "Accepted suggestion: Waiting for an opponent to agree\n");
		return "Accept";
	}

	@Override
	public void acceptGame(Player aPlayer, GameDesign aGame) throws RemoteException {
		ArrayList<Player> opponents;
		PlayerInfo p1;
		PlayerInfo p2 = aPlayer.getPlayerInfo();
		
		// Find the game that was accepted
		for(int i=0; i<requestedGames.size(); i++){
			if(requestedGames.get(i).getPlayer2().equals(aPlayer.getPlayerInfo()) 
					&& requestedGames.get(i).getTheGame().equals(aGame)){	// ensure that this is the game that was originally sent to the client
				
				// move to the end of the Queue
				currentGame = requestedGames.get(i);
				p1 = currentGame.getPlayer1();
				
				printStatus(p2.getName(), "Accepted Game: " + currentGame.getCurrentBoard().getBoardDesign().getGameType()
						+ " between [ " + p1.getName() + " ] and [ " + p2.getName() + " ]");
				
				break;
			}
		}
		
		if(currentGame == null){
			printStatus(p2.getName(), "Reject accept game: I did not recognize " + currentGame.getCurrentBoard().getBoardDesign().getGameType()
					+ " send to [ " + p2.getName() + " ]");
			return;
		}
		
		gameInProgress = true;
		this.currentGame.setCurrentRound(1);
		this.currentGame.setPlayerTurn(1);
		p1 = currentGame.getPlayer1();	// FIXME - redundant
		opponents = new ArrayList<Player>();
		opponents.add(aPlayer);
		
		for(Player p : players){
			// tell p1 that it is their turn
			if(p.getPlayerInfo().equals(currentGame.getPlayer1())){
				opponents.add(p);
				p.startGame();
				printStatus("Server", "Start Game: [ " + p1.getName() + " ] informed to make first move");
			}
			
			// remove any players that are not part of the current game
			else if(!p.getPlayerInfo().equals(currentGame.getPlayer2())){
				printStatus("Server", "Removed Inactive Player: [ " + p.getPlayerInfo().getName() + " ] is not playing this round");
			}
		}
		
		this.players = opponents;

		System.out.println();
	}

	@Override
	public void move(Move playersMove) throws RemoteException {
//		this.currentGame.makeMove(playersMove);
//		System.out.println("passed the block");
		// FIXME - I have no idea why the above two lines do not work ...
		
		for(Player p : players)
			p.move(playersMove);
		for(GameObserver o : observers)
			o.receiveMove(playersMove);
		
		// FIXME - Update internal GameInfo
		
		printStatus("Move", "Broadcasted move to all players and observers\n");
	}

	@Override
	public void resign(Player aPlayer, char code, String reason) throws RemoteException {
		String playerName = aPlayer.getPlayerInfo().getName();
		printStatus(playerName, "Resignation request");
		
		if(!players.contains(aPlayer)){
			printStatus(playerName, "Rejected Resignation: The requester is not a player\n");
			return;
		}
		
		printStatus(playerName, "Resignation Accepted: [ " + playerName + "] has lost.\n" );
		
		for(Player p : players){
			printStatus("Server", "Now sending quit to Player " + p.getPlayerInfo().getName());
			p.playerResigned(aPlayer.getPlayerInfo(), code, reason);
		}

		for(GameObserver o : observers){
			printStatus("Server", "Now sending quit to Observer " + o.getPlayerInfo().getName());
			o.playerResigned(aPlayer.getPlayerInfo(), code, reason);
		}
		
		this.restart();
	}
	
	
	/*************************************************************************
	 * 							Printing and main methods
	 *************************************************************************/
	
	public void printStatus(String requester, String message){
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
			
			// TODO Make the program find its own IP (print the IP of the machine that you have run the server on)
			//			Then print it to the command line
			server.startup();
			
			// TODO find a better way to determine the lifespan of the server
			Scanner reader = new Scanner(System.in);
			reader.nextLine();
			reader.close();
			
			server.shutdown();
		}
		catch (Exception e){
			System.out.println("An error occured?");
			e.printStackTrace();
		}
    }
}
