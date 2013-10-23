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

import javax.swing.*;  //notice javax
import javax.swing.border.LineBorder;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;

public class Gui implements ActionListener 
{

  /*Create Window*/
  private JFrame window = new JFrame("Premium Checkers Deluxe 3000");
  public static final int windowSize = 600;

  /*Create button array for grids up to 12x12*/
  private JButton square[] = new JButton[144];
  private JButton resign = new JButton();
  private JButton turn = new JButton();
  private JButton connect = new JButton();
  private JPanel board;
  private JPanel menuButtons;

  /*Board info */
  private Board currentBoard;
  private PlayerInfo currentPlayer;
  private int currentClick[] = {-1,-1,-1};
  private int gridSize;

  
  
  
  
  /* Initialize GUI */
  public Gui(Board theBoard,PlayerInfo pInfo)
  {
	currentBoard = theBoard;
	currentPlayer = pInfo; 
    try {
		gridSize = currentBoard.getTheBoard().gridSize;
	} catch (Exception e2) {
		e2.printStackTrace();
	}
    
    /* Setup Window */
    window.setSize(windowSize,windowSize+50);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    /* Setup Window, Board and Menu */
    window.setLayout(new BorderLayout());
    GridLayout grid = new GridLayout(gridSize,gridSize);
    GridLayout menu = new GridLayout(1,3);
    board = new JPanel(grid);
    menuButtons = new JPanel(menu);
    board.setSize(windowSize,windowSize);
    menuButtons.setSize(windowSize,50);
    Font font = new Font("Helvetica", Font.PLAIN, 30);
    
    /* Add menu Buttons */
    resign.setText("RESIGN");
    menuButtons.add(resign);
    connect.setText("**CHECKERS**");
    menuButtons.add(connect);
    
    /* Setup Turn Counter in menu*/
    try {
		if( currentPlayer.getName().equals("Player1") ){
			turn.setText("GO");
		}
		else {
			turn.setText("STOP");
		}
	} catch (RemoteException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    turn.setBackground(Color.gray);
    menuButtons.add(turn);
    
    
    /*Add Buttons To The Board*/
    try {
    	int checkerLine = 0;
	    for(int i=0; i<=((gridSize*gridSize)-1); i++){      
	        square[i] = new JButton();
	        /* Set Color of squares depending on type*/
	        /* Anti Checkers not yet known */
	        if (i%gridSize == 0) {
	          checkerLine = 1 + checkerLine;
	        }
	        if( (i + checkerLine)%2 == 0) {
	          if(currentBoard.getTheBoard().getGameEncoding() == 0
	        		  || currentBoard.getTheBoard().getGameEncoding() == 1 ) {
	            square[i].setBackground(Color.red);
	          }
	          if( currentBoard.getTheBoard().getGameEncoding() == 2
	        		  || currentBoard.getTheBoard().getGameEncoding() == 3) {
	            square[i].setBackground(Color.white);
	          }
	        } else {
	          if(currentBoard.getTheBoard().getGameEncoding() == 0
	        		  || currentBoard.getTheBoard().getGameEncoding() == 1
	        		  || currentBoard.getTheBoard().getGameEncoding() == 2 ) {
	            square[i].setBackground(Color.black);
	          }
	          if(currentBoard.getTheBoard().getGameEncoding() == 3) {
	            square[i].setBackground(Color.red);
	          }
	        }
	        
	        square[i].addActionListener(this);
	        square[i].setFont(font);
	        board.add(square[i]);
	        square[i].setName( Integer.toString(i) );
	    }
    } catch (Exception e) {
    }

    window.getContentPane().add(menuButtons,BorderLayout.NORTH);
    window.getContentPane().add(board,BorderLayout.CENTER);

    /*Make The Window Visible*/ 
    window.setVisible(true);
  }



  
  
  public void drawBoard(Board theBoard){
    clearGUI();
    try {
		for (int i=0; i<theBoard.getPiecePlacement().length; i++){
		  try {
		    int pos = (theBoard.getPiecePlacement()[i].getPiecePosition().getY()*gridSize) + (theBoard.getPiecePlacement()[i].getPiecePosition().getX());
		    square[pos].setIcon(new ImageIcon(scale(
					ImageIO.read(getClass().getResource(theBoard.getPiecePlacement()[i].getImageURL())), 600/gridSize, 600/gridSize)));
		  } catch (Exception ex) {
		  }
		}
  	} catch (RemoteException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
  }



  public void clearGUI(){
  	LineBorder bord = new LineBorder(Color.BLACK, 1);
 	for(int i=0; i<=((gridSize*gridSize)-1); i++){ 
	  try {     
	    square[i].setIcon(null);
	    square[i].setBorder(bord);
	  }  catch (Exception ex) {
		  System.out.println(ex);
	  }
 	}
  }




  public void actionPerformed(ActionEvent a) {
    JButton pressedButton = (JButton)a.getSource();
    drawBoard(currentBoard);
    
    // If this click is moving a piece
    if(Integer.parseInt(pressedButton.getName()) == currentClick[0] || Integer.parseInt(pressedButton.getName()) == currentClick[1]){
    	int pos = Integer.parseInt(pressedButton.getName());
    	int x = pos - (pos/gridSize)*gridSize;
    	int y = pos/gridSize;
    	try {
    		//Move the piece
    		Piece[] p = currentBoard.getPiecePlacement();
    		p[currentClick[2]].setPiecePosition(new Position(x,y));
			currentBoard.setPiecePlacement(p);
			
			//Check if the piece is now a king
			if(p[currentClick[2]].getColour() == Piece.WHITE && y == 0){
				p[currentClick[2]].turnKing();
			}
			if(p[currentClick[2]].getColour() == Piece.BLACK && y == gridSize){
				p[currentClick[2]].turnKing();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	drawBoard(currentBoard);
    	currentClick[0] = -1;
    	currentClick[1] = -1;
    	currentClick[2] = -1;
	}
    
    // If this click selects a piece to plan a move
    else {
	    try {
		  	for (int i=0; i<currentBoard.getPiecePlacement().length; i++){
	  		    int pos = (currentBoard.getPiecePlacement()[i].getPiecePosition().getY()*gridSize) + (currentBoard.getPiecePlacement()[i].getPiecePosition().getX());
	  		    if(pos == Integer.parseInt(pressedButton.getName())){
	  		    	
	  		    	//Only allow players to move their own pieces
	  		    	if(currentBoard.getPiecePlacement()[i].getColour() == Piece.WHITE && currentPlayer.getName().equals("Player1") ){
	  		    		highlightSquare(pos);
	  		    		currentClick = showMoves(pos,i);
	  		    		currentClick[2] = i;
	  		    	}
	  		    	if(currentBoard.getPiecePlacement()[i].getColour() == Piece.BLACK && currentPlayer.getName().equals("Player2") ){
	  		    		highlightSquare(pos);
	  		    		currentClick = showMoves(pos,i);
	  		    		currentClick[2] = i;
	  		    	}
	  		    }
	  		}
	    } catch (Exception e ){ 	
	    }
    }  
  }

  
  public void highlightSquare(int pos){
	  this.square[pos].setBorder(new LineBorder(Color.GREEN, 2));
  }
  
  public int[] showMoves(int pos, int pieceNum){
	int isValid[] = { -1, -1 , -1};
	try {
		if(currentBoard.getPiecePlacement()[pieceNum].getColour() == Piece.WHITE && currentPlayer.getName().equals("Player1")){
			//Move logic for white regular move#1
			if( (pos-gridSize-1) > (((pos/gridSize)-1)*gridSize)-1 ){
				if( !positionOccupied(pos-gridSize-1) ){
					highlightSquare(pos-gridSize-1);
					isValid[0] = pos-gridSize-1;
				} 
				else {
					//highlightSquare(pos-gridSize-1-gridSize-1);
				}
			}
			//Move logic for white regular move#2
			if( (pos-gridSize+1) < ((pos/gridSize)*gridSize) ){
				if( !positionOccupied(pos-gridSize+1) ){
					highlightSquare(pos-gridSize+1);
					isValid[1] = pos-gridSize+1;
				} 
				else {
					//highlightSquare(pos-gridSize+1-gridSize+1);
				}
			}
		}
		if(currentBoard.getPiecePlacement()[pieceNum].getColour() == Piece.BLACK &&currentPlayer.getName().equals("Player2")){
			//Move logic for black regular move#1
			if( (pos+gridSize-1) > (pos/gridSize)*gridSize+(gridSize-1) ){
				if( !positionOccupied(pos+gridSize-1) ){
					highlightSquare(pos+gridSize-1);
					isValid[0] = pos+gridSize-1;
				} else {
					
				}
			}
			//Move logic for black regular move#2
			if( (pos+gridSize+1) < (((pos/gridSize)+1)*gridSize)+(gridSize)){
				if( !positionOccupied(pos+gridSize+1) ){
					highlightSquare(pos+gridSize+1);
					isValid[1] = pos+gridSize+1;
				} else {
					
				}
			}
		}
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return isValid;
  }

  
  
  
  
  public void changeTurn(){
    if (turn.getText().equals("STOP")){
        turn.setText("GO");
        turn.setBackground(Color.green);
    }
    else if (turn.getText().equals("GO")){
        turn.setText("STOP");
        turn.setBackground(Color.red);
    }
  }



  
  public Boolean positionOccupied(int pos){
	  Boolean occupied = false;
	  try {
		  	for (int i=0; i<currentBoard.getPiecePlacement().length; i++){
	  		    int p = (currentBoard.getPiecePlacement()[i].getPiecePosition().getY()*gridSize) + (currentBoard.getPiecePlacement()[i].getPiecePosition().getX());
	  		    if(p == pos){
	  		    	occupied = true;
	  		    }
	  		}
	    } catch (Exception e ){
	    	
	    }
	  return occupied;
  }


  public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

      int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
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

}



