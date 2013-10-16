/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

public class Piece {

	private Position piecePosition;
	private Boolean crown;
	private char colour;
	private BufferedImage pieceImage;

	public Piece(Piece copy)  throws RemoteException{
		this.piecePosition = new Position(copy.getPiecePosition());
		this.crown = new Boolean(copy.isCrown());
		this.colour = copy.getColour();
	}

	public Piece(Position piecePosition, Boolean crown, char colour, BufferedImage pieceImage)  throws RemoteException{
		this.piecePosition = piecePosition;
		this.crown = crown;
		this.colour = colour;
		this.setPieceImage(pieceImage);
	}

	public Piece() {
		// TODO Auto-generated constructor stub
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

	public BufferedImage getPieceImage() {
		return pieceImage;
	}

	public void setPieceImage(BufferedImage pieceImage) {
		this.pieceImage = pieceImage;
	}

}
