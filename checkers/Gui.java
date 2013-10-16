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

  /*Create button array for grids up to 12x12*/
  private JButton square[] = new JButton[144];
  private JButton resign = new JButton();
  private JButton turn = new JButton();
  private JButton connect = new JButton();
  private JPanel board;
  private JPanel menuButtons;

  /*Board info */
  private int gridSize = 0;
  private int type = 0;



  /* Init GUI */
  public Gui(Board theBoard)
  {
    try {
		  setBoardType(theBoard.getTheBoard());
  	} catch (RemoteException e1) {
  		// TODO Auto-generated catch block
  		e1.printStackTrace();
  	}
    
    
    /* Setup Window */
    window.setSize(600,650);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    /* Setup Window, Board and Menu */
    window.setLayout(new BorderLayout());
    GridLayout grid = new GridLayout(gridSize,gridSize);
    GridLayout menu = new GridLayout(1,3);
    board = new JPanel(grid);
    menuButtons = new JPanel(menu);
    board.setSize(600,600);
    menuButtons.setSize(600,50);

    Font font = new Font("Helvetica", Font.PLAIN, 30);
    int checkerLine = 0;

    resign.setText("RESIGN");
    menuButtons.add(resign);
    connect.setText("**CHECKERS**");
    menuButtons.add(connect);
    turn.setText("BEGIN");
    turn.setBackground(Color.gray);
    menuButtons.add(turn);

    try {
    /*Add Buttons To The Board*/
    for(int i=0; i<=((gridSize*gridSize)-1); i++){      
        square[i] = new JButton();
        
        /* Set Color of squares depending on type*/
        if (i%gridSize == 0) {
          checkerLine = 1 + checkerLine;
        }
        if( (i + checkerLine)%2 == 0) {
          if(type == 0) {
            square[i].setBackground(Color.red);
          }
          if(type == 1 || type == 2) {
            square[i].setBackground(Color.white);
          }
        } else {
          if(type == 0) {
            square[i].setBackground(Color.black);
          }
          if(type == 1) {
            square[i].setBackground(Color.red);
          }
          //square[i].addActionListener(this);
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
		    square[pos].setIcon(new ImageIcon(theBoard.getPiecePlacement()[i].getPieceImage()));
		  } catch (Exception ex) {
		  }
		}
  	} catch (RemoteException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
  }



  public void clearGUI(){
   for(int i=0; i<=((gridSize*gridSize)-1); i++){ 
      try {     
        square[i].setIcon(new ImageIcon(getClass().getResource("/resources/blank.png")));
      }  catch (Exception ex) {
      }
    }
  }




  public void actionPerformed(ActionEvent a) {
    JButton pressedButton = (JButton)a.getSource();
    pressedButton.setBorder(new LineBorder(Color.GREEN, 2));
    
    // TODO get all valid moves, then highlight them
  	/*for (int i=0; i<boardInfo.getPiecePlacement().length; i++){
  		    int pos = (boardInfo.getPiecePlacement()[i].getPiecePosition().getY()*gridSize) + (boardInfo.getPiecePlacement()[i].getPiecePosition().getX());
  		    if(pos == Integer.parseInt(pressedButton.getName())){
  		    	
  		    	if(boardInfo.getPiecePlacement()[i].getColour() == 'w'){
  		    		pressedButton.setBorder(new LineBorder(Color.GREEN, 2));
  		    	}
  		    }
  		}*/
      //pressedButton.setBorder(new LineBorder(Color.GREEN, 2));
    
  }

  
  public void highlightSquare(int pos){
	  this.square[pos].setBorder(new LineBorder(Color.GREEN, 2));
  }

  public void changeTurn(){
    if (turn.getText().equals("STOP")){
        turn.setText("GO");
        turn.setBackground(Color.green);
    }
    if (turn.getText().equals("GO")){
        turn.setText("STOP");
        turn.setBackground(Color.red);
    }
  }



  private void setBoardType(BoardDesign boardInfo){
    /* Check Board Type */
    try {
      gridSize = boardInfo.getGridSize();
      if(boardInfo.getBlackCorner() == 'L'){
        type = 0;
      } else {
        type = 1;
      }
    } catch (Exception e) {

    }
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


