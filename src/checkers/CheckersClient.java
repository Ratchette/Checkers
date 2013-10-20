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

import javax.swing.JOptionPane;

public class CheckersClient implements GameObserver, Player{
	public static final int PLAYER 		= 0;
	public static final int OBSERVER 	= 1;
	
	private CheckersObserver observer;
	private CheckersPlayer player;
	
	private GameInfo myGame;
	
	// constructor
	public CheckersClient() throws Exception{
		int isPlayer = choosePlayStyle();
		
		if(isPlayer == 0){
			setObserver(null);
			
			int gameType = chooseGameType();
			player = new CheckersPlayer(gameType);
			//player.startGame();
			
			Gui window = new Gui(player.getBoard());
			window.drawBoard(player.getBoard());
			
		}
		else{
			player = null;
			setObserver(new CheckersObserver());
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
				options[2]);
		
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
	
	// getter for game observer
	public GameObserver getObserver() {
		return observer;
	}

	// setter for game observer
	public void setObserver(CheckersObserver observer) {
		this.observer = observer;
	}

	// getter for gameInfo
	public GameInfo getGame() throws RemoteException{
		return new GameInfo(myGame);
	}

	@Override
	public Object considerGame(GameDesign aGame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void youWin() {
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
	public void gameOver(PlayerInfo winner) {
		// TODO Auto-generated method stub
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
				&& this.getObserver().equals(otherClient.getObserver())
				&& this.myGame.equals(otherClient.getGame());
		}
		catch(Exception e){
			System.out.println("Excpetion Occured");
			e.printStackTrace();
			return false;
		}
		
	}

	
	public static void main(String[] args) {
		Server server;		// The client does not need to know the name of the class that imeplements the server interface
		CheckersClient client;
		
		//TODO: Implement connection to server
		try {
			// setup connection to server
			String hostname = Server.granger;
			
			if(args.length > 0)
				hostname = args[0];
			
			server = (Server) Naming.lookup("//" + hostname + "/" + Server.serverName);
			client = new CheckersClient();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
