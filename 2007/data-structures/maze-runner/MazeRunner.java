import java.io.*;
import java.util.ArrayList;

public class MazeRunner{
	public static void main(String[] args) throws IOException{
		//------------------------------------------------------
		// Establishes bufferedReader to read in character array
		//------------------------------------------------------

		BufferedReader br = new BufferedReader(new FileReader("Maze4"));
		ArrayList<String> alist = new ArrayList<String>();

		char[][] oldMatrix = new char[100][];

		alist.add(br.readLine());

		//-------------------------------------
		//	Reads in maze array to an arraylist
		//-------------------------------------

		while(alist.get(alist.size()-1)!=null){
			alist.add(br.readLine());
		}

		alist.remove(alist.size()-1);
		br.close();

		//--------------------------------------------------
		//	Copies characters from arrayList to matrix array
		//--------------------------------------------------

		for(int i=0;i<alist.size();i++){
			oldMatrix[i] = new char[alist.get(i).length()];
			for(int j=0;j<alist.get(0).length();j++){
				oldMatrix[i][j] = alist.get(i).charAt(j);
			}
		}

		//---------------------------------------------------------
		//	Creates new matrix so that it is the exact size of maze
		//---------------------------------------------------------

		char[][] matrix = new char[alist.size()][alist.get(0).length()];

		for(int i=0;i<alist.size();i++){
			for(int j=0;j<alist.get(0).length();j++){
				matrix[i][j] = oldMatrix[i][j];
			}
		}

		//--------------------------------------
		//	Creates a matrix from the maze array
		//--------------------------------------

		Matrix mat = new Matrix(matrix);

		PriorityQueue p = new PriorityQueue();
		p.add(mat.start);

		Comparable curr;
		Point current;
		boolean success = false;
		MazeFrame frame = new MazeFrame(mat.maze);

		while(!success){
			if(p.isEmpty()){
				System.out.println("\n\n----------------Failure----------------\n");
				break;
			}
			curr = (Point)p.remove();
			current = new Point(((Point)curr).x,((Point)curr).y,((Point)curr).c,((Point)curr).maze);
			mat.set(current.x,current.y,'T');
			if(current.c=='G'){
				frame.draw();
				System.out.println("\n\n----------------SUCCESS----------------\n");
				break;
			}
			else{
				if(current.isCorner()){
					if(current.x-1 > 0) p.add(new Point(current.x-1,current.y,current.maze.get(current.x-1,current.y),current.maze));
					if(current.x+1 < current.maze.maze[0].length) p.add(new Point(current.x+1,current.y,current.maze.get(current.x+1,current.y),current.maze));
					if(current.y-1 > 0) p.add(new Point(current.x,current.y-1,current.maze.get(current.x,current.y-1),current.maze));
					if(current.y+1 < current.maze.maze.length) p.add(new Point(current.x,current.y+1,current.maze.get(current.x,current.y+1),current.maze));
				}
				else if(current.isEdge()){
				}
				else{
					if(current.maze.get(current.x,current.y+1) == '.' || current.maze.get(current.x,current.y+1) == 'G') p.add(new Point(current.x,current.y+1,current.maze.get(current.x,current.y+1),current.maze));
					if(current.maze.get(current.x,current.y-1) == '.' || current.maze.get(current.x,current.y-1) == 'G') p.add(new Point(current.x,current.y-1,current.maze.get(current.x,current.y-1),current.maze));
					if(current.maze.get(current.x+1,current.y) == '.' || current.maze.get(current.x+1,current.y) == 'G') p.add(new Point(current.x+1,current.y,current.maze.get(current.x+1,current.y),current.maze));
					if(current.maze.get(current.x-1,current.y) == '.' || current.maze.get(current.x-1,current.y) == 'G') p.add(new Point(current.x-1,current.y,current.maze.get(current.x-1,current.y),current.maze));
				}
			}

			int pause = 200;
			try{Thread.sleep(pause);
			} catch (Exception e){ };

			frame.draw();

			mat.set(current.x,current.y,'V');
		}
	}
}