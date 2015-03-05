public class Move implements Comparable
{
	public int row;
	public int col;
	public int lengthSoFar;
	public int distanceRemaining;
	public int total;

	// Contructs a move with lengthSoFar = # misplaced tiles, and the distanceRemaning = manhatten distance of the displaced tile to its final destination
	public Move(int r, int c, int misplacedNum, int currDist)
	{
		row = r;
		col = c;
		lengthSoFar = misplacedNum;
		distanceRemaining = currDist;
		total = lengthSoFar + distanceRemaining;
	}

	// Need compareTo in order to add Move to priority queue
	public int compareTo(Object o)
	{
		if( this.total > ((Move) o).total )
		{
			return 1;
		}
		else if( this.total < ((Move) o).total)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	public String toString()
	{
		return "Misplaced: " + lengthSoFar + "   ManDist: " + distanceRemaining + "   Total: " + total;
	}
}