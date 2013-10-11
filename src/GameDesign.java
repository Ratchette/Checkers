public class GameDesign {

	private int gameCode;
	private BoardDesign gameBoardDesign;
	private Board initialBoard;
	
	public GameDesign() {

	}
	
	public GameDesign(int gameCode, BoardDesign gameBoardDesign, Board initialBoard) {
		this.gameCode = gameCode;
		this.gameBoardDesign = gameBoardDesign;
		this.initialBoard = initialBoard;
	}
	
	public 	int getGameCode() {
		return gameCode;
	}
	
	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}	

	public BoardDesign getGameBoardDesign() {
		return gameBoardDesign;
	}

	public void setGameBoardDesign() {
		this.gameBoardDesign = gameBoardDesign;
	}
}
