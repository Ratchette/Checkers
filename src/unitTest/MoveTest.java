package unitTest;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import checkers.Board;
import checkers.BoardDesign;
import checkers.Piece;
import checkers.Position;
import checkers.SingleMove;

public class MoveTest {

	private Piece pieceToBeMoved;
	private Piece capturedPiece;
	private Position endPosition;
	private Position pieceToBeMovedPosition;
	private Position capturedPiecePosition;
	private Board board;
	private Piece[] piecePlacement;
	
	
	@Before
	public void setup() {
		try {
			pieceToBeMoved = new Piece(new Position(1,1), false, 'b');
			capturedPiece = new Piece(new Position(1,3), false, 'w');
			endPosition = new Position();
			capturedPiecePosition = new Position();
			pieceToBeMovedPosition = new Position();
			board = new Board(BoardDesign.BRITISH);
			piecePlacement = new Piece[10];
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test if the player want to move the piece to an invalid position.
	 */
	@Test
	public void setInvalidMoveTest() {
		try {
			pieceToBeMovedPosition.setX(1);
			pieceToBeMovedPosition.setY(2);
			
			endPosition.setX(2);
			endPosition.setY(2);
			
		} catch (Exception e) {
			TestCase.fail();
		}
	
		try {

			pieceToBeMoved.setPiecePosition(pieceToBeMovedPosition);
			piecePlacement[0] = pieceToBeMoved;
			board.setPiecePlacement(piecePlacement);
			SingleMove move = new SingleMove(pieceToBeMoved, null, endPosition);
			TestCase.assertFalse(board.validateMove(move));			
		
		} catch (Exception e) {
			TestCase.fail();
		}
	}
	
	/**
	 * Test if the player want to move to a valid position.
	 */
	@Test
	public void setValidMoveTest() {		
		try {
			pieceToBeMovedPosition.setX(1);
			pieceToBeMovedPosition.setY(2);
			
			endPosition.setX(2);
			endPosition.setY(3);
			
		} catch (Exception e) {
			TestCase.fail();
		}
		
		pieceToBeMoved.setPiecePosition(pieceToBeMovedPosition);
		try {
			
		} catch (Exception e) {
			TestCase.fail();
		}
	}

	/**
	 * Test if the player want to do an invalid first jump based on the American
	 * and British checkers rules.
	 * The first jump following the rules for this type of checkers
	 * the player can just jump in a diagonal forward direction.
	 */
	@Test
	public void setValidFirstJumpMoveTest() {
		String failureMessage = "Expected exception message is not corrected.\n";
		
		try {
			pieceToBeMovedPosition.setX(1);
			pieceToBeMovedPosition.setY(2);
			
			capturedPiecePosition.setX(3);
			capturedPiecePosition.setY(2);
			
			endPosition.setX(3);
			endPosition.setY(4);
			
		} catch (Exception e) {
			TestCase.fail();
		}
		
		pieceToBeMoved.setPiecePosition(pieceToBeMovedPosition);
		capturedPiece.setPiecePosition(capturedPiecePosition);
		try {
		
		} catch (Exception e) {
			String expectedErrorMessage = "Invalid move.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
			
			System.out.println("Test for end position = " + endPosition + " "
					+ ": Test ok Expected an exception.");
		}
	}
	
	/**
	 * Test if the player want to do the first jump based on the American
	 * and British checkers rules.
	 * The first jump following the rules for this type of checkers
	 * the player can just jump in a diagonal forward direction.
	 */
	@Test
	public void setInvalidFirstJumpMoveTest() {
		
		try {
			pieceToBeMovedPosition.setX(1);
			pieceToBeMovedPosition.setY(2);
			
			capturedPiecePosition.setX(2);
			capturedPiecePosition.setY(3);
			
			endPosition.setX(3);
			endPosition.setY(4);
			
		} catch (Exception e) {
			TestCase.fail();
		}
		
		pieceToBeMoved.setPiecePosition(pieceToBeMovedPosition);
		capturedPiece.setPiecePosition(capturedPiecePosition);

		try {
			
		} catch (Exception e) {
			TestCase.fail();
		}
	}
}
