public abstract class Move {
	
	public int numberOfSteps();

	public Piece[] capturedPieces();

	public SingleMove[] moveSequence();
}
