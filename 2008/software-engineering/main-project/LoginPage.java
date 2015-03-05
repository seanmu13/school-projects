// --------------------------------------------
// The LIGHTS login page to login to the system
// --------------------------------------------

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.*;

public class LoginPage extends JInternalFrame implements ActionListener, KeyListener
{
	public JButton loginButton;
	public JButton clearButton;
	public JButton regButton;
	//public JButton quitButton;

	public JTextField userField;
	public JPasswordField passField;

	public JPanel buttonPanel;
	public JPanel fieldPanel;
	public JPanel labelPanel;
	public JPanel bigPanel;

	public JLabel userLabel;
	public JLabel passLabel;
	public JLabel welcomeLabel;

	public ResultSet r = null;
	public Connection c = null;
	public Statement stmt = null;

	public Dispatcher d = null;
	public Responder res = null;
	public Admin a = null;

	public JDesktopPane desktop;
	public boolean loginError = false;

	public LoginPage(JDesktopPane des)
	{
    	super("LIGHTS Login Page",true,false,false,false);
    	desktop = des;

		// ---------------------------------------
		// Set to windows look and feel
		// ---------------------------------------

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0,0));

		TitledBorder title;

		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Welcome to the LIGHTS Login");
		title.setTitleJustification(TitledBorder.CENTER);
		panel.setBorder(title);

		Container c = getContentPane();
		c.setLayout(new BorderLayout(0,0));

		// ---------------------------------------
		// Initialization of Buttons, labels, etc.
		// ---------------------------------------

    	loginButton = new JButton("Login");
    	clearButton = new JButton("Clear");
    	regButton = new JButton("Register");

    	userField = new JTextField();
    	passField = new JPasswordField();

    	userLabel = new JLabel("Username");
    	passLabel = new JLabel("Password");
    	welcomeLabel = new JLabel("Welcome to the L.I.G.H.T.S. login page");

		// ---------------------------------------
		// Create Panels for various components
		// ---------------------------------------

    	buttonPanel = new JPanel();
    	fieldPanel = new JPanel(new GridLayout(2,1));
    	labelPanel = new JPanel(new GridLayout(2,1));
    	bigPanel = new JPanel();

		userField.setPreferredSize(new Dimension(130,20));
		passField.setPreferredSize(new Dimension(130,20));

		// ---------------------------------------
		// Add buttons to panel
		// ---------------------------------------

    	buttonPanel.add(loginButton);
    	buttonPanel.add(clearButton);
    	buttonPanel.add(regButton);
    	//buttonPanel.add(quitButton);

		// ---------------------------------------
		// Add text fields to panel
		// ---------------------------------------

    	fieldPanel.add(userField);
    	fieldPanel.add(passField);

		// ---------------------------------------
		// Add labels to panel
		// ---------------------------------------

    	labelPanel.add(userLabel);
    	labelPanel.add(passLabel);

    	bigPanel.add(labelPanel);
    	bigPanel.add(fieldPanel);

		// ---------------------------------------
		// Add actionListener to buttons
		// ---------------------------------------

    	loginButton.addActionListener(this);
    	clearButton.addActionListener(this);
    	regButton.addActionListener(this);

		// ---------------------------------------
		// Add panels to container
		// ---------------------------------------

    	//panel.add(welcomeLabel,BorderLayout.PAGE_START);
    	panel.add(bigPanel,BorderLayout.CENTER);
    	panel.add(buttonPanel,BorderLayout.PAGE_END);

		c.add(panel);

		// ---------------------------------------
		// Set unresizable, center it on the screen
		// ---------------------------------------

		passField.addKeyListener(this);
    	setSize(300,150);
    	pack();
		setLocation( desktop.getSize().width/2 - getSize().width/2, desktop.getSize().height/2 - getSize().height/2);
		setFrameIcon(new ImageIcon("icon.gif"));
    	setVisible(true);
	}

	// ---------------------------------------
	// Attempt to login w/ username and password
	// ---------------------------------------

	public void checkLogin(String userName, String password)
	{
		// ---------------------------------------
		// Try to connect to database
		// ---------------------------------------

		if( ! DBops.connectToDB() )
		{
			JOptionPane.showMessageDialog(this,"Could not connect to database!","Database Error",JOptionPane.ERROR_MESSAGE);
			return;
		}

		// ---------------------------------------------------------
		// Get various strings returned from the checkLogin function
		// ---------------------------------------------------------

		String[] s = DBops.checkLogin(userName,password);
		String success = s[0];
		String userType = s[1];
		String userID = s[2];

		DBops.close();

		// ---------------------------------------
		// If login is a failure, show error
		// ---------------------------------------

		if( success.equals("false") )
		{
			loginError = true;
			JOptionPane.showMessageDialog(this,"Username or password incorrect.\nPlease try again.","Failed Login",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// --------------------------------------------
			// Distinguish between dispatcher and responder
			// --------------------------------------------

			/*LightsDesktop.loggedIn = true;

			if( userType.equals("Dispatcher") )
			{
				d = new Dispatcher(userID, desktop);
			}
			else
			{
				res = new Responder(userID, desktop);
			}*/
			// --------------------------------------------
			// Modified 12/06/07 to check for Administrator
			// --------------------------------------------
			if( userType.equals("Dispatcher") )
			{
				d = new Dispatcher(userID, desktop);
			}
			else if ( userType.equals("Responder"))
			{
				res = new Responder(userID, desktop);
			}else if ( userType.equals("Administrator"))
			{
                 a = new Admin(userID, desktop);
            }
			// --------------------------------------------
			// Modified 12/06/07 to check for Administrator
			// --------------------------------------------

			dispose();
		}

		return;
	}

	// ---------------------------------------
	// ActionListeners for various components
	// ---------------------------------------

	public void actionPerformed(ActionEvent e)
	{
		// --------------------------------------------
		// If login button pressed attempt to login
		// --------------------------------------------

		if(e.getSource() == loginButton)
		{
			checkLogin( userField.getText(), passField.getText() );
		}

		// --------------------------------------------
		// If clear button pressed just clear the fields
		// --------------------------------------------

		else if(e.getSource() == clearButton)
		{
			userField.setText("");
			passField.setText("");
		}

		// -----------------------------------------------------
		// If register button pressed open the registration page
		// -----------------------------------------------------

		else if(e.getSource() == regButton)
		{
			RegPage rp = new RegPage(this);
			desktop.add(rp);
			rp.moveToFront();

            try
            {
            	rp.setSelected(true);
			}
			catch(Exception e1)
			{
				System.out.println(e1);
			}
		}

		// --------------------------------------------
		// If quit button pressed exit the program
		// --------------------------------------------

		/*
		else if(e.getSource() == quitButton)
		{
			System.exit(0);
		}
		*/
	}

	// -----------------------------------------------
	// If enter key is pressed while in password, login
	// -----------------------------------------------

	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(!loginError)
			{
				loginButton.doClick();
			}
			else
			{
				loginError = false;
				this.updateUI();
			}
		}
	}

	public void keyPressed(KeyEvent e){}

	public void keyTyped(KeyEvent e){}

	// ------------------------------------------------------
	// Simply call the constructor to start the login process
	// ------------------------------------------------------

	public static void main(String[] args)
	{
		//new LoginPage();
	}
}