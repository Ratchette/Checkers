package checkers;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

public class CheckersPlayer extends UnicastRemoteObject implements Player {
	private static final long serialVersionUID = 1L;
	
	private Server server;
	private Board theBoard;
	private PlayerInfo myID;

	public CheckersPlayer(PlayerInfo myName) throws RemoteException{
		myID = myName;
	}
	
	
	
	private int chooseGameType(){
		// ensure that the order the game types are displayed in matches their declarations above
		Object[] options = {"British", "American", "International", "Canadian", "Anti-Checkers", "Quit"};
		int response;
		
		response = JOptionPane.showOptionDialog(null,
				"Please choose a game style",
				"CIS 4150 - Checkers Clinet",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		
		if(response == 5)
			System.exit(0);
		
		return response;
	}
	
	public CheckersPlayer(int gameType, PlayerInfo myName) throws Exception{
		myID = myName;
		
		try {
			theBoard = new Board(gameType);
			// TODO - needs more stuff
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	
	public Board getBoard() throws Exception{
		return new Board(theBoard);
	}
	public void setBoard(Board newBoard){
		theBoard = newBoard;
	}

	@Override
	public void startGame() throws RemoteException {
		// FIXME This function needs to be rewritten
	}

	@Override
	public String move(Move playersMove) throws RemoteException {
		try {
			// Move the piece
			SingleMove move = (SingleMove) playersMove;
			Position pos = move.getEndPosition();
			move.getPieceBeignMoved().setPiecePosition(pos);

			// Check if the piece is now a king
			if (move.getPieceBeignMoved().getColour() == Piece.WHITE && pos.getY() == 0) {
				move.getPieceBeignMoved().turnKing();
			}
			if (move.getPieceBeignMoved().getColour() == Piece.BLACK
					&& pos.getY() == theBoard.getTheBoard().gridSize) {
				move.getPieceBeignMoved().turnKing();
			}
			
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



	@Override
	public String considerGame(GameDesign aGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void youWin() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
