import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class LightsDesktop extends JFrame implements ActionListener
{
    public JDesktopPane desktop;
	public LoginPage loginPage;
	public Dispatcher disp;
	public Responder resp;
	public TicketGui ticketGui;
	public JToolBar toolBar;
	public JButton loginButton;
	public JButton logoutButton;
	public JButton quitButton;
	public static boolean loggedIn = false;
	public static boolean pressed = false;

    public LightsDesktop()
    {
        super("Welcome to the L.I.G.H.T.S. Desktop Environment");

		// -----------------------------------------------------------------------
        // Make the big window be indented 50 pixels from each edge of the screen.
        // -----------------------------------------------------------------------

        int inset = 100;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width  - inset * 2, screenSize.height - inset * 2);

        desktop = new JDesktopPane();
        setContentPane(desktop);
        setJMenuBar(createMenuBar());
        setIconImage(new ImageIcon("icon.gif").getImage());

		ImageIcon back = new ImageIcon("back.png");

		JLabel backGroundPic = new JLabel("<html><img src=file:back.png width=100% height=100% /></html>");
		backGroundPic.setOpaque(false);
		backGroundPic.setBounds(0, 0, screenSize.width, screenSize.height);
		desktop.add(backGroundPic, new Integer(-1));
    }

    public JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

		// ------------------------
        // Set up the lone menu.
        // ------------------------

        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

		// ---------------------------
        // Set up the first menu item.
        // ---------------------------

        JMenuItem menuItem = new JMenuItem("Login/Register");
        menuItem.setMnemonic(KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Login");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Logout");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Logout");
        menuItem.addActionListener(this);
        menu.add(menuItem);

		// ----------------------------
        // Set up the second menu item.
        // ----------------------------

        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);


        return menuBar;
    }

    public void actionPerformed(ActionEvent e)
    {
        if("Login".equals(e.getActionCommand()))
        {
			if(pressed && !loggedIn)
			{
				return;
			}

			if(loggedIn)
			{
				int choice = JOptionPane.showConfirmDialog(desktop, "You are already signed in.\nAre you sure you want to logout\nand sign in with a different account?", "Confirm Logout", JOptionPane.YES_NO_OPTION);

				if(choice == 0)
				{
					logOut();
				}

				return;
			}

			login();
        }
        else if("Logout".equals(e.getActionCommand()))
        {
			int choice = JOptionPane.showConfirmDialog(desktop, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);

			if(choice == 0)
			{
				logOut();
			}
		}
        else if("Quit".equals(e.getActionCommand()))
        {
			int choice = JOptionPane.showConfirmDialog(desktop, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION);

			if(choice == 0)
			{
            	System.exit(0);
			}
        }
    }

	public void login()
	{
		loginPage = new LoginPage(desktop);
		pressed = true;
		desktop.add(loginPage);
		loggedIn = true;

		try
		{
			loginPage.setSelected(true);
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
	}

	public void logOut()
	{
		loggedIn = false;
		pressed = false;
		JInternalFrame[] internalFrames = desktop.getAllFrames();

		for(JInternalFrame f: internalFrames)
		{
			f.dispose();
		}

		login();
	}

    public static void createAndShowGUI()
    {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
		      System.out.println("Error setting native LAF: " + e);
		}

        LightsDesktop frame = new LightsDesktop();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame.desktop, "Welcome to the L.I.G.H.T.S. Desktop Environment.\nTo begin, login with your username and password.", "WELCOME TO L.I.G.H.T.S",JOptionPane.DEFAULT_OPTION);
        frame.login();
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
