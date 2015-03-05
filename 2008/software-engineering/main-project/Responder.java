import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.TitledBorder;

public class Responder extends JInternalFrame implements ActionListener{

	private String userID;
    private DefaultListModel listModel;
	private JInternalFrame body;
	private JList probList;
	private JButton selectButton, historyButton;
	private Connection con;
	private String lastTicket;
	public JDesktopPane desktop;
	public JMenuBar menuBar = new JMenuBar();

	public Responder (String uid, JDesktopPane jdp){
		userID = uid;
		desktop = jdp;

		body = new JInternalFrame("Responder: " + userID, false, false, false, true);
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

		selectButton = new JButton("View Ticket");
		selectButton.addActionListener(this);
		historyButton = new JButton("Ticket History");
		historyButton.addActionListener(this);

		listModel = new DefaultListModel();
		/*********MODIFIED 12/5*****************/
		String query = "select * from Tickets where Responder =" + userID + " AND STATUS IN ('ASSIGNED','IN_PROGRESS');";
		/*********END MODIFIED******************/
		ResultSet r = queryDB(query, stmt, null);

		try{
			while(r.next()){
				listModel.addElement("TicketID: " + r.getString("Ticket_ID") + " | Light #" + r.getString("Sensor_ID") + " | Status " + r.getString("STATUS"));
			}
		}catch(SQLException e){
			System.out.println(e);
		}

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

		try{
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		probList = new JList(listModel);
		probList.setSelectedIndex(0);
        body.add(historyButton, BorderLayout.NORTH);
        body.add(probList, BorderLayout.CENTER);
        body.add(selectButton, BorderLayout.SOUTH);

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
        try{body.setSelected(true);body.toFront();this.setSelected(true);}catch(Exception e){System.out.println(e.getMessage());}

		body.pack();
		body.setSize(300,400);
		body.setLocation( desktop.getSize().width/2 - body.getSize().width/2, desktop.getSize().height/2 - body.getSize().height/2);
		body.setVisible(true);

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
			Responder respNew = new Responder(userID, desktop);
			body.dispose();

		}
		else if(event.getSource() == historyButton){
			History his = new History(userID, desktop);
		}
		else if(event.getSource() == selectButton){
			String s = (String) probList.getSelectedValue();
			System.out.println(s);
			String [] S = s.split(" ");
			lastTicket = S[1];

			new TicketGui(new Ticket(Integer.parseInt(lastTicket)), Integer.parseInt(userID), desktop);
		}
	}

	public static void main(String [] args){
		//Responder res = new Responder("3");
	}
}
