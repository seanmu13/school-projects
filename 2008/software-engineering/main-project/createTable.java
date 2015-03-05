import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class createTable{
	public static void main(String[] args){
		Connection con = null;

		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			String connectString = "jdbc:odbc:db";

			con = DriverManager.getConnection(connectString);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		System.out.println("Connected");

		Statement stmt = null;
		try{
			stmt = con.createStatement();

			//stmt.executeUpdate(

			stmt.executeUpdate("drop table User");
			stmt.executeUpdate("drop table Light_Columns");
			stmt.executeUpdate("drop table Tickets");
			/****************MODIFIED 12/5***********************************/
			stmt.executeUpdate("create table User (USER_ID COUNTER, USERNAME VARCHAR(30) NOT NULL, PASSWORD VARCHAR(10) NOT NULL, USERTYPE VARCHAR(15) NOT NULL, LAT FLOAT, LON FLOAT, PRIMARY KEY(USER_ID))");
			stmt.executeUpdate("create table Tickets (TICKET_ID COUNTER, DISPATCHER INT NOT NULL, RESPONDER INT NOT NULL, SENSOR_ID INT NOT NULL, STATUS VARCHAR(15) NOT NULL, COMMENTS VARCHAR(200), PRIMARY KEY(TICKET_ID))");
            stmt.executeUpdate("create table Light_Columns (SENSOR_ID INT, STATUS VARCHAR(15) NOT NULL, LAT FLOAT NOT NULL, LON FLOAT NOT NULL, PRIMARY KEY(SENSOR_ID))");
			stmt.executeUpdate("insert into User (USERNAME, PASSWORD, USERTYPE, LAT, LON) values ('MCP', 'p', 'Dispatcher', NULL, NULL)");
			stmt.executeUpdate("insert into User (USERNAME, PASSWORD, USERTYPE, LAT, LON) values ('test', 'test', 'Dispatcher', NULL, NULL)");
			stmt.executeUpdate("insert into User (USERNAME, PASSWORD, USERTYPE, LAT, LON) values ('IJones', 'holy_Grail', 'Responder', '40.447778', '-79.973056')");
			stmt.executeUpdate("insert into User (USERNAME, PASSWORD, USERTYPE, LAT, LON) values ('NFacts', '2000CPD', 'Responder', '40.861111', '-79.893333')");
			stmt.executeUpdate("insert into User (USERNAME, PASSWORD, USERTYPE, LAT, LON) values ('LPort', 'introniX', 'Responder', '40.416111', '-79.806111')");
			stmt.executeUpdate("insert into User (USERNAME, PASSWORD, USERTYPE, LAT, LON) values ('testAdmin', 'testAdmin', 'Administrator', NULL, NULL)");
			stmt.executeUpdate("insert into Light_Columns values (1, 'FAIL', 40.444444, -79.955000)");
			stmt.executeUpdate("insert into Light_Columns values (2, 'OK', 40.443889, -79.956389)");
			stmt.executeUpdate("insert into Light_Columns values (3, 'OK', 40.442778, -79.958056)");
			stmt.executeUpdate("insert into Light_Columns values (4, 'OK', 40.442778, -79.955833)");
			stmt.executeUpdate("insert into Light_Columns values (5, 'OK', 40.442222, -79.953611)");
			stmt.executeUpdate("insert into Light_Columns values (6, 'OK', 40.440833, -79.957500)");
			stmt.executeUpdate("insert into Light_Columns values (7, 'OK', 40.438333, -79.954444)");
			stmt.executeUpdate("insert into Light_Columns values (8, 'OK', 40.441389, -79.959722)");
			stmt.executeUpdate("insert into Light_Columns values (9, 'FAIL', 40.443889, -79.951386)");
			stmt.executeUpdate("insert into Light_Columns values (10, 'OK', 40.448333, -79.953333)");
			stmt.executeUpdate("insert into Light_Columns values (11, 'OK', 40.861111, -79.893611)");
			stmt.executeUpdate("insert into Light_Columns values (12, 'OK', 40.860000, -79.896667)");
			stmt.executeUpdate("insert into Light_Columns values (13, 'OK', 40.862778, -79.898611)");
			stmt.executeUpdate("insert into Light_Columns values (14, 'OK', 40.425833, -79.789722)");
			stmt.executeUpdate("insert into Light_Columns values (15, 'OK', 40.417778, -79.785556)");
			stmt.executeUpdate("insert into Light_Columns values (16, 'OK', 40.419167, -79.811389)");
			stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (1, 3, 1, 'ASSIGNED', NULL)");
			/*stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (1, 3, 12, 'ASSIGNED', NULL)");
			stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (2, 4, 1, 'ASSIGNED', NULL)");
			stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (1, 5, 11, 'ASSIGNED', NULL)");
			stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (2, 3, 5, 'ASSIGNED', NULL)");
			stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (2, 4, 8, 'ASSIGNED', NULL)");
			stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (1, 3, 4, 'ASSIGNED', NULL)");
			stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values (1, 5, 7, 'ASSIGNED', NULL)");*/
			/*************************END MODIFIED*************************/
		} catch (Exception e) {
			System.out.println(e);
		}

		try{
			con.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		System.out.println("Connection Closed");
	}
}