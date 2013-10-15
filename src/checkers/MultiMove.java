/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.rmi.RemoteException;

public class MultiMove extends Move {
	private static final long serialVersionUID = 1L;
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
	public int numberOfSteps() throws RemoteException{
		return moveSequence.length;
	}

	@Override
	public Piece[] capturedPieces() throws RemoteException{
		Piece[] capturedPieces = new Piece[moveSequence.length];
		for(int i = 0; i < capturedPieces.length; i ++) {
			capturedPieces[i] = moveSequence[i].getCapturedPiece();
		}
		return capturedPieces;
	}

	@Override
	public SingleMove[] moveSequence() throws RemoteException{
		return moveSequence;
	}
	
}

