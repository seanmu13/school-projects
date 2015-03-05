import java.sql.*;
import javax.swing.*;
import java.util.*;
import javax.accessibility.AccessibleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.accessibility.AccessibleText;
import javax.swing.*;
import java.lang.*;

public class Assignment extends JInternalFrame implements ActionListener{
    private String lightID, userID, Status, Lat, Lon;
    private JPanel info, list;
    private JLabel idLabel, statusLabel, latLabel, LonLabel;
    private String[] respList = null;
    private JList responderList;
    private String respname, LatResp, LonResp;
    private JComboBox respBox;
    private JButton assignButton;
    private int Resp;
    private JDesktopPane desktop;

    public Assignment(String lid, String uid, JDesktopPane jdp){

        super("Ticket Assignment for Light #" + lid, false, true, false, true);

        lightID = lid;
        userID = uid;
        desktop = jdp;

		desktop.add(this);
		moveToFront();

		try
		{
			setSelected(true);
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}

        // queries database for user type
        Connection con  = null;
        ResultSet r = null;

        try
        {
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
			//selects specific outage from lightID
                Statement stmt = con.createStatement();
                r = stmt.executeQuery("SELECT * FROM Light_Columns WHERE Sensor_ID = "+ lightID);
                r.next();
                Status = r.getString("Status");
                Lat = r.getString("Lat");
                Lon = r.getString("Lon");


        }
        catch(SQLException sqle) {
                System.out.println("Unsuccessful Execution - error in ticket");
                System.out.println(sqle);
        }
		//get closest responder ID
        Resp = this.getClosestResponder(lightID, con);
        try {
				//select closest responder to display for selection
                respList = new String[1];
                Statement stmt3 = con.createStatement();

                ResultSet r2 = stmt3.executeQuery("SELECT * FROM user WHERE USER_ID  = "+Resp);
                int i = 0;
                while(r2.next()){
                    respname = r2.getString("USERNAME");

                    LatResp = r2.getString("LAT");
                    LonResp = r2.getString("LON");
                    respList[i] = respname + " : " + LatResp + "," + LonResp;
                    i++;
                }
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle);
        }

        JLabel idLabel = new JLabel("Light #" + lightID);
        idLabel.setSize(300, 50);
        JLabel statusLabel = new JLabel("Status: " + Status);
        statusLabel.setSize(300, 50);
        JLabel latLabel = new JLabel("Latitude: " + Lat);
        latLabel.setSize(300, 50);
        JLabel lonLabel = new JLabel("Longitude: " + Lon);
        lonLabel.setSize(300, 50);

        info = new JPanel(new GridLayout(4,1));

        respBox = new JComboBox(respList);
        list = new JPanel(new BorderLayout());
        list.add(respBox, BorderLayout.NORTH);
        assignButton = new JButton("Assign Outage");
        assignButton.addActionListener(this);
        list.add(assignButton, BorderLayout.CENTER);
        info.add(idLabel);
        info.add(statusLabel);
        info.add(latLabel);
        info.add(lonLabel);
        this.setLayout(new BorderLayout());
        this.add(info, BorderLayout.WEST);
        this.add(list, BorderLayout.EAST);
        this.pack();
		this.setFrameIcon(new ImageIcon("icon.gif"));
        //this.setLocationRelativeTo(null);
        this.setVisible(true);

    }


	//method to find the closest responder to the light outage
    public int getClosestResponder(String light_id, Connection con)
    {
		Integer temp = new Integer(light_id);
		int temp2 = temp.intValue();
		Light outageLight = new Light(temp2);
		double lightX = outageLight.location.longitude;
		double lightY = outageLight.location.latitude;

		int numResp = 0;
		ResultSet res1 = null;
		ResultSet res2 = null;
		Statement stmt = null;
		try
		{
			stmt = con.createStatement();
			//select all possible responders
        	String query1 = "SELECT * FROM user WHERE USERTYPE = 'Responder'";
        	res2 = stmt.executeQuery(query1);
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}

		double respX = 0;
		double respY = 0;
		double respDistance;
		int resp_ID = 0;
		ResultSet res3 = null;
		int numTix;
		String resp_IDString = null;
		ClosestResponder closestResp = null;
		try{
			Statement stmt2 = con.createStatement();
			while(res2.next())
			{
				try{
					resp_ID = res2.getInt("USER_ID");
					res3 = stmt2.executeQuery("SELECT count(*) FROM Tickets WHERE RESPONDER = " + resp_ID);
					res3.next();
					numTix = res3.getInt(1);

					//check if current resp. is still avail to assign (<5 open tickets)
					if(numTix >= 5)
					{
						continue;
					}
					respX = lightX - res2.getDouble("LON");

					respY = lightY - res2.getDouble("LAT");
				}
				catch(SQLException e)
				{
					System.out.println(e);
				}
					//calculate the distance from the light
					respDistance = (respX*respX) + (respY*respY);

					//if no closest responder exists as of this time, create a new one for this resp
					if(closestResp == null)
					{
						closestResp = new ClosestResponder(resp_ID,respDistance);
					}
					//if one does exist, compare this resp with current closest resp.  If closer, replace
					else
					{
						if(respDistance < closestResp.distanceToLight)
						{
						closestResp.id = resp_ID;
						closestResp.distanceToLight = respDistance;
					}
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}

		return closestResp.id;
	}

    public static void main(String [] args){
        //Assignment ass = new Assignment("3", "1");
    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == assignButton)
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

        	try
        	{
				Statement stmt = con.createStatement();
				Integer dispID = new Integer(userID);
				int dispIDInt = dispID.intValue();
				Integer sensorID = new Integer(lightID);
				int lightIDInt = sensorID.intValue();

				stmt.executeUpdate("insert into Tickets (DISPATCHER, RESPONDER, SENSOR_ID, STATUS, COMMENTS) values ("+dispIDInt+", "+Resp+", "+lightIDInt+", 'ASSIGNED', NULL)");
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
            this.dispose();
        }
        else
        {
        }
    }

	//class to hold info on "current" closest responder.
    class ClosestResponder
    {
		int id;
		double distanceToLight;

		public ClosestResponder(int userId, double dist)
		{
			id = userId;
			distanceToLight = dist;
		}
	}
}
