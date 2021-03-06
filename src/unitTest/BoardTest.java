package unitTest;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import checkers.Board;
import checkers.BoardDesign;
import checkers.Piece;

public class BoardTest {

	private Board board;
	private BoardDesign boardDesign;

	@Before
	public void setup() {
		try {
			board = new Board(BoardDesign.BRITISH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testNullPiecePlacementValue() {
		try {
			TestCase.assertNotNull(board.getPiecePlacement());
			board.setPiecePlacement(null);
			TestCase.assertNotNull(board.getPiecePlacement());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			try {
				TestCase.assertNotNull(board.getPiecePlacement());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Test
	public void testInvalidBoardDesignValue() {
		try {
			TestCase.assertTrue(board.getBoardDesign() != null);
			board.setBoardDesign(null);
			TestCase.assertTrue(board.getBoardDesign() != null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			try {
				TestCase.assertTrue(board.getBoardDesign() != null);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
