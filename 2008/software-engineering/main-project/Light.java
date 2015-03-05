import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Light{

	public int ID;
	public String status;
	public locationPair location;

	public Light(){}

	public Light(int LightID, Connection con)
	{
		ResultSet r = null;
		String k = new String("OK");
		try {
				Statement stmt = con.createStatement();
				r = stmt.executeQuery("SELECT STATUS,LAT,LON FROM LIGHT_COLUMNS WHERE SENSOR_ID = "+LightID);

			}
			catch(SQLException sqle) {
			//System.out.println("Unsuccessful Execution");
			//System.out.println(sqle);
			}
		ID = LightID;
		try {
			r.next();
			status = new String(r.getString("STATUS"));
			location = new locationPair(r.getDouble("lat"),r.getDouble("lon"));
		}
		catch(Exception e) {
		}
	}

	public Light(int LightID)
	{
		Connection con  = null;
		ResultSet r = null;
		String k = new String("OK");
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
				Statement stmt = con.createStatement();
				r = stmt.executeQuery("SELECT STATUS,LAT,LON FROM LIGHT_COLUMNS WHERE SENSOR_ID = "+LightID);


			}
			catch(SQLException sqle) {
			//System.out.println("Unsuccessful Execution");
			//System.out.println(sqle);
			}

			ID = LightID;

			try {
				r.next();
				status = new String(r.getString("STATUS"));
				location = new locationPair(r.getDouble("lat"),r.getDouble("lon"));
			}
			catch(Exception e) {
			}


	}

	public static void main(String args[]) {
		Light L1 = new Light(1);
		System.out.println(L1.ID);
		System.out.println(L1.status);
		System.out.println((L1.location).latitude);
		System.out.println((L1.location).longitude);
	}

	public class locationPair
	{
		public double latitude;
		public double longitude;

		public locationPair(double y, double x)
		{
			latitude = y;
			longitude = x;
		}

		public String toString()
		{
			return latitude + "," + longitude;
		}
	}

}
