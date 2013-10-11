public Class Board {
	private BoardDesign theBoard;
	private Piece[] piecePlacement;
	
	public Board() {

	}
	
	public Board(BoardDesign theBoard, Piece[] piecePlacement) {
		this.theBoard = theBoard;
		this.piecePlacement = piecePlacement;
	}	
	
	public BoardDesign getTheBoard() {
		return theBoard;
	}
	
	public void setTheBoard(BoardDesign theBoard) {
		this.theBoard = theBoard;
	}

	public Piece[] getPiecePlacement() {
		return piecePlacement;
	}
	
	public void setPiecePlacement(Piece[] piecePlacement) {
		this.piecePlacement = piecePlacement;
	}
}
