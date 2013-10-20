package checkers;

import java.rmi.RemoteException;

public class CheckersPlayer implements Player {
	private GameInfo myGame;
	public Board theBoard;

	@Override
	public Object considerGame(GameDesign aGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame() throws RemoteException {
		// FIXME This function needs to be rewritten
		
		String gameType = "British";
		
		// Create a new board
		try {
			Piece pieces[] = new Piece[36];
			theBoard = new Board(new BoardDesign(gameType), pieces);

			
			
			int count = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < theBoard.getTheBoard().getGridSize(); j++) {
					if ((i + j) % 2 == 0) {
						try {
							Piece piece = new Piece(new Position(j, i), false, Piece.BLACK);
							piece.setPieceImage(piece.getPieceImage(), Gui.gridLength / theBoard.getTheBoard().gridSize);
							pieces[count] = piece;
							count++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			for (int i = theBoard.getTheBoard().getGridSize() - 1; i > theBoard
					.getTheBoard().getGridSize() - 4; i--) {
				for (int j = 0; j < theBoard.getTheBoard().getGridSize(); j++) {
					if ((i + j) % 2 == 0) {
						try {
							Piece piece = new Piece(new Position(j, i), false, Piece.WHITE);
							piece.setPieceImage(piece.getPieceImage(), Gui.gridLength / theBoard.getTheBoard().gridSize);
							pieces[count] = piece;
							count++;
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
	public void youWin() throws RemoteException {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage)
			throws RemoteException {
		// TODO Auto-generated method stub

	}
}
