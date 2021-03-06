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
 * This class is used to indicate the properties (rules) of the game being played
 */
public class GameDesign implements Remote, Serializable{
	private static final long serialVersionUID = 1L;
	
	private Board initialBoard;
	private BoardDesign gameBoardDesign;
	private int gameCode;
	
	public GameDesign(int gameType) throws Exception{
		// FIXME for testing purposes only
		//initialBoard = new Board("testCaseBoards/sampleBoard.csv");
		
		initialBoard = new Board(gameType);
		gameBoardDesign = initialBoard.getBoardDesign();
		gameCode = -1; // FIXME - I have no idea what to do with this ...
	}
	
	public GameDesign(String filename) throws Exception{
		// FIXME for testing purposes only
		//initialBoard = new Board("testCaseBoards/sampleBoard.csv");
		
		initialBoard = new Board(filename);
		gameBoardDesign = initialBoard.getBoardDesign();
		gameCode = -1; // FIXME - I have no idea what to do with this ...
	}
	
	public GameDesign(GameDesign copy) throws RemoteException{
		initialBoard = new Board(copy.getInitialBoard());
		gameBoardDesign = initialBoard.getBoardDesign();
		gameCode = copy.getGameCode();
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
