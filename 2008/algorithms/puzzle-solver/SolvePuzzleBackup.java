import java.util.*;

public class SolvePuzzle
{
	public static void solve(Puzzle start, Puzzle goal)
	{
		PriorityQueue<Move> pq = new PriorityQueue<Move>();
		Puzzle puzzleTemp;
		Move moveTemp;
		//ArrayList<Move> moves = new ArrayList<Move>();
		int rowBlank, colBlank;
		int r, c;
		int w = 0;

		while( ! start.isSolved(goal) )
		{
			rowBlank = start.getRowOfBlank();
			colBlank = start.getColOfBlank();

			if( rowBlank == 0 && colBlank == 0 )
			{
				puzzleTemp = start.swap(0,1);
				moveTemp = new Move( 0, 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(1,0);
				moveTemp = new Move( 1, 0, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			else if( rowBlank == 0 && colBlank == start.a[0].length - 1 )
			{
				puzzleTemp = start.swap(0, start.a[0].length - 2);
				moveTemp = new Move( 0, start.a[0].length - 2, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				System.out.println(puzzleTemp);
				System.out.println(moveTemp);

				pq.add(moveTemp);

				puzzleTemp = start.swap(1, start.a[0].length - 1);
				moveTemp = new Move( 1, start.a[0].length - 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				System.out.println(puzzleTemp);
				System.out.println(moveTemp);

				pq.add(moveTemp);
			}
			else if( rowBlank == start.a.length - 1 && colBlank == 0 )
			{
				puzzleTemp = start.swap(start.a.length - 2,0);
				moveTemp = new Move( start.a.length - 2, 0, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(start.a.length - 1,1);
				moveTemp = new Move( start.a.length - 1, 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
			else if( rowBlank == start.a.length - 1 && colBlank == start.a[0].length - 1 )
			{
				puzzleTemp = start.swap(start.a.length - 1,start.a[0].length - 2);
				moveTemp = new Move( start.a.length - 1, start.a[0].length - 2, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);

				puzzleTemp = start.swap(start.a.length - 2,start.a[0].length - 1);
				moveTemp = new Move( start.a.length - 2, start.a[0].length - 1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				pq.add(moveTemp);
			}
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
			else
			{
				r = rowBlank;
				c = colBlank;

				puzzleTemp = start.swap(r-1,c);
				moveTemp = new Move( r-1, c, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				//System.out.println(puzzleTemp);
				//System.out.println(moveTemp);

				pq.add(moveTemp);

				puzzleTemp = start.swap(r+1,c);
				moveTemp = new Move( r+1, c, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				//System.out.println(puzzleTemp);
				//System.out.println(moveTemp);

				pq.add(moveTemp);

				puzzleTemp = start.swap(r,c-1);
				moveTemp = new Move( r, c-1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				//System.out.println(puzzleTemp);
				//System.out.println(moveTemp);

				pq.add(moveTemp);

				puzzleTemp = start.swap(r,c+1);
				moveTemp = new Move( r, c+1, puzzleTemp.numberOfMisplacedTiles(goal), puzzleTemp.distanceToSolved(goal, rowBlank, colBlank) );

				//System.out.println(puzzleTemp);
				//System.out.println(moveTemp);

				pq.add(moveTemp);
			}

			moveTemp = pq.peek();
			pq.clear();

			start = start.swap(moveTemp.row, moveTemp.col );

			System.out.println(start);

			w++;

			if( w > 2)
			{
				break;
			}
		}
	}

  public static void main(String[] args) {
    //Puzzle scrambled = new Puzzle("2 5 3 1 _ 6 4 7 8") ;
    Puzzle scrambled = new Puzzle("1 5 _ 2 4 7 8 3 6") ;
    Puzzle solved = new Puzzle("1 2 3 4 5 6 7 8 _") ;

    System.out.println(scrambled);
    //System.out.println(solved);

    SolvePuzzle.solve(scrambled,solved) ;
  }
}