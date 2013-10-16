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

public class GameDesign implements Remote, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int gameCode;
	private BoardDesign gameBoardDesign;
	private Board initialBoard;
	
	public GameDesign(int gameCode, BoardDesign gameBoardDesign, Board initialBoard) throws RemoteException{
		this.gameCode = gameCode;
		this.gameBoardDesign = gameBoardDesign;
		this.setInitialBoard(initialBoard);
	}
	
	public GameDesign(GameDesign copy) throws RemoteException{
		this.gameCode = copy.getGameCode();
		this.gameBoardDesign = new BoardDesign(copy.getGameBoardDesign());
		this.setInitialBoard(new Board(copy.getInitialBoard()));
	}
	
	public 	int getGameCode() throws RemoteException{
		return gameCode;
	}
	
	public void setGameCode(int gameCode) throws RemoteException {
		this.gameCode = gameCode;
	}	

	public BoardDesign getGameBoardDesign() throws RemoteException {
		return gameBoardDesign;
	}

	public void setGameBoardDesign(BoardDesign gameBoardDesign) throws RemoteException {
		this.gameBoardDesign = gameBoardDesign;
	}

	public Board getInitialBoard() throws RemoteException {
		return initialBoard;
	}

	public void setInitialBoard(Board initialBoard) throws RemoteException {
		this.initialBoard = initialBoard;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GameDesign)) {
			return false;
		}
		GameDesign newObj = (GameDesign) obj;
		
		try {
			return newObj.getGameBoardDesign().equals(this.getGameBoardDesign()) &&
					newObj.getGameCode() == this.getGameCode() && newObj.getInitialBoard().equals(this.getInitialBoard());
		} catch (RemoteException e) {
			return false;
		}
	}
}
