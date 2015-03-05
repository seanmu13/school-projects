import java.util.ArrayList;

public class MyQueue
{
	public ArrayList<Path> a = new ArrayList<Path>();

	// Adds to the queue
    public void enqueue(Path p)
    {
		a.add(0,p);
    }

	// Removes the oldest item in the queue
    public Path dequeue()
    {
		if( a.isEmpty() )
		{
			return null;
		}
		else
		{
			return a.remove( a.size()-1 );
		}
    }

	// Tells if queue is empty
    public boolean isEmpty()
    {
		return a.isEmpty();
    }
}
