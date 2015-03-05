import java.util.*;

public class Stack
{
	// -------------------------------------------------------
	// Uses an ArrayList to keep track of entries on the stack
	// -------------------------------------------------------

	public ArrayList<ArrayList> list;

    public Stack()
    {
        list = new ArrayList<ArrayList>();
    }

	// --------------------------------------------------------------------------------
	// When you push items onto the stack, just add them to the front of the ArrayList
	// --------------------------------------------------------------------------------

    public void push(ArrayList item)
    {
        list.add(0, item);
    }

	// ----------------------------------------------------------------------------------------------------
	// When you pop items off of the stack, simply remove the first item because it is the most recent item
	// ----------------------------------------------------------------------------------------------------

    public ArrayList pop()
    {
        return list.remove(0);
    }

	// ---------------------------------------------------------------------------------------
	// Returns whether or not the stack/ArrayList is empty, using ArrayList isEmpty() function
	// ---------------------------------------------------------------------------------------

    public boolean isEmpty()
    {
        return list.isEmpty();
    }
}
