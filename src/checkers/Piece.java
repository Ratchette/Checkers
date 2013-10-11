/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public class Piece {

	private Position piecePosition;
	private Boolean crown;
	private char colour;

	public Piece() {

	}

	public Piece(Position piecePosition, Boolean crown, char colour) {
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
}
