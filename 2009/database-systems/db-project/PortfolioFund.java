/*
Purpose:  Provides a mechanism for storing stocks value, symbol, and #of shares for the portfolio
*/

public class PortfolioFund
{
	private String symbol;
	private int price;
	private int shares;

	// Contructor takes a stock symbol, price, and # of shares

	public PortfolioFund(String sym, int p, int s)
	{
		symbol = sym;
		price = p;
		shares = s;
	}

	public int getValue()
	{
		return shares * price;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public void print()
	{
		System.out.println("\nSymbol: " + symbol);
		System.out.println("Fund Historical Value: " + price);
		System.out.println("Number of Shares: " + shares);
		System.out.println("Total Fund Investment: " + getValue());
	}
}