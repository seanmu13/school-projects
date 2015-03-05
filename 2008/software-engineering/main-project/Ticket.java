import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/***************MODIFIED 12/5***********/
enum Status { ASSIGNED, IN_PROGRESS, UNDER_REVIEW, CLOSED };
/***************END MODIFIED************/
public class Ticket{

	public User responder;
	public User dispatcher;
	public Light light;
	public Status status;
	public String comments = "";
	public int ID;

	public Ticket() {}

	public Ticket(int TicketID) {

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
			Statement stmt = con.createStatement();
			r = stmt.executeQuery("SELECT Ticket_ID,Dispatcher,Responder,Sensor_ID,Status,Comments FROM Tickets WHERE Ticket_ID = "+ TicketID);
			r.next();
			ID = r.getInt("Ticket_ID");
			responder = new User(r.getInt("Responder"));
			light = new Light(r.getInt("Sensor_ID"));
			dispatcher = new User(r.getInt("Dispatcher"));


			String stat = r.getString("Status");
			if(stat.equals("ASSIGNED"))
				status = Status.ASSIGNED;
			else if(stat.equals("IN_PROGRESS"))
				status = Status.IN_PROGRESS;
			else if(stat.equals("UNDER_REVIEW"))
				status = Status.UNDER_REVIEW;
			else if(stat.equals("CLOSED"))
				status = Status.CLOSED;
			System.out.println(status);


			comments = r.getString("Comments");

		}
		catch(SQLException sqle) {
			System.out.println("Unsuccessful Execution - error in ticket");
			System.out.println(sqle);
		}
	}

	public Ticket(int ResponderID, int LightID, int DispatcherID)
	{
		responder = new User(ResponderID);
		dispatcher = new User(DispatcherID);
		light = new Light(LightID);
		this.status = Status.ASSIGNED;

		Connection con  = null;
		ResultSet r = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String connectString = "jdbc:odbc:DB";
			con = DriverManager.getConnection(connectString);
		}
		catch(Exception e) {
			System.out.println("Error Connecting");
			System.out.println(e);
			System.exit(1);
		}

		try {
			Statement cnt = con.createStatement();
			r = cnt.executeQuery("SELECT COUNT(*) FROM Tickets");
			r.next();
			ID = r.getInt(1) + 1;



			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Tickets VALUES("+ID+","+ResponderID+","+DispatcherID+","+LightID+",'"+Ticket.statusToString(status)+"','"+comments+"')");
			System.out.println("INSERT INTO Tickets(TICKET_ID, Responder, Dispatcher, Light, Status, Comments) VALUES("+ID+","+ResponderID+","+DispatcherID+","+LightID+",'"+Ticket.statusToString(status)+"','"+comments+"')");
			System.out.println("Successful insert");
		}
		catch(SQLException sqle) {
			System.out.println("Unsuccessful Execution");
			System.out.println(sqle);
		}


	}

	public static String statusToString(Status status)
	{
		String str = "";
		switch(status){
			case ASSIGNED: str = new String("ASSIGNED"); break;
			case IN_PROGRESS: str = new String("IN_PROGRESS"); break;
			case UNDER_REVIEW: str = new String("UNDER_REVIEW"); break;
			case CLOSED: str = new String("CLOSED"); break;
			}
			return str;
	}

	public static void main(String args[]) {
		Ticket TT = new Ticket(1);
		System.out.println(TT.ID);
		System.out.println(TT.responder);
		System.out.println(TT.dispatcher);
		//System.out.println(TT.ID);
	}


}