/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public class GameInfo {
	
	private GameDesign theGame;
	private Board currentBoard;
	private PlayerInfo player1;
	private PlayerInfo player2;
	private int playerTurn;
	private int currentRound;

	public GameInfo() {
	
	}
	
	public GameInfo(GameDesign theGame, Board currentBoard, PlayerInfo player1, PlayerInfo player2, int playerTurn, int currentRound) {
		this.theGame = theGame;
		this.currentBoard = currentBoard;
		this.player1 = player1;
		this.player2 = player2;
		this.playerTurn = playerTurn;
		this.currentRound = currentRound;
	}

	public GameDesign getTheGame() {
		return theGame;
	}
	
	public void setTheGame(GameDesign theGame) {
		this.theGame = theGame;
	}

	public Board getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
	}

	public PlayerInfo getPlayer1() {
		return player1;
	}

	public void setPlayer1(PlayerInfo player1) {
		this.player1 = player1;
	}

	public PlayerInfo getPlayer2() {
		return player2;
	}

	public void setPlayer2(PlayerInfo player2) {
		this.player2 = player2;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public int getCurrentRoud() {
		return currentRound;
	}

	public void setCurrentRoud(int currentRound) {
		this.currentRound = currentRound;
	}
}
