// ---------------------------------------
// LIGHTS registration page for new users
// ---------------------------------------

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class RegPage extends JInternalFrame implements ActionListener
{
	public JTextField rUserField;
	public JPasswordField rPassField;
	public JTextField rPassField2;
	public JTextField rLatField;
	public JTextField rLongField;

	public JRadioButton rDispRadioButton;
	public JRadioButton rRespRadioButton;
	public JRadioButton rAdminRadioButton;
	public ButtonGroup group;

	public JButton rRegisterButton;
	public JButton rClearButton;
	public LoginPage lp;

	public RegPage(LoginPage lgp)
	{
		// ---------------------------------------
		// Initialization of Buttons, labels, etc.
		// ---------------------------------------
		super("Registration", false, false, false, true);

		JPanel panel = new JPanel();
		lp = lgp;

		rUserField = new JTextField();
		rPassField = new JPasswordField();
		rPassField2 = new JPasswordField();
		rLatField = new JTextField();
		rLatField.setText("");
		rLongField = new JTextField();
		rLongField.setText("");

		rRegisterButton = new JButton("Register");
		rClearButton = new JButton("Clear");

		rDispRadioButton = new JRadioButton("Dispatcher");
		rDispRadioButton.setSelected(true);
		rRespRadioButton = new JRadioButton("Responder");
		rAdminRadioButton = new JRadioButton("Administrator");

		// -----------------------------------------------------------------------
		// Add radio buttons to button group so that only 1 is selected at a time
		// -----------------------------------------------------------------------

		group = new ButtonGroup();
		group.add(rDispRadioButton);
		group.add(rRespRadioButton);
		group.add(rAdminRadioButton);

		rRegisterButton.addActionListener(this);
		rClearButton.addActionListener(this);

		Container con = getContentPane();
		panel.setLayout(new GridLayout(8, 2, 3, 3));

		TitledBorder title;

		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Registration");
		title.setTitleJustification(TitledBorder.CENTER);
		panel.setBorder(title);

		// ---------------------------------------
		// Add all components to container
		// ---------------------------------------

		panel.add(new JLabel("Username"));
		panel.add(rUserField);
		panel.add(new JLabel("Password"));
		panel.add(rPassField);
		panel.add(new JLabel("Re-type Password"));
		panel.add(rPassField2);
		panel.add(rDispRadioButton);
		panel.add(rRespRadioButton);
		panel.add(rAdminRadioButton);
		panel.add(new JLabel(""));
		panel.add(new JLabel("Latitude"));
		panel.add(rLatField);
		panel.add(new JLabel("Longitude"));
		panel.add(rLongField);
		panel.add(rRegisterButton);
		panel.add(rClearButton);

		con.add(panel);

		// ---------------------------------------
		// Set unresizable and centered on screen
		// ---------------------------------------

		pack();
		setVisible(true);
		setSize(220,240);
	}

	// ---------------------------------------------
	// Implement Action Listener for various buttons
	// ---------------------------------------------

	public void actionPerformed(ActionEvent e)
	{
		// ---------------------------------------
		// If register button is pressed
		// ---------------------------------------

		if(e.getSource() == rRegisterButton)
		{
			if( ! checkIfRegistrationFieldsAreFilled() )
			{
				JOptionPane.showMessageDialog(this,"Please fill out all fields!","Registration Login",JOptionPane.ERROR_MESSAGE);
			}
			else if( ! checkPasswords() )
			{
				JOptionPane.showMessageDialog(this,"Your passwords to not match.\nPlease retype the passwords.","Passwords Incorrect",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				registerUser();
			}
		}

		// ---------------------------------------
		// If clear button is pressed
		// ---------------------------------------

		else if(e.getSource() == rClearButton)
		{
			rUserField.setText("");
			rPassField.setText("");
			rPassField2.setText("");
			rLatField.setText("");
			rLongField.setText("");
		}
	}

	// ---------------------------------------
	// Check to see if all fields are complete
	// ---------------------------------------

	public boolean checkIfRegistrationFieldsAreFilled()
	{
		if( rUserField.getText().equals("") || rPassField.getText().equals("") || rPassField2.getText().equals("") )
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	// ---------------------------------------
	// Register a user with the sustem
	// ---------------------------------------

	public void registerUser()
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
		// Get username, password, type, and lat and lon from fields
		// ---------------------------------------------------------

		String type = "";
		String user = rUserField.getText();
		String pass = rPassField.getText();
		float lat = 0; //= Double.parseDouble(rLatField.getText());
		float lon = 0; //= Double.parseDouble(rLongField.getText());

		if(!(rLatField.getText().equals("")))
		{
			lat = Float.parseFloat(rLatField.getText());
		}
		if(!(rLongField.getText().equals("")))
		{
			lon = Float.parseFloat(rLongField.getText());
		}

		// ---------------------------------------
		// Choose between dispatcher and responder
		// ---------------------------------------

		if( rDispRadioButton.isSelected() )
		{
			type = "Dispatcher";
		}
		else if( rRespRadioButton.isSelected() )
		{
			type = "Responder";
		}
		else if( rAdminRadioButton.isSelected() )
		{
			type = "Administrator";
		}

		// -----------------------------
		// Try to add record to database
		// -----------------------------

		if( ! DBops.addEntry(user, pass, type, lat, lon) )
		{
			JOptionPane.showMessageDialog(this,"Registration failed.  Please try again","Registration Failed",JOptionPane.ERROR_MESSAGE);
			DBops.close();
		}
		else
		{

			// --------------------------------------------------------------------------------------------------------
			// If the entry was successfull, close registration page and set userField on login to last registered user
			// --------------------------------------------------------------------------------------------------------

			JOptionPane.showMessageDialog(this,"Registration complete.  Please login","Registration Complete",JOptionPane.INFORMATION_MESSAGE);
			DBops.close();
			dispose();
			lp.userField.setText(user);
			lp.passField.setText("");
		}
	}

	// ---------------------------------------------------------------
	// Check to see if passwords are the same to check for consistency
	// ---------------------------------------------------------------

	public boolean checkPasswords()
	{
		if( rPassField.getText().equals(rPassField2.getText()) )
		{
			return true;
		}

		return false;
	}
}