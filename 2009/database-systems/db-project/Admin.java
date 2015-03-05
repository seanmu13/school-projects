/*
Purpose:  A class to handle all Administrative operations, including custom menus and procedures.
*/

import java.io.*;
import java.util.*;

public class Admin implements User
{
	private String userID;
	private String name;
	private String[] options = {"Register New User","Update Share Quotes","Add new mutual fund","Update time and date","Populate Statistics"};
	private String[] fundCats = {"fixed", "bonds", "stocks", "mixed"};
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in) );

    public Admin(String userName, String adminName)
    {
		userID = userName;
		name = adminName;
	}

	/*
	Main entry method for the Admin class.  Prints welcome message and waits for administrative input
	for menu selection.
	*/
	public void start()
	{
		System.out.println("\n\n***Welcome, "+name+"!***");
    	while(true)
		{
			printMenu();
			doOperation( enterChoice() );
		}
	}

	public void doOperation(int choice)
	{
		boolean success = false;
		switch(choice)
		{
			case 1:
					success = createNewUser();
					if(!success)
					{
						System.out.println("\nNew User Could Not Be Created!\n\n");
					}
					break; //call DBops ;
			case 2: //update share quote
					success = updateShareQuote();
					if(!success)
					{
						System.out.println("\nCould not update Share price!\n");
					}
					break;  //call DBops ;
			case 3:
					success = addNewFund();
					if(!success)
					{
						System.out.println("\nCould not add new fund!\n");
					}
					break;  //call DBops ;
			case 4:
					//update time and date
					success = updateDate();
					if(!success)
					{
						System.out.println("\nMutualFuture DATE Could Not Be updated!\n\n");
					}
					break;  //call DBops ;
			case 5:
					success = performStats();
					if(!success)
					{
						System.out.println("\nCould not perform requested statistics!\n\n");
					}
					break;  //call DBops ;
		}
	}

	public void printMenu()
	{
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
						   "$                                                       $\n"+
						   "$                 Administration Menu                   $\n"+
						   "$                                                       $\n"+
						   "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
		System.out.println();
		for(int i=0; i < options.length; i++)
		{
			System.out.println((i+1) + ". " + options[i]);
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println();
	}

	public int enterChoice()
	{
		int choice = options.length - 1;

		while(true)
		{
			System.out.print("Please enter a value between 1 and "+ options.length +" or press Q to quit: ");
			String choiceString = getInput();

			if(choiceString.toLowerCase().equals("q") || choiceString.toLowerCase().equals("quit") )
			{
				System.out.println("\nGoodbye " + name + "!");
				this.exit(0);
			}

			try
			{
				choice = Integer.parseInt(choiceString);
			}
			catch(Exception e)
			{
				continue;
			}

			if(choice <= 0 || choice > options.length) continue;

			break;
		}
		return choice;
	}

	/*
	A method to request new user information to properly create a new MutualFuture client.
	*/
	private boolean createNewUser()
	{
		String name = "";
		String addr = "";
		String email = "";
		String userName = "";
		String passWord = "";
		boolean adminAcct = false;

		//accumulate new user info and register with DB!
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                 NEW USER REGISTRATION                 *\n"+
						   "*                                                       *\n"+
						   "*********************************************************\n"+
						   "                                                       \n"+
						   "Please supply the following registration information to\n"+
						   "enroll a new user into MutualFuture:                   \n");

		System.out.println("\n-------------------------------------------------------\n"+
						   "Administrator Account (enter Y for yes, N for no:\n"+
						   "-------------------------------------------------------");
		while(true)
		{
			System.out.print(">");
			String adminResp = this.getInput();
			if(adminResp == null || adminResp.equals("") || (!((adminResp.toUpperCase()).equals("N")) && !((adminResp.toUpperCase()).equals("Y"))))
			{
				System.out.println("must enter Y or N!");
			}
			else
			{
				if(adminResp.toUpperCase().equals("Y"))
				{
					adminAcct = true;
				}
				break;
			}
		}
		System.out.println("-------------------------------------------------------\n"+
						   "Name (just first name):\n"+
						   "-------------------------------------------------------");
		while(true)
		{
			System.out.print(">");
			name = this.getInput();
			if(name == null || name.equals(""))
			{
				System.out.println("must enter valid name!");
			}
			else if(name.length() > 20)
			{
				System.out.println("Too many characters.  Max. 20 characters!");
			}
			else
			{
				break;
			}
		}
		System.out.println("-------------------------------------------------------\n"+
						   "Address (house number and street):\n"+
						   "-------------------------------------------------------");
		while(true)
		{
			System.out.print(">");
			addr = this.getInput();
			if(addr == null || addr.equals(""))
			{
				System.out.println("must enter valid address!");
			}
			else if(name.length() > 30)
			{
				System.out.println("Too many characters.  Max. 30 characters!");
			}
			else
			{
				break;
			}
		}
		System.out.println("-------------------------------------------------------\n"+
						   "Email Address:\n"+
						   "-------------------------------------------------------");
		while(true)
		{
			System.out.print(">");
			email = this.getInput();
			if(email == null || email.equals(""))
			{
				System.out.println("must enter valid e-amail address!");
			}
			else if(name.length() > 20)
			{
				System.out.println("Too many characters.  Max. 20 characters!");
			}
			else
			{
				break;
			}
		}
		System.out.println("-------------------------------------------------------\n"+
						   "Preferred Login Name (username):\n"+
						   "-------------------------------------------------------");
		while(true)
		{
			System.out.print(">");
			userName = this.getInput();
			if(userName == null || userName.equals(""))
			{
				System.out.println("must enter valid username!");
			}
			else if(name.length() > 10)
			{
				System.out.println("Too many characters.  Max. 10 characters!");
			}
			else if(!(DBops.validateUserName(userName, adminAcct)))
			{
				System.out.println("Username already registered.  Please select a different one.");
			}
			else
			{
				break;
			}
		}
		System.out.println("-------------------------------------------------------\n"+
						   "Password:\n"+
						   "-------------------------------------------------------");
		while(true)
		{
			System.out.print(">");
			passWord = this.getInput();
			if(passWord == null || passWord.equals(""))
			{
				System.out.println("must enter valid password!");
			}
			else if(name.length() > 10)
			{
				System.out.println("Too many characters.  Max. 10 characters!");
			}
			else
			{
				break;
			}
		}

		System.out.println("-------------------------------------------------------");
		System.out.println("*********************************************************\n");

		boolean success = false;
		if(adminAcct)
		{
			success = DBops.registerAdmin(userName, name, email, addr, passWord);
			if(success)
			{
				System.out.println("\nNew Administrator registered!\n");
			}
			return success;
		}
		else
		{
			success = DBops.registerCustomer(userName, name, email, addr, passWord);
			if(success)
			{
				System.out.println("\nNew Customer registered!\n");
			}
			return success;
		}
	}

	/*
	A method for updating the MutualFuture system date.
	*/
	public boolean updateDate()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                      UPDATE DATE                      *\n"+
						   "*                                                       *\n"+
						   "*********************************************************\n"+
						   "                                                       \n");
		Calendar cal;
		String tempDate = DBops.getDate();
		if(tempDate.equals(""))
		{
			return false;
		}
		System.out.println("The current DATE is "+tempDate);
		System.out.println("Are you sure you want to update MutualFuture's DATE?(Y/N)\n");
		String ans = "";
		while(true)
		{
			ans = getInput();
			if(ans == null || ans.equals("") || (!(ans.toUpperCase().equals("Y")) && !(ans.toUpperCase().equals("N"))))
			{
				System.out.println("You must enter a valid response (Y or N).");
			}
			else
			{
				ans = ans.toUpperCase();
				break;
			}
		}

		if(ans.equals("Y"))
		{
			//change date
			String Year = "";
			String Month = "";
			String Day = "";
			System.out.println("Enter New Year (E.g., for 2009, enter 09):");
			while(true)
			{
				Year = getInput();
				if((Year == null || Year.equals("")) || (Integer.parseInt(Year) <= 0) || Year.length() != 2)
				{
					System.out.println("You must enter a valid year.");
				}
				else
				{
					break;
				}
			}
			System.out.println("Enter New Month (E.g., for April, enter 04):");
			while(true)
			{
				Month = getInput();
				if((Month == null || Month.equals("")) || (Integer.parseInt(Month) < 1 || Integer.parseInt(Month) > 12))
				{
					System.out.println("You must enter a valid Month.");
				}
				else
				{
					int mon = Integer.parseInt(Month);
					cal = new GregorianCalendar(Integer.parseInt("20"+Year), mon-1, 1);
					switch(mon)
					{
						case 1:
									Month = "JAN";
									break;
						case 2:
									Month = "FEB";
									break;
						case 3:
									Month = "MAR";
									break;
						case 4:
									Month = "APR";
									break;
						case 5:
									Month = "MAY";
									break;
						case 6:
									Month = "JUN";
									break;
						case 7:
									Month = "JUL";
									break;
						case 8:
									Month = "AUG";
									break;
						case 9:
									Month = "SEP";
									break;
						case 10:
									Month = "OCT";
									break;
						case 11:
									Month = "NOV";
									break;
						case 12:
									Month = "DEC";
									break;
					}
					break;
				}
			}
			System.out.println("Enter New Day\n(E.g., for the first day of the month, enter 01):");
			while(true)
			{
				Day = getInput();
				if((Day == null || Day.equals("")) || (Integer.parseInt(Day) > cal.getActualMaximum(Calendar.DAY_OF_MONTH) || Integer.parseInt(Day) <= 0))
				{
					System.out.println("You must enter a valid day.");
				}
				else
				{
					break;
				}
			}
			DBops.updateDate(Day+"-"+Month+"-"+Year);
			System.out.println("\nMUTUALFUTRE date has been updated!\n");
		}
		return true;
	}

	/*
	A method to udpate to the share price (closing price) for a mutual fund available in the
	MutualFuture system.
	*/
	public boolean updateShareQuote()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                  UPDATE SHARE QUOTE                   *\n"+
						   "*                                                       *\n"+
						   "*********************************************************\n"+
						   "                                                       \n");

		System.out.println("Available Funds:\n");
		ArrayList<String> funds = DBops.getFunds();
		int listSize = funds.size();
		for(int i = 0; i<listSize; i++)
		{
			System.out.println("("+(i+1)+") "+funds.get(i));
		}
		System.out.println("Please select the Mutual Fund You would like to update:\n(Enter Corresponding Number)");
		int index = 0;
		while(true)
		{
			String temp = getInput();
			int tempIndex = Integer.parseInt(temp);
			if(temp == null || temp.equals("") || !(tempIndex > 0 && tempIndex < listSize))
			{
				System.out.println("Invalid response.  Please Try Again!");
			}
			else
			{
				index = tempIndex-1;
				break;
			}
		}

		System.out.println("\nPlease enter the new quote price:");
		float price = 0;
		while(true)
		{
			String temp = getInput();
			float tempPrice = Float.parseFloat(temp);
			if(temp == null || temp.equals("") || tempPrice < (float)0)
			{
				System.out.println("Invalid response.  Please Try Again!");
			}
			else
			{
				price = tempPrice;
				break;
			}
		}

		if(DBops.updateFundPrice(funds.get(index), price))
		{
			System.out.println("\nFund Updated!\n\n");
			return true;
		}
		else
		{
			return false;
		}
	}

	/*
	A method to request necessary information from administrator to add a new mutual fund
	to the MutualFuture system.
	*/
	public boolean addNewFund()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                      ADD A NEW FUND                   *\n"+
						   "*                                                       *\n"+
						   "*********************************************************\n"+
						   "                                                       \n");

		System.out.println("\nPlease enter the fund symbol:");
		String symbol = "";
		while(true)
		{
			symbol = getInput();
			if(symbol == null || symbol.equals(""))
			{
				System.out.println("Invalid response.  Please Try Again!");
			}
			else if(name.length() > 20)
			{
				System.out.println("Too many characters.  Max. 20 characters!");
			}
			else
			{
				break;
			}
		}
		System.out.println("\nPlease enter the fund name:");
		String name = "";
		while(true)
		{
			name = getInput();
			if(name == null || name.equals(""))
			{
				System.out.println("Invalid response.  Please Try Again!");
			}
			else if(name.length() > 30)
			{
				System.out.println("Too many characters.  Max. 30 characters!");
			}
			else
			{
				break;
			}
		}
		System.out.println("\nPlease enter fund description:");
		String desc = "";
		while(true)
		{
			desc = getInput();
			if(desc == null || desc.equals(""))
			{
				System.out.println("Invalid response.  Please Try Again!");
			}
			else if(name.length() > 100)
			{
				System.out.println("Too many characters.  Max. 100 characters!");
			}
			else
			{
				break;
			}
		}
		System.out.println("\nPlease enter the fund category:");
		for(int i = 0; i<fundCats.length; i++)
		{
			System.out.println("("+(i+1)+") "+fundCats[i]);
		}
		String strCat = "";
		int choice = 0;
		String cat = "";
		while(true)
		{
			System.out.print("\n>");
			strCat = getInput();

			if((strCat == null || strCat.equals("")) || (Integer.parseInt(strCat) < 1 || Integer.parseInt(strCat) > fundCats.length))
			{
				System.out.println("Invalid response.  Please Try Again!");
			}
			else
			{
				choice = Integer.parseInt(strCat) - 1;
				cat = fundCats[choice];
				break;
			}
		}

		MutualFund temp = new MutualFund(symbol, name, desc, cat, DBops.getDate(), 0);

		if(DBops.addFund(temp))
		{
			System.out.println("\nNew Fund added!\n\n");
			return true;
		}
		else
		{
			return false;
		}
	}

	/*
	A method for administrative use for populating two types of statistics from the data
	in the MutualFuture system.
	(1)  The top K investors over X amount of months
	(2)  The top K performing mutual funds over X amount of months
	*/
	public boolean performStats()
	{
		System.out.println("\n*********************************************************\n"+
						   "*                                                       *\n"+
						   "*                MUTUALFUTURE STATISTICS                *\n"+
						   "*                                                       *\n"+
						   "*********************************************************\n"+
						   "                                                       \n");
		System.out.println("\nFor the past how many months? (e.g., 12):");
		String strMonths = "";
		int months;
		while(true)
		{
			strMonths = getInput();
			if(strMonths == null || strMonths.equals(""))
			{
				System.out.println("Invalid response.  Please Try Again!");
			}
			else
			{
				months = Integer.parseInt(strMonths);
				if(months < 0)
				{
					System.out.println("Invalid response.  Please Try Again!");
				}
				else
				{
					break;
				}
			}
		}
		System.out.println("\n(1) Populate the top K highest volume categories over "+months+" months\n\n-OR-\n");
		System.out.println("(2) Populate the top K most investors over "+months+" months\n");

		boolean success = false;

		while(true)
		{
			System.out.print("\nPlease Make Your Selection:");
			String opt = getInput();

			if(opt == null || opt.equals("") || (!(opt.equals("1")) && !(opt.equals("2"))))
			{
				System.out.println("Invalid response!");
			}
			else
			{
				int numK;
				String strNum = "";
				int option = Integer.parseInt(opt);
				System.out.println("\nShow How many top "+((option == 1) ? "categories ":"investors ")+" (e.g., 3):");
				while(true)
				{
					strNum = getInput();
					if(strNum == null || strNum.equals(""))
					{
						System.out.println("Invalid response.  Please Try Again!");
					}
					else
					{
						numK = Integer.parseInt(strNum);
						if(numK < 0)
						{
							System.out.println("Invalid response.  Please Try Again!");
						}
						else
						{
							break;
						}
					}
				}
				if(option == 1)
				{
					success = DBops.runCategoryStats(months, numK);
				}
				else
				{
					success = DBops.runInvestorStats(months, numK);
				}
				break;
			}
		}

		return success;
	}

	/*
	A method to acquire user input from the command line.
	*/
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
		return input;
	}

	/*
	Accessor Method for the Admin instance's name variable.
	*/
	public String Name()
	{
		return this.name;
	}

	/*
	An exit method to cleanly exit the program (e.g., close DB connections)
	*/
	private void exit(int i)
	{
		DBops.close();
		System.exit(i);
	}
}