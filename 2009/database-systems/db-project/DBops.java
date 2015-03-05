/*
Purpose:  The provides the interface to perform all Database operations
*/


// --------------------------------------------------------
// Performs database operations for user login/registration
// --------------------------------------------------------

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import oracle.jdbc.driver.*;

public class DBops
{
	public static ResultSet r = null;
	public static Connection c = null;
	public static Statement stmt = null;
	public static int count = 0;

	// --------------------------------------------
	// Try to connect to the database
	// --------------------------------------------

	public static boolean connectToDB()
	{
		try
		{
			DriverManager.registerDriver(new OracleDriver());
			String connectString = "jdbc:oracle:thin:@(description=(address=(host=db0.cs.pitt.edu)(protocol=tcp)(port=1521))(connect_data=(sid=dbclass)))";
			String userid = "ceb32";
			String pwd = "CyprusDB09";
			c = DriverManager.getConnection(connectString,userid,pwd);
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			//c = DriverManager.getConnection("jdbc:odbc:db");
			//stmt = c.createStatement();
		}
		catch(Exception e)
		{
			return false;
		}

		return true;
	}

	// --------------------------------------------
	// Try to close the connection to the database
	// --------------------------------------------

	public static void close()
	{
		try
		{
     		c.close();
		}
		catch(SQLException sqle){}
	}

	// --------------------------------------------
	// Confirm the login process by checking the DB
	// --------------------------------------------

	public static boolean validateUserName(String usrName, boolean adminAcct)
	{
		try
		{
			Statement checkSTMT = c.createStatement();
			if(adminAcct)
			{
				r = checkSTMT.executeQuery("SELECT name FROM ADMINISTRATOR WHERE admin_login = '"+usrName+"'");
			}
			else
			{
				r = checkSTMT.executeQuery("SELECT name FROM CUSTOMER WHERE customer_login = '"+usrName+"'");
			}
			try
			{
				boolean rowsExist = r.next();
				if(rowsExist)
				{
					return false;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	// ----------------------------------------------
	// Login either the administrator or the customer
	// ----------------------------------------------

	public static String[] checkLogin(String usrName, String pWord)
	{
		ResultSet r = null;

		// --------------------------------------------
		// Check to see if user is in DB
		// --------------------------------------------

		try
		{
			Statement checkSTMT = c.createStatement();
			r = checkSTMT.executeQuery("SELECT name, password FROM CUSTOMER WHERE customer_login = '"+usrName+"'");


		// --------------------------------------------
		// If the user is in the DB check the password
		// --------------------------------------------

			try
			{
				boolean rowsExist = r.next();
				if(rowsExist)
				{
					//System.out.println("Got HERE!");
					if( pWord.equals(r.getString("password")) )
					{
						String[] s = {"true","Customer",r.getString("name")};
						return s;
					}
					else
					{
						String[] s = {"false",null,null};
						return s;
					}
				}
				else
				{
					//check admin!
					r = checkSTMT.executeQuery("SELECT name, password FROM ADMINISTRATOR WHERE admin_login = '"+usrName+"'");
					try
					{
						rowsExist = r.next();
						if(rowsExist)
						{
							if( pWord.equals(r.getString("password")) )
							{
								String[] s = {"true","Administrator",r.getString("name")};
								return s;
							}
							else
							{
								String[] s = {"false",null,null};
								return s;
							}
						}
						else
						{
							String[] s = {"false","not registered",null};
							return s;
						}
					}
					catch(Exception e)
					{
						String[] s = {"false",null,null};
						System.out.println(e);
						return s;
					}
				}
			}
			catch(Exception e)
			{
				String[] s = {"false",null,null};
				System.out.println(e);
				return s;
			}
		}
		catch(Exception e)
		{
			String[] s = {"false",null,null};
			return s;
		}
	}

	// ---------------------
	// Register a new admin
	// ---------------------

	public static boolean registerAdmin(String usrName, String name, String email, String addr, String pWord)
	{
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				fillSTMT.executeUpdate("INSERT INTO ADMINISTRATOR VALUES('"+usrName+"','"+name+"','"+email+"','"+addr+"','"+pWord+"')");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
				return false;
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		return true;
	}

	// ------------------------
	// Register a new customer
	// ------------------------

	public static boolean registerCustomer(String usrName, String name, String email, String addr, String pWord)
	{
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				fillSTMT.executeUpdate("INSERT INTO CUSTOMER VALUES('"+usrName+"','"+name+"','"+email+"','"+addr+"','"+pWord+"',0)");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
				return false;
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		return true;
	}

	// ----------------------------------
	// Update the date with the new date
	// ---------------------------------

	public static boolean updateDate(String newDate)
	{
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				fillSTMT.executeUpdate("UPDATE MUTUALDATE set c_date = to_date('"+newDate+"', 'DD-MON-YY')");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
				return false;
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		return true;
	}

	// --------------------
	// Get the current date
	// --------------------

	public static String getDate()
	{
		String retVal = "";
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				r = fillSTMT.executeQuery("SELECT to_char(c_date, 'DD-MON-YY') as c_date FROM MUTUALDATE");

				boolean rowsExist = r.next();
				if(rowsExist)
				{
					retVal = r.getString("c_date");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return retVal;
	}

	// -------------------------
	// Retrieve the mutual funds
	// -------------------------

	public static ArrayList<String> getFunds()
	{
		ArrayList<String> fundList= new ArrayList<String>();
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				r = fillSTMT.executeQuery("SELECT symbol FROM MUTUALFUND");

				boolean rowsExist = r.next();
				while(rowsExist)
				{
					fundList.add(r.getString("symbol"));
					rowsExist = r.next();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return fundList;
	}

	// --------------------------------------
	// Update the given fund with a new price
	// --------------------------------------

	public static boolean updateFundPrice(String fundName, float price)
	{
		String currDate = DBops.getDate();
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				//System.out.println("Fund Name = "+fundName);
				//System.out.println("Price = "+price);
				fillSTMT.executeUpdate("INSERT INTO CLOSINGPRICE VALUES('"+fundName+"',"+price+", to_date('"+currDate+"', 'DD-MON-YY'))");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
				return false;
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		return true;
	}

	// -----------------------------------------
	// Add a mutual fund to the MutualFund table
	// -----------------------------------------

	public static boolean addFund(MutualFund fund)
	{
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				fillSTMT.executeUpdate("INSERT INTO MUTUALFUND VALUES('"+fund.getSymbol()+"','"+fund.getName()+"','"+fund.getDescription()+"','"+fund.getCategory()+"', to_date('"+fund.getDate()+"', 'DD-MON-YY'))");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
				return false;
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		return true;
	}

	// --------------------------------
	// Run the category stats functions
	// --------------------------------

	public static boolean runCategoryStats(int months, int numObjs)
	{
		String currDate = getDate();
		ArrayList<CategoryEntry> cats = new ArrayList<CategoryEntry>();
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				r = fillSTMT.executeQuery("SELECT symbol, sum(num_shares) as volume from("+
										          "SELECT symbol, num_shares from TRANSACTION where action='buy' and MONTHS_BETWEEN(to_date('"+currDate+"', 'DD-MON-YY'), t_date) <= "+months+") "+
										  "GROUP BY symbol order by volume DESC");
				boolean rowsExist = r.next();
				while(rowsExist)
				{
					cats.add(new CategoryEntry(r.getString("symbol"), r.getInt("volume")));
					rowsExist = r.next();
				}
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
				return false;
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		System.out.println("\nRank:        Symbol             Volume");
		System.out.println("-----        ------             ------");
		for(int i = 0; i < ((cats.size() <= numObjs) ? cats.size() : numObjs); i++)
		{
			CategoryEntry temp = cats.get(i);
			System.out.println((i+1)+"              "+temp.getCategory()+"              "+temp.getNumShares()+" shares");
		}
		System.out.println("\n\n");
		return true;
	}

	// ------------------------------
	// Run the investor stats function
	// ------------------------------

	public static boolean runInvestorStats(int months, int numObjs)
	{
		String currDate = getDate();
		ArrayList<InvestorEntry> investors = new ArrayList<InvestorEntry>();
		try
		{
			Statement fillSTMT = c.createStatement();

			try
			{
				r = fillSTMT.executeQuery("SELECT customer_login, sum(amount) as invested from("+
												  "SELECT customer_login, amount from TRANSACTION where action='buy' and MONTHS_BETWEEN(to_date('"+currDate+"', 'DD-MON-YY'), t_date) <= "+months+") "+
										  "GROUP BY customer_login order by invested DESC");
				boolean rowsExist = r.next();
				while(rowsExist)
				{
					investors.add(new InvestorEntry(r.getString("customer_login"), r.getFloat("invested")));
					rowsExist = r.next();
				}
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
				return false;
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		System.out.println("\nRank:          Customer          Invested");
		System.out.println("-----          -------           --------");
		for(int i = 0; i < ((investors.size() <= numObjs) ? investors.size() : numObjs); i++)
		{
			InvestorEntry temp = investors.get(i);
			System.out.println((i+1)+"              "+temp.getInvestor()+"              $"+temp.getInvested());
		}
		System.out.println("\n\n");
		return true;
	}




	//---------------------------------------------------------------------------------------------------------------------------
	// For Customer Operations
	//---------------------------------------------------------------------------------------------------------------------------

	// -----------------------
	// Browse the mutual funds
	// -----------------------

	public static ArrayList<MutualFund> browseMutualFunds(String option, String param)
	{
		String query = null;
		ArrayList<MutualFund> mutualFunds = new ArrayList<MutualFund>();

		if( option.equals("category") )
		{
			query = "SELECT MutualFund.symbol, name, description, category, to_char(c_date, 'DD-MON-YY') as c_date FROM MutualFund WHERE category='"+param+"'";
		}
		else if( option.equals("price") )
		{
			System.out.println("$$$$$$$  DATE: " + param);
			query = "SELECT MutualFund.symbol, name, description, category, to_char(c_date, 'DD-MON-YY') as c_date, to_char(p_date, 'DD-MON-YY') as p_date, price FROM MutualFund,ClosingPrice WHERE MutualFund.symbol=ClosingPrice.symbol and p_date=(to_date('"+param+"','dd-mon-yy')-1) order by price desc";
		}
		else if( option.equals("alpha") )
		{
			query = "SELECT MutualFund.symbol, name, description, category, to_char(c_date, 'DD-MON-YY') as c_date FROM MutualFund order by name asc";
		}
		else
		{
			query = "SELECT MutualFund.symbol, name, description, category, to_char(c_date, 'DD-MON-YY') as c_date FROM MutualFund";
		}

		ResultSet r = null;
		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery(query);

			if( option.equals("price") )
			{
				int i = 0;

				while(r.next())
				{
					mutualFunds.add(new MutualFund( r.getString("symbol"), r.getString("name"), r.getString("description"), r.getString("category"), r.getString("p_date"), r.getFloat("price") ) );
					i++;
				}

				if(i == 0) System.out.println("\nSorry, there were no listed prices for the date you provided.");
				else System.out.println("\nStock prices are current as of the close of the previous business day.");
			}
			else
			{
				while(r.next())
				{
					mutualFunds.add(new MutualFund( r.getString("symbol"), r.getString("name"), r.getString("description"), r.getString("category"), r.getString("c_date"),0.0f ) );
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("\nError: Could not complete query: Browse Mutual Funds");
			e.printStackTrace();
			return null;
		}

		return mutualFunds;
	}

	// ---------------------------------------------------------
	// Search through the mutual funds with 1 or 2 keywords
	// ---------------------------------------------------------

	public static ArrayList<MutualFund> searchMutualFunds(String keyword1, String keyword2)
	{
		ArrayList<MutualFund> mutualFunds = new ArrayList<MutualFund>();

		ResultSet r = null;
		try
		{
			Statement s = c.createStatement();
			if( keyword2 == null )
				r = s.executeQuery("SELECT symbol, name, description, category, to_char(c_date, 'DD-MON-YY') as c_date FROM MutualFund WHERE description like '%" + keyword1 +"%'");
			else
				r = s.executeQuery("SELECT symbol, name, description, category, to_char(c_date, 'DD-MON-YY') as c_date FROM MutualFund WHERE description like '%" + keyword1 +"%' AND description like '%" + keyword2 +"%'");
			while(r.next())
			{
				mutualFunds.add(new MutualFund( r.getString("symbol"), r.getString("name"), r.getString("description"), r.getString("category"), r.getString("c_date"),0.0f ) );
			}
		}
		catch(Exception e)
		{
			System.out.println("\nError: Could not complete query: Browse Mutual Funds");
		}

		return mutualFunds;
	}

	// -------------------------------------------------------------------------------------
	// Invest the money deposited into different funds based on their allocation preferences
	// -------------------------------------------------------------------------------------

	public static void invest(int amount,String userID)
	{
		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery("select count(*) as num_entries from allocation where customer_login='"+userID+"'");

			r.next();
			if(r.getInt("num_entries") <= 0 )
			{
				System.out.println("You must first specify your allocation preferences before investing.");
				return;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		try
		{
			Statement s = c.createStatement();
			String currDate = DBops.getDate();
			s.executeUpdate("insert into TRANSACTION (trans_id, customer_login, t_date, action, amount) values (TRANSAC_SEQ.nextval, '" + userID + "', to_date('"+currDate+"', 'DD-MON-YY'), 'deposit', " + amount + ")");
			DBops.repopulateTransaction();
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error in the investment transaction.");
			return;
		}
		System.out.println("$"+amount+" has been invested according to your allocations.");
	}

	// ----------------------------------------------------------------------
	// Repopulate the transaction database from the tempory transaction table
	// ----------------------------------------------------------------------

	private static void repopulateTransaction()
	{
		String currDate = DBops.getDate();
		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * from temp_transaction");
			while(r.next())
			{
				Statement s2 = c.createStatement();
				s2.executeUpdate("insert into transaction values("+r.getInt("trans_id")+", '"+r.getString("customer_login")+"', '"+r.getString("symbol")+"', to_date('"+currDate+"', 'DD-MON-YY'), 'buy',"+r.getInt("num_shares")+", "+r.getFloat("price")+", "+r.getFloat("amount")+")");
			}
			s.executeUpdate("delete from temp_transaction");
	    }
	    catch(Exception e)
	    {
			System.out.println(e);
		}
	}

	// -----------------------------------
	// Get the current balance of the user
	// -----------------------------------

	public static float getBalance(String userID)
	{
		ResultSet r = null;
		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT balance from Customer WHERE customer_login='" + userID + "'");
			r.next();
			return r.getFloat("balance");
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error retrieving the balance.");
			return -1;
		}
	}

	// --------------------------------------------------
	// Sells the fund based on the sybmol and # of shares
	// --------------------------------------------------

	public static void sell(String userID, String symbol, int number)
	{
		String current_date = DBops.getDate();
		float price = getStockValue(symbol);
		float amount = price*number;

		try
		{
			String query = "insert into transaction values(TRANSAC_SEQ.nextval, '"+userID+"', '"+symbol+"', to_date('"+current_date+"','DD-MON-YY'),'sell',"+number+", "+price+", "+amount+")";

			Statement s = c.createStatement();
			s.executeUpdate(query);
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error selling the shares.");
		}
		System.out.println("\nYou have sold " + number + " shares of " + symbol + " for $" + amount );
	}

	// --------------------------------------------------------------------
	// Buy the shares based on the symbol, number of shares, and fund value
	// --------------------------------------------------------------------

	public static void buy(String userID, String symbol, int numShares, float stockValue)
	{
		float amount = numShares * stockValue;
		String current_date = DBops.getDate();

		try
		{
			Statement s = c.createStatement();
			s.executeUpdate("insert into TRANSACTION values (TRANSAC_SEQ.nextval, '" + userID + "', '" + symbol + "', to_date('" + current_date +"','DD-MON-YY'), 'buy', " + numShares + ", " + stockValue + ", "+ amount +")");
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error buying the stock.");
		}
	}

	// ----------------------------
	// Change the users allocations
	// ----------------------------

	public static void changeAllocations(String userID, ArrayList<Allocation> list)
	{
		String current_date = DBops.getDate();

		/*
		// Get the old allocations
		try
		{
			Statement s1 = c.createStatement();
			r = s1.executeQuery("select * from Prefers where allocation_no='"++"'");

			while(r.next())
			{
				//System.out.println("Allocation No: " + r.getInt("allocation_no") + " Symbol: " + r.getString("symbol") + " Percentage: " + r.getFloat("Percentage") );
			}

		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error creating a old allocs");
			return;
		}
		*/

		try
		{
			Statement s = c.createStatement();
			s.executeUpdate(" insert into Allocation values( ALLOC_SEQ.nextval, '" + userID + "', to_date('" + current_date +"','DD-MON-YY') ) ");
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error creating a new Allocation row.");
			return;
		}

		ResultSet r = null;
		int allocation_no = 0;

		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT allocation_no from Allocation WHERE customer_login='" + userID + "' AND p_date=to_date('" + current_date + "', 'DD-MON-YY') ");

			r.next();
			allocation_no = r.getInt("allocation_no");
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error retrieving the allocation number");
			return;
		}

		for(Allocation a: list)
		{
			try
			{
				Statement s = c.createStatement();
				s.executeUpdate("insert into Prefers values( " + allocation_no + ", '" + a.getSymbol() + "', " + a.getPercentage() + " ) ");
			}
			catch(Exception e)
			{
				System.out.println("Sorry, there was an error updating the allocations");
				return;
			}
		}

		// Create the new allocations
		try
		{
			Statement s1 = c.createStatement();
			r = s1.executeQuery("select * from Prefers where allocation_no='"+allocation_no+"'");

			while(r.next())
			{
				System.out.println("Allocation No: " + r.getInt("allocation_no") + " Symbol: " + r.getString("symbol") + " Percentage: " + r.getFloat("Percentage") );
			}

		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error testing new allocs");
			return;
		}
		//

	}

	// ----------------------------------------------------
	// Get the date of the last allocation for a given user
	// ----------------------------------------------------

	public static String getLastAllocDate(String userID)
	{
		String theDate = "";
		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT to_char(p_date, 'DD-MON-YY') as p_date,allocation_no FROM Allocation WHERE customer_login='"+userID+"' order by allocation_no desc");

			int i = 0;
			if(r.next() && i == 0)
			{
				theDate = r.getString("p_date");
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return theDate;
	}

	// ----------------------------------------------------
	// View the portfolio of a given user on a certain date
	// ----------------------------------------------------

	public static void viewPortfolio(String userID, String date)
	{
		// Store the current date so we can restore it for later
		String orig_date = DBops.getDate();
		try
		{
			ArrayList<PortfolioFund> current_funds = new ArrayList<PortfolioFund>();

			ResultSet r = null;

			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * from Owns,ClosingPrice WHERE customer_login='" + userID + "' AND Owns.symbol=ClosingPrice.symbol AND p_date=(to_date('"+orig_date+"','DD-MON-YY')-1)");

			while(r.next())
			{
				current_funds.add(new PortfolioFund(r.getString("symbol"), r.getInt("price"),r.getInt("shares") ));
			}

			int current_value_total = 0;

			for(PortfolioFund p: current_funds)
			{
				current_value_total += p.getValue();
			}

			// Add balance to total value
			s = c.createStatement();
			r = s.executeQuery("SELECT balance from Customer where customer_login='"+userID+"'");

			int balance = 0;
			while(r.next())
			{
				balance += r.getInt("balance");
			}

			current_value_total += balance;

			// Change the date to view old stuff for portfolio
			DBops.updateDate(date);
			ArrayList<PortfolioFund> old_funds = new ArrayList<PortfolioFund>();

			s = c.createStatement();
			r = s.executeQuery("SELECT * from Owns,ClosingPrice WHERE customer_login='" + userID + "' AND Owns.symbol=ClosingPrice.symbol AND p_date=(to_date('"+date+"','DD-MON-YY')-1)");

			while(r.next())
			{
				old_funds.add(new PortfolioFund(r.getString("symbol"), r.getInt("price"),r.getInt("shares") ));
			}

			int buySum = 0;

			s = c.createStatement();
			r = s.executeQuery("select sum(amount)as buySum ,customer_login from Transaction where customer_login='"+userID+"' AND action='buy' group by customer_login");
			r.next();
			buySum = r.getInt("buySum");
			int yield = current_value_total - buySum;

			DBops.updateDate(orig_date);

			for(PortfolioFund p: old_funds)
			{
				p.print();
				System.out.println("Current Stock Closing Price: " +  getStockValue(p.getSymbol()) );
			}

			System.out.println("Total cost to buy the stocks: " + buySum);
			System.out.println("Total current value of portfolio: " + current_value_total);
			System.out.println("Total yield: " + yield );
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error creating the portfolio (current_funds)");
			DBops.updateDate(orig_date);
			return;
		}
	}

	// -------------------------------------
	// Get the value of the fund with symbol
	// -------------------------------------

	public static float getStockValue(String symbol)
	{
		float value = 0f;
		ResultSet r = null;
		String current_date = DBops.getDate();

		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT price from ClosingPrice WHERE symbol='" + symbol + "' AND p_date=(to_date('" + current_date + "', 'DD-MON-YY')-1) ");

			r.next();
			value = r.getFloat("price");
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error retrieving the stock value.");
		}
		return value;
	}

	// ------------------------------------------------
	// Get all the mutual funds owned by a certain user
	// ------------------------------------------------

	public static ArrayList<Share> getShares(String userID)
	{
		ArrayList<Share> shares = new ArrayList<Share>();

		ResultSet r = null;
		try
		{
			Statement s = c.createStatement();
			r = s.executeQuery("SELECT * from Owns WHERE customer_login='" + userID + "'");

			while(r.next())
			{
				shares.add(new Share( r.getString("symbol"), r.getString("shares") ) );
			}
		}
		catch(Exception e)
		{
			System.out.println("Sorry, there was an error retrieving the shares.");
		}

		return shares;
	}
}