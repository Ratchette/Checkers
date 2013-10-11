/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

public class BoardDesign {

	private int n;
	private char blackCorner;

	public BoardDesign() {
	}

	public BoardDesign(int n, char blackCorner) {
		this.n = n;
		this.blackCorner = blackCorner;
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
	public char getBlackCorner() {
		return blackCorner;
	}
	
	public void setBlackCorner(char blackCorner) {
		this.blackCorner = blackCorner;
	}
}
