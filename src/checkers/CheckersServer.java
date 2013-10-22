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
	private ArrayList<GameInfo> suggestions;
	
	private Boolean gameInProgress;
	private GameInfo currentGame;
	
	// constructor
	CheckersServer() throws RemoteException{
		this.players = new ArrayList<Player>();
		this.observers = new ArrayList<GameObserver>();
		this.suggestions = new ArrayList<GameInfo>();
		
		this.gameInProgress = false;
		this.currentGame = null;
		
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

	// End current game of checkers 
	private void restart(){
		this.players = new ArrayList<Player>();
		this.observers = new ArrayList<GameObserver>();
		this.suggestions = new ArrayList<GameInfo>();
		
		this.gameInProgress = false;
		
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

    public Object gameInfo() throws RemoteException{
		printStatus("UNKNOWN", "Request to view Game Info");
		
		if(gameInProgress){
			printStatus("UNKNOWN", "Sent current game information\n");
			return new GameInfo(currentGame);
		}
		
		if(suggestions.size() == 0){
			printStatus("UNKNOWN", "No game suggestions yet\n");
			return "No Game Being Played: No Players";
		}
		
		printStatus("UNKNOWN", "Returned first suggestion: game " + currentGame.getCurrentBoard().getTheBoard().getGameType()
				+ " suggested by [ " + currentGame.getPlayer1().getName()+ "\n");
		
    	return new GameInfo(suggestions.get(0));
    }
	
    
 // Create GameInfo object that contains GameBoard, PlayerInfo p1, PlayerInfo p2, current turn and current round
	/*************************************************************************
	 * 								PLAYERS
	 *************************************************************************/
	
	@Override
    public Object playGame(Player requestingClient) throws RemoteException{
    	String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Request to play game");
    	
		if(gameInProgress){
			printStatus(playerName, "Reject play request: A game is already in progress\n");
			return "Reject:Game in Progress";
		}
		
		if(currentGame == null){
			// if no suggestions exist yet
			if(suggestions.size() == 0){
				printStatus(playerName, "Reject play request: There has been no suggested game yet\n");
				return "No Players";
			}
			
			// if a suggestion has been made, but there is no disagreement yet [Scenario 1.2]
			currentGame = suggestions.remove(0);
			printStatus(playerName, "Game offered: [ " + currentGame.getPlayer1() + " ] suggested " 
					+ currentGame.getCurrentBoard().getTheBoard().getGameType() + " checkers \n");

			return new GameDesign(currentGame.getTheGame());
		}
		
		// if a 3+ player comes along while the second is deliberating [Scenario ????]
		printStatus(playerName, " Game offered: [ " + currentGame.getPlayer1() + " ] suggested " 
				+ currentGame.getCurrentBoard().getTheBoard().getGameType() + " checkers \n");

		return new GameDesign(currentGame.getTheGame());
		
		
	}
	
	// FIXME - will only work for a maximum of two clients at a time ...
	@Override
	public Object considerGame(Player requestingClient, GameDesign aGame) throws RemoteException {
		String response = "reject";
		
		String playerName = requestingClient.getPlayerInfo().getName();
		printStatus(playerName, "Suggested game: " + aGame.getGameBoardDesign().getGameType());
		
		if(gameInProgress){
			printStatus(playerName, "Reject suggestion: There is already a game in progress\n");
			return "Reject: Game in progress";
		}
		
		if(currentGame == null){
			// nobody has suggested a game yet
			if(suggestions.size() == 0){
				players.add(requestingClient);
				suggestions.add(new GameInfo (aGame, requestingClient.getPlayerInfo(), new PlayerInfo("None")));
				
				printStatus(playerName, "Accepted suggestion: Waiting for an opponent to agree\n");
				return "Accept";
			} 
			
			// someone already suggested a game, but it has not been offered to anyone yet
			currentGame = suggestions.remove(0);
			printStatus(playerName, "Game offered: [ " + currentGame.getPlayer1().getName() + " ] suggested " 
					+ currentGame.getCurrentBoard().getTheBoard().getGameType() + " checkers \n");

			return new GameDesign(currentGame.getTheGame());
		}
		
		// (P2 suggests an alternate game to P1's suggestion) OR (P3 comes along *** which is technically a bug ... ***) // FIXME
		printStatus(playerName, "Counter Suggestion Recieved: " + aGame.getGameBoardDesign().getGameType() + " checkers");
//		return "Game is being considered";
		
		Player p1 = players.get(0);
		for(Player p : players)
			if(p.getPlayerInfo().equals(currentGame.getPlayer1()))
				p1 = p;

		// FIXME - client will send back decline if they don't want to play
		response = p1.considerGame(aGame);
		playerName = p1.getPlayerInfo().getName();
		
		// FIXME - now you can only ever accept the first client's suggestion
		if(response.equalsIgnoreCase("reject")){
			printStatus(playerName, "Counter Suggestion Rejected: " + aGame.getGameBoardDesign().getGameType() + " checkers\n");
			currentGame = null;
			return "Suggestion rejected: Initiating player not interested";
		}
		
		players.add(requestingClient);
		currentGame = new GameInfo(new GameDesign(aGame), requestingClient.getPlayerInfo(), p1.getPlayerInfo());
		
		if(response.equalsIgnoreCase("AcceptGame") && !gameInProgress){
			printStatus(playerName, "Counter Suggestion Accepted: " + aGame.getGameBoardDesign().getGameType() + " checkers\n");
			this.acceptGame(p1);
			return "Player Accepted: [ "+ aGame.getGameBoardDesign().getGameType() + " ] from [ "
					+ requestingClient.getPlayerInfo().getName() + " ]";
		}
		
		// Player 1 sent back garbage OR a new game started while p1 was deciding
		printStatus(playerName, "Counter Suggestion Rejected: " + response + "\n");
		return "The other player sent me back garbage. Please try again later";
	}

	@Override
	public void acceptGame(Player aPlayer) throws RemoteException {
		PlayerInfo p1 = currentGame.getPlayer1();
		PlayerInfo p2 = aPlayer.getPlayerInfo();
		
		printStatus(p2.getName(), "Accepted Game: " + currentGame.getCurrentBoard().getTheBoard().getGameType()
				+ " between [ " + p1.getName() + " ] and [ " + p2.getName() + " ]");
		
		currentGame.setPlayer2(aPlayer.getPlayerInfo());
		gameInProgress = true;
		
		
		for(Player p : players){
			// tell p1 that it is their turn
			if(p.getPlayerInfo().equals(currentGame.getPlayer1())){
				p.startGame();
				printStatus("Server", "Start Game: [ " + p1.getName() + " ] informed to make first move");
			}
			
			// remove any players that are not part of the current game
			else if(!p.getPlayerInfo().equals(currentGame.getPlayer2())){
				players.remove(p);
				printStatus("Server", "Removed Inactive Player: [ " + p.getPlayerInfo().getName() + " ] is not playing this round");
			}
		}
		
		System.out.println();
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
			printStatus(playerName, "Rejected Resignation: The requester is not a player\n");
			return;
		}
		
		printStatus(playerName, "Resignation Accepted: [ " + playerName + "] has lost.\n" );
		
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
	 * 							Printing and main methods
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
			
			
			// FIXME to kill the server enter anything into the terminal
			Scanner reader = new Scanner(System.in);
			reader.nextLine();
			
			server.shutdown();
		}
		catch (Exception e){
			System.out.println("An error occured?");
			e.printStackTrace();
		}
    }
}
