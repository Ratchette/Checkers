/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The representation of the game in its entirety
 */
public class GameInfo implements Remote, Serializable{
	private static final long serialVersionUID = 1L;

	private GameDesign theGame;
	private Board currentBoard;
	private PlayerInfo player1;
	private PlayerInfo player2;
	private int playerTurn;
	private int currentRound;

	public GameInfo(GameDesign gameDesign, PlayerInfo player1, PlayerInfo player2) throws RemoteException {
		this.theGame = gameDesign;
		this.currentBoard = new Board(gameDesign.getInitialBoard());
		this.player1 = new PlayerInfo(player1);
		this.player2 = new PlayerInfo(player2);
		this.playerTurn = 0;
		this.currentRound = 0;
	}

	public GameInfo(GameInfo copy) throws RemoteException{
		this.theGame = new GameDesign(copy.theGame);
		this.currentBoard = new Board(copy.currentBoard);
		this.player1 = new PlayerInfo(copy.player1);
		this.player2 = new PlayerInfo(copy.player2);
		this.playerTurn = copy.playerTurn;
		this.currentRound = copy.currentRound;
	}

	
	public GameDesign getTheGame() throws RemoteException{
		return theGame;
	}

	public void setTheGame(GameDesign theGame) throws RemoteException{
		this.theGame = theGame;
	}
	

	public Board getCurrentBoard() throws RemoteException{
		return currentBoard;
	}

	public void setCurrentBoard(Board currentBoard) throws RemoteException{
		this.currentBoard = currentBoard;
	}
	

	public PlayerInfo getPlayer1() throws RemoteException{
		return player1;
	}

	public void setPlayer1(PlayerInfo player1) throws RemoteException{
		this.player1 = player1;
	}
	

	public PlayerInfo getPlayer2() throws RemoteException{
		return player2;
	}

	public void setPlayer2(PlayerInfo player2) throws RemoteException{
		this.player2 = player2;
	}
	

	public int getPlayerTurn(){
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) throws RemoteException{
		this.playerTurn = playerTurn;
	}
	
	public void changePlayerTurn() {
		this.playerTurn = ((playerTurn)%2)+1;
	}
	

	public int getCurrentRound() throws RemoteException{
		return currentRound;
	}

	public void setCurrentRound(int currentRound)throws RemoteException {
		this.currentRound = currentRound;
	}

	@Override
	public boolean equals (Object other){
		if(other == null){
			return false;
		}

		if (!(other instanceof GameInfo)) {
			return false;
			
		}
		GameInfo otherGame = (GameInfo) other;
		try {
			return otherGame.getCurrentBoard().equals(this.getCurrentBoard()) && otherGame.getCurrentRound() == this.getCurrentRound()
					&& otherGame.getPlayer1().equals(this.getPlayer1()) && otherGame.getPlayer2().equals(this.getPlayer2())
					&& otherGame.getPlayerTurn() == this.getPlayerTurn();
		} catch (RemoteException e1) {
			return false;
		}
	}
	
	public void makeMove(Move playersMove){
		try {
			// Move the piece
			SingleMove move = (SingleMove) playersMove;
			Piece tempPiece = new Piece(move.getPieceBeignMoved());
			Position pos = move.getEndPosition();
			move.getPieceBeignMoved().setPiecePosition(pos);

			if (move.getPieceBeignMoved().getColour() == Piece.WHITE
					&& pos.getY() == currentBoard.getBoardDesign().gridSize - 1) {
				move.getPieceBeignMoved().turnKing();
			}
			
			if (move.getPieceBeignMoved().getColour() == Piece.BLACK
					&& pos.getY() == 0) {
				move.getPieceBeignMoved().turnKing();
			}
			
			for (int i = 0; i < currentBoard.getPiecePlacement().length; i++) {
				Piece piece = currentBoard.getPiecePlacement()[i];
				if(piece != null && tempPiece.equals(piece)) {
					currentBoard.getPiecePlacement()[i] = move.getPieceBeignMoved();
				}
				Piece capturedPiece = move.getCapturedPiece();
				if(piece != null && capturedPiece != null) {
					if (capturedPiece.equals(piece)) {
						currentBoard.getPiecePlacement()[i] = null;
					}
				}
			}
			
			changePlayerTurn();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
