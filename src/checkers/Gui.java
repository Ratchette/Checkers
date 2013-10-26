/**
 * @author Rafael Aquino de Carvalho
 * @author Danielle Fudger
 * @author Ben Douek
 * @author Jennifer Winer
 *
 */

package checkers;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*; //notice javax
import javax.swing.border.LineBorder;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Gui implements ActionListener {
	/* Create Window */
	public static final int windowSize = 600;
	public static final int menuSize = 50;
	public static final int numMenuButtons = 3;

	/* Create button array for grids up to 12x12 */
	private JFrame window;
	private JButton square[][];
	private JButton resign;
	private JButton turn;
	private JButton connect;
	private JPanel board;
	private JPanel menuButtons;

	
	private Board currentBoard;
	private Piece currentPiece;
	private char myColour;
	private ArrayList<Move> potentialMoves;

	
	/*************************************************************************
	 * 							Board Creation
	 *************************************************************************/
	public Gui(Board theBoard, char colour) throws Exception {
		myColour = colour;
		currentBoard = theBoard;
		currentPiece = null;
		potentialMoves = null;
		
		window = new JFrame("Premium Checkers Deluxe 3000");
		window.setSize(windowSize, windowSize + menuSize);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		
		createMenu();
		createBoard(theBoard.getBoardDesign());

		window.getContentPane().add(menuButtons, BorderLayout.NORTH);
		window.getContentPane().add(board, BorderLayout.CENTER);

		/* Make The Window Visible */
		window.setVisible(true);
		drawBoard(theBoard);
	}
	
	private void createMenu(){
		// Create Menu Buttons
		this.resign = new JButton();
		this.turn = new JButton();
		this.connect = new JButton();
		
		// Create panels and layout
		GridLayout menu = new GridLayout(1, numMenuButtons);
		menuButtons = new JPanel(menu);
		menuButtons.setSize(windowSize, menuSize);
		
		// resign button
		resign.setText("Resign");
		menuButtons.add(resign);
		
		// connect button
		connect.setText("Stop Watching");
		menuButtons.add(connect);
		
		// turn button
//		if (!currentPlayer.getName().equals("Opponent")) {
//			turn.setText("GO");
//		} else {
			turn.setText("STOP");
//		}
			
		turn.setBackground(Color.gray);
	    menuButtons.add(turn);
	}
	
	private void createBoard(BoardDesign boardDesign) throws RemoteException{
		int  gridSize;
		Font font;
		
		font = new Font("Helvetica", Font.PLAIN, 30);
		
		gridSize = boardDesign.gridSize;
		square = new JButton[gridSize][];
		for(int i=0; i<square.length; i++)
			square[i] = new JButton[gridSize];
		
		GridLayout grid = new GridLayout(gridSize, gridSize);
		board = new JPanel(grid);
		board.setSize(windowSize, windowSize);

		/* Add Buttons To The Board */
		for (int i = 0; i < gridSize; i++) {
			for(int j=0; j<gridSize; j++){
				
				square[i][j] = new JButton();
				
				// set the background colours of the dark tiles depending on the game type
				if ((i + j) % 2 == 0) {	
					if (boardDesign.getGameEncoding() == BoardDesign.BRITISH
							|| boardDesign.getGameEncoding() == BoardDesign.AMERICAN
							|| boardDesign.getGameEncoding() == BoardDesign.ANTICHECKERS) {
						square[i][j].setBackground(Color.red);
					}
					
					else{
						square[i][j].setBackground(Color.white);
					}
				} 
				
				// set the background colours of the light tiles depending on the game type
				else {
					if (boardDesign.getGameEncoding() == BoardDesign.BRITISH
							|| boardDesign.getGameEncoding() == BoardDesign.AMERICAN
							|| boardDesign.getGameEncoding() == BoardDesign.INTERNATIONAL
							|| boardDesign.getGameEncoding() == BoardDesign.ANTICHECKERS) {
						square[i][j].setBackground(Color.black);
					}
					
					else {
						square[i][j].setBackground(Color.red);
					}
				}
	
				square[i][j].addActionListener(this);
				
				square[i][j].setFont(font);
				board.add(square[i][j]);
				
				// NOTE: i == y  and  j == x
				square[i][j].setName(Integer.toString(j) + "," + Integer.toString(i));
			}
		}
	}
	
	
	
	/*************************************************************************
	 * 							Board Drawing
	 *************************************************************************/
	public void drawBoard(Board theBoard) throws Exception{
		Piece[] pieces;
		int gridSize;
		int x, y;
		
		pieces = theBoard.getPiecePlacement();
		gridSize = theBoard.getBoardDesign().gridSize;
		resetGui(gridSize);

		for (int i = 0; i < pieces.length; i++) {
			if(pieces[i] != null){
				x = pieces[i].getPiecePosition().getX();
				y = pieces[i].getPiecePosition().getY();

				// NOTE: the reversal of X and Y is VITAL. Think about how a 2D array is set up!
				square[y][x].setIcon(new ImageIcon(scale(ImageIO.read(getClass().getResource(
								pieces[i].getImageURL())), windowSize / gridSize, windowSize / gridSize)));
			}
		}
	}

	public void resetGui(int gridSize) {
		LineBorder border = new LineBorder(Color.BLACK, 1);
		
		for (int i = 0; i < gridSize; i++) {
			for(int j=0; j < gridSize; j++){
				square[i][j].setIcon(null);
				square[i][j].setBorder(border);
			}
		}
	}


	/**
	 * BEN - Please cite this function ....
	 * @param img
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = img;
		BufferedImage scratchImage = null;
		Graphics2D g2 = null;

		int w = img.getWidth();
		int h = img.getHeight();

		int prevW = w;
		int prevH = h;

		do {
			if (w > targetWidth) {
				w /= 2;
				w = (w < targetWidth) ? targetWidth : w;
			}

			if (h > targetHeight) {
				h /= 2;
				h = (h < targetHeight) ? targetHeight : h;
			}

			if (scratchImage == null) {
				scratchImage = new BufferedImage(w, h, type);
				g2 = scratchImage.createGraphics();
			}

			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

			prevW = w;
			prevH = h;
			ret = scratchImage;
		} while (w != targetWidth || h != targetHeight);

		if (g2 != null) {
			g2.dispose();
		}

		if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
			scratchImage = new BufferedImage(targetWidth, targetHeight, type);
			g2 = scratchImage.createGraphics();
			g2.drawImage(ret, 0, 0, null);
			g2.dispose();
			ret = scratchImage;
		}

		return ret;

	}
	
	/*************************************************************************
	 * 							Action Listeners
	 *************************************************************************/
	public void actionPerformed(ActionEvent a){
		JButton pressedButton; 
		ArrayList<Move> possibleMoves;
		String[] buttonCoordinates;
		int x, y;
		
		pressedButton = (JButton) a.getSource();
		buttonCoordinates = pressedButton.getName().split(",");
		x = Integer.parseInt(buttonCoordinates[0]);
		y = Integer.parseInt(buttonCoordinates[1]);
		
		
		// user has clicked on a destination move
		try{
			if(currentPiece != null){
				new SingleMove(currentPiece, /* piece captured */ null, new Position(x, y)); // FIXME - this line does nothing
				drawBoard(currentBoard);
				
				changeTurn();
				clearMoves();
			}
	
			// user has selected a piece to move
			else {
				currentPiece = currentBoard.getPieceAtPosition(x, y);
				
				if(currentPiece.getColour() == myColour){
					highlightSquare(x, y);
					possibleMoves = currentBoard.getPossibleMoves(x, y);
					showMoves(possibleMoves, x, y);
				}
				
				else{
					clearMoves();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void highlightSquare(int x, int y) {
		this.square[y][x].setBorder(new LineBorder(Color.GREEN, 2));
	}

	
	/*************************************************************************
	 * 							Moves Calculations
	 *************************************************************************/
	public void showMoves(ArrayList<Move> moves, int x, int y) throws RemoteException{
		int finalMove;
		Position finalPosition;
		
		clearMoves();
		currentPiece = currentBoard.getPieceAtPosition(x, y);
		
		for (int i = 0; i < moves.size(); i++) {
			finalMove = moves.get(i).moveSequence().length - 1;
			finalPosition = moves.get(i).moveSequence()[finalMove].getEndPosition();
			highlightSquare(finalPosition.getX(), finalPosition.getY());
		}
		
		potentialMoves = moves;
	}

	private void clearMoves(){
		potentialMoves = new ArrayList<Move>();
		currentPiece = null;
	}
	
	
	// FIXME this function could use some work
	public void changeTurn() {
		if (turn.getText().equals("STOP")) {
			turn.setText("GO");
			turn.setBackground(Color.green);
		} else if (turn.getText().equals("GO")) {
			turn.setText("STOP");
			turn.setBackground(Color.red);
		}
	}
}