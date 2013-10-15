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

public class Position implements Remote, Serializable{
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;

	
	public Position(int xPos, int yPos) throws Exception {
		if(xPos < 1) {
			throw new Exception("Ivalid position: The x position " + xPos + 
					" is not a valid position.");
		}

		if(yPos < 1) {
			throw new Exception("Ivalid position: The y position " + yPos + 
					" is not a valid position.");
		}

		x = xPos;
		y = yPos;
	}

	public Position() throws RemoteException{
		x = 0;
		y = 0;
	}
	
	public Position(Position copy) throws RemoteException{
		this.x = copy.getX();
		this.y = copy.getY();
	}

	public int getX (){
		return x;
	}

	public int getY (){
		return y;
	}

	public void setX (int xPos) throws Exception {
		if(xPos < 1) {
			throw new Exception("Ivalid position: The x position " + xPos + 
					" is not a valid position.");
		}

		x = xPos;
	}

	public void setY (int yPos) throws Exception {
		if(yPos < 1) {
			throw new Exception("Ivalid position: The y position " + yPos + 
					" is not a valid position.");
		}

		y = yPos;
	}

	@Override
	public String toString() {
		return "x: " + this.x + " y: " + this.y;
	}

	@Override
	public boolean equals(Object obj) {
		Position positionToCompare;
		if (obj instanceof Position) {
			positionToCompare = (Position) obj;
			return (this.getX() == positionToCompare.getX() && this.getY() == positionToCompare.getY());
		}
		return false;
	}

}