package checkers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CheckersObserver extends UnicastRemoteObject implements GameObserver{
	private static final long serialVersionUID = 1L;

	private Gui display;
	private GameInfo myGame;
	private PlayerInfo myID;
	public int myTurn;
	

	public CheckersObserver(PlayerInfo myName) throws RemoteException {
		this.myID = myName;
	}
	
	public GameInfo getMyGame() {
		return myGame;
	}

	public void setMyGame(GameInfo myGame) {
		this.myGame = myGame;
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
	
	public boolean isMyTurn(){
		return this.myTurn == this.myGame.getPlayerTurn();
	}


	@Override
	public void receiveMove(Move playersMove) throws RemoteException {
		this.myGame.makeMove(playersMove);
		
		try {
			this.display.drawBoard(myGame.getCurrentBoard());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startGame(Gui myGui){
		this.display = myGui;
		this.myTurn = PlayerInfo.OBSERVER;
	}

}
