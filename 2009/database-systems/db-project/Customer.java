/*
Purpose:  The provides the interface for the Customer to perform all of the Customer functions specified in the project description
*/

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Customer
implements User
{
	private String userID = null;
	private String name = null;
	private String[] options = {"Filler","Browse Mutual Funds","Search Mutual Fund by Text","Invest","Sell Shares","Buy Shares","Change Allocation Preferences","View Customer Portfolio"};
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in) );

	// -------------------------------------------
	// Constructor reads in a user id and username
	// -------------------------------------------

	public Customer(String uid, String rname)
	{
		userID = uid;
		name = rname;
		DBops.connectToDB();
	}

	// --------------------------------------------------------------------------
	// Prints the menu with the options listed above in the options string array
	// --------------------------------------------------------------------------

	public void start()
	{
		System.out.println("\n\n***Welcome, "+name+"!***");
		while(true)
		{
			printMenu();
			doOperation( enterChoice() );
		}
	}

	// --------------------------------------------------------------------------
	// Calls the function pertaining to a certain menu item number
	// --------------------------------------------------------------------------

	public void doOperation(int choice)
	{
		switch(choice)
		{
			case 1: browseMutualFunds(); break;
			case 2: searchMutualFunds(); break;
			case 3: invest(); break;
			case 4: sell(); break;
			case 5: buy(); break;
			case 6: changePrefs(); break;
			case 7: viewPortfolio(); break;
		}
	}

	// --------------------------------------------------------------------------
	// Allows the user to browse the mutal funds
	// --------------------------------------------------------------------------

	public void browseMutualFunds()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                 BROWSE MUTUAL FUNDS                   *\n"+
						   "*                                                       *\n"+
						   "*********************************************************\n");

		System.out.println("\t1. Browse All Mutual Funds");
		System.out.println("\t2. Browse Mutual Funds By Category");
		System.out.println("\t3. Browse Mutual Funds Sorted By Price");
		System.out.println("\t4. Browse Mutual Funds Sorted Alphabetically\n");
		String input;

		while(true)
		{
			System.out.print("Please enter a choice (1-4): ");
			input = getInput();

			if( Pattern.matches("[1-4]",input) )	break;
		}

		int choice = Integer.parseInt(input);
		String option = null;
		String param = null;

		if( choice == 1 )
		{
			option = "all";
		}
		else if( choice == 2 )
		{
			option = "category";
			while(true)
			{
				System.out.print("Please enter a category (fixed, bonds, mixed, stocks): ");
				param = getInput().toLowerCase();

				if( Pattern.matches("\\b(fixed|bonds|mixed|stocks)\\b",param) )	break;
			}
		}
		else if( choice == 3 )
		{
			option = "price";
			while(true)
			{
				System.out.print("Please enter a date (dd-mon-yy): ");
				param = getInput().toLowerCase();

				if( Customer.isLegitDate(param) )	break;
			}
		}
		else if( choice == 4 )
		{
			option = "alpha";
		}

		ArrayList<MutualFund> mutualFunds = DBops.browseMutualFunds(option, param);

		for(int i=0; i < mutualFunds.size(); i++)
		{
			mutualFunds.get(i).print();
		}
	}

	public static boolean isLegitDate(String date)
	{
		return Pattern.matches("(0[1-9]|1[0-9]|2[0-9]|3[01])[-](jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[-]\\d\\d",date);
	}

	// --------------------------------------------------------------------------
	// Allows the user to search through the mutual funds descriptions
	// --------------------------------------------------------------------------

	public void searchMutualFunds()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                 SEARCH MUTUAL FUNDS                   *\n"+
						   "*                                                       *\n"+
						   "*********************************************************\n");
		String[] words;
		while(true)
		{
			System.out.print("Please enter a search query (up to 2 keywords): ");
			String search = getInput();
			if( Pattern.matches("\\w+[ ]*\\w*",search) )
			{
				Pattern p = Pattern.compile("[ ]+");
				words = p.split(search,2);
				break;
			}
		}

		String keyword2 = null;
		if(words.length > 1) keyword2 = words[1];

		ArrayList<MutualFund> mutualFunds = DBops.searchMutualFunds(words[0],keyword2);

		for(int i=0; i < mutualFunds.size(); i++)
		{
			mutualFunds.get(i).print();
		}

		if(mutualFunds.size() == 0)	System.out.println("Sorry, your search did not yield any results.");
	}

	// --------------------------------------------------------------------------
	// Allows the user to invest x amount of money and distribute it based on their allocation preferences
	// --------------------------------------------------------------------------

	public void invest()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                          INVEST                       *\n"+
						   "*                                                       *\n"+
						   "*********************************************************");
		int amount;

		while(true)
		{
			System.out.print("\nHow much would you like to invest? ");
			String amountStr = getInput();

			try
			{
				amount = Integer.parseInt(amountStr);
			}
			catch(Exception e)
			{
				System.out.println("Please enter an integer value.");
				continue;
			}

			if(amount <= 0)
			{
				System.out.println("Please enter an integer value > 0.");
				continue;
			}
			break;
		}

		DBops.invest(amount,userID);
	}

	// --------------------------------------------------------------------------
	// Allows a user to sell his or her shares with the resulting money being put into their balance
	// --------------------------------------------------------------------------

	public void sell()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                          SELL                         *\n"+
						   "*                                                       *\n"+
						   "*********************************************************");

		ArrayList<Share> shares = DBops.getShares(userID);

		if(shares.size() == 0)
		{
			System.out.println("\nSorry, you currently do not have any shares.");
			return;
		}

		System.out.println("\nCurrent Shares for " + name + ":");

		for(int i=0; i< shares.size(); i++)
		{
			shares.get(i).print();
		}

		int number;
		String symbol;
		int maxShares = 0;
		while(true)
		{
			System.out.print("\nWhat shares would you like to sell (symbol)? ");
			symbol = getInput().toUpperCase();

			boolean foundSym = false;
			for(Share s: shares)
			{
				if( s.getSymbol().equals(symbol) )
				{
					foundSym = true;
					maxShares = Integer.parseInt(s.getNumber());
				}
			}

			if(!foundSym)
			{
				System.out.println("Sorry, you don't have shares with the symbol: " + symbol);
				continue;
			}

			while(true)
			{
				System.out.print("\nHow many shares (Max: " + maxShares + ") would you like to sell of " + symbol + "? ");
				String numberStr = getInput();

				try
				{
					number = Integer.parseInt(numberStr);
				}
				catch(Exception e)
				{
					System.out.println("Please enter an integer value between 1 and " + maxShares);
					continue;
				}

				if(number < 1 || number > maxShares)
				{
					System.out.println("Please enter an integer value between 1 and " + maxShares);
					continue;
				}
				break;
			}
			break;
		}

		DBops.sell(userID, symbol, number);
	}

	// --------------------------------------------------------------------------
	// Allows a user to purcahse mutual funds
	// --------------------------------------------------------------------------

	public void buy()
	{
		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
						   "$                                                       $\n"+
						   "$                          BUY                          $\n"+
						   "$                                                       $\n"+
						   "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

		float balance = DBops.getBalance(userID);

		if(balance <= 0)
		{
			System.out.println("\nSorry, your balance is 0.  You must first invest before buying.");
			return;
		}

		ArrayList<String> funds = DBops.getFunds();

		for(int i=0; i < funds.size(); i++)
		{
			System.out.println( i + 1 + ". " + funds.get(i));
		}

		int choice = 0;
		while(true)
		{
			System.out.print("Please choose an item to buy (1-" + funds.size() + ") : ");
			String choiceStr = getInput();

			try
			{
				choice = Integer.parseInt(choiceStr);
			}
			catch(Exception e)
			{
				continue;
			}

			if( choice < 1 || choice > funds.size() )
				continue;

			break;
		}

		String symbol = funds.get(choice-1);
		boolean buyShares = true;
		float dollarAmt = 0;
		int numShares = 0;
		float stockValue = 0;

		while(true)
		{
			System.out.println("How many/much would you like to buy?");
			System.out.print("Response can be number of shares or a $amount: ");
			String quantity = getInput();

			if(quantity.contains("$"))
			{
				quantity = quantity.substring(1);

				try
				{
					dollarAmt = Float.parseFloat(quantity);
				}
				catch(Exception e)
				{
					continue;
				}
				buyShares = false;
			}
			else
			{
				try
				{
					numShares = Integer.parseInt(quantity);
				}
				catch(Exception e)
				{
					continue;
				}
			}

			//if(balance < 0) return;

			stockValue = DBops.getStockValue(symbol);

			if(buyShares)
			{
				if( numShares < 1 || (numShares * stockValue) > balance)
				{
					System.out.println("At stock value " + stockValue + ",the amount you specified exceeds your balance, please try again.");
					continue;
				}
			}
			else
			{
				if(dollarAmt < 1 || dollarAmt > balance || dollarAmt < stockValue)
				{
					System.out.println("The amount you specified exceeds your balance or is too small for the stock value of " + stockValue + ", please try again.");
					continue;
				}

				try
				{
					numShares = (int)(dollarAmt / stockValue);
				}
				catch(Exception e)
				{
					return;
				}
			}

			break;
		}

		System.out.println("Symbol: " + symbol);
		System.out.println("NumShares: " + numShares);

		DBops.buy(userID, symbol, numShares, stockValue);
	}

	// --------------------------------------------------------------------------
	// Allows a user to make changes to his or her allocation preferences
	// --------------------------------------------------------------------------

	public void changePrefs()
	{
		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
						   "$                                                       $\n"+
						   "$             CHANGE ALLOCATION PREFERENCES             $\n"+
						   "$                                                       $\n"+
						   "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

		String currentDate = DBops.getDate();
		String dateOfLastAlloc = DBops.getLastAllocDate(userID);

		System.out.println("Today: " + currentDate);
		System.out.println("Last: " + dateOfLastAlloc);

		if( withinAMonth(currentDate,dateOfLastAlloc) )
		{
			System.out.println("Sorry, you have edited your preferences in the last month.");
			System.out.println("You may only edit your preferences once a month.");
			return;
		}

		ArrayList<String> funds = DBops.getFunds();
		ArrayList<String> fundsChosen = new ArrayList<String>();
		ArrayList<Allocation> allocations = new ArrayList<Allocation>();
		int allocationLeft = 100;

		for(int i=0; i < funds.size(); i++)
		{
			System.out.println( i + 1 + ". " + funds.get(i));
		}

		System.out.println();

		int choice = 0;
		while(allocationLeft > 0)
		{
			System.out.print("Please choose an item for allocation that you have not chosen already(1-" + funds.size() + "): ");
			String choiceStr = getInput();

			try
			{
				choice = Integer.parseInt(choiceStr);
			}
			catch(Exception e)
			{
				continue;
			}

			if( choice < 1 || choice > funds.size())
				continue;

			String symbol = funds.get(choice-1);

			if(fundsChosen.contains(symbol))
				continue;

			fundsChosen.add(symbol);
			String percentStr;
			int percentage;

			while(true)
			{
				System.out.print("Please choose an allocation percentage (10 - " + allocationLeft + ", by 10s): ");
				percentStr = getInput();
				if( ! Pattern.matches("[1-9]0|100",percentStr ) || Integer.parseInt(percentStr) > allocationLeft ) continue;

				break;
			}

			percentage = Integer.parseInt(percentStr);
			allocations.add(new Allocation(symbol, ((double)percentage)/100 ));
			allocationLeft -= percentage;

			System.out.println("\n\tSymbol: " + symbol);
			System.out.println("\tPercentage: " + percentage + "%");
			System.out.println("\tAllocation Percentage Remaining: " + allocationLeft + "%\n");

			if(allocationLeft > 0)
				continue;

			break;
		}
		DBops.changeAllocations(userID, allocations);
	}

	public boolean withinAMonth(String today, String lastAlloc)
	{
		if(lastAlloc.equals("")) return false;

		String[] tSplit = today.split("-");
		String[] aSplit = lastAlloc.split("-");
		return tSplit[1].equals(aSplit[1]) &&  tSplit[2].equals(aSplit[2]);
	}

	// --------------------------------------------------------------------------
	// Allows a user to view his or her portfolio, which contains information on the stocks they have and their net worth
	// --------------------------------------------------------------------------

	public void viewPortfolio()
	{
		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
						   "$                                                       $\n"+
						   "$                     CUSTOMER PORTFOLIO                $\n"+
						   "$                                                       $\n"+
						   "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
		String date;

		while(true)
		{
			System.out.print("Please enter a date for the portfolio(dd-mon-yy): ");
			date = getInput().toLowerCase();

			if( Customer.isLegitDate(date) ) break;
		}

		DBops.viewPortfolio(userID, date);
	}

	// --------------------------------------------------------------------------
	// Prints the main customer menu
	// --------------------------------------------------------------------------

	public void printMenu()
	{
		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
						   "$                                                       $\n"+
						   "$                     CUSOMTER MENU                     $\n"+
						   "$                                                       $\n"+
						   "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

		for(int i=1; i < options.length; i++)
		{
			System.out.println(i + ". " + options[i]);
		}
		System.out.println();
	}

	// --------------------------------------------------------------------------
	// Allows the user to enter a number pertaining to an item on the menu
	// --------------------------------------------------------------------------

	public int enterChoice()
	{
		int choice = options.length - 1;

		while(true)
		{
			System.out.print("Please enter a value between 1 and 7 or press Q to quit: ");
			String choiceString = getInput();

			if(choiceString.toLowerCase().equals("q") || choiceString.toLowerCase().equals("quit") )
			{
				System.out.println("\nGoodbye " + name + "!");
				exit(0);
			}

			try
			{
				choice = Integer.parseInt(choiceString);
			}
			catch(Exception e)
			{
				continue;
			}

			if(choice < 1 || choice > options.length - 1) continue;

			break;
		}
		return choice;
	}

	// --------------------------------------------------------------------------
	// Gets the input from the command line
	// --------------------------------------------------------------------------

	public String getInput()
	{
		String input = null;
		try
		{
			input = in.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return input.trim();
	}

	// --------------------------------------------------------------------------
	// Returns this person's name
	// --------------------------------------------------------------------------

	public String Name()
	{
		return this.name;
	}

	// --------------------------------------------------------------------------
	// Exits the program
	// --------------------------------------------------------------------------

	public void exit(int i)
	{
		DBops.close();
		System.exit(i);
	}
}