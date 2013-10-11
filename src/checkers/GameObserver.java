/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public class GameObserver{
    
    public String move(Move playerMove) {
    	return new String();
    }
    
	public String playerResigned(PlayerInfo aPlayer, char code, String aMessage) {
		return new String();
	}

	public void gameOver (PlayerInfo winner)
	{
		//To be implemented
	}
    
}