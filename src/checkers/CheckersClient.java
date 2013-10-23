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
	
	private GameInfo myGame;
	
	
	/*************************************************************************
	 * 						Client startup and shutdown
	 *************************************************************************/
	public CheckersClient(Server server) throws Exception{
		int isPlayer = choosePlayStyle();
		
		// TODO - come up with a better way to create the PlayerIDs
		String date = new SimpleDateFormat("HH-mm-ss").format(new Date());
		myID = new PlayerInfo(InetAddress.getLocalHost().getHostName() + " @ " + date);
		
		if(isPlayer == 0){
			observer = null;
			player = new CheckersPlayer(server, myID);
		}
		else if(isPlayer == 1){
			player = null;
			observer = new CheckersObserver(server, myID);
		}
		else{
			printStatus("Chose to exit the program ");
			System.exit(0);
		}
		
		this.myGame = null;
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
	
	// getter for the equals portion of gameInfo
	private GameInfo getGame() throws RemoteException{
		return new GameInfo(myGame);
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
					
					//client.observer.setGame((GameInfo)response);
					// TODO - implement the option for the observer to deattach
				}
				
				else{
					
					// TESTING
					response = server.considerGame(client, new GameDesign(BoardDesign.BRITISH));
					
					if(response != null && response.getClass().equals(String.class)){
						System.out.println(response);
					}
					
					keyboard = new Scanner(System.in);
					keyboard.next();
					keyboard.close();
					
					
					// continue
					//server.acceptGame(client.player, (GameDesign) response);
					
				}
			} catch (Exception e) {e.printStackTrace();}
		}

	@Override
	public String considerGame(GameDesign aGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void youWin() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
