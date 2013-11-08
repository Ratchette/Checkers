package functionalTests;

import junit.framework.TestCase;

import org.junit.Test;

import checkers.Board;
import checkers.Piece;
import checkers.Position;
import checkers.SingleMove;

public class EachChoiceTest {

	private Board board;
	
	/**
	 * Test if a pawn in the center of the boardcould move to a invalid place
	 * in the board, with no piece on it.
	 * Expects that the validateMove method return false, because it is a invalid move.
	 */
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
	
	/**
	 * Test if a pawn in the left edge could move to the right diagonal forward place, 
	 * which already has another piece.
	 * Expects that the validateMove method return false, because it is a invalid move.
	 */
	@Test
	public void testPawnLeftEdgeMoveRightSpotTake() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
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
	
	/**
	 * Test if a pawn on the right edge of the board could move to the diagonal left forward which 
	 * has a piece with the same colour.
	 * Expects that the validateMove method return false, because it is a invalid move.
	 */
	@Test
	public void testPawnRightEdgeMoveLeftSameColour() {
		try {
			board = new Board("testCaseBoards/rightEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() - 1);
			Piece otherPiece = new Piece(endPosition, false, Piece.WHITE);
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	/**
	 * Test if a king piece on the top edge of the board could jump 
	 * an opponent piece in a diagonal left backward.
	 * Expects that the test that  the method validateMove return true, because for a king it is a valid move.
	 * This test found a bug, which the King was not moving or jumping backward properly.
	 */
	@Test
	public void testKingTopEdgeJumpOpponentDownLeft() {
		try {
			board = new Board("testCaseBoards/topEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() + 2);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() + 1), false, Piece.WHITE);
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(piece, otherPiece, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	/**
	 * Test if the king on the bottom edge of the board could move left backward.
	 * This test expects that the return of validateMove method return false, because it
	 * is trying to move off board, and it is an invalid move.
	 */
	@Test
	public void testKingBottomEdgeMoveDownLeft() {
		try {
			board = new Board("testCaseBoards/bottomEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	/**
	 * Test if the king could jump to an spot already taken.
	 * The return of the method validate move is expected to return false.
	 */
	@Test
	public void testKingAlmostRightEdgeJumpLeftSpotTaken() {
		try {
			board = new Board("testCaseBoard/almostRightEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 2, initialPosition.getY() - 2), false, Piece.WHITE);
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(piece, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	/**
	 * Test if the king could move to a place that is already occupied by a piece with the same colour.
	 * The return of the method validateMove is expected to be false.
	 */
	@Test
	public void testKingAlmostLeftEdgeMoveDownRightSameColour() {
		try {
			board = new Board("testCaseBoards/almostLeftEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
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
