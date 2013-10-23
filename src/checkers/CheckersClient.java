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
 * NO functionality should take place in this class.
 * 
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 */
public class CheckersClient extends UnicastRemoteObject implements GameObserver, Player{
	private static final long serialVersionUID = 1L;
	
	public final PlayerInfo myID;
	
	public static final int PLAYER 		= 0;
	public static final int OBSERVER 	= 1;
	
	private CheckersObserver observer;
	private CheckersPlayer player;
	
	/*************************************************************************
	 * 						Client startup and shutdown
	 *************************************************************************/
	public CheckersClient(Server server) throws Exception{
		int isPlayer = choosePlayStyle();
		
		// TODO - come up with a better way to create the PlayerIDs
		String date = new SimpleDateFormat("HH-mm-ss").format(new Date());
		myID = new PlayerInfo(InetAddress.getLocalHost().getHostName() + " @ " + date);
		
		// FIXME change variable name
		if(isPlayer == 0){
			observer = null;
			player = new CheckersPlayer(server, myID);
			
			printStatus("Initialized a player");
		}
		else if(isPlayer == 1){
			player = null;
			observer = new CheckersObserver(server, myID);
			
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
	
	
	/*************************************************************************
	 * 							Player Methods
	 *************************************************************************/

	@Override
	public void startGame() throws RemoteException{
		if(player != null)
			player.startGame();
	}

	@Override
	public String move(Move playersMove) throws RemoteException {
		if(player != null)
			return player.move(playersMove);
		
		// Called a player's method to talk to the observer ... fixing it here 
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
				"Please choose a game style", "CIS 4150 - Checkers Clinet",
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
	
	public static Server connect(String[] args) throws Exception{
		String hostname = Server.granger;

		if (args.length > 0)
			hostname = args[0];

		printStatus("Connecting to " + Server.serverName + " on " + hostname);
		return (Server) Naming.lookup("//" + hostname + ":4150/" + Server.serverName);
	}
	
	
	public static void main(String[] args) throws RemoteException, Exception {
		Scanner keyboard;		// testing feature that waits for user input
		CheckersClient client;
		Server server;			// The client does not need to know the name of the class that implements the server interface
		Object response;
		int gameType;
		
		
		try {
			server = connect(args);
			printStatus("Now connected to server");
			
			client = new CheckersClient(server);
			if(client.observer != null){
				response = server.watch(client);
				
				if(response != null && response.getClass().equals(String.class)){
					printStatus("[ Server ] " + response);
					return;
				}
				
				client.observer.setGame((GameInfo)response);
				// TODO - implement the option for the observer to detach
			}
			
			else{
				
				// FIXME - change to be an exchange be part of a GUI?
				gameType = client.chooseGameType();
				client.player.setMyGame(new GameInfo(new GameDesign(gameType),
						client.player.getPlayerInfo(), new PlayerInfo("Opponent")));
				response = server.considerGame(client, client.player.getMyGame().getTheGame());
				
				if(response != null){
					if(response.getClass().equals(String.class))
						printStatus((String)response);
					else{
						client.player.setMyGame(new GameInfo (new GameDesign((GameDesign)response), 
								new PlayerInfo("Opponent"), client.player.getPlayerInfo()));
						
						server.acceptGame(client, (GameDesign)response);
						client.startGame();
					}
				}
//				
//				keyboard = new Scanner(System.in);
//				keyboard.nextLine();
//				keyboard.close();
			}
		} catch (Exception e) {e.printStackTrace();}

	}
}

////TESTING start
//client1 = new CheckersClient(server);
//client1.myID = new PlayerInfo("Player1");
//Thread.sleep(1000);
//client2 = new CheckersClient(server);
//client2.myID = new PlayerInfo("Player2");
//
//server.considerGame(client1, new GameDesign(BoardDesign.BRITISH));
//client2.myGame = new GameInfo((GameDesign) server.considerGame(client2, new GameDesign(BoardDesign.BRITISH)), 
//			client1.getPlayerInfo(), client2.getPlayerInfo());
//server.acceptGame(client2, client2.myGame.getTheGame());
//Thread.sleep(1000);
//client2.startGame();
//client1.myGame = new GameInfo(new GameDesign(BoardDesign.BRITISH), 
//		client1.getPlayerInfo(), client2.getPlayerInfo());
//
//client1.player.setMyGame(client1.myGame);
//client2.player.setMyGame(client2.myGame);
//client1.player.display = new Gui(client1.myGame.getCurrentBoard(), client1.myID, client1.player, server);
//client2.player.display = new Gui(client2.myGame.getCurrentBoard(), client2.myID, client2.player, server);
//
//// TESTING end

