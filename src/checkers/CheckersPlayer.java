package checkers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
		}
		else{
			myColour = Piece.BLACK;
			myTurn = PlayerInfo.PLAYER2;
		}
	}

	
	public String sendMove(Move playersMove) throws RemoteException {
		///FIXME this should call client sendMove
		client.sendMove(playersMove);
		return null;
	}
	
	
	@Override
	public String move(Move playersMove) throws RemoteException {
		try {
			// Move the piece
			SingleMove move = (SingleMove) playersMove;
			Position pos = move.getEndPosition();
			move.getPieceBeignMoved().setPiecePosition(pos);

			// Check if the piece is now a king
			if (move.getPieceBeignMoved().getColour() == Piece.WHITE
					&& pos.getY() == 0) {
				move.getPieceBeignMoved().turnKing();
			}
			if (move.getPieceBeignMoved().getColour() == Piece.BLACK
					&& pos.getY() == myGame.getCurrentBoard().getBoardDesign().gridSize) {
				move.getPieceBeignMoved().turnKing();
			}
			
			myGame.changePlayerTurn();
			this.display.drawBoard(myGame.getCurrentBoard());

		} catch (Exception e) {
			e.printStackTrace();
		}
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
