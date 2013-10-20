/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 */

package checkers;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;

public class Board implements Remote, Serializable {
	private static final long serialVersionUID = 1L;

	private BoardDesign theBoard;
	private Piece[] piecePlacement;

	// Copy constructor
	public Board(Board copy) throws RemoteException {
		this.theBoard = new BoardDesign(copy.getTheBoard());
		this.piecePlacement = new Piece[copy.getPiecePlacement().length];

		for (int i = 0; i < copy.getPiecePlacement().length; i++) {
			this.piecePlacement[i] = new Piece(copy.getPiecePlacement()[i]);
		}

	}

	// regular constructor
	public Board(BoardDesign theBoard, Piece[] piecePlacement) throws RemoteException {
		this.theBoard = theBoard;
		this.piecePlacement = piecePlacement;
	}

	// getter for the board
	public BoardDesign getTheBoard() throws RemoteException {
		return theBoard;
	}

	// setter for the board
	public void setTheBoard(BoardDesign theBoard) throws Exception {
		if (theBoard == null) {
			throw new Exception("Wrong value");
		}
		this.theBoard = theBoard;
	}

	// getter for piece placement
	public Piece[] getPiecePlacement() throws RemoteException {
		return piecePlacement;
	}

	// setter for piecePlacement
	public void setPiecePlacement(Piece[] piecePlacement) throws Exception {
		if (piecePlacement == null) {
			throw new Exception("Wrong value");
		}
		this.piecePlacement = piecePlacement;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Board)) {
			return false;
		}
		Board newObj = (Board) obj;
		try {
			return newObj.getTheBoard().equals(this.getTheBoard())
					&& Arrays.equals(newObj.getPiecePlacement(),
							this.getPiecePlacement());
		} catch (RemoteException e) {
			return false;
		}
	}
}
