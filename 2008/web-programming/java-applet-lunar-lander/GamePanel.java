import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.geom.*;

// Defines the area where the ship and planet reside
public class GamePanel extends JPanel implements Runnable
{
	public Ship ship;
	public Planet planet;
	public Graphics2D g2;
	public boolean refresh = true;
	public LunarLander lunarLander;

	// Makes an new GamePanel
	public GamePanel(LunarLander land, Ship s, Planet p)
	{
		super();
		ship = s;
		planet = p;
		lunarLander = land;

		// Sets some parameters of the panel
		setPreferredSize(new Dimension(750,600) );
		setBorder( new LineBorder(Color.black) );
		setOpaque(true);
        setBackground(Color.white);
		addMouseListener(new MouseHandler());
		addKeyListener(new KeyHandler());
		setFocusable(true);
	}

	// We update the panel with a new ship and planet for new levels, new games
	public void update(Ship s, Planet p)
	{
		ship = s;
		planet = p;
		requestFocus();
	}

	// Refreshes the panel
	public void run()
	{
		refresh = true;
		while (refresh)               // infinite loop -- runs until program ends
		{
			 repaint();      // all we do here is repaint the
			 try                       // panel, then sleep for a bit.
			 {                         // Since the sleep() method could
				   Thread.sleep(20);   // throw InterruptedException (which
			 }                                    // is a checked exception)
			 catch (InterruptedException e) {}    // we must catch it
		}
	}

	// Stops the refreshing cycle
	public void stopRefresh()
	{
		refresh = false;
	}

	// Paints the ship and planet on the screen
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		ship.draw(g2d);
		planet.draw(g2d);
	}

	// MouseHandler for detecting left and right mouse clicks
	class MouseHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if( e.getButton() == MouseEvent.BUTTON1 )
			{
				ship.rotate(true);
			}
			else if( e.getButton() == MouseEvent.BUTTON3 )
			{
				ship.rotate(false);
			}
		}
	}

	// KeyHandler for detecting left, right, and space keys
	class KeyHandler extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			if( e.getKeyCode() == KeyEvent.VK_LEFT )
			{
				ship.rotate(true);
			}
			else if( e.getKeyCode() == KeyEvent.VK_RIGHT )
			{
				ship.rotate(false);
			}
			else if( e.getKeyCode() == KeyEvent.VK_SPACE )
			{
				ship.startThrusting();
				lunarLander.playThrust();
			}
		}

		// When space bar is pressed, we stop thrusting and stop the thrusting sound
		public void keyReleased(KeyEvent e)
		{
			if( e.getKeyCode() == KeyEvent.VK_SPACE )
			{
				ship.stopThrusting();
				lunarLander.stopThrustSound();
			}
		}
	}

	public static void main(String[] args)
	{
		new LunarLander();
	}
}