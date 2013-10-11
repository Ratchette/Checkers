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

    void Position(int xPos, int yPos) {
         x = xPos;
         y = yPos;
    }

    int getX () {
         return x;
    }

    int getY () {
         return y;
    }

    void setX (int xPos) {
         x = xPos;
    }

    void setY (int yPos) {
         y = yPos;
    }

}