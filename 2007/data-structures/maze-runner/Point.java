public class Point implements Comparable{

	public int x;
	public int y;
	public char c;
	public Matrix maze;

	//-------------------------------------------------------------
	//	Point constructor sets the x,y coordinates, and character c
	//-------------------------------------------------------------

	public Point(int x1, int y1, char ch, Matrix mze){
		x = x1;
		y = y1;
		c = ch;
		maze = mze;
	}

	//-------------------------------------------------------------
	//	Finds the manhattan distance between one point and another
	//-------------------------------------------------------------

	public int distance(Point other){
		int dist;
		dist = Math.abs(this.x-other.x) + Math.abs(this.y-other.y);
		return dist;
	}

	//------------------------------------
	//	Prints the point in format <x,y,c>
	//------------------------------------

	public String toString(){
		return "<" + x + ", " + y + ", '" + c + "', " + this.distance(maze.goal) + ">";
	}

	//-----------------------------------
	//	Checks for equalty between points
	//-----------------------------------

	public boolean equals(Point other){
		if(this.x == other.x && this.y == other.y)	return true;
		return false;
	}

	//-----------------------------
	//	Checks if point is a corner
	//-----------------------------

	public boolean isCorner(){
		if((this.x==0 && this.y==0) || (this.x==0 && this.y==maze.maze.length-1)) return true;
		else if((this.x==maze.maze[0].length-1 && this.y==0) || (this.x==maze.maze[0].length-1 && this.y==maze.maze.length-1)) return true;
		else return false;
	}

	//-----------------------------
	//	Checks if point is an edge
	//-----------------------------

	public boolean isEdge(){
		if(this.x==0 || this.x==maze.maze[0].length-1 || this.y==0 || this.y==maze.maze.length-1) return true;
		return false;
	}

	//-----------------------------
	//	Compare to method for Point
	//-----------------------------

	public int compareTo(Object other){

		if(this.distance(maze.goal) < ((Point)other).distance(maze.goal)) return 1;
		if(this.distance(maze.goal) > ((Point)other).distance(maze.goal)) return -1;
		else return 0;

		//if(this.c==((Point)other).c) return 0;
		//if(this.c=='G' || ((Point)other).c=='S' || (this.c=='.' && ((Point)other).c=='*')) return 1;
		//if(this.c=='S' || ((Point)other).c=='G' || (this.c=='*' && ((Point)other).c=='.')) return -1;
		//return 0;
	}

	public static void main(String[] args){
	}
}