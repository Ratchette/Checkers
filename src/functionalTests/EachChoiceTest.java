package functionalTests;

import junit.framework.TestCase;

import org.junit.Test;

import checkers.Board;
import checkers.Piece;
import checkers.Position;
import checkers.SingleMove;

public class EachChoiceTest {

	private Board board;
	
	@Test
	public void testPawnNoPieceInTheWayInvalidMove() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 3, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testPawnLeftEdgeMoveRightSpotTake() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() - 1);
			Piece otherPiece = new Piece(endPosition, false, Piece.WHITE);
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
}
