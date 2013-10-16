package checkers;

import java.rmi.RemoteException;

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
	public void startGame() throws RemoteException {
		// Create a new board
		
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
