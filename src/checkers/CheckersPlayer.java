package checkers;

import java.rmi.RemoteException;

public class CheckersPlayer implements Player {
	private GameInfo myGame;
	public Board theBoard;

	public CheckersPlayer(int gameType){
		
		try {
			// FIX THIS
			theBoard = new Board(gameType);
			// TODO - needs more stuff
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	
	@Override
	public Object considerGame(GameDesign aGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
