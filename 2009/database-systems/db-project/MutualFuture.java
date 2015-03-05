/*
Purpose:  The main entry class for the MutualFuture program.
*/

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import oracle.jdbc.driver.*;
import java.io.*;

public class MutualFuture
{

	private UserType type;
	public User u;
	private int loginCount = 0;
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in) );

	public MutualFuture()
	{
		printWelcomeMessage();
	}

    public static void main(String[] args)
    {
		MutualFuture app = new MutualFuture();
		DBops.connectToDB();
		if(app.loginUser())
		{
			//start the session based upon user type (admin v. customer)
			app.u.start();
		}
		else
		{
			DBops.close();
		}
    }

	/*
	A Method to request user login credentials.
	*/
    public boolean loginUser()
    {
		System.out.println("This program is for authorized users only!!!\n");
		System.out.println("Please enter your username and password:");
		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\nUSERNAME:  ");
		String uName = "";
		try
		{
		    uName = rdr.readLine();
	    }
	    catch(Exception e)
	    {
		}
		String passWord = PasswordField.readPassword("PASSWORD:\040\040");
	    return processLogin(uName, passWord);

	}

	/*
	Method to process login credentials.  A call into DBops performs the
	actual login authentication process.
	*/
	public boolean processLogin(String userName, String passWord)
	{
		//DBops.connectToDB();
		String[] results = DBops.checkLogin(userName, passWord);
		if(results[0] == "true")
		{
			//successull login!
			if(results[1].equals("Customer"))
			{
				this.type = UserType.Customer;
				this.u = new Customer(userName, results[2]);
			}
			else
			{
				this.type = UserType.Admin;
				this.u = new Admin(userName, results[2]);
			}
			loginCount = 0;
			return true;
		}
		else
		{
			System.out.println("\nIncorrect login...please try again!");
			if(loginCount == 4)
			{
				loginCount = 0;
				System.out.println("Too many failed attempts!  Please try again later!");
				return false;
			}
			else
			{
				loginCount++;
				return loginUser();
			}
		}
	}

	/*
	Outputs MutualFuture welcome art.
	*/
    public static void printWelcomeMessage()
    {
		System.out.println();
		System.out.println("                           $$$$$$$$\n"+
			               "                           $$$$$$$$\n"+
			               "                           $$$$$$$$\n"+
			               "                         $$$$$$$$$$$$\n"+
			               "                      $$$$$$$$$$$$$$$$$$$$$$$\n"+
		                   "                  $$$$$    $$$$$$$$\n"+
		                   "               $$$         $$$$$$$$\n"+
		                   "            $$$            $$$$$$$$\n"+
		                   "          $$$              $$$$$$$$\n"+
        	               //"        $$$                $$$$$$$$\n"+
			               //"      $$$                  $$$$$$$$\n"+
			               //"      $$$                  $$$$$$$$\n"+
			               //"      $$$                  $$$$$$$$\n"+
			               //"      $$$                  $$$$$$$$\n"+
			               //"      $$$                  $$$$$$$$\n"+
			               "        $$$                $$$$$$$$\n"+
			               "         $$$               $$$$$$$$\n"+
			               "           $$$             $$$$$$$$\n"+
			               "             $$$           $$$$$$$$\n"+
			               "               $$$         $$$$$$$$\n"+
			               "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
			               "$      M       M U     U TTTTTTT U     U     A     L          $\n"+
   						   "$      MM     MM U     U    T    U     U    A A    L          $\n"+
   						   "$      M M   M M U     U    T    U     U   A   A   L          $\n"+
   						   "$      M  M M  M U     U    T    U     U  AAAAAAA  L          $\n"+
   						   "$      M   M   M  UUUUU     T     UUUUU  A       A LLLLLL     $\n"+
   						   "$                                                             $\n"+
                           "$     FFFFFF   U     U   TTTTTTT   U     U   RRRRR     EEEEEEE$\n"+
                           "$    F        U     U      T      U     U   R    R    E       $\n"+
                           "$   FFFFFF   U     U      T      U     U   RRRRR     EEEEEEE  $\n"+
                           "$  F        U     U      T      U     U   R    R    E         $\n"+
                           "$ F         UUUUU       T       UUUUU    R     R   EEEEEEE    $\n"+
                           "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
                           "                           $$$$$$$$         $$$\n"+
                           "                           $$$$$$$$           $$$\n"+
                           "                           $$$$$$$$             $$$\n"+
                           "                           $$$$$$$$               $$$\n"+
                           "                           $$$$$$$$                $$$\n"+
                           //"                           $$$$$$$$                  $$$\n"+
                           //"                           $$$$$$$$                  $$$\n"+
                           //"                           $$$$$$$$                  $$$\n"+
                           //"                           $$$$$$$$                  $$$\n"+
                           //"                           $$$$$$$$                  $$$\n"+
                           //"                           $$$$$$$$                $$$\n"+
                           "                           $$$$$$$$              $$$\n"+
                           "                           $$$$$$$$            $$$\n"+
                           "                           $$$$$$$$         $$$\n"+
                           "                           $$$$$$$$     $$$$\n"+
                           "               $$$$$$$$$$$$$$$$$$$$$$$$$$\n"+
                           "                     $$$$$$$$$$$$$$$$$\n"+
                           "                           $$$$$$$$\n"+
                           "                           $$$$$$$$\n"+
                           "                           $$$$$$$$\n");
        System.out.print("\nProgress....|");
        int count = 1;
        for(int i = 1; i < 110000000; i++)
        {
			if(i%10000000 == 0)
			{
				if(count == 1)
				{
					System.out.print("|||||"+10*count+"%");
				}
				else
				{
					System.out.print("\010\010\010|||||"+10*count+"%");
				}
				count++;
		    }
	    }
	    System.out.print(" DONE...\n\n");
    }
}
