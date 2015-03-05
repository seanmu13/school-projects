import java.util.*;

public class Puzzle
{
	public String[][] a;

	// Creates a puzzle with the given string
	public Puzzle(String puz)
	{
		StringTokenizer st = new StringTokenizer(puz);
		int rows = (int)Math.sqrt(st.countTokens());
		int cols = rows;

		a = new String[rows][cols];

		for (int i = 0; i < rows; i++ )
		{
			for (int j = 0; j < cols; j++ )
			{
				a[i][j] = st.nextToken();
			}
		}
	}

	// Creates a puzzle with an array of the puzzle already made
	public Puzzle(String[][] b)
	{
		a = b;
	}

	// Finds manhatten distance of the char at r,c compared to its solved position
	public int distanceToSolved(Puzzle solved, int r, int c)
	{
		String ch = a[r][c];
		int rSolved = 0, cSolved = 0, rPuzzle = 0, cPuzzle = 0;

		for(int i = 0; i < solved.a.length; i++)
		{
			for(int j = 0; j < solved.a[0].length; j++)
			{
				if(solved.a[i][j].equals(ch))
				{
					rSolved = i;
					cSolved = j;
				}

				if(a[i][j].equals(ch))
				{
					rPuzzle = i;
					cPuzzle = j;
				}
			}
		}

		return Math.abs(rSolved - rPuzzle) + Math.abs(cSolved - cPuzzle);
	}

	// Finds the # of misplaced tiles in the puzzle
	public int numberOfMisplacedTiles(Puzzle solved){
		int totalMisplaced = 0;

		for(int i = 0; i < solved.a.length; i++)
		{
			for(int j = 0; j < solved.a[0].length; j++)
			{
				if( ! a[i][j].equals(solved.a[i][j]) )
				{
					totalMisplaced++;
				}
			}
		}

		return totalMisplaced;
	}

	// Checks to see if puzzle is solved
	public boolean isSolved(Puzzle solved)
	{
		for(int i = 0; i < solved.a.length; i++)
		{
			for(int j = 0; j < solved.a[0].length; j++)
			{
				if( ! a[i][j].equals(solved.a[i][j]))
				{
					return false;
				}
			}
		}

		return true;
	}

	// Gets the row of the blank character
	public int getRowOfBlank()
	{
		int row = 0;

		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[0].length; j++)
			{
				if( a[i][j].equals("_"))
				{
					row = i;
					break;
				}
			}
		}

		return row;
	}

	// Gets the column of the blank character
	public int getColOfBlank()
	{
		int col = 0;

		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[0].length; j++)
			{
				if( a[i][j].equals("_"))
				{
					col = j;
					break;
				}
			}
		}

		return col;
	}

	// Performs a swap between the blank and the char at row r and column c
	// Does not affect this Puzzle, but just returns a new one
	public Puzzle swap(int r, int c)
	{
		int rBlank = getRowOfBlank();
		int cBlank = getColOfBlank();
		String[][] b = new String[a.length][a[0].length];

		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[0].length; j++)
			{
				b[i][j] = a[i][j];
			}
		}

		String temp = b[r][c];
		b[r][c] = "_";
		b[rBlank][cBlank] = temp;

		Puzzle newPuz = new Puzzle(b);

		return newPuz;
	}

	// Prints out the matrix of the array of the Puzzle
	public String toString()
	{
		String ret = new String("");

		for (int i = 0; i < a.length ; i++)
		{
			for (int j = 0; j < a[0].length ; j++)
			{
				ret += a[i][j] + " ";
			}

			ret += "\n";
		}

		return ret;
	}
}