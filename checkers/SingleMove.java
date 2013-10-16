/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.rmi.RemoteException;

public class SingleMove extends Move{
	private static final long serialVersionUID = 1L;
	
	private Piece pieceBeignMoved;
    private Piece capturedPiece;
    private Position endPosition;
    
    public SingleMove() {
    	
	}
    
    public SingleMove(Piece pieceBeingMoved, Piece capturedPiece, Position endPosition) {
    	this.pieceBeignMoved = pieceBeingMoved;
    	this.capturedPiece = capturedPiece;
    	this.endPosition = endPosition;
    }
    
	public Piece getPieceBeignMoved() {
		return pieceBeignMoved;
	}
	
	public void setPieceBeignMoved(Piece pieceBeignMoved) {
		this.pieceBeignMoved = pieceBeignMoved;
	}
	
	public Piece getCapturedPiece() {
		return capturedPiece;
	}
	
	public void setCapturedPiece(Piece capturedPiece) {
		this.capturedPiece = capturedPiece;
	}
	
	public Position getEndPosition() {
		return endPosition;
	}
	
	public void setEndPosition(Position endPosition) {
		this.endPosition = endPosition;
	
	}

	@Override
	public int numberOfSteps() throws RemoteException{
		return 1;
	}

	@Override
	public Piece[] capturedPieces() throws RemoteException{
		Piece[] capturedPieces = new Piece[1];
		capturedPieces[0] = capturedPiece;
		return capturedPieces;
	}

	@Override
	public SingleMove[] moveSequence() throws RemoteException{
		SingleMove[] moveSequence = new SingleMove[1];
		moveSequence[0] = this;
		return moveSequence;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SingleMove) {
			SingleMove newObj = (SingleMove) obj;
			if(newObj.getCapturedPiece() == null) {
				if (this.getCapturedPiece() != null ) {
					return false;
				}
				return newObj.getEndPosition().equals(this.getEndPosition()) && 
						newObj.getPieceBeignMoved().equals(this.getPieceBeignMoved());
			}
			else {
				if (this.getCapturedPiece() == null ) {
					return false;
				}
				return newObj.getCapturedPiece().equals(this.getCapturedPiece()) &&
						newObj.getEndPosition().equals(this.getEndPosition()) && 
						newObj.getPieceBeignMoved().equals(this.getCapturedPiece());		}
		}
		return false;
	}
}