package unitTest;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import checkers.BoardDesign;

public class BoardDesignTest {

	private BoardDesign boardDesign;

	@Before
	public void setup() {
		try {
			boardDesign = new BoardDesign("British");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidGameTypeValue() throws RemoteException {
		TestCase.assertEquals(boardDesign.getBlackCorner(),'L');
		TestCase.assertEquals(boardDesign.getGridSize(),8);
		
		boardDesign = new BoardDesign("American");
		
		TestCase.assertEquals(boardDesign.getBlackCorner(),'L');
		TestCase.assertEquals(boardDesign.getGridSize(),8);
	}

}
