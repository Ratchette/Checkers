/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

class Position {

    private int x = 0;
    private int y = 0;

    public Position(int xPos, int yPos) throws Exception {
    	if(xPos < 0) {
    			throw new Exception("Ivalid position: The x position " + xPos + 
    					" is not a valid position.");
    	}
    	
    	if(yPos < 0) {
			throw new Exception("Ivalid position: The y position " + yPos + 
					" is not a valid position.");
    	}
    	
        x = xPos;
        y = yPos;
    }

    int getX () {
         return x;
    }

    int getY () {
         return y;
    }

    void setX (int xPos) throws Exception {
    	if(xPos < 0) {
			throw new Exception("Ivalid position: The x position " + xPos + 
					" is not a valid position.");
    	}
        x = xPos;
    }

    void setY (int yPos) throws Exception {
    	if(yPos < 0) {
			throw new Exception("Ivalid position: The y position " + yPos + 
					" is not a valid position.");
    	}
        y = yPos;
    }

}