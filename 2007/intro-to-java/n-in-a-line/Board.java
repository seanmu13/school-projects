//--------------------------------------------------------------------------------------------------------------------
//-----------------------------------------Assignment 2 - Board-------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------

public class Board
{
	//---------------------
	//Private data declared
	//---------------------

	private char[][] data;
	private int N;
	private int nRows;
	private int nCols;

	//---------------------------
	//Constructor for board class
	//---------------------------

	public Board(int n, int rows, int cols)
	{
		N = n;
		nRows = rows;
		nCols = cols;
		data = new char[nRows][nCols];

		for(int r=0; r<nRows; r++)
			for(int c=0; c<nCols; c++)
				data[r][c] = ' ';
	}

	//------------------------------------------
	//Prints the board object with width, hieght
	//------------------------------------------

	public void print()
	{
		for(int r=0; r<nRows; r++)
		{
			System.out.print("\n|");
			for(int c=0; c<nCols; c++)
				System.out.print(data[r][c] + "|");
		}

		System.out.print("\n");

		for(int i=0; i<nCols*2+1; i++)
		{
			System.out.print("-");
		}

		System.out.print("\n");

		for(int t=0; t<nCols; t++)
		{
			if(t>=10)
				System.out.print(" " + (t-10));
			else
				System.out.print(" "+ t);
		}
	}

	//---------------------------------------------
	//Adds the players move to the board if allowed
	//---------------------------------------------

	public void addMove(int c, char player)
	{

		for(int r=nRows-1;r>=0; r--)
		{
			if(data[r][c] == ' ')
			{
				data[r][c] = player;
				break;
			}
		}

		return;
	}

	//------------------------------------------------------
	//Checks to see if the players requested move is allowed
	//------------------------------------------------------

	public boolean allowsMove(int c)
	{
		for(int r=nRows-1;r>=0;r--)
		{
			if(data[r][c] == ' ')
				return true;
		}

		return false;
	}

	//----------------------------------
	//Checks to see if the board is full
	//----------------------------------

	public boolean isFull()
	{
		boolean full = true;

		for(int r=0; r<nRows; r++)
			for(int c=0; c<nRows; c++)
				full = full && !allowsMove(c);

		return full;
	}

	//---------------------------------------------------------------------
	//Checks to see if either player has one the game by getting N in a row
	//---------------------------------------------------------------------

	public boolean winsFor(char player)
	{
		boolean result = true;

		//----------
		//N-in-a-row
		//----------

		for(int r=nRows-1; r>=0;r--)
		{
			for(int c=0; c<nCols-N+1; c++)
			{
				result = true;

				for(int k=0; k<N; k++)
				{
					result = result && (data[r][c+k] == player);
				}

				if(result) return true;
			}
		}

		//-------------
		//N-in-a-column
		//-------------

		for(int c=nCols-1; c>=0;c--)
		{
			for(int r=0; r<nRows-N+1; r++)
			{
				result = true;

				for(int k=0; k<N; k++)
				{
					result = result && (data[r+k][c] == player);
				}

				if(result) return true;
			}
		}

		//--------------
		//NW-SE diagonal
		//--------------

		for(int r=0; r<=nRows-N; r++)
		{
			for(int c=0; c<=nCols-N; c++)
			{
				result = true;

				for(int k=0; k<N; k++)
				{
					result = result && (data[r+k][c+k] == player);
					if(!result) break;
				}

				if(result) return true;
			}
		}

		//--------------
		//NE-SW diagonal
		//--------------

		for(int r=0; r<=nRows-N; r++)
		{
			for(int c=nCols-1; c>=N-1; c--)
			{
				result = true;

				for(int k=0; k<N; k++)
				{
					result = result && (data[r+k][c-k] == player);

					if(!result) break;
				}

				if(result) return true;
			}
		}

		//--------------
		//SE-NW diagonal
		//--------------

		for(int r=nRows-1; r>=N-1; r--)
		{
			for(int c=nCols-1; c>=N-1; c--)
			{
				result = true;

				for(int k=0; k<N; k++)
				{
					result = result && (data[r-k][c-k] == player);

					if(!result) break;
				}

				if(result) return true;
			}
		}

		//--------------
		//SW-NE diagonal
		//--------------

		for(int r=nRows-1; r>=N-1; r--)
		{
			for(int c=0; c<=nCols-N; c++)
			{
				result = true;

				for(int k=0; k<N; k++)
				{
					result = result && (data[r-k][c+k] == player);

					if(!result) break;
				}

				if(result) return true;
			}
		}

		return false;

	}

	public static void main(String[] args)
	{
	}
}