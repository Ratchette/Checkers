
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
	public void startGame(PlayerInfo player, String gameType) {
		// Create a new board

		Board theBoard;
		try {
			Piece pieces[] = new Piece[36];
			theBoard = new Board(new BoardDesign(gameType), pieces );
			Gui window = new Gui(theBoard);
			int count = 0;
			for (int i = 0; i< 3; i++) {
				for (int j = 0; j < theBoard.getTheBoard().getGridSize(); j++) {
					if ((i + j) % 2 == 0) {
						try {
							Piece piece = new Piece();
							piece.setPiecePosition(new Position(j,i));
							piece.setCrown(false);
							piece.setColour('b'); 
							piece.setPieceImage(window.scale(
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
							piece.setPieceImage(window.scale(
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
