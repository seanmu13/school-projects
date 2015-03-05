import java.util.*;

public class SolvePuzzle
{
	public static void solve(Puzzle start, Puzzle goal)
	{
		PriorityQueue<Move> pq = new PriorityQueue<Move>();
		Puzzle puzzleTemp;
		Move moveTemp;
		int rowBlank, colBlank;
		int r, c;

		while( ! start.isSolved(goal) )
		{
			rowBlank = start.getRowOfBlank();
			colBlank = start.getColOfBlank();

			// For each of the possibilites, basically I make a temporary copy of the puzzle and use to swap 2 chars, thus not affecting the original puzzle
			// Then I create a move at these positions so that they can be added to the priority queue
			// Then, the best move is found and the position from the move is used in the swap for the starting puzzle

			// Upper left corner
			if( rowBlank == 0 && colBlank == 0 )
			{
				puzzleTemp = start.swap(0,1);
				moveTemp = new Move( 0, 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(1,0);
				moveTemp = new Move( 1, 0, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			// Upper right corner
			else if( rowBlank == 0 && colBlank == start.a[0].length - 1 )
			{
				puzzleTemp = start.swap(0, start.a[0].length - 2);
				moveTemp = new Move( 0, start.a[0].length - 2, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(1, start.a[0].length - 1);
				moveTemp = new Move( 1, start.a[0].length - 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			// Lower left corner
			else if( rowBlank == start.a.length - 1 && colBlank == 0 )
			{
				puzzleTemp = start.swap(start.a.length - 2,0);
				moveTemp = new Move( start.a.length - 2, 0, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(start.a.length - 1,1);
				moveTemp = new Move( start.a.length - 1, 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			// Lower right corner
			else if( rowBlank == start.a.length - 1 && colBlank == start.a[0].length - 1 )
			{
				puzzleTemp = start.swap(start.a.length - 1,start.a[0].length - 2);
				moveTemp = new Move( start.a.length - 1, start.a[0].length - 2, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(start.a.length - 2,start.a[0].length - 1);
				moveTemp = new Move( start.a.length - 2, start.a[0].length - 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			// If it is not on a corner but on the first row
			else if( rowBlank == 0 )
			{
				puzzleTemp = start.swap(0,colBlank + 1);
				moveTemp = new Move( 0, colBlank + 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(0,colBlank - 1);
				moveTemp = new Move( 0, colBlank - 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(1,colBlank);
				moveTemp = new Move( 1, colBlank, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

			}
			// If it is not on a corner but on the last row
			else if( rowBlank == start.a.length - 1 )
			{
				r = start.a.length - 1;

				puzzleTemp = start.swap(r,colBlank + 1);
				moveTemp = new Move( r, colBlank + 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r,colBlank - 1);
				moveTemp = new Move( r, colBlank - 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(1,colBlank);
				moveTemp = new Move( r - 1, colBlank, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			// If it is not on a corner but on the first col
			else if( colBlank == 0 )
			{
				r = rowBlank;

				puzzleTemp = start.swap(r-1,0);
				moveTemp = new Move( r-1, 0, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r+1,0);
				moveTemp = new Move( r+1, 0, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r,1);
				moveTemp = new Move( r, 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			// If it is not on a corner but on the last col
			else if( colBlank == start.a[0].length - 1 )
			{
				r = rowBlank;
				c = start.a[0].length - 1;

				puzzleTemp = start.swap(r-1,c);
				moveTemp = new Move( r-1, c, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r+1,c);
				moveTemp = new Move( r+1, c, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r,c-1);
				moveTemp = new Move( r, c-1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			// If the blank is not on the corner or the sides
			else
			{
				r = rowBlank;
				c = colBlank;

				puzzleTemp = start.swap(r-1,c);
				moveTemp = new Move( r-1, c, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r+1,c);
				moveTemp = new Move( r+1, c, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r,c-1);
				moveTemp = new Move( r, c-1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(r,c+1);
				moveTemp = new Move( r, c+1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}

			// get the best move
			moveTemp = pq.peek();

			// clear the move queue
			pq.clear();

			// make the best move
			start = start.swap(moveTemp.row, moveTemp.col );

			System.out.println(start);
		}
	}
}