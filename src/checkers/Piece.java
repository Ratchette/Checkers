/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;

public class Piece {
	public static final char BLACK = 'b';
	public static final char WHITE = 'w';

	private Position piecePosition;
	private Boolean crown;
	private char colour;
	private BufferedImage pieceImage;

	public Piece(Piece copy) throws RemoteException {
		this.piecePosition = new Position(copy.getPiecePosition());
		this.crown = new Boolean(copy.isCrown());
		this.colour = copy.getColour();
	}

	public Piece(Position piecePosition, Boolean crown, char colour)
			throws IOException {
		this.piecePosition = piecePosition;
		this.crown = crown;
		this.colour = colour;

		if (colour == Piece.WHITE)
			this.pieceImage = ImageIO.read(getClass().getResource(
					"/peice8x8w.png"));
		else
			this.pieceImage = ImageIO.read(getClass().getResource(
					"/peice8x8.png"));
	}

	public Position getPiecePosition() {
		try {
			return new Position(piecePosition);
		} catch (Exception e) {
		}
		;
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

	public BufferedImage getPieceImage() {
		return pieceImage;
	}

	public void setPieceImage(BufferedImage pieceImage, int size) {
		this.pieceImage = scale(pieceImage, size, size);
	}

	// Ben's scaling function
	public BufferedImage scale(BufferedImage img, int targetWidth,
			int targetHeight) {

		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = img;
		BufferedImage scratchImage = null;
		Graphics2D g2 = null;

		int w = img.getWidth();
		int h = img.getHeight();

		int prevW = w;
		int prevH = h;

		do {
			if (w > targetWidth) {
				w /= 2;
				w = (w < targetWidth) ? targetWidth : w;
			}

			if (h > targetHeight) {
				h /= 2;
				h = (h < targetHeight) ? targetHeight : h;
			}

			if (scratchImage == null) {
				scratchImage = new BufferedImage(w, h, type);
				g2 = scratchImage.createGraphics();
			}

			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

			prevW = w;
			prevH = h;
			ret = scratchImage;
		} while (w != targetWidth || h != targetHeight);

		if (g2 != null) {
			g2.dispose();
		}

		if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
			scratchImage = new BufferedImage(targetWidth, targetHeight, type);
			g2 = scratchImage.createGraphics();
			g2.drawImage(ret, 0, 0, null);
			g2.dispose();
			ret = scratchImage;
		}

		return ret;
	}

}
