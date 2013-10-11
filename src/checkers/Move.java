/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public abstract class Move {
	
	public int numberOfSteps(){
            return -1;
        };

	public Piece[] capturedPieces(){
            return null;
        };

	public SingleMove[] moveSequence(){
            return null;
        };
}
