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

public class BoardDesign implements Remote, Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int BRITISH 		= 0;
	public static final int AMERICAN	 	= 1;
	public static final int INTERNATIONAL 	= 2;
	public static final int CANADIAN 		= 3;
	public static final int ANTICHECKERS 	= 4;

	public final int gridSize;
	private char blackCorner;
	private int gameType;

	// copy constructor
	public BoardDesign(BoardDesign copy) {
		this.gridSize = copy.gridSize;
		this.blackCorner = copy.getBlackCorner();
		this.gameType = copy.getGameEncoding();
	}

	// regular constructor
	public BoardDesign(int game) throws Exception {
		gameType = game;
		
		if (game == BRITISH || game == AMERICAN || game == ANTICHECKERS){
			gridSize = 8;
			blackCorner = Piece.WHITE;
		}
		else if (game == INTERNATIONAL) {
			this.gridSize = 10;
			blackCorner = Piece.BLACK;
		}
		else if (game == CANADIAN) {
			this.gridSize = 12;
			blackCorner = Piece.WHITE;
		}
		else{
			throw new Exception("Unkonwn Game type!");
		}
	}
	
	public BoardDesign(String game) throws Exception {
		gameType = gameNametoEncoding(game);
		
		if (gameType == BRITISH || gameType == AMERICAN || gameType == ANTICHECKERS){
			gridSize = 8;
			blackCorner = Piece.WHITE;
		}
		else if (gameType == INTERNATIONAL) {
			this.gridSize = 10;
			blackCorner = Piece.BLACK;
		}
		else if (gameType == CANADIAN) {
			this.gridSize = 12;
			blackCorner = Piece.WHITE;
		}
		else{
			throw new Exception("Unkonwn Game type!");
		}
	}
	
	public String getGameType(){
		switch(this.gameType){
			case 0: return "British";
			case 1: return "American";
			case 2: return "International";
			case 3: return "Canadian";
			default: return "Anti-Checkers";
		}
	}
	
	public int gameNametoEncoding(String game) throws Exception{
		if(game.equalsIgnoreCase("British"))
			return BRITISH;
		if(game.equalsIgnoreCase("American"))
			return AMERICAN;
		if(game.equalsIgnoreCase("International"))
			return INTERNATIONAL;
		if(game.equalsIgnoreCase("Canadian"))
			return CANADIAN;
		if(game.equalsIgnoreCase("AntiCheckers"))
			return ANTICHECKERS;
		
		throw new Exception("Unrecognized game type: " + game);
	}
	
	public int getGameEncoding(){
		return this.gameType;
	}

	// getter for black corner
	public char getBlackCorner() {
		return blackCorner;
	}

	// setter for black corner
	public void setBlackCorner(char blackCorner) throws RemoteException {
		this.blackCorner = blackCorner;
	}

	// setter for game type
	public void setGameType(int game) {
		this.gameType = game;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardDesign)) {
			return false;
		}
		BoardDesign newObj = (BoardDesign) obj;
		return newObj.getBlackCorner() == this.getBlackCorner()
				&& newObj.getGameType() == this.getGameType()
				&& newObj.gridSize == this.gridSize;
	}

}
