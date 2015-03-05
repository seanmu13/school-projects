import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.TitledBorder;

public class Admin extends JFrame implements ActionListener{
    private String userID;
    //private DefaultListModel listModel;
    private JInternalFrame body;
    private JButton diagnostic, ticketmanage, lightReg;
    public JDesktopPane desktop;

    public Admin(String uid, JDesktopPane jdp) {
        super("Administrator Options");
        userID = uid;
        desktop = jdp;

        body = new JInternalFrame("Administrator: " + userID, false, true, false, true);
        body.setFrameIcon(new ImageIcon("icon.gif"));
        desktop.add(body);
        body.moveToFront();

        body.setLayout(new GridLayout(3, 1));
        diagnostic = new JButton("Light Diagnostics");
        diagnostic.addActionListener(this);
        ticketmanage = new JButton("Ticket Management");
        ticketmanage.addActionListener(this);
        lightReg = new JButton("Light Registration");
		lightReg.addActionListener(this);
		body.add(diagnostic);
		body.add(ticketmanage);
        body.add(lightReg);

        body.setSize(300,100);
		body.pack();
		body.setLocation( desktop.getSize().width/2 - body.getSize().width/2, desktop.getSize().height/2 - body.getSize().height/2);
        body.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
       if(event.getSource() == diagnostic){
            new Diagnostic(userID, desktop);
       }else if(event.getSource() == ticketmanage){
            new TicketManagement(userID, desktop);
       }
	   else if(event.getSource() == lightReg){
            new LightRegistration(userID, desktop);
       }


    }

}
