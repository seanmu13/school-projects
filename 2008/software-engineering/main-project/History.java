import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.TitledBorder;

public class History extends JInternalFrame implements ActionListener, MouseListener{

	private String userID;
    private DefaultListModel listModel;
	private JInternalFrame body;
	private JList probList;
	private JButton selectButton, historyButton;
	private Connection con;
	/***********MODIFIED 12/5**************/
	public String lastTicket = null;
	/***********END MODIFIED**************/
	public JDesktopPane desktop;

	public History (String uid, JDesktopPane jdp){
		userID = uid;
		desktop = jdp;

		body = new JInternalFrame("Ticket History for " + userID, false, true, false, true);
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

		selectButton = new JButton("View Ticket");
		selectButton.addActionListener(this);

		listModel = new DefaultListModel();

		String query0 = "select * from User where USER_ID = " + userID + ";";
		ResultSet r = null;
		try{
			ResultSet r0 = queryDB(query0, stmt, null);
			r0.next();
			String userType = r0.getString("USERTYPE");
			String query = "select * from Tickets where " + userType + " = " + userID + ";";
        	r = queryDB(query, stmt, null);
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}

                try{
			while(r.next()){
				listModel.addElement("TicketID: " + r.getString(1) + " | Light #" + r.getString(4) + " | Status " + r.getString("STATUS"));
			}
                }catch(SQLException e){
                    System.out.println(e);
                }



		try{
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		probList = new JList(listModel);
		/****************MODIFIED 12/5*************/
		probList.addMouseListener(this);
		/***************END MODIFIED***************/
        body.add(probList, BorderLayout.CENTER);
        body.add(selectButton, BorderLayout.SOUTH);
		body.pack();
		body.setSize(300,400);
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

	public void actionPerformed(ActionEvent event)
	{
		/**************MODIFIED 12/5*******************/
		if(event.getSource() == selectButton)
		{
			if(lastTicket == null)
			{
				new JOptionPane().showMessageDialog(this,"Please select a ticket first","No Ticket Selected",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				TicketGui temp = new TicketGui(new Ticket(Integer.parseInt(lastTicket)), Integer.parseInt(userID), desktop);
				temp.ticketBox.setEnabled(false);
				temp.lightBox.setEnabled(false);
				temp.commentArea.setEnabled(false);
				temp.button.setEnabled(false);
			}
		}
	   /**************END MODIFIED*****************/
	}
	/**************MODIFIED 12/5*******************/
	public void mousePressed(MouseEvent e)
	{
		if(e.getSource() == probList)
		{
			String s = (String) probList.getSelectedValue();
			String [] S = s.split(" ");
			lastTicket = S[1];
		}
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	/**************END MODIFIED*****************/

	public static void main(String [] args){
//		History his = new History("3", this);
	}
}

