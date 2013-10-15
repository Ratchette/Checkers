/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.rmi.RemoteException;

public class Piece {

	private Position piecePosition;
	private Boolean crown;
	private char colour;

	public Piece(Piece copy)  throws RemoteException{
		this.piecePosition = new Position(copy.getPiecePosition());
		this.crown = new Boolean(copy.isCrown());
		this.colour = copy.getColour();
	}

	public Piece(Position piecePosition, Boolean crown, char colour)  throws RemoteException{
		this.piecePosition = piecePosition;
		this.crown = crown;
		this.colour = colour;
	}

	public Position getPiecePosition() {
		return piecePosition;
	}
	
	public void setPiecePosition(Position piecePosition) {
		this.piecePosition = piecePosition;
	}
	
	public Boolean isCrown() {
		return crown;
	}
	
	public void setCrown(Boolean crown) {
		this.crown = crown;
	}

	public char getColour() {
		return colour;
	}

	public void setColour(char colour) {
		this.colour = colour;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Piece) {
			Piece pieceToBeCompared = (Piece) obj;
			Position pieceToBeComparedPosition = pieceToBeCompared.getPiecePosition();
			char pieceToBeComparedColour = pieceToBeCompared.getColour();
			return pieceToBeComparedPosition.equals(this.getPiecePosition()) 
					&& pieceToBeComparedColour == this.getColour();
		}
		return false;
	}
}
