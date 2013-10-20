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

public class BoardDesign implements Remote, Serializable {
	private static final long serialVersionUID = 1L;

	public final int gridSize;
	private char blackCorner;
	private String gameType;

	// copy constructor
	public BoardDesign(BoardDesign copy) throws RemoteException {
		this.gridSize = copy.getGridSize();
		this.blackCorner = copy.getBlackCorner();
	}

	// regular constructor
	public BoardDesign(String gameType) throws Exception {
		this.gameType = gameType;
		if (gameType.equalsIgnoreCase("British")
				|| gameType.equalsIgnoreCase("American")) {
			this.gridSize = 8;
			blackCorner = Piece.WHITE;
		}
		else if (gameType.equalsIgnoreCase("International")) {
			this.gridSize = 10;
			blackCorner = Piece.BLACK;
		}
		else if (gameType.equalsIgnoreCase("Canadian")) {
			this.gridSize = 12;
			blackCorner = Piece.WHITE;
		}
		else{
			throw new Exception("Unkonwn Game type!");
		}
	}

	// getter for grid size
	public int getGridSize() throws RemoteException {
		return gridSize;
	}


	// getter for black corner
	public char getBlackCorner() throws RemoteException {
		return blackCorner;
	}

	// setter for black corner
	public void setBlackCorner(char blackCorner) throws RemoteException {
		this.blackCorner = blackCorner;
	}

	// getter for game type
	public String getGameType() {
		return gameType;
	}

	// setter for game type
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardDesign)) {
			return false;
		}
		BoardDesign newObj = (BoardDesign) obj;
		try {
			return newObj.getBlackCorner() == this.getBlackCorner()
					&& newObj.getGameType().equals(this.getGameType())
					&& newObj.getGridSize() == this.getGridSize();
		} catch (RemoteException e) {
			return false;
		}
	}

}
