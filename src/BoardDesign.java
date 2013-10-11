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
	
	public void setBlackCorner() {
		this.blackCorner = blackCorner;
	}
}
