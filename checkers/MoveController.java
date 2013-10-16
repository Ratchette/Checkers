package checkers;

public class MoveController {

	public SingleMove manSimpleMove(Piece currentPiece, 
			Position endPosition) throws Exception {

		Position piecePosition = currentPiece.getPiecePosition();
		SingleMove singleMove = new SingleMove();

		if (Math.abs(endPosition.getX() - piecePosition.getX()) != 1) {
			throw new Exception("Invalid move.");
		}

		if (endPosition.getY() - piecePosition.getY() != 1) {
			throw new Exception("Invalid move.");
		}
		currentPiece.setPiecePosition(endPosition);
		singleMove.setEndPosition(endPosition);
		singleMove.setPieceBeignMoved(currentPiece);

		return singleMove;
	}
	
	public SingleMove kingSimpleMove(Piece currentPiece, 
			Position endPosition) throws Exception {

		Position piecePosition = currentPiece.getPiecePosition();
		SingleMove singleMove = new SingleMove();

		if (Math.abs(endPosition.getX() - piecePosition.getX()) != 1) {
			throw new Exception("Invalid move.");
		}

		if (Math.abs(endPosition.getY() - piecePosition.getY()) != 1) {
			throw new Exception("Invalid move.");
		}
		currentPiece.setPiecePosition(endPosition);
		singleMove.setEndPosition(endPosition);
		singleMove.setPieceBeignMoved(currentPiece);

		return singleMove;
	}

	public SingleMove manFirstJumpMove(Piece currentPiece, 
			Piece capturedPiece, Position endPosition) throws Exception {

		Position piecePosition = currentPiece.getPiecePosition();
		Position capturedPiecePosition = capturedPiece.getPiecePosition();
		SingleMove singleMove = new SingleMove();
		
		if(currentPiece.getColour() == capturedPiece.getColour()) {
			throw new Exception("Invalid move.");
		}

		
		if (manJumpPositionValidation(endPosition, piecePosition,
				capturedPiecePosition)) {

			currentPiece.setPiecePosition(endPosition);
			singleMove.setCapturedPiece(capturedPiece);
			singleMove.setPieceBeignMoved(currentPiece);
			singleMove.setEndPosition(endPosition);
			return singleMove;
		}
		
		else {
			throw new Exception("Invalid move.");
		}
		
	}

	private Boolean manJumpPositionValidation(Position endPosition,
			Position piecePosition, Position capturedPiecePosition) {

		if (Math.abs(endPosition.getX() - piecePosition.getX()) != 2) {
			return false;
		}

		if (endPosition.getY() - piecePosition.getY() != 2) {
			return false;
		}

		if (capturedPiecePosition.getY() - piecePosition.getY() != 1) {
			return false;
		}

		if (Math.abs(piecePosition.getX() - capturedPiecePosition.getX()) != 1) {
			return false;
		}

		return true;
	}
	
	public SingleMove kingFirstJumpMove(Piece currentPiece, 
			Piece capturedPiece, Position endPosition) throws Exception {

		Position piecePosition = currentPiece.getPiecePosition();
		Position capturedPiecePosition = capturedPiece.getPiecePosition();
		SingleMove singleMove = new SingleMove();
		
		if(currentPiece.getColour() == capturedPiece.getColour()) {
			throw new Exception("Invalid move.");
		}

		
		if (kingJumpPositionValidation(endPosition, piecePosition,
				capturedPiecePosition)) {

			currentPiece.setPiecePosition(endPosition);
			singleMove.setCapturedPiece(capturedPiece);
			singleMove.setPieceBeignMoved(currentPiece);
			singleMove.setEndPosition(endPosition);
			return singleMove;
		}
		
		else {
			throw new Exception("Invalid move.");
		}
		
	}

	private Boolean kingJumpPositionValidation(Position endPosition,
			Position piecePosition, Position capturedPiecePosition) {

		if (Math.abs(endPosition.getX() - piecePosition.getX()) != 2) {
			return false;
		}

		if (Math.abs(endPosition.getY() - piecePosition.getY()) != 2) {
			return false;
		}

		if (Math.abs(capturedPiecePosition.getY() - piecePosition.getY()) != 1) {
			return false;
		}

		if (Math.abs(piecePosition.getX() - capturedPiecePosition.getX()) != 1) {
			return false;
		}

		return true;
	}

}
