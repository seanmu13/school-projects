import java.util.* ;

public class Program_A_Sketch
{
	public static int COLUMNS = 100 ;
	public static int ROWS = 100 ;
	public static int PIXELS = 5 ;

	public static void main(String[] args)
	{
		Sketch sketch = new Sketch(COLUMNS,ROWS,PIXELS) ;
		Scanner keyboard = new Scanner(System.in) ;
		String line, command ;
		Variables variables = new Variables() ;
		variables.setStoreValue("columns",COLUMNS) ;
		variables.setStoreValue("rows",ROWS) ;

		while (true)
		{
			System.out.println("------------------------------------") ;
			command = "" ;

			while ( !(line = keyboard.nextLine()).equals(".") )
			{
				command += " " + line ;
			}

			Interpreter.execute(Parser.fastParse(Parser.tokenize(command),"COMMAND"),variables,sketch ) ;
			sketch.repaint() ;
		}
	}
}
