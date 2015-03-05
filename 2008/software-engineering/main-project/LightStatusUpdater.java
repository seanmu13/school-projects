// -------------------------------------------------------------------
// The LIGHTS status updater program to randomly select lights to fail
// -------------------------------------------------------------------

import java.sql.*;
import java.math.*;
import java.util.*;

public class LightStatusUpdater
{
	public static void main(String[] args)
	{
		String SQLString;
		ResultSet r = null;
		Connection c = null;
		Statement stmt = null;
		String databaseName = null;
		String connectString = null;
		ArrayList rands = new ArrayList();
		int random = 0, count = 0, sleepTime = 3000;

		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("------------------------------Light Status Updater-----------------------------");
		System.out.println("-------------------------------------------------------------------------------\n");

		// -----------------------------------------------------------
		// Try to connect to local Access database named light_columns
		// -----------------------------------------------------------

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			connectString = "jdbc:odbc:db";
			c = DriverManager.getConnection(connectString);
			stmt = c.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error Connecting, exiting program");
			System.exit(1);
		}

		databaseName = connectString.substring(connectString.lastIndexOf(":") + 1);

		System.out.println("Connected to database: " + databaseName);

		// -----------------
		// Get the row count
		// -----------------

		try
		{
			r = stmt.executeQuery("select count(*) from light_columns");
			r.next();
			count = r.getInt(1) ;
			r.close() ;
		}
		catch(Exception e1)
		{
			System.out.println("Could not get row count, exiting program\n");
			System.exit(1);
		}

		System.out.println("Number of rows in " + databaseName + ": " + count);
		System.out.println("Time to sleep between updates: " + sleepTime/1000 + " seconds\n");

		// -------------------------
		// Resets all statuses to OK
		// -------------------------

		try
		{
			stmt.executeUpdate("update light_columns set STATUS='OK'");
		}
		catch(Exception e4)
		{
			System.out.println("Could not update all STATUSs to 'OK'\n");
		}

		// ---------------------------------------------------------
		// Update a single light column where the SENSOR_ID = random
		// ---------------------------------------------------------

		for(int i = 0; i < 3; i++)
		{
			// -----------------------------------------------------
			// Sleeps in between updates by specified time sleepTime
			// -----------------------------------------------------

			if(i != 0)
			{
				try
				{
					Thread.sleep(sleepTime);
				}
				catch(Exception the){}
			}

			// ---------------------------------------------------
			// Generates random number up to the highest SENSOR_ID
			// ---------------------------------------------------

			random = (int) Math.floor(Math.random() * count + 1);

			// ------------------------------------------------
			// Ensures random number has not been chosen before
			// ------------------------------------------------

			while(true)
			{
				if(rands.contains(random))
				{
					random = (int) Math.floor(Math.random() * count + 1);
				}
				else
				{
					rands.add(random);
					break;
				}
			}

			// ----------------------------------------
			// Update the light column with status FAIL
			// ----------------------------------------

			SQLString = "update light_columns set STATUS='FAIL' where SENSOR_ID = " + random;

			try
			{
				stmt.executeUpdate(SQLString);
				System.out.println("Updating status of SENSOR_ID " + random + " from 'OK' to 'FAIL'\n");
			}
			catch(Exception e4)
			{
				System.out.println("Error updating status of SENSOR_ID " + random + "\n");
			}
		}

		// -------------------------------------
		// Closes the connection to the database
		// -------------------------------------

		try
		{
			c.close();
			System.out.println("Closing connection to database: " + connectString.substring(connectString.lastIndexOf(":") + 1) + "\n");
		}
		catch(Exception e2){}
	}
}