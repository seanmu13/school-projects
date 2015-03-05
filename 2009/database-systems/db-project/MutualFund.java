/*
Purpose:  A class to handle MutualFund entries in the MutualFuture system.
*/

public class MutualFund
{
	private String symbol, name, description, category, date;
	private float price;

	public MutualFund(String sym, String nm, String desc, String cat, String dt, float pr)
	{
		symbol = sym;
		name = nm;
		description = desc;
		category = cat;
		date = dt;
		price = pr;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public String getCategory()
	{
		return category;
	}

	public String getDate()
	{
		return date;
	}

	public float getPrice()
	{
		return price;
	}

	public void print()
	{
		System.out.println("\nSymbol:\t\t" + symbol);
		System.out.println("Name:\t\t" + name);
		System.out.println("Description:\t" + description);
		System.out.println("Category:\t" + category);



		System.out.println("Date"+(price == 0 ? " Added:\t" : ":\t\t") + date);

		if( price != 0.0 )
		{
			System.out.println("Price:\t\t" + price);
		}
	}
}