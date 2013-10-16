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

public class BoardDesign implements Remote, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int n;
	private char blackCorner;

	public BoardDesign(BoardDesign copy)  throws RemoteException
	{
		this.n = copy.getN();
		this.blackCorner = copy.getBlackCorner();
	}

	public BoardDesign(int n, char blackCorner) throws RemoteException 
	{
		this.n = n;
		this.blackCorner = blackCorner;
	}
	
	public int getN() throws RemoteException 
	{
		return n;
	}

	public void setN(int n) throws RemoteException 
	{
		this.n = n;
	}
	
	public char getBlackCorner() throws RemoteException 
	{
		return blackCorner;
	}
	
	public void setBlackCorner(char blackCorner) throws RemoteException 
	{
		this.blackCorner = blackCorner;
	}
}
