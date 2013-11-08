package functionalTests;

import junit.framework.TestCase;

import org.junit.Test;

import checkers.Board;
import checkers.Piece;
import checkers.Position;
import checkers.SingleMove;

public class MoveCenterBoardTest {

	private Board board;
	
	@Test
	public void testKingEmptyJump() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testKingJumpForward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testKingJumpForwardOverSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testKingJumpBackward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testKingMoveBackward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testKingMoveForward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() - 1);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testKingJumpBackwardOverSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testManEmptyJump() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testManJumpForward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testManJumpForwardOverSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testManJumpBackwardOverSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testManJumpBackward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			Piece capturedPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = capturedPiece;
			SingleMove move = new SingleMove(pieceToMove, capturedPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testManMoveBackward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testManMoveForward() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() - 1);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}

}
