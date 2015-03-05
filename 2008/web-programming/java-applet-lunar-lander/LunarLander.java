import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.applet.*;
import java.net.*;

//	LunarLander drives the applet itself.  It has access to all of threads and display panels.  It also contains the timer.
public class LunarLander extends JApplet
{
	// Declaration of gloab variables
	public ButtonPanel buttonPanel;
	public GamePanel gamePanel;
	public Ship ship;
	public Planet planet;
	public Thread labelRefresher, displayRefresher, motionThread, timerThread;
	public Timer timer;
	public LunarLander lander = this;
	public JMenuItem helpItem;
	public AudioClip[] sounds;
	public URL url;
	public static final int PORT = 8844;

	// Applet initiation occurs here
	// I make a new planet, new ship, and make the GUI, all of threads are also made here
	public void init()
	{
		planet = new Planet(750,600);
		ship = new Ship(20.0,750,600,planet);
		makeGUI();

		displayRefresher = new Thread(gamePanel);
		motionThread = new Thread(ship);
		labelRefresher = new Thread(buttonPanel);
		timer = new Timer();
		timerThread = new Thread(timer);

		displayRefresher.start();
		motionThread.start();
		labelRefresher.start();
		timerThread.start();
	}

	// Makes the gui by adding the gamepanel and buttonpanel to the content pane
	// Adds a menu bar and creates the sound objects
	public void makeGUI()
	{
		buttonPanel = new ButtonPanel(this,ship,planet);
		gamePanel = new GamePanel(this,ship,planet);

		JMenuBar menubar = new JMenuBar();
		JMenu menuHelp = new JMenu("Help");
		helpItem = new JMenuItem("Help");
		menuHelp.add(helpItem);
		menubar.add(menuHelp);
		setJMenuBar(menubar);

		sounds = new AudioClip[2];

		try
		{
			url = new URL(getCodeBase() + "/thrust.au");
			sounds[0] = Applet.newAudioClip(url);
			url = new URL(getCodeBase() + "/crash.au");
			sounds[1] = Applet.newAudioClip(url);
		}
		catch(Exception e){}

		helpItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource() == helpItem)
				{
					String helpMessage = "The goal of the game is to land\nthe ship on the red lines (pads).\nThe ship must be completely contained\nwithin the bounds of the red pad.\nThere are 10 levels total\nand you will have a certain amount\nof time to complete each level.\nGood Landing: velocities must be <10\nOK Landing:  velocities must be <20\nThe ship's bottom must be facing the ground.";
					JOptionPane.showMessageDialog(lander, helpMessage, "Help Display", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		getContentPane().add(gamePanel, BorderLayout.PAGE_START);
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		setVisible(true);
	}

	// Tells is thrust sound is playing
	public boolean playing = false;

	// Play Sounds
	public void playThrust()
	{
		if( !playing)
		{
			playing = true;
			sounds[0].play();
		}
	}

	public void stopThrustSound()
	{
		playing = false;
		sounds[0].stop();
	}

	public void playCrash()
	{
		sounds[1].play();
	}

	//If a new game needs created
	public void newGame()
	{
		planet.setLevel(0);
		ship.resetScore();
		stopThreads();
		planet = new Planet(750,600);
		ship = new Ship(20.0,750,600,planet);
		buttonPanel.update(ship,planet);
		gamePanel.update(ship,planet);

		restartThreads();
	}

	// IF a game is over
	public void endGame()
	{
		buttonPanel.endGameButton.setEnabled(false);
		stopThreads();
		sendScore();
	}

	// Advance to the next level
	public void startNewLevel()
	{
		stopThreads();
		planet = new Planet(750,600);
		ship = new Ship(20.0,750,600,planet);
		planet.increaseLevel();
		buttonPanel.update(ship,planet);
		gamePanel.update(ship,planet);
		restartThreads();
	}

	// If all levels are completed
	public void completedGame()
	{
		JOptionPane.showMessageDialog(this,"Congratulations, you beat the game!" + ship.getTotalScore(), "Beat the Game", JOptionPane.INFORMATION_MESSAGE);
		buttonPanel.endGameButton.setEnabled(false);
		sendScore();
	}

	// Stops all threads
	public void stopThreads()
	{
		buttonPanel.stopRefresh();
		ship.stopMotion();
		gamePanel.stopRefresh();
		timer.stopTimer();
	}

	// Restarts threads for new levels/games
	public void restartThreads()
	{
		displayRefresher = new Thread(gamePanel);
		motionThread = new Thread(ship);
		labelRefresher = new Thread(buttonPanel);
		timer = new Timer();
		timerThread = new Thread(timer);

		displayRefresher.start();
		motionThread.start();
		timerThread.start();
		labelRefresher.start();
	}

	// Sends score to server
	public void sendScore()
	{
		int score = ship.getTotalScore();
		String name = "";

		// Ask for person's initials
		do
		{
			 name = JOptionPane.showInputDialog(this,"Game Over - Final Score: " + score + "\nPlease enter your initials (3)");
		}
		while( name.length() != 3);

		// Prepare socket/io variables
		Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try
        {
            socket = new Socket("localhost", PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader( socket.getInputStream() ));
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host: localhost.");
            //System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for " + "the connection to: localhost.");
        }

		// Write score to output and create arraylist for the top 10 scores
		String input;
		out.println(score);
		boolean winner = false;
		ArrayList<String> topScores = new ArrayList<String>();

		// Keep reading input from server until DONE is read
		try
		{
			while( !(input = in.readLine()).equals("DONE") )
			{
				// If you are winner, the name is sent to server
				if( input.equals("WINNER") )
				{
					out.println(name);
					winner = true;
				}
				else if( input.equals("LOSER") )
				{
					winner = false;
				}
				// add string to topScores
				else
				{
					topScores.add(input);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Client error: " + e);
		}

		String topMessage = "";

		if(winner)
		{
			topMessage = "Congrats! You made the top 10.";
		}
		else
		{
			topMessage = "Sorry! You did not make the top 10.";
		}

		int i = 1;

		// Create string consisting of the top 10 scores
		for(String line : topScores)
		{
			String[] a = line.split(" ");
			topMessage += "\n" + i++ + ". " + a[0] + "  " + a[1];
		}

		JOptionPane.showMessageDialog(this,topMessage, "Top 10", JOptionPane.INFORMATION_MESSAGE);

		try
		{
			out.close();
			in.close();
			socket.close();
		}
		catch(Exception e){}
	}

	// Kill the timer thread
	public void endTimer()
	{
		timer.stopTimer();
	}

	// Timer class used for the countdown for each level
	public class Timer implements Runnable
	{
		public int countdown;
		public long startTime;
		public long nowTime;
		public long endTime;
		public Date date;
		public boolean timerRunning;

		// Gets the current time and the time given in a level
		// Then sets an 'alarm' for the countdown time and alerts the program if time expired
		public void run()
		{
			timerRunning = true;
			date = new Date();
			startTime = date.getTime();
			endTime = startTime + planet.getTimeMS();
			buttonPanel.getTimeLabel().setText("Time: " + planet.getTimeMS()/1000 + " s");

			while(timerRunning)
			{
				date = new Date();
				nowTime = date.getTime();

				// If the countdown has reached 0 (or less)
				// Stop the timer and other threads and end the game
				if(nowTime >= endTime)
				{
					stopTimer();
					buttonPanel.getTimeLabel().setText("Time: 0 s");
					stopThreads();
					JOptionPane.showMessageDialog(lander,"Sorry, you ran out of time.\nGame Over!", "Ran Out Of Time", JOptionPane.INFORMATION_MESSAGE);
					endGame();
				}
				else
				{
					buttonPanel.getTimeLabel().setText("Time: " + (endTime-nowTime)/1000 + " s");
				}

				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {}
			}
		}

		public void stopTimer()
		{
			timerRunning = false;
		}

		public void restartTimer()
		{
			timerRunning = true;
		}
	}

	public static void main(String[] args)
	{
		new LunarLander();
	}
}