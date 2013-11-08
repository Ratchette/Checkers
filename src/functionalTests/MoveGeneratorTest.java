package functionalTests;

import static org.junit.Assert.fail;

import org.junit.Test;

import checkers.Board;
import checkers.Position;

public class MoveGeneratorTest {

	private Board board;
	
	@Test
	public void test() {
		try {
			board = new Board("filename");
			board.getPiecePlacement()[0].setCrown(true);
			Position position = new Position();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
