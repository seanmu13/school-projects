import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class User{

	public int ID;
	public String name;

	public User(){}

	public User(int UserID)
	{
		Connection con  = null;
		ResultSet r = null;
		try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				String connectString = "jdbc:odbc:db";
				con = DriverManager.getConnection(connectString);
			}

			catch(Exception e) {
				System.out.println("Error Connecting");
				System.out.println(e);
				System.exit(1);
			}

			try {
				System.out.println("starting query...");
				Statement stmt = con.createStatement();
				r = stmt.executeQuery("SELECT Username FROM User WHERE User_ID = "+ UserID);
				System.out.println("Successful Query");
			}
			catch(SQLException sqle) {
				System.out.println("Unsuccessful Execution");
				System.out.println(sqle);
			}

			ID = UserID;

			try {
				r.next();
				name = r.getString("Username");
			}
			catch(Exception e) {
				System.out.println("error in result set");
				System.out.println(e);
			}

			try {
				con.close();
			}
			catch(SQLException sqle) {
				System.out.println("Error Disconnecting");
				System.out.println(sqle);
			}

	}

	public String toString()
	{
		return name + " : " + ID;
	}

	public static void main(String args[]) {
		User R1 = new User(2);
		System.out.println(R1.ID);
		System.out.println(R1.name);
	}
}