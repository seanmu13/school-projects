/*
Purpose:  A class to handle customers as investor clients and to maintain their 'invested' worth.
*/

public class InvestorEntry
{
	private String investor;
	private float invested;

	public InvestorEntry(String owner, float value)
	{
		investor = owner;
		invested = value;
	}

	public String getInvestor()
	{
		return investor;
	}

	public float getInvested()
	{
		return invested;
	}
}