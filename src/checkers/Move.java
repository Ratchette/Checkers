/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public abstract class Move {
	
	public abstract int numberOfSteps();

	public abstract Piece[] capturedPieces();

	public abstract SingleMove[] moveSequence();
}
