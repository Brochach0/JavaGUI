import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GuiWindow3 extends JFrame {
   public static final int ROWS = 3;  
   public static final int COLS = 3;

   public static final int CELL_SIZE = 200; // size of the square for the tic tacy
   public static final int CANVAS_WIDTH = CELL_SIZE * COLS;  
   public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
   public static final int GRID_WIDTH = 1;           
   public static final int GRID_WIDHT_HALF = GRID_WIDTH / 4;
   public static final int CELL_PADDING = CELL_SIZE / 6;
   public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
   public static final int SYMBOL_STROKE_WIDTH = 8;
 
   public enum GameState {
      PLAYING, DRAW, CROSS_WON, NOUGHT_WON
   }
   private GameState currentState;  
 
   public enum Seed {
      EMPTY, CROSS, NOUGHT
   }
   private Seed currentPlayer;  // player in the game
 
   private Seed[][] board   ;
   private DrawCanvas canvas; 
   private JLabel statusBar;  
 
   public GuiWindow3() {
      canvas = new DrawCanvas(); 
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
 
      canvas.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int rowSelected = mouseY / CELL_SIZE;
            int colSelected = mouseX / CELL_SIZE;
 
            if (currentState == GameState.PLAYING) {
               if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                     && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
                  board[rowSelected][colSelected] = currentPlayer; // Make a move
                  updateGame(currentPlayer, rowSelected, colSelected); // update state
                  // Switch player
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
               }
            } else {       // game over
               initGame(); // restart the game
            }
            // Refreshed
            repaint();  
         }
      });
 
      statusBar = new JLabel("  ");
      statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
      statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
 
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(canvas, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END); 
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();  
      setTitle("TicTacToe.exe");
      setVisible(true);  
 
      board = new Seed[ROWS][COLS]; 
      initGame(); 
   }
 
   public void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = Seed.EMPTY; 
         }
      }
      currentState = GameState.PLAYING; // Game is ready to play
      currentPlayer = Seed.CROSS;       // X's play first
   }
 
   public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
      if (hasWon(theSeed, rowSelected, colSelected)) {  // Each row is checked to see a winner
         currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
      } else if (isDraw()) {  // checks to see any draws within the game
         currentState = GameState.DRAW;
      }
      //Else no tie nor a win/loss game keeps going
   }
 
   public boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == Seed.EMPTY) {
               return false; 
            }
         }
      }
      return true;  // If all of the boxes have something filled in and no winner. the game is a tie.
   }
 
   public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
      return (board[rowSelected][0] == theSeed  
            && board[rowSelected][1] == theSeed
            && board[rowSelected][2] == theSeed
       || board[0][colSelected] == theSeed    
            && board[1][colSelected] == theSeed
            && board[2][colSelected] == theSeed
       || rowSelected == colSelected          
            && board[0][0] == theSeed
            && board[1][1] == theSeed
            && board[2][2] == theSeed
       || rowSelected + colSelected == 2  
            && board[0][2] == theSeed
            && board[1][1] == theSeed
            && board[2][0] == theSeed);
   }
 
  
   class DrawCanvas extends JPanel {
      @Override
      public void paintComponent(Graphics g) {  
         super.paintComponent(g);    
         setBackground(Color.LIGHT_GRAY); // Background color
 
         g.setColor(Color.BLACK);
         for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                  CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
         }
         for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                  GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
         }
 
         Graphics2D g2d = (Graphics2D)g;
         g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
               BasicStroke.JOIN_ROUND));  // Graphics2D only
         for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
               int x1 = col * CELL_SIZE + CELL_PADDING;
               int y1 = row * CELL_SIZE + CELL_PADDING;
               if (board[row][col] == Seed.CROSS) {
                  g2d.setColor(Color.WHITE);
                  int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                  int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                  g2d.drawLine(x1, y1, x2, y2);
                  g2d.drawLine(x2, y1, x1, y2);
               } else if (board[row][col] == Seed.NOUGHT) {
                  g2d.setColor(Color.WHITE);
                  g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
               }
            }
         }
 
         if (currentState == GameState.PLAYING) {
            statusBar.setForeground(Color.PINK);
            if (currentPlayer == Seed.CROSS) {
               statusBar.setText("X's Turn");
            } else {
               statusBar.setText("O's Turn");
            }
         } else if (currentState == GameState.DRAW) {
            statusBar.setForeground(Color.MAGENTA);
            statusBar.setText("Nobody Won, It is a Draw.");
         } else if (currentState == GameState.CROSS_WON) {
            statusBar.setForeground(Color.MAGENTA);
            statusBar.setText("'X' Won!/Player 1 Won");
         } else if (currentState == GameState.NOUGHT_WON) {
            statusBar.setForeground(Color.MAGENTA);
            statusBar.setText("'O' Won!/Player 2 Won");
         }
      }
   }
 
   public static void main(String[] args) {
      // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new GuiWindow3(); 
         }
      });
   }
}
