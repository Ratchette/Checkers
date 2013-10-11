public class MultiMove extends Move {

	private Piece pieceBeingMoved;
	private Position endPosition;
	private Piece capturedPiece;

	public Move() {

	}
	
	public Move(Piece pieceBeingMoved, Position endPosition, Piece capturedPiece) {
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

