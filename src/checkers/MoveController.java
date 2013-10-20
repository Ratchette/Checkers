package checkers;

public class MoveController {

	public SingleMove manSimpleMove(Piece currentPiece, Position endPosition) throws Exception {
		Position piecePosition = currentPiece.getPiecePosition();
		
		System.out.println("WARNING: manSimpleMove has not been implemented yet");
		
		// FIXME - this logic is pretty bad
		if (Math.abs(endPosition.getX() - piecePosition.getX()) != 1) 
			throw new Exception("Invalid Move: You did not move one square left or right");
		// FIXME - this logic is pretty bad
		if (endPosition.getY() - piecePosition.getY() != 1)
			throw new Exception("Invalid Move: You did not move one square forward or backwards");
		
		currentPiece.setPiecePosition(endPosition);
		
//		// FIXME - this line is incorrect
//		SingleMove singleMove = new SingleMove(currentPiece, null, endPosition);
//		return singleMove;
		
		return null;
	}
	
	public SingleMove kingSimpleMove(Piece currentPiece, Position endPosition) throws Exception {
		Position piecePosition = currentPiece.getPiecePosition();
		
		System.out.println("WARNING: kingSimpleMove has not been implemented yet");
		
		// FIXME - this logic is pretty bad
		if (Math.abs(endPosition.getX() - piecePosition.getX()) != 1) 
			throw new Exception("Invalid Move: You did not move one square left or right");
		// FIXME - this logic is pretty bad
		if (endPosition.getY() - piecePosition.getY() != 1)
			throw new Exception("Invalid Move: You did not move one square forward or backwards");
		
		currentPiece.setPiecePosition(endPosition);
		
//		// FIXME - this line is incorrect
//		SingleMove singleMove = new SingleMove(currentPiece, null, endPosition);
//		return singleMove;
		
		return null;
	}

	public SingleMove manFirstJumpMove(Piece currentPiece, 
			Piece capturedPiece, Position endPosition) throws Exception {

		Position piecePosition = currentPiece.getPiecePosition();
		Position capturedPiecePosition = capturedPiece.getPiecePosition();
		
		if(currentPiece.getColour() == capturedPiece.getColour())
			throw new Exception("Invalid Move: You have tried to capture your own piece");
		
		if (manJumpPositionValidation(endPosition, piecePosition, capturedPiecePosition)) {
			currentPiece.setPiecePosition(endPosition);
			return new SingleMove(currentPiece, capturedPiece, endPosition);
		}
		
		else {
			throw new Exception("Invalid Move: man jump position validation failed");
		}
		
	}

	private Boolean manJumpPositionValidation(Position endPosition,
			Position piecePosition, Position capturedPiecePosition) {

		// FIXME Implement this function properly 
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
		
		if(currentPiece.getColour() == capturedPiece.getColour())
			throw new Exception("Invalid Move: You have tried to capture your own piece");

		
		if (kingJumpPositionValidation(endPosition, piecePosition, capturedPiecePosition)) {
			currentPiece.setPiecePosition(endPosition);
			return new SingleMove(currentPiece, capturedPiece, endPosition);
		}
		
		else {
			throw new Exception("Invalid Move: king jump validation failed");
		}
		
	}

	private Boolean kingJumpPositionValidation(Position endPosition,
			Position piecePosition, Position capturedPiecePosition) {

		// FIXME Implement this function properly 
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
