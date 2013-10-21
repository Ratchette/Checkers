package unitTest;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import checkers.BoardDesign;
import checkers.Piece;

public class BoardDesignTest {

	private BoardDesign boardDesign;

	@Before
	public void setup() {
		try {
			boardDesign = new BoardDesign(BoardDesign.BRITISH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidGameTypeValue() throws RemoteException {
		TestCase.assertEquals(boardDesign.getBlackCorner(), Piece.WHITE);
		TestCase.assertEquals(boardDesign.gridSize, 8);
		
		try{
		boardDesign = new BoardDesign(BoardDesign.AMERICAN);
		}
		catch(Exception e){
			System.out.println("Could not create an american board");
		}
		
		TestCase.assertEquals(boardDesign.getBlackCorner(), Piece.WHITE);
		TestCase.assertEquals(boardDesign.gridSize, 8);
		
		TestCase.assertNotSame(boardDesign.getBlackCorner(), Piece.BLACK);
		
	}

}
