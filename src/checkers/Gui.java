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


public class Gui implements ActionListener 
{
  
  private JFrame board = new JFrame("Premium Checkers Deluxe 3000");
  private JButton square[] = new JButton[64];

  public Gui() // the frame constructor method
  {

    int checkerLine = 0;

    /*Create Window*/
    /* Sets size and grid size */
    board.setSize(600,600);
    GridLayout grid = new GridLayout(8,8);
    board.setLayout(grid);
    board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Font font = new Font("Helvetica", Font.PLAIN, 30);


    /*Add Buttons To The Window*/
    for(int i=0; i<=63; i++){
        
        square[i] = new JButton();
        
        /* Set Colors of squares */
        if (i%8 == 0) {
          checkerLine = 1 + checkerLine;
        }
        if( (i + checkerLine)%2 == 0) {
          square[i].setBackground(Color.red);
        } else {
          square[i].setBackground(Color.black);
          square[i].addActionListener(this);
        }

        square[i].setFont(font);
        board.add(square[i]);
        square[i].setName( Integer.toString(i) );
    }

    /*Make The Window Visible*/
    board.setVisible(true);
  }



  public static void main(String args[]) {
    new Gui();
  }

  public void actionPerformed(ActionEvent a) {
    JButton pressedButton = (JButton)a.getSource();
    pressedButton.setBackground(Color.green);
  }


}