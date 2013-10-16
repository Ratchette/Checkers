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

public abstract class Move implements Remote, Serializable{
	private static final long serialVersionUID = 1L;
	
	public abstract int numberOfSteps() throws RemoteException;
	public abstract Piece[] capturedPieces() throws RemoteException;
	public abstract SingleMove[] moveSequence() throws RemoteException;
}
