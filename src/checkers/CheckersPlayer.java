
package checkers;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;

public class CheckersPlayer implements Player{
	private GameInfo myGame;

	public CheckersPlayer()
	{


	}

	@Override
	public Object considerGame(GameDesign aGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame(PlayerInfo player) {
		// Create a new board

		Board theBoard;
		try {
			Piece pieces[] = new Piece[24];
			theBoard = new Board(new BoardDesign("British"), pieces );
			Gui window = new Gui(theBoard);

			for (int j = 0; j < pieces.length/2; j++) {
				try {
					Piece piece = new Piece();
					piece.setPiecePosition(new Position(j,j));
					piece.setCrown(false);
					piece.setPieceImage(window.scale(
							ImageIO.read(getClass().getResource("peice8x8K.png")), 73, 73));
					pieces[j] = piece;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			theBoard.setPiecePlacement(pieces);
			window.drawBoard(theBoard);
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

}
