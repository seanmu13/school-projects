// CS 0401 Fall 2007-01
// Specifications of the MyBag interface.  Read these specifications very
// carefully -- your MyDB class must implement all of these methods
// exactly as specified (so that it implements the interface).

public interface MyBag {

	// Return true if no items are in the MyBag, and false otherwise
	public boolean isEmpty ();

	// Return the number of items in the MyBag
	public int size ();

	// Add a new Object to the MyBag.  Note that the location of the
	// new element is not specified, so you should add it in the most
	// convenient location.
	public void addElement (Object value);

	// Return true if the parameter Object is found in the MyBag, and
	// false otherwise.  The search should be based solely on the
	// equals() method as defined for the data in the MyBag.  Note that
	// equals() is defined for class Object, so the correctness of this
	// method depends on equals() being correctly overridden for the
	// class of the object passed in.
	public boolean containsElement (Object value);

	// Return the object found in the MyBag that equals() the parameter
	// object, or null if the parameter object is not found.
	public Object findElement (Object value);

	// Find (using equals()) and remove the parameter object from the
	// MyBag.  If the object is not found, do nothing.
	public void removeElement (Object value);
}

