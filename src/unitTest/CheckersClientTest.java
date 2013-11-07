package unitTest;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import checkers.CheckersClient;
import checkers.MoveController;
import checkers.Piece;
import checkers.Position;

public class CheckersClientTest {

	private CheckersClient client, clientPlayer, clientObserver;
	
	
	@Before
	public void setup() {
		try {
			//client = new CheckersClient();
			//clientPlayer = new CheckersClient(true);
			//clientObserver = new CheckersClient(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructorVars() {
		
		//Test default constructor
		TestCase.assertTrue(client != null);
		TestCase.assertFalse(client == null);
		
		//Test client type constructor
		TestCase.assertTrue(clientPlayer != null);
		TestCase.assertTrue(clientObserver != null);
		
		//TestCase.assertNull(client.getObserver());	
		//TestCase.assertNull(clientPlayer.getObserver());
		//TestCase.assertNotNull(clientObserver.getObserver());
		
	}

}
