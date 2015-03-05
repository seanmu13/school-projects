import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.TitledBorder;

public class Dispatcher extends JInternalFrame implements ActionListener, MouseListener{

	private String userID;
    private DefaultListModel listModel;
	private JInternalFrame body;
	private JPanel problemsQ, ticketQ, reviewQ, selected, reviewButtonPan;
	private JList probList, ticketList, reviewList;
	private JButton assignButton, reviewButton, viewEditTicketButton, getAllButton;
    private JLabel selTicketID, selResponder, selSensorID, selStatus, selComments;
    private JScrollPane problemPane, ticketPane, reviewPane, selectedPane;
	private Connection con;
	public String lastTicket = null;
	public JDesktopPane desktop;
	public JMenuBar menuBar = new JMenuBar();

	public Dispatcher (String uid, JDesktopPane jdp){
		userID = uid;
		desktop = jdp;

		body = new JInternalFrame("Dispatcher: " + userID, false, false, false, true);
		body.setFrameIcon(new ImageIcon("icon.gif"));
		desktop.add(body);
		body.moveToFront();

		try
		{
			body.setSelected(true);
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}

		problemsQ = new JPanel();
		ticketQ = new JPanel();
		reviewQ = new JPanel();
		selected = new JPanel();

		assignButton = new JButton("Assign Outage");
		reviewButton = new JButton("Review Ticket");
		getAllButton = new JButton("View Ticket History");
		getAllButton.addActionListener(this);
		viewEditTicketButton = new JButton("View/Edit Ticket");

		TitledBorder title;

		body.setLayout(new GridLayout(2,2));

		problemsQ.setLayout(new BorderLayout());
                problemsQ.setBackground(Color.PINK);
                title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Outages");
                title.setTitleJustification(TitledBorder.CENTER);
                problemsQ.setBorder(title);

		ticketQ.setLayout(new BorderLayout());
                ticketQ.setBackground(new Color(238, 238, 0));
                title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Ticket Queue");
                title.setTitleJustification(TitledBorder.CENTER);
                ticketQ.setBorder(title);

		reviewQ.setLayout(new BorderLayout());
                reviewQ.setBackground(new Color(102, 205, 0));
                title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Tickets for Review");
                title.setTitleJustification(TitledBorder.CENTER);
                reviewQ.setBorder(title);

		selected.setLayout(new GridLayout(6,1));
                selected.setBackground(Color.LIGHT_GRAY);
                title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Selected Ticket");
                title.setTitleJustification(TitledBorder.CENTER);
                selected.setBorder(title);
		/*
		body.addWindowListener(new WindowAdapter()
		{
			public void windowClosing( WindowEvent evt )
			{
				body.dispose();
			}
		}
		);
		*/

		Statement stmt = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			String connectString = "jdbc:odbc:db";

			con = DriverManager.getConnection(connectString);

			stmt = con.createStatement();
		} catch(Exception e) {
			System.out.println("Error Connecting");
			System.out.println(e);
			System.exit(1);
		}

		String query1 = "select Light_Columns.Sensor_ID from Light_Columns " +
						"where Status = 'FAIL' AND Light_Columns.Sensor_ID NOT IN(select sensor_id from tickets);";
		/*********************MODIFIED 12/5*****************/
		//String query2 = "select * from Tickets where Dispatcher =" + userID + " AND STATUS = 'ASSIGNED';";
		String query2 = "select * from Tickets where Dispatcher =" + userID + " AND STATUS IN ('ASSIGNED','IN_PROGRESS');";
		/*********************END MODIFIED******************/
		String query3 = "select * from Tickets where Dispatcher =" + userID + " AND STATUS = 'UNDER_REVIEW';";

		String query4 = "select * from Tickets where Ticket_ID = 2;";

		ResultSet r = null;
		int size1, size2, size3, size4;

		try{

			r = queryDB(query1, stmt, r);
			//generate first quadrant

			listModel = new DefaultListModel();

			while(r.next()){
				listModel.addElement("Light #" + r.getString("Sensor_ID") + " | Status FAIL");
			}

			probList = new JList(listModel);
			probList.setSelectedIndex(0);
			problemPane = new JScrollPane(probList);

			problemsQ.add(problemPane, BorderLayout.CENTER);
			problemsQ.add(assignButton, BorderLayout.SOUTH);

/////////////////////////////////////////////////////////////////////////////

			r = queryDB(query2, stmt, r);
			//generate second quadrant

			listModel = new DefaultListModel();

			while(r.next()){
				listModel.addElement("Ticket ID " + r.getString("Ticket_ID") + " | Light #" + r.getString("Sensor_ID") + " | Status " + r.getString("STATUS"));
			}

			ticketList = new JList(listModel);
			ticketList.addMouseListener(this);

			ticketPane = new JScrollPane(ticketList);

			ticketQ.add(ticketPane, BorderLayout.CENTER);

/////////////////////////////////////////////////////////////////////////////
			/*************MODIFIED 12/5********************/
			r = queryDB(query3, stmt, r);
			//generate third quadrant

			listModel = new DefaultListModel();

			while(r.next()){
				listModel.addElement("Ticket ID " + r.getString("Ticket_ID") + " | Light #" + r.getString("Sensor_ID") + " | Status " + r.getString("STATUS"));
			}

			reviewList = new JList(listModel);
			reviewList.addMouseListener(this);
			reviewPane = new JScrollPane(reviewList);
			/*************END MODIFIED********************/

			reviewButtonPan = new JPanel(new GridLayout(1,2));
			reviewButtonPan.add(reviewButton);
			reviewButtonPan.add(getAllButton);

			reviewQ.add(reviewPane, BorderLayout.CENTER);
			reviewQ.add(reviewButtonPan, BorderLayout.SOUTH);

////////////////////////////////////////////////////////////////////////////

			// Generate 4th quadrant
			selTicketID = new JLabel("");
			selSensorID = new JLabel("");
			selStatus = new JLabel("");
			selComments = new JLabel("");
			selResponder = new JLabel("");

			/*
			String default_ticket = "select * from tickets where ticket_id = (select min(ticket_id) from tickets);";
			//generate fouth quadrant

			r = queryDB(default_ticket, stmt, r);

			r.next();
			selTicketID = new JLabel("Ticket ID: "+r.getString("Ticket_ID"));
			String responder_id = r.getString("Responder");
			selSensorID = new JLabel("Sensor ID: "+r.getString("Sensor_ID"));
			selStatus = new JLabel("Light Status: "+r.getString("Status"));
			selComments = new JLabel("Comments: " +r.getString("Comments"));

			String getResponder = "select Username from User where User_ID = " + responder_id + ";";
			r = queryDB(getResponder, stmt, r);

			r.next();
			selResponder = new JLabel("Responder Name: "+r.getString("Username"));
			*/

			selected.add(selTicketID);
			selected.add(selResponder);
			selected.add(selSensorID);
			selected.add(selStatus);
			selected.add(selComments);
			selected.add(viewEditTicketButton);

		} catch (SQLException e){
			System.out.println(e);
		}

		try{
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		assignButton.addActionListener(this);
		reviewButton.addActionListener(this);
		viewEditTicketButton.addActionListener(this);

		selectedPane = new JScrollPane(selected);

		body.add(problemsQ);
		body.add(ticketQ);
		body.add(reviewQ);
		body.add(selected);
		//added
  		// ----------------------------
		// Set up the menu item.
        // ----------------------------

		JMenu menu = new JMenu("Options");
        JMenuItem menuItem = new JMenuItem("Refresh");
		menuItem.setMnemonic(KeyEvent.VK_R);
		menu.setMnemonic(KeyEvent.VK_P);
		//menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuItem.setActionCommand("Refresh");
		menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);
        body.setJMenuBar(menuBar);

		body.pack();
		//body.setLocationRelativeTo(null);
		body.setSize(600,400);
		body.setLocation( desktop.getSize().width/2 - body.getSize().width/2, desktop.getSize().height/2 - body.getSize().height/2);
		body.setVisible(true);
		try{body.setSelected(true);body.toFront();this.setSelected(true);}catch(Exception e){System.out.println(e.getMessage());}
	}

	public static ResultSet queryDB(String query, Statement stmt, ResultSet r){
		int size = -1;
		try{
			r = stmt.executeQuery(query);
		} catch (Exception e) {
			System.out.println("queryDB: "+e);
		}

		return r;
	}

	public void actionPerformed(ActionEvent event){
		if("Refresh".equals(event.getActionCommand()))
		{
			Dispatcher dispNew = new Dispatcher(userID, desktop);
			body.dispose();

		}
		else if(event.getSource() == assignButton){
			String s = (String) probList.getSelectedValue();
			String[] st = s.split(" ");
			String lightID = st[1].substring(1);
			new Assignment(lightID,userID,desktop);
		}
		/*************MODIFIED 12/5**********************/
		else if(event.getSource() == reviewButton){
			if(lastTicket == null)
			{
				new JOptionPane().showMessageDialog(this,"Please select a ticket first","No Ticket Selected",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				new TicketGui(new Ticket(Integer.parseInt(lastTicket)), Integer.parseInt(userID), desktop);
			}
		}
		/*******************END MODIFIED***************/
		else if(event.getSource() == viewEditTicketButton){

			if(lastTicket == null)
			{
				new JOptionPane().showMessageDialog(this,"Please select a ticket first","No Ticket Selected",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				TicketGui temp = new TicketGui(new Ticket(Integer.parseInt(lastTicket)), Integer.parseInt(userID), desktop);
				temp.ticketBox.setEnabled(false);
			}
		}
		else if(event.getSource() == getAllButton){

			History his = new History(userID, desktop);

		}
	}

	public void mousePressed(MouseEvent e)
	{
	   if(e.getSource() == ticketList)
	   {
			String s = (String) ticketList.getSelectedValue();
			String [] S = s.split(" ");
			lastTicket = S[2];

			System.out.println("Ticket ID: " + S[2]);

			Statement stmt = null;

			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				String connectString = "jdbc:odbc:db";

				con = DriverManager.getConnection(connectString);

				stmt = con.createStatement();
			} catch(Exception e2) {
				System.out.println("Error Connecting");
				System.out.println(e2);
				System.exit(1);
			}

			String default_ticket = "select * from tickets where ticket_id = " + lastTicket + ";";
				//generate fouth quadrant
			try{
				ResultSet r = queryDB(default_ticket, stmt, null);

				r.next();
				selTicketID.setText("Ticket ID: "+r.getString("Ticket_ID"));
				String responder_id = r.getString("Responder");
				selSensorID.setText("Sensor ID: "+r.getString("Sensor_ID"));
				selStatus.setText("Light Status: "+r.getString("STATUS"));
				String comments = r.getString("Comments");

				if( comments == null)
				{
					comments = "No Comment";
				}

				selComments.setText("Comments: "+comments);

				String getResponder = "select Username from User where User_ID = " + responder_id + ";";
				r = queryDB(getResponder, stmt, r);

				r.next();

				selResponder.setText("Responder Name: "+r.getString(1));
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			try{
				con.close();
			} catch (Exception e1) {
				System.out.println(e1);
			}
	   }
	   /**************MODIFIED 12/5*******************/
	   if(e.getSource() == reviewList)
	   {
		   if(!(reviewList.isSelectionEmpty()))
		   {
			   String s = (String) reviewList.getSelectedValue();
		   	   String [] S = s.split(" ");
		   	   lastTicket = S[2];
		   }
		   else
		   {
			   lastTicket = null;
		   }
	   }
	   /**************END MODIFIED*****************/
	}

	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}

	public static void main(String [] args){
		//Dispatcher dis = new Dispatcher("1");
	}
}