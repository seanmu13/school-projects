public class Matrix{

	public char[][] maze;
	public Point start;
	public Point goal;

	//----------------------------------------------------------
	//	Matrix constructor, places characters into char[][] maze
	//----------------------------------------------------------

	public Matrix(char[][] m){
		maze = m;

		for(int i=0;i<maze.length;i++){
			for(int j=0;j<maze[0].length;j++){

				//--------------------------
				//	Finds the starting point
				//--------------------------

				if(maze[i][j]=='S'){
					start = new Point(j,i,'S',this);
				}

				//----------------------
				//	Finds the goal point
				//----------------------

				else if(maze[i][j]=='G'){
					goal = new Point(j,i,'G',this);
				}
			}
		}
	}

	//----------------------------------------
	//	Prints the char[][] maze to the screen
	//----------------------------------------

	public void print(){
		for(int k=0;k<maze.length-1;k++){
			System.out.println(maze[k]);
		}
	}

	//------------------------------------------------------
	// Changes the maze[row][column] to the specified char c
	//------------------------------------------------------

	public void set(int x, int y, char c){
		maze[y][x] = c;
	}

	//---------------------------------------------
	// Retrieves the character at maze[row][column]
	//---------------------------------------------

	public char get(int x, int y){
		return maze[y][x];
	}

	public static void main(String[] args){
	}
}