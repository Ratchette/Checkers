/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * ATTENTION BEN!!!!
 * 
 * I needed to change the images from BufferedImages to ImageIcons so that they could be sent over the network
 *     - Would it be possible to remove all BufferedImages stuff and only deal with imageIcon objects?
 */


public class Piece implements Remote, Serializable{
	public static final char BLACK = 'b';
	public static final char WHITE = 'w';

	private Position piecePosition;
	private Boolean crown;
	private char colour;
	
	private String imageURL;

	public Piece(Piece copy) throws RemoteException {
		piecePosition = new Position(copy.getPiecePosition());
		crown = new Boolean(copy.isCrown());
		colour = copy.getColour();
		imageURL = new String(copy.getImageURL());
	}

	public Piece(Position piecePosition, Boolean crown, char colour) {
		this.piecePosition = piecePosition;
		this.crown = crown;
		this.colour = colour;

		if (colour == Piece.WHITE)
			this.imageURL = "/peice8x8w.png";
		else
			this.imageURL = "/peice8x8.png";
	}

	public Position getPiecePosition() {
		try {
			return new Position(piecePosition);
		} catch (Exception e) {};
		// TODO Implement the catch block?

		return null;
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
			Position pieceToBeComparedPosition = pieceToBeCompared
					.getPiecePosition();
			char pieceToBeComparedColour = pieceToBeCompared.getColour();
			return pieceToBeComparedPosition.equals(this.getPiecePosition())
					&& pieceToBeComparedColour == this.getColour();
		}
		return false;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setPieceImage(String imageLocation) {
		this.imageURL = imageLocation;
	}
	
	public void turnKing(){
		if(this.colour == 'w')
			this.imageURL = "/peice8x8Wk.png";
		else
			this.imageURL = "/peice8x8K.png";
		
		this.crown = true;
	}
}
