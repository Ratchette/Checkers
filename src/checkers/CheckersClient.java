/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 */

package checkers;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * A bridge for communication between the server and the instantiated player or observer
 * NO game functionality should take place within this class. Instead, it forwards all 
 * messages to the player or observer object within.
 */
public class CheckersClient extends UnicastRemoteObject implements GameObserver, Player{
	private static final long serialVersionUID = 1L;
	
	public final PlayerInfo myID;
	
	public static final int PLAYER 		= 0;
	public static final int OBSERVER 	= 1;
	
	private Server server;
	private CheckersPlayer player;
	private CheckersObserver observer;
	private static CheckersClient 	client;

	
	/*************************************************************************
	 * 						Client startup and shutdown
	 *************************************************************************/
	public CheckersClient() throws Exception{
		int playStyle = choosePlayStyle();
		
		// TODO - come up with a better way to create the PlayerIDs
		String date = new SimpleDateFormat("HH-mm-ss").format(new Date());
		myID = new PlayerInfo(InetAddress.getLocalHost().getHostName() + " @ " + date);
		
		if(playStyle == 0){
			observer = null;
			player = new CheckersPlayer(myID);
			printStatus("Initialized a player");
		}
		
		else if(playStyle == 1){
			player = null;
			observer = new CheckersObserver(myID);
			printStatus("Initialized an observer");
		}
		
		else{
			printStatus("Chose to exit the program");
			System.exit(0);
		}
	}
	
	private int choosePlayStyle(){
		Object[] options = {"Play", "Observe", "Quit"};
		int response;
		
		response = JOptionPane.showOptionDialog(null,
				"Welcome to Checkers!\nWould you like to play or observe a game?",
				"CIS 4150 - Checkers Clinet",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		
		if(response == 2)
			System.exit(0);
		
		return response;
	}
	
	public void connect(String hostname) throws Exception{
		if(hostname == null)
			hostname = Server.granger;

		server = (Server)Naming.lookup("//" + hostname + ":4150/" + Server.serverName);
		printStatus("Connected to " + Server.serverName + " on " + hostname);
		
		if(player != null)
			connectPlayer();
		else
			connectObserver();
	}
	
	// FIXME - this function is incomplete
	private void connectObserver() throws Exception{
		Object response;
		
		response = server.watch(this);
		
		if(response != null && response.getClass().equals(String.class)){
			printStatus("[ Server ] " + response);
			return;
		}
		
		observer.setMyGame(new GameInfo((GameInfo)server.gameInfo()));
		Gui gui = new Gui( ((Board)((GameInfo) server.gameInfo()).getCurrentBoard()), observer);
		observer.startGame(gui, this);
		// TODO - implement the option for the observer to detach
	}
	
	private void connectPlayer() throws Exception{
		Object response;
		int gameType;
		
		// Set the player's game to a temporary gameInfo until a game is chosen
		gameType = chooseGameType();
		player.setMyGame(new GameInfo(new GameDesign(gameType), player.getPlayerInfo(), new PlayerInfo("Opponent")));
		response = server.considerGame(this, player.getMyGame().getTheGame());
		
		if(response != null){
			if(response.getClass().equals(String.class))
				printStatus((String)response);
			
			// FIXME Change the logic here to allow the user to choose the type of game being played
			else{
//				player.setMyGame(new GameInfo (new GameDesign((GameDesign)response), 
//						new PlayerInfo("Opponent"), player.getPlayerInfo()));
				
				server.acceptGame(player, (GameDesign)response);
				startGame();
			}
		}
	}
	
	/*************************************************************************
	 * 							Player Methods
	 *************************************************************************/

	@Override
	public void startGame() throws RemoteException{
		Gui display;
		if(player == null)
			return;
		
		try {
			 display = new Gui( ((Board)((GameInfo) server.gameInfo()).getCurrentBoard()), player);
			 player.setMyGame(new GameInfo((GameInfo)server.gameInfo()));
		     player.startGame(display, client);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		
	}

	public String sendMove(Move playersMove) throws RemoteException {
		server.move(playersMove);
		return null;
	}
	

	@Override
	public String move(Move playersMove) throws RemoteException {
		if(player != null)
			return player.move(playersMove);
		else if(observer != null)
			observer.receiveMove(playersMove);
		
		return "Accept";
	}
	
	private int chooseGameType() {
		// ensure that the order the game types are displayed in matches their
		// declarations above
		Object[] options = { "British", "American", "International",
				"Canadian", "Anti-Checkers", "Quit" };
		int response;

		response = JOptionPane.showOptionDialog(null,
				"Please choose a game style", "CIS 4150 - Checkers Client",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);

		if (response == 5)
			System.exit(0);

		return response;
	}
	
	/*************************************************************************
	 * 						    Observer Methods
	 *************************************************************************/
	
	@Override
	public void receiveMove(Move playersMove) throws RemoteException {
		if(player != null)
			player.move(playersMove);
		else
			observer.receiveMove(playersMove);
	}
	
	public void stopWatching() {
		try {
			String response = server.doNotWatch(this);
			printStatus(response);
			System.exit(0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*************************************************************************
	 * 						    Common Methods
	 *************************************************************************/
	
	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage) throws RemoteException {
		if(player != null)
			player.playerResigned(aPlayer, code, aMessage);
		else
			observer.playerResigned(aPlayer, code, aMessage);
	}
	
	@Override
	public PlayerInfo getPlayerInfo() throws RemoteException {
		if(player == null)
			return observer.getPlayerInfo();
		else
			return player.getPlayerInfo();
	}
	
	@Override
	public boolean equals (Object other){
		if(other == null){
			return false;
		}
		
		if(other.getClass() != this.getClass()){
			return false;
		}
		
		CheckersClient otherClient = (CheckersClient) other;
		try{
			return this.player.equals(otherClient.player)
				&& this.observer.equals(otherClient.observer);
		}
		catch(Exception e){
			System.out.println("Excpetion Occured");
			e.printStackTrace();
			return false;
		}
		
	}

	/*************************************************************************
	 * 							    Main
	 *************************************************************************/
	
	public static void printStatus(String message){
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println("\t" + date + " >> " + message);
	}
	
	
	public static void main(String[] args) throws RemoteException, Exception {
		Scanner 		keyboard;
		String 			serverAddress;
		
		
		try {
			client = new CheckersClient();
			
			// connect to the server
			serverAddress = null;
			if(args.length > 0)
				serverAddress = args[0];
			client.connect(serverAddress);
				
				// Testing stuffs
//				keyboard = new Scanner(System.in);
//				keyboard.nextLine();
//				keyboard.close();
			
		} catch (Exception e) {e.printStackTrace();}

	}

}

