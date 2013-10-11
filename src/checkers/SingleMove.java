/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public class SingleMove{
    private Piece pieceBeignMoved;
    private Piece capturedPiece;
    private Position endPosition;
    
    public SingleMove() {
    	
	}
    
    public SingleMove(Piece pieceBeingMoved, Piece capturedPiece, Position endPosition){
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
    
    
}