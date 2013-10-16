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


public class Board implements Remote, Serializable{
	private static final long serialVersionUID = 1L;
	
	private BoardDesign theBoard;
	private Piece[] piecePlacement;
	
	public Board(Board copy) throws RemoteException
	{
		this.theBoard = new BoardDesign(copy.getTheBoard());
		this.piecePlacement = new Piece[copy.getPiecePlacement().length];
		
		for(int i=0; i<copy.getPiecePlacement().length; i++)
		{
			this.piecePlacement[i] = new Piece(copy.getPiecePlacement()[i]);
		}
		
	}
	
	public Board(BoardDesign theBoard, Piece[] piecePlacement) throws RemoteException 
	{
		this.theBoard = theBoard;
		this.piecePlacement = piecePlacement;
	}	
	
	public BoardDesign getTheBoard() throws RemoteException 
	{
		return theBoard;
	}
	
	public void setTheBoard(BoardDesign theBoard) throws RemoteException 
	{
		this.theBoard = theBoard;
	}

	public Piece[] getPiecePlacement() throws RemoteException 
	{
		return piecePlacement;
	}
	
	public void setPiecePlacement(Piece[] piecePlacement) throws RemoteException 
	{
		this.piecePlacement = piecePlacement;
	}
}
