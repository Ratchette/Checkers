package checkers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

/**
 * This is the class that houses the game of checkers.
 * This class does NOT communicate with the server. It passes messages between the Board, 
 * the GUI. It calls methods from the client class instead. 
 * 
 * To send a message please pass in a checkersClient object, then create a method within
 * CheckersClient to relay the message to the server for the player 
 */
public class CheckersPlayer extends UnicastRemoteObject implements Player {
	private static final long serialVersionUID = 1L;

	private GameInfo myGame;
	private PlayerInfo myID;
	public int myTurn;
	public char myColour;
	private Gui display;
	private CheckersClient client;

	public CheckersPlayer(PlayerInfo myName) throws RemoteException {
		this.myID = myName;
	}

	
	public GameInfo getMyGame() {
		return myGame;
	}

	public void setMyGame(GameInfo myGame) {
		this.myGame = myGame;
	}
	

	@Override
	public void startGame() throws RemoteException {
		myGame.setCurrentRound(1);
		myGame.setPlayerTurn(1);
		
		// FIXME Each game type has a different colour piece going first
		// incorperate the data from our design document into here
		if(myGame.getPlayer1().equals(this.myID)){
			myColour = Piece.WHITE;
			myTurn = PlayerInfo.PLAYER1;
			display.changeTurn();
		}
		else{
			myColour = Piece.BLACK;
			myTurn = PlayerInfo.PLAYER2;
		}
	}
	
	public void startGame(Gui window, CheckersClient c) throws RemoteException {
		client = c;
		display = window;
		myGame.setCurrentRound(1);
		myGame.setPlayerTurn(1);
		
		// FIXME Each game type has a different colour piece going first
		// incorperate the data from our design document into here
		if(myGame.getPlayer1().equals(this.myID)){
			myColour = Piece.WHITE;
			myTurn = PlayerInfo.PLAYER1;
			display.changeTurn();
		}
		else{
			myColour = Piece.BLACK;
			myTurn = PlayerInfo.PLAYER2;
		}
	}

	
	public String sendMove(Move playersMove) throws RemoteException {
		// FIXME does this function need to return anything? if so why a string?
		client.sendMove(playersMove);
		return null;
	}
	
	
	@Override
	public String move(Move playersMove) throws RemoteException {
		this.myGame.makeMove(playersMove);
		this.display.changeTurn();
		try {
			this.display.drawBoard(myGame.getCurrentBoard());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage) throws RemoteException {
		System.out.println("You Win!");
		JOptionPane.showMessageDialog(null, "Your Opponent Quit: " + aMessage, "You Win!",
			    JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	@Override
	public PlayerInfo getPlayerInfo() throws RemoteException {
		return new PlayerInfo(myID);
	}
	
	public boolean isMyTurn(){
		return this.myTurn == this.myGame.getPlayerTurn();
	}

	public void sendResignation(){
		client.sendResignation();
	}
	
	@Override
	public boolean equals (Object other){
		if(other == null){
			return false;
		}

		if (!(other instanceof CheckersPlayer)) {
			return false;
			
		}
		CheckersPlayer player = (CheckersPlayer) other;
		try {
			return this.myID.equals(player.getPlayerInfo());
		} catch (RemoteException e1) {
			return false;
		}
	}
}
