
package checkers;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;

public class CheckersPlayer implements Player{
	private GameInfo myGame;
	public Board theBoard;

	public CheckersPlayer()
	{


	}

	@Override
	public Object considerGame(GameDesign aGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame(PlayerInfo player, String gameType) {
		// Create a new board
		try {
			Piece pieces[] = new Piece[36];
			theBoard = new Board(new BoardDesign(gameType), pieces );

			int count = 0;
			for (int i = 0; i< 3; i++) {
				for (int j = 0; j < theBoard.getTheBoard().getGridSize(); j++) {
					if ((i + j) % 2 == 0) {
						try {
							Piece piece = new Piece();
							piece.setPiecePosition(new Position(j,i));
							piece.setCrown(false);
							piece.setColour('b'); 
							piece.setPieceImage(scale(
									ImageIO.read(getClass().getResource("/peice8x8.png")), 600/theBoard.getTheBoard().getGridSize(), 600/theBoard.getTheBoard().getGridSize()));
							pieces[count] = piece;
							count ++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			for (int i = theBoard.getTheBoard().getGridSize()-1; i > theBoard.getTheBoard().getGridSize()-4; i--) {
				for (int j = 0; j < theBoard.getTheBoard().getGridSize(); j++) {
					if ((i + j) % 2 == 0) {
						try {
							Piece piece = new Piece();
							piece.setPiecePosition(new Position(j,i));
							piece.setCrown(false);
							piece.setColour('w'); 
							piece.setPieceImage(scale(
									ImageIO.read(getClass().getResource("/peice8x8w.png")), 600/theBoard.getTheBoard().getGridSize(), 600/theBoard.getTheBoard().getGridSize()));
							pieces[count] = piece;
							count ++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			theBoard.setPiecePlacement(pieces);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	@Override
	public String move(Move playersMove) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void opponentResigned(PlayerInfo player, char code, String message)
			throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public String resign() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void youWin() throws RemoteException {
		// TODO Auto-generated method stub

	}

	public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

      int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
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
