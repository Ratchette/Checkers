package checkers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

public class CheckersObserver extends UnicastRemoteObject implements GameObserver{
	private static final long serialVersionUID = 1L;

	private CheckersClient client;
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
		System.out.println("Game Over");
		JOptionPane.showMessageDialog(null, "A player quit the game", "Game Over",
			    JOptionPane.INFORMATION_MESSAGE);
//		System.exit(0);

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
	
	public void startGame(Gui myGui, CheckersClient myClient){
		this.client = myClient;
		this.display = myGui;
		this.myTurn = PlayerInfo.OBSERVER;
	}
	
	public void stopWatching(){
		this.client.stopWatching();
	}
	
	public void sendResignation(){
		client.sendResignation();
	}

}
