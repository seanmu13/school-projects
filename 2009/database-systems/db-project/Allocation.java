/*
Purpose:  A class to handle Allocations of mutual funds in the MutualFuture system.
*/

public class Allocation
{
	private String symbol;
	private double percentage;

	public Allocation(String sym, double per)
	{
		symbol = sym;
		percentage = per;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public double getPercentage()
	{
		return percentage;
	}

	public void print()
	{
		System.out.println("\nSymbol: " + symbol);
		System.out.println("Percentage: " + percentage);
	}
}