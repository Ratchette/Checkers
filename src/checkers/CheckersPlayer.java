package checkers;

import java.net.InetAddress;
import java.rmi.RemoteException;

public class CheckersPlayer implements Player {
	private Board theBoard;
	private PlayerInfo myID;

	public CheckersPlayer(int gameType, PlayerInfo myName){
		myID = myName;
		
		try {
			theBoard = new Board(gameType);
			// TODO - needs more stuff
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	
	@Override
	public String considerGame(GameDesign aGame) throws RemoteException {
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

	@Override
	public PlayerInfo getPlayerInfo() throws RemoteException {
		return new PlayerInfo(myID);
	}
}
