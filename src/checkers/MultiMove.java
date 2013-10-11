/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public class MultiMove extends Move {

	private Piece pieceBeingMoved;
	private Position endPosition;
	private Piece capturedPiece;

	public MultiMove() {

	}
	
	public MultiMove(Piece pieceBeingMoved, Position endPosition, Piece capturedPiece) {
		this.pieceBeingMoved = pieceBeingMoved;
		this.endPosition = endPosition;
		this.capturedPiece = capturedPiece;
	}

	public Piece getPieceBeingMoved() {
		return pieceBeingMoved;
	}	
	
	public void setPieceBeingMoved(Piece pieceBeingMoved) {
		this.pieceBeingMoved = pieceBeingMoved;
	}

	public Position getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Position endPosition) {
		this.endPosition = endPosition;
	}

	public Piece getCapturedPiece() {
		return capturedPiece;
	}

	public void setCapturedPiece(Piece capturedPiece) {
		this.capturedPiece = capturedPiece;
	} 
}

