import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LightRegistration extends JInternalFrame implements ActionListener{
    private String userID;
   // private DefaultListModel listModel;
    private JInternalFrame body;
    private JButton submit, clear;
    public JDesktopPane desktop;
    private JLabel latitude, longitude;
    private JTextField lat, lon;

    public LightRegistration(String uid, JDesktopPane jdp){
        super("Light Registration");
        userID = uid;
        desktop = jdp;

        body = new JInternalFrame("Register Light", false, true, false, true);
        body.setFrameIcon(new ImageIcon("icon.gif"));
        desktop.add(body);
        body.moveToFront();

        body.setLayout(new GridLayout(3, 2));
        //diagnostic = new JButton("Light Diagnostics");
        //diagnostic.addActionListener(this);
        //ticketmanage = new JButton("Ticket Management");
       // ticketmanage.addActionListener(this);
        latitude = new JLabel("Latitude:");
        longitude = new JLabel("Longitude:");
        lat = new JTextField();
        lon = new JTextField();
        lat.setText("");
        lon.setText("");
        submit = new JButton("Submit");
        submit.addActionListener(this);
        clear = new JButton("Clear");
        clear.addActionListener(this);
        body.add(latitude);
        body.add(lat);
        body.add(longitude);
        body.add(lon);
        body.add(submit);
        body.add(clear);

        body.pack();
        //body.setLocationRelativeTo(null);
        body.setSize(200,100);
	body.setLocation( desktop.getSize().width/2 - getSize().width/2, desktop.getSize().height/2 - getSize().height/2);
        body.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){

       if(event.getSource() == submit){
            String val1 = lat.getText(), val2 = lon.getText();

            String regex = "-?\\d*[.]\\d*";

            if( val1.isEmpty() || val2.isEmpty() || !val1.matches(regex) || !val2.matches(regex)){
                JOptionPane.showMessageDialog(this,"Incorrect Input","Input Error",JOptionPane.ERROR_MESSAGE);
            } else {
                DBops.addLight(Float.parseFloat(val1), Float.parseFloat(val2));
                lat.setText("");
                lon.setText("");
                JOptionPane.showMessageDialog(this,"Light Added","Light Added",JOptionPane.INFORMATION_MESSAGE);
            }

       }else if(event.getSource() == clear){
            lat.setText("");
            lon.setText("");
       }
    }
}
