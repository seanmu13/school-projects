import java.sql.*;
import java.util.*;
import javax.accessibility.AccessibleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.accessibility.AccessibleText;
import javax.swing.*;
import javax.swing.event.*;

public class TicketGui extends JInternalFrame implements ActionListener
{
	private JPanel topPanel1,topPanel2,topPanel3,topPanel4,topPanel5,topPanel6, mapPanel,
			commentsPannel, submitPanel, mapTopPanel1, mapTopPanel2, commentsPanel, commentsPanelTop1, commentsPanelTop2,
			submitPanelTop1, submitPanelTop2, submitPanelTop2Left, submitPanelTop2Left1, submitPanelTop2Left2, submitPanelTop2Right, secondaryPanel;
	public JComboBox ticketBox, lightBox;
	public JTextArea commentArea, prevCommentsArea;
	private JEditorPane mapPane;
	public JButton button;
	/***********************MODIFIED 12/5**********************/
	private String[] comboList1 = {"ASSIGNED", "IN_PROGRESS", "UNDER_REVIEW", "CLOSED"};
	private String[] comboListResp = {"ASSIGNED", "IN_PROGRESS", "SUBMIT_FOR_REVIEW"};
	private String[] comboList2 = {"NOT WORKING", "WORKING", "COULD NOT FIX"};
	/***********************END MODIFIED**********************/
	private JLabel titleLabel, ticketStatusLabel, lightStatusLabel, colIDLabel,
			respIDLabel, dispIDLabel, locationLabel, ticketStatus, lightStatus,
			LightID, RespID, DispID, outageLoc, mapTitleLabel, mapLink, commentsLabel,
			submitLabel;
	private Ticket ticket;
	private String userType;
	private JDesktopPane desktop;

	public TicketGui(Ticket t, int UserID, JDesktopPane jdp)
	{
		super("Ticket Display", false, true, false, true);

		ticket = t;
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
			r = stmt.executeQuery("SELECT usertype FROM User WHERE User_ID = "+ UserID);
			r.next();
			userType = r.getString("usertype");
		}
		catch(SQLException sqle) {
			System.out.println("Unsuccessful Execution - could not get user ID");
			System.out.println(sqle);
		}
		//System.out.println(userType);



		//begin labels
		//titleLabel = new JLabel("Ticket 654-4564");
		titleLabel = new JLabel("Ticket " + Integer.toString(ticket.ID));
		ticketStatusLabel = new JLabel("Ticket Status:");
		colIDLabel = new JLabel("Light Column ID:");
		lightStatusLabel = new JLabel("Light Status:");
		respIDLabel = new JLabel("Responder ID:");
		dispIDLabel = new JLabel("Dispatcher ID:");
		locationLabel = new JLabel("Outage Location:");
		ticketStatus = new JLabel(Ticket.statusToString(ticket.status));
		lightStatus = new JLabel((ticket.light).status);
		LightID = new JLabel(Integer.toString((ticket.light).ID));
		RespID = new JLabel((ticket.responder).toString());
		DispID = new JLabel((ticket.dispatcher).toString());
		outageLoc = new JLabel(((ticket.light).location).toString());
		//ticketStatus = new JLabel("UNRESOLVED");
		//lightStatus = new JLabel("FAILURE");
		//LightID = new JLabel("1354");
		//RespID = new JLabel("Jones, Indiana : 312564");
		//outageLoc = new JLabel("40.24654,-79.35445");

		topPanel1 = new JPanel();
		topPanel2 = new JPanel();
		topPanel3 = new JPanel();
		/*topPanel4 = new JPanel();
		topPanel5 = new JPanel();
		topPanel6 = new JPanel();
		topPanel7 = new JPanel();
		topPanel8 = new JPanel();
		topPanel9 = new JPanel();
		topPanel10 = new JPanel();*/
		topPanel1.setLayout(new BorderLayout());

		topPanel3.setLayout(new GridLayout(6,2));
		/*topPanel4.setLayout(new GridLayout(1,2));
		topPanel5.setLayout(new GridLayout(1,2));
		topPanel6.setLayout(new GridLayout(1,2));*/
		//end of buttons

		topPanel3.add(ticketStatusLabel);
		topPanel3.add(ticketStatus);
		topPanel3.add(lightStatusLabel);
		topPanel3.add(lightStatus);
		topPanel3.add(colIDLabel);
		topPanel3.add(LightID);
		topPanel3.add(respIDLabel);
		topPanel3.add(RespID);
		topPanel3.add(dispIDLabel);
		topPanel3.add(DispID);
		topPanel3.add(locationLabel);
		topPanel3.add(outageLoc);

		topPanel2.add(titleLabel);

		topPanel1.add(topPanel2, BorderLayout.NORTH);
		topPanel1.add(topPanel3, BorderLayout.CENTER);

		//add webpage!!!

 		//mapPane = new JEditorPane();
     	//mapPane.setEditable(false);
     	//added
     	JEditorPane jep = new JEditorPane("text/html", "<A href=http://maps.google.com/maps?f=q&hl=en&geocode=&time=&date=&ttype=&q="+((ticket.light).location).latitude+",+"+((ticket.light).location).longitude+"&ie=UTF8&ll="+((ticket.light).location).latitude+","+((ticket.light).location).longitude+"&spn=0.003703,0.006706&z=18&iwloc=addr&om=1>Link To Map</A>");
     	jep.setEditable(false);	jep.setOpaque(false);
     	jep.addHyperlinkListener(new HyperlinkListener()
     	{
			public void hyperlinkUpdate(HyperlinkEvent hle)
			{
				if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType()))
				{
					String[] urlExec = {"C:\\Program Files\\Internet Explorer\\iexplore.exe",(hle.getURL()).toString()};
					//String prg = "C:\\ProgramFiles\\Internet Explorer\\iexplore.exe";
					//String ex = "cmd /C start " + hle.getURL();
					String ex = urlExec[0] + " " +urlExec[1];
					try{Process proc = Runtime.getRuntime().exec(ex);}catch(Exception e){ System.out.println(e.getMessage());}
					//System.out.println(hle.getURL());
				}
			}
		});
		//end add
    	try {
      	 	//mapPane.setPage("http://maps.google.com/maps?f=q&hl=en&geocode=&time=&date=&ttype=&q=40.65626,+-79.654165&ie=UTF8&ll=40.656082,-79.653845&spn=0.003703,0.006706&z=18&iwloc=addr&om=1");
      	 	//mapPane.setPage("http://maps.google.com/maps?f=q&hl=en&geocode=&time=&date=&ttype=&q=40.2654,+-79.55164&ie=UTF8&z=14&iwloc=addr&om=1");
      	 	//mapPane.setPage("http://www.mapquest.com/maps/map.adp?searchtype=address&formtype=address&latlongtype=decimal&latitude=40.54544&longitude=-79.64564");
      	 	mapPane.setPage("http://www.multimap.com/maps/?hloc=US|40.2645,%20-79.32454");
     	}
     	catch (Exception e) {
    	   //mapPane.setContentType("text/html");
     	   //mapPane.setText("<html>Could not load http://maps.google.com/maps?f=q&hl=en&geocode=&time=&date=&ttype=&q=40.65626,+-79.654165&ie=UTF8&ll=40.656082,-79.653845&spn=0.003703,0.006706&z=18&iwloc=addr&om=1 </html>");
    	 }
		//end add webpage

		mapTopPanel1 = new JPanel();
		mapTopPanel2 = new JPanel();

		mapTitleLabel = new JLabel("Map");
		mapTopPanel1.add(mapTitleLabel);

		mapLink = new JLabel("<html><A href=http://maps.google.com/maps?f=q&hl=en&geocode=&time=&date=&ttype=&q=40.65626,+-79.654165&ie=UTF8&ll=40.656082,-79.653845&spn=0.003703,0.006706&z=18&iwloc=addr&om=1>Link To Map</A></html>");
		//mapLink.add(Hyp
		mapTopPanel2.add(jep);
		//mapTopPanel2.add(mapLink);

		mapPanel = new JPanel(new BorderLayout());
		mapPanel.add(mapTopPanel1, BorderLayout.NORTH);
		mapPanel.add(mapTopPanel2, BorderLayout.CENTER);



		commentsPanelTop1 = new JPanel(new BorderLayout());
		commentsLabel = new JLabel("Comments");
		commentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		commentsPanelTop1.add(commentsLabel, BorderLayout.NORTH);
		commentsPanelTop2 = new JPanel();
		commentArea = new JTextArea(10,20);
		/********************MODIFIED 12/5*****************/
		if(ticket.comments != null)
		{
			prevCommentsArea = new JTextArea("Previous Comments:\n" + ticket.comments);
			prevCommentsArea.setEnabled(false);
			commentsPanelTop1.add(prevCommentsArea, BorderLayout.CENTER);
			//String prevComments = "Previous Comments:" + ticket.comments + "\n ---------";
			//commentArea.insert(prevComments, 0);
		}
		/*******************END MODIFIED 12/5**************/
		commentsPanelTop2.add(commentArea);
		commentsPanel = new JPanel(new BorderLayout());
		commentsPanel.add(commentsPanelTop1, BorderLayout.NORTH);
		commentsPanel.add(commentsPanelTop2, BorderLayout.CENTER);

		submitPanel = new JPanel(new BorderLayout());
		submitPanelTop1 = new JPanel();
		submitPanelTop2 = new JPanel(new BorderLayout());
		submitPanelTop2Left = new JPanel(new BorderLayout());
		submitPanelTop2Left1 = new JPanel();
		submitPanelTop2Left2 = new JPanel();
		submitPanelTop2Right = new JPanel();

		/**************MODIFIED 12/5***************/
		lightBox = new JComboBox(comboList2);
		lightBox.setSelectedItem((ticket.light).status);
		lightBox.addActionListener(this);
		if(userType.equals("Responder"))
		{
			ticketBox = new JComboBox(comboListResp);
		}
		else if (userType.equals("Dispatcher"))
		{
			ticketBox = new JComboBox(comboList1);
			lightBox.setEnabled(false);
		}
		else if (userType.equals("Administrator")){
		    ticketBox = new JComboBox(comboListResp);
		    ticketBox = new JComboBox(comboList1);
        }
		ticketBox.setSelectedItem(Ticket.statusToString(ticket.status));
		ticketBox.addActionListener(this);
		/*************END MODIFIED*****************/

		submitPanelTop2Left1.add(lightBox);
		submitPanelTop2Left2.add(ticketBox);

		submitLabel = new JLabel("Submit Ticket For Review");
		submitPanelTop1.add(submitLabel);
		submitPanel.add(submitPanelTop1, BorderLayout.NORTH);
		button = new JButton("Submit");
		button.addActionListener(this);
		submitPanelTop2Right.add(button);
		submitPanelTop2.add(submitPanelTop2Right, BorderLayout.EAST);
		submitPanelTop2Left.add(submitPanelTop2Left1, BorderLayout.NORTH);
		submitPanelTop2Left.add(submitPanelTop2Left2, BorderLayout.CENTER);
		submitPanelTop2.add(submitPanelTop2Left, BorderLayout.WEST);
		submitPanel.add(submitPanelTop2, BorderLayout.CENTER);



		//adding components to top frame
		secondaryPanel = new JPanel(new BorderLayout());
		secondaryPanel.add(commentsPanel, BorderLayout.NORTH);
		secondaryPanel.add(submitPanel, BorderLayout.CENTER);
		this.add(topPanel1, BorderLayout.NORTH);
		this.add(mapPanel, BorderLayout.CENTER);
		this.add(secondaryPanel, BorderLayout.SOUTH);
		this.pack();
		this.setFrameIcon(new ImageIcon("icon.gif"));
        this.setVisible(true);
		Container container = getContentPane();

		/*
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing( WindowEvent evt )
			{
				//System.exit(0);
				dispose();
			}
		}
		);
		*/
	}

	public void actionPerformed(ActionEvent event)
	{
		AccessibleContext CommentsContext = commentArea.getAccessibleContext();
		AccessibleText CommentsText = CommentsContext.getAccessibleText();
		/**************************MODIFIED 12/5***************************/
		String Comments = "";
		if(ticket.comments != null)
		{
			Comments = ticket.comments + userType + ": "+ CommentsText.getAtIndex(AccessibleText.SENTENCE,0);
		}
		else
		{
			Comments = userType + ": "+ CommentsText.getAtIndex(AccessibleText.SENTENCE,0);
		}
		/**************************Modified 12/5***************************/
		String ticketStatus = null;
		String lightStatus = null;

		if(event.getSource() == button)
		{
			System.out.println(Comments);
			lightStatus = (String)(lightBox.getSelectedItem());
			ticketStatus = (String)(ticketBox.getSelectedItem());
			//added for comments processing
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
				/*****************************MODIFIED 12/5******************************/
				String SQLString1;
				if(ticketStatus.equals("SUBMIT_FOR_REVIEW"))
				{
				    SQLString1 = "update Tickets set COMMENTS='"+Comments+"', STATUS = 'UNDER_REVIEW' where TICKET_ID = " + ticket.ID;
				}
				else
				{
					SQLString1 = "update Tickets set COMMENTS='"+Comments+"', STATUS = '"+ticketStatus+"' where TICKET_ID = " + ticket.ID;
				}
				String SQLString2 = "update Light_Columns set STATUS = '"+lightStatus+"' where SENSOR_ID = " + ticket.light.ID;
				stmt.executeUpdate(SQLString1);
				stmt.executeUpdate(SQLString2);
			}
			/****************************END MODIFIED************************************/
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			this.dispose();
		}
	}


	public static void main (String arg[])
	{
			//Ticket TT = new Ticket(2,1,1);
			//TicketGui application = new TicketGui(TT, 1);
			//application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}
