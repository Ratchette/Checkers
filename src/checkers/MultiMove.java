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

	public void setMoveSequence(SingleMove[] moveSequence) {
		this.moveSequence = moveSequence;
	}

	@Override
	public int numberOfSteps() {
		return moveSequence.length;
	}

	@Override
	public Piece[] capturedPieces() {
		Piece[] capturedPieces = new Piece[moveSequence.length];
		for(int i = 0; i < capturedPieces.length; i ++) {
			capturedPieces[i] = moveSequence[i].getCapturedPiece();
		}
		return capturedPieces;
	}

	@Override
	public SingleMove[] moveSequence() {
		return moveSequence;
	}
	
}

