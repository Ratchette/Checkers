package unitTest;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import checkers.Position;

public class PositionTest {

	private Position position;
	
	
	@Before
	public void setup() {
		try {
			position = new Position();
		} catch (RemoteException e) {
	
		}
	}
	
	@Test
	public void setInvalidXPositionTest() {
		int x = -1;
		String failureMessage = "Expected exception message is not corrected.\n";
		try {
			position.setX(x);
		} catch (Exception e) {
			String expectedErrorMessage = "Ivalid position: The "
					+ "x position " + x + " is not a valid position.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
			
			System.out.println("Test for x = " + x + " : Test ok Expected an "
					+ "exception.");
		}
		
		x = 0;
		try {
			position.setX(x);
		} catch (Exception e) {
			String expectedErrorMessage = "Ivalid position: The "
					+ "x position " + x + " is not a valid position.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
			System.out.println("Test for x = " + x + " : Test ok Expected an "
					+ "exception.");
		}
	}
	
	@Test
	public void setValidXPositionTest() {
		int x = 5;
		String failureMessage = "Expected exception message is not corrected.\n";
		try {
			position.setX(x);
			TestCase.assertEquals(x, position.getX());
			System.out.println("Test for x = " + x + " : Test ok. Valid value.");
			
		} catch (Exception e) {
			
			String expectedErrorMessage = "Ivalid position: The "
					+ "x position " + x + " is not a valid position.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
		}
		
		x = 2;
		try {
	
			position.setX(x);
			TestCase.assertEquals(x, position.getX());
			System.out.println("Test for x = " + x + " : Test ok. Valid value.");
			
		} catch (Exception e) {
		
			String expectedErrorMessage = "Ivalid position: The "
					+ "x position " + x + " is not a valid position.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
			
		}
	}
	
	@Test
	public void setInvalidYPositionTest() {
		int y = -1;
		String failureMessage = "Expected exception message is not corrected.\n";
		try {
			position.setY(y);
		} catch (Exception e) {
			String expectedErrorMessage = "Ivalid position: The "
					+ "y position " + y + " is not a valid position.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
			
			System.out.println("Test for y = " + y + " : Test ok Expected an "
					+ "exception.");
		}
		
		y = -10;
		try {
			position.setY(y);
		} catch (Exception e) {
			String expectedErrorMessage = "Ivalid position: The "
					+ "y position " + y + " is not a valid position.";
			
			TestCase.assertEquals(failureMessage, expectedErrorMessage, e.getMessage());
			System.out.println("Test for y = " + y + " : Test ok Expected an "
					+ "exception.");
		}
	}
	
	@Test
	public void setValidYPositionTest() {
		int y = 6;
		try {
			position.setY(y);
			TestCase.assertEquals(y, position.getY());
			System.out.println("Test for y = " + y + " : Test ok. Valid value.");
			
		} catch (Exception e) {
			
			TestCase.fail();
		}
		
		y = 1;
		try {
	
			position.setY(y);
			TestCase.assertEquals(y, position.getY());
			System.out.println("Test for y = " + y + " : Test ok. Valid value.");
			
		} catch (Exception e) {
		
			TestCase.fail();
			
		}
	}
}
