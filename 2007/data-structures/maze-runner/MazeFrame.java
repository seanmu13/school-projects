//////////////////////////////////////////////////////////////////////
///
///  Contents: Picture of Maze.
///  Author:   John Aronis
///  Date:     July 2005
///
//////////////////////////////////////////////////////////////////////

/// The class MazeFrame contains two public methods:
///
///   MazeFrame(char[][] maze) constructs a JFrame and displays the maze in it.
///
///   draw() redisplays the maze.
///
/// Note that maze is a reference to an array of characters.  As the
/// search proceeds, draw() can be used to update the display.

import java.awt.* ;
import javax.swing.* ;

public class MazeFrame extends JFrame {

  private static char start   = 'S' ;
  private static char goal    = 'G' ;
  private static char open    = '.' ;
  private static char closed  = '*' ;
  private static char visited = 'V' ;
  private static char turtle  = 'T' ;

  private static Color startColor   = Color.red ;
  private static Color goalColor    = Color.yellow ;
  private static Color openColor    = Color.white ;
  private static Color closedColor  = Color.black ;
  private static Color visitedColor = Color.black ;
  private static Color turtleColor  = Color.green ;

  private static int side = 30 ;

  private char[][] maze ;
  private int rows ;
  private int columns ;
  private int height ;
  private int width ;
  private Graphics g ;

  public MazeFrame(char[][] m) {
    maze = m ;
    rows = maze.length ;
    columns = maze[0].length ;
    height = rows*side ;
    width = columns*side ;
    setSize(width,height) ;
    setTitle("Maze Runner") ;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    setVisible(true) ;
    g = getGraphics() ;
  }

  private void drawBox(int row, int column, int diameter, Color color, Graphics g) {
    int centerX = (column*side) + (side/2) ;
    int centerY = (row*side) + (side/2) ;
    int radius = diameter / 2 ;
    g.setColor(color) ;
    g.fillRect( centerX-radius , centerY-radius , diameter , diameter ) ;
  }

  private void drawDot(int row, int column, int diameter, Color color, Graphics g) {
    int centerX = (column*side) + (side/2) ;
    int centerY = (row*side) + (side/2) ;
    int radius = diameter / 2 ;
    g.setColor(color) ;
    g.fillOval( centerX-radius , centerY-radius , diameter , diameter ) ;
  }

  public void draw() {
    for (int r = 0 ; r < rows ; r++) { for (int c = 0 ; c < columns ; c++) {
      if ( maze[r][c] == start ) { drawBox(r,c,side,startColor,g) ; }
      if ( maze[r][c] == goal ) { drawBox(r,c,side,goalColor,g) ; }
      if ( maze[r][c] == open ) { drawBox(r,c,side,openColor,g) ; }
      if ( maze[r][c] == closed ) { drawBox(r,c,side,closedColor,g) ; }
      if ( maze[r][c] == visited ) { drawDot(r,c,side/2,visitedColor,g) ; }
      if ( maze[r][c] == turtle ) { drawDot(r,c,side/2,turtleColor,g) ; }
    }}
  }

  public static void main(String[] args){
  }

}
/// End-of-File