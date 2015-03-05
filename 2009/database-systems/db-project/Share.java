/*
Purpose:  A class to handle Shares existing (or being created) in the MutualFuture system.
*/

public class Share
{
	private String symbol;
	private String number;

	public Share(String sym, String num)
	{
		symbol = sym;
		number = num;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public String getNumber()
	{
		return number;
	}

	public void print()
	{
		System.out.println("\nSymbol: " + symbol + "  Number of Shares: " + number);
	}
}