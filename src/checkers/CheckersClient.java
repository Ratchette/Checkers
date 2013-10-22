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

public class CheckersClient extends UnicastRemoteObject implements GameObserver, Player{
	private static final long serialVersionUID = 1L;
	public final PlayerInfo myID;
	
	public static final int PLAYER 		= 0;
	public static final int OBSERVER 	= 1;
	
	private CheckersObserver observer;
	private CheckersPlayer player;
	
	private GameInfo myGame;
	
	// constructor
	public CheckersClient() throws Exception{
		int isPlayer = choosePlayStyle();
		
		// FIXME - come up with a better way to create the PlayerIDs
		String date = new SimpleDateFormat("HH-mm-ss").format(new Date());
		myID = new PlayerInfo(InetAddress.getLocalHost().getHostName() + "@" + date);
		
		if(isPlayer == 0){
			observer = null;
			
			int gameType = chooseGameType();
			player = new CheckersPlayer(gameType, myID);
			
			Gui window = new Gui(player.getBoard());
			window.drawBoard(player.getBoard());
			
		}
		else{
			player = null;
			observer = new CheckersObserver(myID);
		}
		
		this.myGame = null;
	}
	
	private int choosePlayStyle(){
		Object[] options = {"Play", "Observe", "Quit"};
		int answer;
		
		answer = JOptionPane.showOptionDialog(null,
				"Welcome to Checkers!\nWould you like to play or observe a game?",
				"CIS 4150 - Checkers Clinet",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		
		if(answer == 2)
			System.exit(0);
		
		return answer;
	}
	
	private int chooseGameType(){
		// ensure that the order the game types are displayed in matches their declarations above
		Object[] options = {"British", "American", "International", "Canadian", "Anti-Checkers", "Quit"};
		int answer;
		
		answer = JOptionPane.showOptionDialog(null,
				"Please choose a game style",
				"CIS 4150 - Checkers Clinet",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		
		if(answer == 5)
			System.exit(0);
		
		return answer;
	}
	
	// getter for gameInfo
	public GameInfo getGame() throws RemoteException{
		return new GameInfo(myGame);
	}

	@Override
	public String considerGame(GameDesign aGame) {
		System.out.println("An alternate game was suggested!");
		return "NEITHER!";
	}

	@Override
	public void startGame() {
		System.out.println("It's my turn!");
		
	}

	@Override
	public void youWin() {
		System.out.println("You Win!");
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String move(Move playersMove) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void gameOver(PlayerInfo winner) throws RemoteException{
		if(player == null)
			observer.gameOver(winner);
		else
			player.youWin();
	}
	
	private void watchGame(Server server) throws RemoteException, InterruptedException{
		Object response;
		
		if(observer == null){
			// TODO ERROR - you tried to watch game after selecting player! 
			// exit the client
			System.out.println("You cannot watch a game if you are a player.");
			System.exit(0);
		}
		
		response = server.watch(this);
		
		if(response != null && response.getClass().equals(String.class)){
			// Dislpay message on GUI
			System.out.println(response);
		}
		else{
			// Display gameInfo on the GUI
		}
		
		Thread.sleep(2000);
		
		response = server.doNotWatch(this);
		if(response != null && response.getClass().equals(String.class)){
			// Dislpay message on GUI
			System.out.println(response);
		}
		else{
			// Display gameInfo on the GUI
		}
	}
	
	private void playGame(Server server) throws RemoteException{
		Object response;
		
		if(player == null){
			// TODO ERROR - you tried to watch game after selecting player! 
			// exit the client
			System.out.println("You cannot play a game if you are an observer.");
			System.exit(0);
		}
		
		response = server.playGame(this);
		
		if(response != null && response.getClass().equals(String.class)){
			// Dislpay message on GUI
			System.out.println(response);
		}
		else{
			// Display gameInfo on the GUI
		}
	}

	@Override
	public void receiveMove(Move playersMove) {
		// TODO Auto-generated method stub
		
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
				&& this.observer.equals(otherClient.observer)
				&& this.myGame.equals(otherClient.getGame());
		}
		catch(Exception e){
			System.out.println("Excpetion Occured");
			e.printStackTrace();
			return false;
		}
		
	}

	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);	// testing feature that waits for user input
		Server server;		// The client does not need to know the name of the class that imeplements the server interface
		CheckersClient client;
		
		//TODO: Implement connection to server
		try {
			// setup connection to server
			String hostname = Server.granger;
			
			if(args.length > 0)
				hostname = args[0];
			
			System.out.println("Connecting to " + hostname);
			
			server = (Server) Naming.lookup("//" + hostname + ":4150/" + Server.serverName);
			
			client = new CheckersClient();
			if(client.observer == null){
				Object response = server.considerGame(client, new GameDesign(BoardDesign.BRITISH));
				if(response != null && response.getClass().equals(String.class)){
					System.out.println(response);
				}
				
				keyboard.next();
				
				server.acceptGame(client);
			}
			else
				client.watchGame(server);
			
			
			// TODO create a shutdown method
			//System.exit(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public PlayerInfo getPlayerInfo() throws RemoteException {
		if(player == null)
			return observer.getPlayerInfo();
		else
			return player.getPlayerInfo();
	}
}
