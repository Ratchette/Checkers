package unitTest;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import checkers.MoveController;
import checkers.Piece;
import checkers.Position;
import checkers.SingleMove;

public class MoveTest {

	private Piece pieceToBeMoved;
	private Piece capturedPiece;
	private Position endPosition;
	private Position pieceToBeMovedPosition;
	private Position capturedPiecePosition;
	private MoveController movecontroller;
	
	
	@Before
	public void setup() {
		
		try {
			pieceToBeMoved = new Piece(new Position(1,1), false, 'b');
			capturedPiece = new Piece(new Position(1,3), false, 'w');
			endPosition = new Position();
			capturedPiecePosition = new Position();
			pieceToBeMovedPosition = new Position();
			movecontroller = new MoveController();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test if the player want to move the piece to an invalid position.
	 */
	@Test
	public void setInvalidMoveTest() {
		String failureMessage = "Expected exception message is not corrected.\n";
		
		try {
			pieceToBeMovedPosition.setX(1);
			pieceToBeMovedPosition.setY(2);
			
			endPosition.setX(2);
			endPosition.setY(2);
			
		} catch (Exception e) {
			TestCase.fail();
		}
		
		pieceToBeMoved.setPiecePosition(pieceToBeMovedPosition);
		try {
			movecontroller.manSimpleMove(pieceToBeMoved, endPosition);
		
		} catch (Exception e) {
			String expectedErrorMessage = "Invalid move.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
			
			System.out.println("Test for end position = " + endPosition + " "
					+ ": Test ok Expected an exception.");
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
			SingleMove singleMove = movecontroller.
					manSimpleMove(pieceToBeMoved, endPosition);
			TestCase.assertEquals(endPosition, singleMove.getEndPosition());
			
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
			movecontroller.manFirstJumpMove(pieceToBeMoved, capturedPiece, endPosition);
		
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
			SingleMove singleMove = movecontroller.
					manFirstJumpMove(pieceToBeMoved, capturedPiece, endPosition);
			TestCase.assertEquals(endPosition, singleMove.getEndPosition());
			TestCase.assertEquals(capturedPiece, singleMove.getCapturedPiece());
			
		} catch (Exception e) {
			TestCase.fail();
		}
	}
}
