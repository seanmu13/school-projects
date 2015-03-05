import java.util.*;

public class Path
{
	// Keeps track of nodes in the path
	public ArrayList<String> nodes;

	// Adds the first string to the path
	public Path(String start)
	{
		nodes = new ArrayList<String>();
		add(start);
	}

	// Constructs a path with the given arraylist
	public Path(ArrayList<String> a)
	{
		nodes = a;
	}

	// Adds a string to the path
	public void add(String s)
	{
		nodes.add(s);
	}

	// Returns a path contructed of the given ArrayList and the String
	public Path add(ArrayList<String> a, String s)
	{
		// Copies the ArrayList items so that it can be used in the new path
		ArrayList<String> list = new ArrayList<String>();

		for(String str: a)
		{
			list.add(str);
		}

		// Creates a new path with the list and adds the string to the end of the path
		Path p = new Path(list);
		p.add(s);

		return p;
	}

	// Returns the size of the path
	public int size()
	{
		return nodes.size();
	}

	// Returns whether or not the path has reached the sink
	public boolean hasReached(String sink)
	{
		return sink.equals( nodes.get(size() - 1) );
	}

	// Print the path to the screen if needed, mainly just for testing
	public void print(){
		for(int i = 0; i < nodes.size(); i++)
		{
			if( i == nodes.size() - 1)
			{
				System.out.print( nodes.get(i) );
			}
			else
			{
				System.out.print( nodes.get(i) + " -> " );
			}
		}

		System.out.println();
	}
}