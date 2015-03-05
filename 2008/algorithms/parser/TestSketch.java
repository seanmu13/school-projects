import java.util.* ;

public class TestSketch
{
	public static int PAUSE = 3000 ;

	public static void main(String[] args)
	{
		Sketch sketch = new Sketch(50,30,10) ;
		Scanner keyboard = new Scanner(System.in) ;
		int column, row ;

		while (true)
		{
			System.out.print("Enter column and row: ") ;
			column = keyboard.nextInt() ;
			row = keyboard.nextInt() ;
			sketch.fill(column,row) ;
			sketch.repaint() ;
		}
	}
}
