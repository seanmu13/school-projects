// ------------------------------------------------------
// Each store is an almost like an instance of a variable
// ------------------------------------------------------

public class Store extends Variables
{
	public String name;
	public int val;

	// -------------------------------------------------------
	// Each store consists of its name and corresponding value
	// -------------------------------------------------------

	public Store(String str, int num)
	{
		super();
		name = str;
		val = num;
	}

	/*
	public String toString()
	{
		return "" + name + " : " + val;
	}
	*/
}