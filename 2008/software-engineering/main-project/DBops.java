// --------------------------------------------------------
// Performs database operations for user login/registration
// --------------------------------------------------------

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

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
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			c = DriverManager.getConnection("jdbc:odbc:db"); // db is the database name (.mdb)
			stmt = c.createStatement();
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

 	public static void addLight(float lat, float lon){
            connectToDB();
            ResultSet r;

            String count = "select max(Sensor_ID) as c from light_columns";
            int countVal=0;

            try{
                r = stmt.executeQuery(count);

                r.next();
                countVal = r.getInt("c");
            } catch (Exception e){
                System.out.println("Exception in insert light: "+e);
                e.printStackTrace();
            }

            countVal++;

            System.out.println("new ID:"+countVal+" Lat:"+lat+" Long:"+lon);

            String query = "Insert into light_columns values("+countVal+",'OK',"+lat+","+lon+");";

            try{
                stmt.executeUpdate(query);
            } catch (Exception e){
                System.out.println("Exception in insert light: "+e);
                e.printStackTrace();
            }

            close();
        }

	// -----------------------------------------------------------
	// During registration, try to add the entry to the user table
	// -----------------------------------------------------------

	public static boolean addEntry(String usrName, String pWord, String type, float x, float y)
	{
		System.out.println(usrName + " " + pWord + " "+ type + " " + x + " " + y);
		ResultSet rs = null;
		count = 0;

		// ------------------------------------------------------------------------
		// Get the # of rows in the DB in order to specify the next entry's User_ID
		// ------------------------------------------------------------------------

		try
		{
			rs = stmt.executeQuery("SELECT COUNT(*) from User");
			rs.next();
			count = rs.getInt(1) + 1 ;
			rs.close() ;
		}
		catch(SQLException e1)
		{
			System.out.println(e1);
			return false;
		}

		try
		{
			Statement fillSTMT = c.createStatement();

			// --------------------------------------------
			// If a responder is trying to register
			// --------------------------------------------

			if(type.equals("Responder"))
			{
				try
				{
					fillSTMT.executeUpdate("INSERT INTO User (USERNAME, PASSWORD, USERTYPE, LAT, LON) VALUES('"+usrName+"','"+pWord+"','"+type+"',"+x+","+y+")");
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
					return false;
				}
			}

			// --------------------------------------------
			// If a dispatcher is trying to register
			// --------------------------------------------

			else if(type.equals("Dispatcher"))
			{
				try
				{
					fillSTMT.executeUpdate("INSERT INTO User(USERNAME, PASSWORD, USERTYPE, LAT, LON) VALUES('"+usrName+"','"+pWord+"','"+type+"',NULL,NULL)");
				}
				catch(SQLException sqle)
				{
					return false;
				}
			}
			else if(type.equals("Administrator"))
			{
				try
				{
					fillSTMT.executeUpdate("INSERT INTO User(USERNAME, PASSWORD, USERTYPE, LAT, LON) VALUES('"+usrName+"','"+pWord+"','"+type+"',NULL,NULL)");
				}
					catch(SQLException sqle)
					{
						System.out.println(sqle);
						return false;
					}
			}
		}
		catch(SQLException sqle)
		{
			return false;
		}

		return true;
	}

	// --------------------------------------------
	// Confirm the login process by checking the DB
	// --------------------------------------------

	public static String[] checkLogin(String usrName, String pWord)
	{
		ResultSet r = null;

		// --------------------------------------------
		// Check to see if user is in DB
		// --------------------------------------------

		try
		{
			Statement checkSTMT = c.createStatement();
			r = checkSTMT.executeQuery("SELECT User_ID,Username,Password,UserType FROM User WHERE Username = '"+usrName+"'");
		}
		catch(Exception e)
		{
			String[] s = {"false",null,null};
			return s;
		}

		// --------------------------------------------
		// If the user is in the DB check the password
		// --------------------------------------------

		try
		{
			r.next();

			if( pWord.equals(r.getString("Password")) )
			{
				String[] s = {"true",r.getString("UserType"),"" + r.getInt("User_ID")};
				return s;
			}
			else
			{
				String[] s = {"false",null,null};
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