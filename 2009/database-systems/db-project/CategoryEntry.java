/*
Purpose:  A class to handle Category Entries fo a given mutual fund.
*/

public class CategoryEntry
{
	private String category;
	private int numShares;

	public CategoryEntry(String cat, int value)
	{
		category = cat;
		numShares = value;
	}

	public String getCategory()
	{
		return category;
	}

	public int getNumShares()
	{
		return numShares;
	}
}