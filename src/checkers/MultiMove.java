/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public class MultiMove extends Move {

	private SingleMove[] moveSequence;
	
	public MultiMove() {

	}
	
	public MultiMove(SingleMove[] moveSequence) {
		this.setMoveSequence(moveSequence);
	}

	public SingleMove[] getMoveSequence() {
		return moveSequence;
	}

	public void setMoveSequence(SingleMove[] moveSequence) {
		this.moveSequence = moveSequence;
	}
	
}

