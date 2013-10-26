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
import java.util.ArrayList;
import java.util.Arrays;

public class Board implements Remote, Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final int NUM_PIECES = 36; // the maximum number of pieces in any game

	private BoardDesign boardDesign;
	private Piece[] piecePlacement;

	/**
	 * NOTES
	 * 
	 * The top of the board is y=0, so to move pieces down, ADD to y
	 */
	
	
	// regular constructor
	// FIXME
	public Board(int gameType) throws Exception {
		int currentPiece;
		int i, j;
		
		boardDesign = new BoardDesign(gameType);
		
		// initialize the pieces
		piecePlacement = new Piece[NUM_PIECES];
		for(i=0; i<piecePlacement.length; i++)
			piecePlacement[i] = null;
		
		currentPiece = 0;
		
		// create all of the black pieces
		for (i = 0; i < 3; i++) {
			for (j = 0; j < boardDesign.gridSize; j++) {
				if ((i + j) % 2 == 0) {
					// NOTE: i == y  and  j == x
					Piece piece = new Piece(new Position(j, i), false, Piece.WHITE);
					piecePlacement[currentPiece++] = piece;
				}
			}
		}

		// create all of the white peices
		for (i = boardDesign.gridSize - 1; i > boardDesign.gridSize - 4; i--) {
			for (j = 0; j < boardDesign.gridSize; j++) {
				if ((i + j) % 2 == 0) {
					// NOTE: i == y  and  j == x
					Piece piece = new Piece(new Position(j, i), false, Piece.BLACK);
					piecePlacement[currentPiece++] = piece;
				}
			}
		}
		
		// FIXME Make sure that the pieces are placed starting on the dark square
	}
	
	// Copy constructor
	public Board(Board copy) throws RemoteException {
		boardDesign = new BoardDesign(copy.getBoardDesign());
		Piece[] copyPieces = copy.getPiecePlacement();
		
		piecePlacement = new Piece[NUM_PIECES];
		for (int i = 0; i < piecePlacement.length && copyPieces[i] != null; i++) {
			piecePlacement[i] = new Piece(copyPieces[i]);
		}

	}

	// getter for the board
	public BoardDesign getBoardDesign() throws RemoteException {
		return boardDesign;
	}

	// setter for the board
	public void setBoardDesign(BoardDesign design) throws Exception {
		if (design == null) {
			throw new Exception("Wrong value");
		}
		boardDesign = design;
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
			return newObj.getBoardDesign().equals(this.getBoardDesign())
					&& Arrays.equals(newObj.getPiecePlacement(),
							this.getPiecePlacement());
		} catch (RemoteException e) {
			return false;
		}
	}
	
	public Piece getPieceAtPosition(int x, int y){
		Position pos;
		
		for(int i=0; i<piecePlacement.length; i++){
			pos = piecePlacement[i].getPiecePosition();
			if(pos.getX() == x && pos.getY() == y)
				return piecePlacement[i];
		}
		
		return null;
	}

	// FIXME this function must be rewritten
	public ArrayList<Move> getPossibleMoves(int x, int y) throws Exception {
		Piece currentPiece;
		ArrayList<Move> moves;
		
		currentPiece = getPieceAtPosition(x, y);
		moves = new ArrayList<Move>();
		
		/**
		 * NOTES
		 * 
		 * The top of the board is y=0, so to move pieces down, ADD to y
		 * (White always starts at the top)
		 */
		
		if(currentPiece.getColour() == Piece.BLACK){
			moves.add(new SingleMove(currentPiece, null, new Position(currentPiece.getPiecePosition().getX()-1 ,currentPiece.getPiecePosition().getY()-1)));
			moves.add(new SingleMove(currentPiece, null, new Position(currentPiece.getPiecePosition().getX()+1 ,currentPiece.getPiecePosition().getY()-1)));
		}
		else{ 
			moves.add(new SingleMove(currentPiece, null, new Position(currentPiece.getPiecePosition().getX()-1 ,currentPiece.getPiecePosition().getY()+1)));
			moves.add(new SingleMove(currentPiece, null, new Position(currentPiece.getPiecePosition().getX()+1 ,currentPiece.getPiecePosition().getY()+1)));
		}
		
		return moves;
	}
}
