package functionalTests;

import junit.framework.TestCase;

import org.junit.Test;

import checkers.Board;
import checkers.Piece;
import checkers.Position;
import checkers.SingleMove;

public class PairWiseTest {

	private Board board;
	
	@Test
	public void testCenterPawnInvalidMoveNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY());
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testCenterPawnMoveDownLeftSpotTaken() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() + 1);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() + 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testCenterPawnJumpDownLeftSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() + 2);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() + 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	  
	@Test
	public void testCenterKingMoveLeftSpotTaken() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() - 1);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testCenterKingJumpLeftSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testCenterKingJumpDownRightNoPiece() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testCenterKingJumpDownRightSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testCenterKingOffEdgeJumpOpponent() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 7, initialPosition.getY() + 7);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testLeftEdgePawnRightMoveNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() - 1);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testLeftEdgePawnJumpRightOverOpponent() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() - 2);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testLeftEdgePawnJumpDownRightSpotTaken() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() + 2);
			Piece pieceToBeCaptured = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = pieceToBeCaptured;
			Piece pieceOnSpot = new Piece(endPosition, false, Piece.WHITE); 
			board.getPiecePlacement()[2] = pieceOnSpot;
			SingleMove move = new SingleMove(pieceToMove, pieceToBeCaptured, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testLeftEdgePawnOffBoardMoveNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testLeftEdgeKingInvalidJumpRightOverOpponent() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 3, initialPosition.getY() - 3);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testLeftEdgeKingDownRightMoveNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testRightEdgePawnInvalidJumpLeftOverSameColour() {
		try {
			board = new Board("testCaseBoards/rightEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 3, initialPosition.getY() - 3);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void tesRightEdgePawnDownLeftMoveNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/rightEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(false);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testRightEdgePawnJumpDownLeftSpotTaken() {
		try {
			board = new Board("testCaseBoards/rightEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() + 2);
			Piece pieceToBeCaptured = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() + 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = pieceToBeCaptured;
			Piece pieceOnSpot = new Piece(endPosition, false, Piece.WHITE); 
			board.getPiecePlacement()[2] = pieceOnSpot;
			SingleMove move = new SingleMove(pieceToMove, pieceToBeCaptured, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void tesRightEdgeKingJumpLeftNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/rightEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testRightEdgeKingOffBoardMoveNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/rightEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testTopEdgeKingInvalidMoveNoPieceInWay() {
		try {
			board = new Board("testCaseBoards/topEdgeBoard.csv");
			Piece piece = board.getPiecePlacement()[0];
			piece.setCrown(true);
			Position initialPosition = piece.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY());
			SingleMove move = new SingleMove(piece, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testTopEdgeKingMoveDownRightSpotTaken() {
		try {
			board = new Board("testCaseBoards/topEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() + 1);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() + 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testTopEdgeKingJumpDownLeftSameColour() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() + 2);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() + 1), false, Piece.BLACK); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testBottomEdgePawnJumpLeftSpotTaken() {
		try {
			board = new Board("testCaseBoards/bottomEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() - 2);
			Piece pieceToBeCaptured = new Piece(new Position(initialPosition.getX() - 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = pieceToBeCaptured;
			Piece pieceOnSpot = new Piece(endPosition, false, Piece.WHITE); 
			board.getPiecePlacement()[2] = pieceOnSpot;
			SingleMove move = new SingleMove(pieceToMove, pieceToBeCaptured, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testBottomEdgePawnMoveDownLeftNoPiece() {
		try {
			board = new Board("testCaseBoards/bottomEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 1, initialPosition.getY() + 1);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testBottomEdgePawnJumpDownLeftNoPiece() {
		try {
			board = new Board("testCaseBoards/centerBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(false);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() - 2, initialPosition.getY() + 2);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testBottomEdgeKingInvalidJumpRightOverOpponent() {
		try {
			board = new Board("testCaseBoards/bottomEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 3, initialPosition.getY() - 3);
			Piece otherPiece = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = otherPiece;
			SingleMove move = new SingleMove(pieceToMove, otherPiece, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}

	@Test
	public void testBottomEdgeKingMoveRightNoPiece() {
		try {
			board = new Board("testCaseBoards/bottomEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 1, initialPosition.getY() - 1);
			SingleMove move = new SingleMove(pieceToMove, null, endPosition);
			TestCase.assertTrue(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	@Test
	public void testBottomEdgeKingJumpRightSpotTaken() {
		try {
			board = new Board("testCaseBoards/leftEdgeBoard.csv");
			Piece pieceToMove = board.getPiecePlacement()[0];
			pieceToMove.setCrown(true);
			Position initialPosition = pieceToMove.getPiecePosition();
			Position endPosition = new Position(initialPosition.getX() + 2, initialPosition.getY() - 2);
			Piece pieceToBeCaptured = new Piece(new Position(initialPosition.getX() + 1, initialPosition.getY() - 1), false, Piece.WHITE); 
			board.getPiecePlacement()[1] = pieceToBeCaptured;
			Piece pieceOnSpot = new Piece(endPosition, false, Piece.WHITE); 
			board.getPiecePlacement()[2] = pieceOnSpot;
			SingleMove move = new SingleMove(pieceToMove, pieceToBeCaptured, endPosition);
			TestCase.assertFalse(board.validateMove(move));
		} catch (Exception e) {
			TestCase.fail();
		}
	}
}
