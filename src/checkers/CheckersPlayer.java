package checkers;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

public class CheckersPlayer extends UnicastRemoteObject implements Player {
	private static final long serialVersionUID = 1L;

	private Server server;
	private GameInfo myGame;
	private PlayerInfo myID;
	private Gui display;

	public CheckersPlayer(Server server, PlayerInfo myName) throws RemoteException {
		this.server = server;
		this.myID = myName;
		this.display = null;
	}



	public CheckersPlayer(int gameType, PlayerInfo myName) throws Exception {
		// myID = myName;
		//
		// try {
		// theBoard = new Board(gameType);
		// // TODO - needs more stuff
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		//
		// }
	}

	public GameInfo getMyGame() {
		return myGame;
	}

	public void setMyGame(GameInfo myGame) {
		this.myGame = myGame;
	}

	@Override
	public void startGame() throws RemoteException {
		myGame.setCurrentRound(1);
		myGame.setPlayerTurn(1);
		
		 try {
			 this.display = new Gui(myGame.getCurrentBoard(), this.myID);
		 } catch (Exception e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
	}

	@Override
	public String move(Move playersMove) throws RemoteException {
		try {
			// Move the piece
			SingleMove move = (SingleMove) playersMove;
			Position pos = move.getEndPosition();
			move.getPieceBeignMoved().setPiecePosition(pos);

			// Check if the piece is now a king
			if (move.getPieceBeignMoved().getColour() == Piece.WHITE
					&& pos.getY() == 0) {
				move.getPieceBeignMoved().turnKing();
			}
			if (move.getPieceBeignMoved().getColour() == Piece.BLACK
					&& pos.getY() == myGame.getCurrentBoard().getTheBoard().gridSize) {
				move.getPieceBeignMoved().turnKing();
			}

			this.display.drawBoard(myGame.getCurrentBoard());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void playerResigned(PlayerInfo aPlayer, char code, String aMessage)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public PlayerInfo getPlayerInfo() throws RemoteException {
		return new PlayerInfo(myID);
	}

}
