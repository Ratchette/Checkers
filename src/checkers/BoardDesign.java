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

public class BoardDesign implements Remote, Serializable{
	private static final long serialVersionUID = 1L;
	
	private int gridSize;
	private char blackCorner;
	private String gameType;

	public BoardDesign(BoardDesign copy)  throws RemoteException{
		this.gridSize = copy.getN();
		this.blackCorner = copy.getBlackCorner();
	}

	public BoardDesign(int gridSize, char blackCorner) throws RemoteException {
		this.gridSize = gridSize;
		this.blackCorner = blackCorner;
	}
	
	public int getGridSize() throws RemoteException {
		return gridSize;
	}

	public void setGridSize(int gridSize) throws RemoteException {
		this.gridSize = gridSize;
	}
	
	public char getBlackCorner() throws RemoteException {
		return blackCorner;
	}
	
	public void setBlackCorner(char blackCorner) throws RemoteException {
		this.blackCorner = blackCorner;
	}

	public String getGameType() {
		return gameType;
	}

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
			return newObj.getBlackCorner() == this.getBlackCorner() && newObj.getGameType().equals(this.getGameType())
					&& newObj.getN() == this.getN();
		} catch (RemoteException e) {
			return false;
		}
	}
	
}
