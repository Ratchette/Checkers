package checkers;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

public class CheckersPlayer extends UnicastRemoteObject implements Player {
	private static final long serialVersionUID = 1L;
	
	private Server server;
	private GameInfo myGame;
//	private Board theBoard;
	private PlayerInfo myID;
	Gui display;

	public CheckersPlayer(Server server, PlayerInfo myName) throws RemoteException{
		this.server = server;
		this.myID = myName;
		this.display = null;
	}
	
	
	
	private int chooseGameType(){
		// ensure that the order the game types are displayed in matches their declarations above
		Object[] options = {"British", "American", "International", "Canadian", "Anti-Checkers", "Quit"};
		int response;
		
		response = JOptionPane.showOptionDialog(null,
				"Please choose a game style",
				"CIS 4150 - Checkers Clinet",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		
		if(response == 5)
			System.exit(0);
		
		return response;
	}
	
//	public CheckersPlayer(int gameType, PlayerInfo myName){
//		myID = myName;
//		
//		try {
//			theBoard = new Board(gameType);
//			// TODO - needs more stuff
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//		}
//	}
	

	public GameInfo getMyGame() {
		return myGame;
	}

	public void setMyGame(GameInfo myGame) {
		this.myGame = myGame;
	}
	
	@Override
	public void startGame() throws RemoteException {
//		try {
//			this.display = new Gui(this.myGame.getCurrentBoard(), this.myID);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public String move(Move playersMove) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public PlayerInfo getPlayerInfo() throws RemoteException {
		return new PlayerInfo(myID);
	}




}
