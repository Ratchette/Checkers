/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 */

package checkers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Board implements Remote, Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final int NUM_PIECES = 36; // the maximum number of pieces in any game

	private BoardDesign boardDesign;
	private Piece[] piecePlacement;

	/**
	 * NOTES
	 * 
	 * The top of the board is y=0, so to move pieces down, ADD to y
	 */
	
	
	// regular constructor
	public Board(int gameType) throws Exception {
		int currentPiece;
		int i, j;
		
		boardDesign = new BoardDesign(gameType);
		
		// initialize the pieces
		piecePlacement = new Piece[NUM_PIECES];
		for(i=0; i<piecePlacement.length; i++)
			piecePlacement[i] = null;
		
		currentPiece = 0;
		
		// create all of the black pieces
		for (i = 0; i < 3; i++) {
			for (j = 0; j < boardDesign.gridSize; j++) {
				if ((i + j) % 2 == 1) {
					// NOTE: i == y  and  j == x
					Piece piece = new Piece(new Position(j, i), false, Piece.WHITE);
					piecePlacement[currentPiece++] = piece;
				}
			}
		}

		// create all of the white pieces
		for (i = boardDesign.gridSize - 1; i > boardDesign.gridSize - 4; i--) {
			for (j = 0; j < boardDesign.gridSize; j++) {
				if ((i + j) % 2 == 1) {
					// NOTE: i == y  and  j == x
					Piece piece = new Piece(new Position(j, i), false, Piece.BLACK);
					piecePlacement[currentPiece++] = piece;
				}
			}
		}
	}
	
	// Copy constructor
	public Board(Board copy) throws RemoteException {
		boardDesign = new BoardDesign(copy.getBoardDesign());
		Piece[] copyPieces = copy.getPiecePlacement();
		
		piecePlacement = new Piece[NUM_PIECES];
		for (int i = 0; i < piecePlacement.length && copyPieces[i] != null; i++) {
			piecePlacement[i] = new Piece(copyPieces[i]);
		}

	}

	// getter for the board
	public BoardDesign getBoardDesign() throws RemoteException {
		return boardDesign;
	}

	// setter for the board
	public void setBoardDesign(BoardDesign design) throws Exception {
		if (design == null) {
			throw new Exception("Wrong value");
		}
		boardDesign = design;
	}

	// getter for piece placement
	public Piece[] getPiecePlacement() throws RemoteException {
		return piecePlacement;
	}

	// setter for piecePlacement
	public void setPiecePlacement(Piece[] piecePlacement) throws Exception {
		if (piecePlacement == null) {
			throw new Exception("Wrong value");
		}
		this.piecePlacement = piecePlacement;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Board)) {
			return false;
		}
		Board newObj = (Board) obj;
		try {
			return newObj.getBoardDesign().equals(this.getBoardDesign())
					&& Arrays.equals(newObj.getPiecePlacement(),
							this.getPiecePlacement());
		} catch (RemoteException e) {
			return false;
		}
	}
	
	public Piece getPieceAtPosition(int x, int y){
		Position pos;
		
		for(int i=0; i<piecePlacement.length; i++){
			if (piecePlacement[i] != null) {
				pos = piecePlacement[i].getPiecePosition();
				if(pos.getX() == x && pos.getY() == y)
					return piecePlacement[i];
			}
		}
		
		return null;
	}
	
	public int getPieceIndex(Position p, Piece[] pArray){
		Position pos;
		
		for(int i=0; i<pArray.length; i++){
			if (pArray[i] != null) {
				pos = pArray[i].getPiecePosition();
				if(pos.getX() == p.getX() && pos.getY() == p.getY())
					return i;
			}
		}
		
		return -1;
	}
	
	

	// FIXME this function must be rewritten
	public ArrayList<Move> getPossibleMoves(Piece piece) throws Exception {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		//Basic jump West
		if( checkWestDiagonal(piece, 1) && !checkWestDiagonal(piece, 2)){
			int x = getWestDiagonal(piece, 1).getX();
			int y = getWestDiagonal(piece, 1).getY();
			Piece temp = getPieceAtPosition(x, y);
			if(temp.getColour() != piece.getColour()){
				moves.add(new SingleMove(piece, temp, getWestDiagonal(piece, 2) ) );
			}
		}
		//Basic West Move logic no jumping
		else if( !checkWestDiagonal(piece, 1) ){
			moves.add(new SingleMove(piece, null, getWestDiagonal(piece, 1)) );
		}
		
		
		//Basic jump East
		if( checkEastDiagonal(piece, 1) && !checkEastDiagonal(piece, 2)){
			int x = getEastDiagonal(piece, 1).getX();
			int y = getEastDiagonal(piece, 1).getY();
			Piece temp = getPieceAtPosition(x, y);
			if( piece.getColour() != temp.getColour() ){
				moves.add(new SingleMove(piece, temp, getEastDiagonal(piece, 2) ) );
			}
		}
		//Basic East Move logic no jumping
		else if( !checkEastDiagonal(piece, 1) ){
			moves.add(new SingleMove(piece, null, getEastDiagonal(piece, 1)) );
		}
		
		
		return moves;
	}
	
	
	
	//Returns true if position is occupied
	public boolean checkWestDiagonal(Piece p, int space){
		Position pos = p.getPiecePosition();
		int x = pos.getX() - space;
		int y = -1;
		
		if(p.getColour() == Piece.BLACK)
			y = pos.getY() - space;
		if(p.getColour() == Piece.WHITE)
			y = pos.getY() + space;
		
		Position posDiagonal = new Position(x,y);
		
		
		if(positionOccupied(posDiagonal)  || x > boardDesign.gridSize || y > boardDesign.gridSize || x < 0 || y < 0){
			return true;
		}
		
		return false;
	}
	
	private Position getWestDiagonal(Piece p, int space){
		Position pos = p.getPiecePosition();
		int x = pos.getX() - space;
		int y = -1;
		
		if(p.getColour() == Piece.BLACK)
			y = pos.getY() - space;
		if(p.getColour() == Piece.WHITE)
			y = pos.getY() + space;
		
		Position posDiagonal = new Position(x,y);
		
		return posDiagonal;
	}
	
	
	public boolean checkEastDiagonal(Piece p, int space){
		Position pos = p.getPiecePosition();
		int x = pos.getX() + space;
		int y = -1;
		
		if(p.getColour() == Piece.BLACK)
			y = pos.getY() - space;
		if(p.getColour() == Piece.WHITE)
			y = pos.getY() + space;
		
		Position posDiagonal = new Position(x,y);
		
		
		if(positionOccupied(posDiagonal) || x > boardDesign.gridSize || y > boardDesign.gridSize || x < 0 || y < 0){
			return true;
		}
		
		return false;
	}
	
	private Position getEastDiagonal(Piece p, int space){
		Position pos = p.getPiecePosition();
		int x = pos.getX() + space;
		int y = -1;
		
		if(p.getColour() == Piece.BLACK)
			y = pos.getY() - space;
		if(p.getColour() == Piece.WHITE)
			y = pos.getY() + space;
		
		Position posDiagonal = new Position(x,y);
		
		return posDiagonal;
	}
	
	
	public boolean validateMove(Move move) throws RemoteException{
		Piece p = move.moveSequence()[0].getPieceBeignMoved();
		int finalMoveP = move.moveSequence().length - 1;
		
		try {
			ArrayList<Move> moves = getPossibleMoves(p);
			for (int i = 0; i < moves.size(); i++) {
				int finalMove = moves.get(i).moveSequence().length - 1;
				Position finalPosition = moves.get(i).moveSequence()[finalMove].getEndPosition();
				if ( finalPosition.equals(move.moveSequence()[finalMoveP].getEndPosition()) ){
					Piece capturedPiece = moves.get(i).moveSequence()[finalMove].getCapturedPiece();
					if (capturedPiece != null) {
						move.moveSequence()[finalMoveP].setCapturedPiece(capturedPiece);
					}
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	
	
	private boolean positionOccupied(Position pos){
		for (int i = 0; i < piecePlacement.length; i++){
			if(piecePlacement[i] != null){
				if(piecePlacement[i].getPiecePosition().equals(pos)){
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * 
	 * @param filename
	 * @throws Exception
	 * 			If the file does not exist / cannot be oppened
	 */
	public Board(String filename) throws Exception{
		Scanner file;
		String inputLine;
		String[] squares;
		
		int boardSize;
		int currentPiece;
		int i, j;
		
		file = new Scanner(new BufferedReader(new FileReader(filename)));
		
		// Assumes that the first line in the file is the Board design
		inputLine = file.nextLine().trim();
		boardDesign = new BoardDesign(inputLine);
		boardSize = boardDesign.gridSize;	
		
		// initialize the pieces
		piecePlacement = new Piece[NUM_PIECES];
		for(i=0; i<piecePlacement.length; i++)
			piecePlacement[i] = null;
		currentPiece = 0;
		
		// import pieces
		for(i=0; i<boardSize; i++){
			inputLine = file.nextLine().trim();
			squares = inputLine.split(",");
			
			for(j=0; j<boardSize; j++){
				if(squares[i].equals("b"))
					piecePlacement[currentPiece++] = new Piece(new Position(j, i), false, Piece.BLACK);
				
				if(squares[i].equals("B"))
					piecePlacement[currentPiece++] = new Piece(new Position(j, i), true, Piece.BLACK);

				if(squares[i].equals("w"))
					piecePlacement[currentPiece++] = new Piece(new Position(j, i), false, Piece.WHITE);
				
				if(squares[i].equals("W"))
					piecePlacement[currentPiece++] = new Piece(new Position(j, i), true, Piece.WHITE);
			}
		}

		file.close();
	}
}
