import java.awt.* ;
import javax.swing.* ;

public class Sketch extends JFrame
{
	private int columns ;
	private int rows ;
	private int pixels ;
	private int width ;
	private int height ;
	private boolean[][] raster ;
	private Color color = Color.red ;

	public Sketch(int c, int r, int p)
	{
		columns = c ;
		rows = r ;
		pixels = p ;
		width = columns * pixels ;
		height = rows * pixels ;
		raster = new boolean[columns][rows] ;
		setSize(width,height) ;
		setTitle("Program A Sketch") ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		setVisible(true) ;
	}

	public void fill(int column, int row)
	{
		raster[column][row] = true ;
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.red) ;
		for (int c = 0 ; c < columns ; c++)
		{
			for (int r = 0 ; r < rows ; r++)
			{
				if ( raster[c][r] )
				{
					g.fillRect(c*pixels,r*pixels,pixels,pixels) ;
				}
			}
		}
	}
}
