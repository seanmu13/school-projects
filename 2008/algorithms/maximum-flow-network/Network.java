import java.util.*;

public class Network
{
	public ArrayList<String> nodes = new ArrayList<String>();
	public int[][] caps;
	public int[][] flows;
	public MyQueue q;

	// Creates a new network with the given args
	public Network(String args)
	{
		StringTokenizer st = new StringTokenizer(args);

		while(st.hasMoreTokens())
		{
			nodes.add( st.nextToken() );
		}

		// Set up the capacitity and flow arrays
		caps = new int[nodes.size()][nodes.size()];
		flows = new int[nodes.size()][nodes.size()];
	}

	// Sets the caps adjacency matrix with the given capacity c
	public void setCapacity(String n1, String n2, int c)
	{
		caps[nodes.indexOf(n1)][nodes.indexOf(n2)] = c;
	}

	// Sets the flow adjacency matrix with the given flow f
	public void setFlow(String n1, String n2, int f)
	{
		flows[nodes.indexOf(n1)][nodes.indexOf(n2)] = f;
	}

	// Maximizes the flow in the network by using the Ford-Fulkerson algorithm
	public void maximizeFlow(String source, String sink)
	{
		Path currentPath;

		// Get all paths from source to sink and keep updated the flows in the network to maximize flows
		while( (currentPath = getPaths(source, sink)) != null )
		{
			changeNetworkFlow(currentPath);
		}
	}

	// Makes new paths and returns an ArrayList of paths
	public ArrayList<Path> getNewNodes(Path path)
	{
		ArrayList<Path> a = new ArrayList<Path>();
		int from = nodes.indexOf( path.nodes.get(path.size() - 1) );

		for(int to = 0; to < nodes.size(); to++)
		{
			// if the path contains the current node, just continue
			if( path.nodes.contains( nodes.get(to) ) )
			{
				continue;
			}

			// If the flow is than the capacity at that edge, add that the node to a new path and add path to the arraylist
			if( flows[from][to] < caps[from][to] )
			{
				a.add( path.add(path.nodes, nodes.get(to)) );
			}

			// If the flow is greater than 0 in the backward link, add the node to a new path and add path to the arraylist
			if( flows[to][from] > 0 )
			{
				a.add( path.add(path.nodes, nodes.get(to)) );
			}
		}

		return a;
	}

	// Finds the possible values for increasing the flow through a certain edge
	public int findPossibleDelta(Path p)
	{
		// Set initial delta value
		int delta = caps[nodes.indexOf(p.nodes.get(0))][nodes.indexOf(p.nodes.get(1))] - flows[nodes.indexOf(p.nodes.get(0))][nodes.indexOf(p.nodes.get(1))];

		for(int i = 0; i < p.size() - 1; i++)
		{
			String first = p.nodes.get(i);
			String next = p.nodes.get(i+1);

			// If capacity at edge > 0, get new delta
			if( caps[ nodes.indexOf(first) ][ nodes.indexOf(next) ] > 0)
			{
				delta = Math.min(delta, caps[ nodes.indexOf(first) ][ nodes.indexOf(next) ] - flows[ nodes.indexOf(first) ][ nodes.indexOf(next) ] );
			}

			// If capacity is > 0 for the backward link, get new delta
			if( caps[ nodes.indexOf(next) ][ nodes.indexOf(first) ] > 0 )
			{
				delta = Math.min(delta, flows[ nodes.indexOf(next) ][ nodes.indexOf(first) ]);
			}
		}

		return delta;
	}

	// Change the flow on the edges to suite the given change = delta
	public void changeNetworkFlow(Path p)
	{
		int change = findPossibleDelta(p);

		for(int i = 0; i < p.size() - 1; i++)
		{
			String first = p.nodes.get(i);
			String next = p.nodes.get(i+1);

			// If caps > 0, update the flow to the given flow + the change
			if( caps[ nodes.indexOf(first) ][ nodes.indexOf(next) ] > 0)
			{
				flows[ nodes.indexOf(first) ][ nodes.indexOf(next) ] = flows[ nodes.indexOf(first) ][ nodes.indexOf(next) ] + change;
			}

			// If caps for the backward link > 0, update the flow to the given flow - the change
			if( caps[ nodes.indexOf(next) ][ nodes.indexOf(first) ] > 0 )
			{
				flows[ nodes.indexOf(next) ][ nodes.indexOf(first) ] = flows[ nodes.indexOf(next) ][ nodes.indexOf(first) ] - change;
			}
		}
	}

	// Get all paths from source to sink
	public Path getPaths(String source, String sink)
	{
		Path p  = new Path(source);
		q = new MyQueue();
		q.enqueue(p);

		while( ! q.isEmpty() )
		{
			p = q.dequeue();

			if( p.hasReached(sink) )
			{
				return p;
			}

			ArrayList<Path> list = getNewNodes(p);

			for(int i = 0; i < list.size(); i++)
			{
				p = list.get(i);
				q.enqueue(p);
			}
		}

		return null;
	}

	// Print the network to the screen
	public void print()
	{
		for(String s: nodes)
		{
			System.out.print(s + ":  ");
			printLinkedNodes(s);
			System.out.println();
		}
	}

	// Prints the nodes connected to the current node s
	public void printLinkedNodes(String s)
	{
		int index = nodes.indexOf(s);

		for(int i = 0; i < caps[0].length; i++)
		{
			if(caps[index][i] != 0)
			{
				System.out.print(nodes.get(i) + "-" + flows[index][i] + "/" + caps[index][i] + "  ");
			}
		}
	}
}