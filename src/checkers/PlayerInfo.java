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

public class PlayerInfo implements Remote, Serializable{
	private static final long serialVersionUID = 1L;
	
	// Please refer to these variables instead of using undocumented integers
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	public static final int OBSERVER = 3;
	
	String name;
	
	public PlayerInfo(String name) throws RemoteException{
		this.name = name;
	}
	public PlayerInfo(PlayerInfo copy) throws RemoteException{
		this.name = copy.getName();
	}
	
	public String getName() throws RemoteException{
		return name;
	}
	
	public void setName(String name) throws RemoteException{
		this.name = name;
	}
	
	public boolean equals(Object other){
		if(other == null)
			return false;
		
		if(! (other instanceof PlayerInfo))
			return false;
		
		PlayerInfo otherPlayer = (PlayerInfo)other;
		try{
			return (otherPlayer.getName().equals(this.getName()));
		}
		catch(Exception e){
			System.out.println("Exception Occured");
			e.printStackTrace();
			return false;
		}
	}
}
