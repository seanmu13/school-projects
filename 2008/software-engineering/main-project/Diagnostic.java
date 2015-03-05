import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Diagnostic extends JFrame implements ActionListener{

    private String userID;
    private DefaultListModel listModel;
    private JInternalFrame body;
    private JPanel problemsQ, ticketQ, reviewQ, selected;
    private JList probList, ticketList, reviewList;
    private JButton assignButton, reviewButton, viewEditTicketButton;
    private JLabel selTicketID, selResponder, selSensorID, selStatus, selComments;
    private JScrollPane problemPane, ticketPane, reviewPane, selectedPane;
    private Connection con;
    public String lastTicket = null;
    public JDesktopPane desktop;
    public JButton selectButton;

    public Diagnostic (String uid, JDesktopPane jdp){
		userID = uid;
		desktop = jdp;

		body = new JInternalFrame("Run Diagnostic", false, true, false, true);
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

		selectButton = new JButton("Run Diagnostic");
		selectButton.addActionListener(this);
		//historyButton = new JButton("Ticket History");
		//historyButton.addActionListener(this);

		listModel = new DefaultListModel();

		String query = "select * from Light_Columns;";

		ResultSet r = queryDB(query, stmt, null);

		try{
			while(r.next()){
				listModel.addElement("SensorID: " + r.getString("Sensor_ID") );
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
		probList.setSelectedIndex(0);
                //body.add(historyButton, BorderLayout.NORTH);
                body.add(probList, BorderLayout.CENTER);
                body.add(selectButton, BorderLayout.SOUTH);
		body.pack();
		body.setSize(300,400);
		body.setLocation(0,0);
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
           if(event.getSource() == selectButton){
                String s = (String) probList.getSelectedValue();
                System.out.println(s);
                String [] S = s.split(" ");
                lastTicket = S[1];

                JOptionPane.showMessageDialog(this,"Sensor Operational","Diagnostic",JOptionPane.INFORMATION_MESSAGE);
                //new TicketGui(new Ticket(Integer.parseInt(lastTicket)), Integer.parseInt(userID), desktop);
            }
	}

}