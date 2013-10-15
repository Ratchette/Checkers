/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class GameInfo implements Remote, Serializable{
	private static final long serialVersionUID = 1L;
	
	private GameDesign theGame;
	private Board currentBoard;
	private PlayerInfo player1;
	private PlayerInfo player2;
	private int playerTurn;
	private int currentRound;
	
	public GameInfo(GameDesign theGame, Board currentBoard, PlayerInfo player1, PlayerInfo player2, int playerTurn, int currentRound) throws RemoteException{
		this.theGame = theGame;
		this.currentBoard = currentBoard;
		this.player1 = player1;
		this.player2 = player2;
		this.playerTurn = playerTurn;
		this.currentRound = currentRound;
	}
	
	public GameInfo(GameInfo copy) throws RemoteException{
		this.theGame = new GameDesign(copy.theGame);
		this.currentBoard = new Board(copy.currentBoard);
		this.player1 = new PlayerInfo(copy.player1);
		this.player1 = new PlayerInfo(copy.player1);
		this.playerTurn = copy.playerTurn;
		this.currentRound = copy.currentRound;
	}

	public GameDesign getTheGame() throws RemoteException{
		return theGame;
	}
	
	public void setTheGame(GameDesign theGame) throws RemoteException{
		this.theGame = theGame;
	}

	public Board getCurrentBoard() throws RemoteException{
		return currentBoard;
	}

	public void setCurrentBoard(Board currentBoard) throws RemoteException{
		this.currentBoard = currentBoard;
	}

	public PlayerInfo getPlayer1() throws RemoteException{
		return player1;
	}

	public void setPlayer1(PlayerInfo player1) throws RemoteException{
		this.player1 = player1;
	}

	public PlayerInfo getPlayer2() throws RemoteException{
		return player2;
	}

	public void setPlayer2(PlayerInfo player2) throws RemoteException{
		this.player2 = player2;
	}

	public int getPlayerTurn() throws RemoteException{
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) throws RemoteException{
		this.playerTurn = playerTurn;
	}

	public int getCurrentRoud() throws RemoteException{
		return currentRound;
	}

	public void setCurrentRoud(int currentRound)throws RemoteException {
		this.currentRound = currentRound;
	}
	

}
