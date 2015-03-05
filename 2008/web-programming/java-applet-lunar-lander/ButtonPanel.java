import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

// ButtonPanel contains all of the buttons and labels and a Thread for refreshing all of the labels
public class ButtonPanel extends JPanel implements ActionListener, Runnable
{
	public JButton newGameButton, endGameButton, nextLevelButton;
	public JLabel scoreLabel, timeLabel, xVeloLabel, yVeloLabel, xLocLabel, yLocLabel, messageLabel, statusLabel, levelLabel;
	public boolean refreshLabels = true;
	public Ship ship;
	public Planet planet;
	public LunarLander lunarLander;

	// Creates a new ButtonPanel
	public ButtonPanel(LunarLander lula, Ship s, Planet p)
	{
		super(new GridBagLayout());
		setBorder( new LineBorder(Color.black) );
		setOpaque(true);

		lunarLander = lula;
		ship = s;
		planet = p;

		// JButtons Initialization
		newGameButton = new JButton("New Game");
		endGameButton = new JButton("End Game");
		nextLevelButton = new JButton("Next Level");
		nextLevelButton.setEnabled(false);

		// JLabels Initialization
		scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
		timeLabel = new JLabel("Time:   -:--", JLabel.CENTER);
		xVeloLabel = new JLabel("X Velocity: -", JLabel.CENTER);
		yVeloLabel = new JLabel("Y Velocity: -", JLabel.CENTER);
		xLocLabel = new JLabel("X Location: -", JLabel.CENTER);
		yLocLabel = new JLabel("Y Location: -", JLabel.CENTER);
		messageLabel = new JLabel(" Msg: ", JLabel.CENTER);
		statusLabel = new JLabel("Status: ", JLabel.CENTER);
		levelLabel = new JLabel("Level: 0", JLabel.CENTER);

		// Adds action listeners to the buttons
		newGameButton.addActionListener(this);
		nextLevelButton.addActionListener(this);
		endGameButton.addActionListener(this);

		GridBagConstraints gb = new GridBagConstraints();

		// ----------------------------------------
		// Row 0 (scorelabel, xVeloLabel, xLocLabel, statusLabel)
		// ----------------------------------------

		gb.gridx = 0;
		gb.gridy = 0;
		gb.gridwidth = 1;
		scoreLabel.setPreferredSize(new Dimension(187,20));
		add(scoreLabel,gb);

		gb.gridx = 1;
		gb.gridy = 0;
		gb.gridwidth = 1;
		xVeloLabel.setPreferredSize(new Dimension(187,20));
		add(xVeloLabel,gb);

		gb.gridx = 2;
		gb.gridy = 0;
		gb.gridwidth = 1;
		xLocLabel.setPreferredSize(new Dimension(187,20));
		add(xLocLabel,gb);

		gb.gridx = 3;
		gb.gridy = 0;
		gb.gridwidth = 1;
		statusLabel.setPreferredSize(new Dimension(187,20));
		add(statusLabel,gb);

		// ----------------------------------------
		// Row 1 (timeLabel, yVeloLabel, yLocLabel, levelLabel)
		// ----------------------------------------

		gb.gridx = 0;
		gb.gridy = 1;
		gb.gridwidth = 1;
		add(timeLabel,gb);

		gb.gridx = 1;
		gb.gridy = 1;
		gb.gridwidth = 1;
		add(yVeloLabel,gb);

		gb.gridx = 2;
		gb.gridy = 1;
		gb.gridwidth = 1;
		add(yLocLabel,gb);

		gb.gridx = 3;
		gb.gridy = 1;
		gb.gridwidth = 1;
		add(levelLabel,gb);

		// --------------------------------------------------------------------
		// Row 2 (newGameButton, endGameButton, nextLevelButton, messageLabel)
		// --------------------------------------------------------------------

		gb.gridx = 0;
		gb.gridy = 2;
		gb.gridwidth = 1;
		gb.fill = GridBagConstraints.HORIZONTAL;
		add(newGameButton,gb);

		gb.gridx = 1;
		gb.gridy = 2;
		gb.gridwidth = 1;
		gb.fill = GridBagConstraints.HORIZONTAL;
		add(endGameButton,gb);

		gb.gridx = 2;
		gb.gridy = 2;
		gb.gridwidth = 1;
		gb.fill = GridBagConstraints.HORIZONTAL;
		add(nextLevelButton,gb);

		gb.gridx = 3;
		gb.gridy = 2;
		gb.gridwidth = 1;
		gb.fill = GridBagConstraints.NONE;
		add(messageLabel,gb);
	}

	// We update the button panel when a new level/game is started
	public void update(Ship s, Planet p)
	{
		ship = s;
		planet = p;
		endGameButton.setEnabled(true);
	}

	// Gets the JLabel for the time label
	public JLabel getTimeLabel()
	{
		return timeLabel;
	}

	// Thread for refresing all of the labels except the time, which is done by the timer
	public void run()
	{
		refreshLabels = true;

		while(refreshLabels)
		{
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e) {}

			// Sets all the labels by receiving the information from the ship object
			xVeloLabel.setText("X Velocity: " + (int) ship.getxVelocity() + " m/s");
			yVeloLabel.setText("Y Velocity: " + (int)ship.getyVelocity() + " m/s");
			xLocLabel.setText("X Location: " + (int)ship.getxLocation() + " m");
			yLocLabel.setText("Y Location: " + (int)ship.getyLocation() + " m");
			scoreLabel.setText("Score: " + ship.getTotalScore() );
			messageLabel.setText(ship.getMessage());
			statusLabel.setText("Status: " + ship.getStatusMessage() );
			levelLabel.setText("Level: " + planet.getLevel() );

			// If the game is over, stop the timer, play crash noise, and end the game
			if(ship.getStatus() == Ship.GAME_OVER)
			{
				lunarLander.endTimer();
				lunarLander.playCrash();
				stopRefresh();
				endGameButton.setEnabled(false);
				JOptionPane.showMessageDialog(lunarLander,"You crashed!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				lunarLander.endGame();
			}
			// If the ship landed, stop the timer, stop refreshing, and allow the player to go to the next level
			else if(ship.getStatus() == Ship.LANDED)
			{
				lunarLander.endTimer();
				stopRefresh();
				JOptionPane.showMessageDialog(lunarLander,"You Landed! Move onto the next level if you wish.", "Good Job", JOptionPane.INFORMATION_MESSAGE);
				nextLevelButton.setEnabled(true);
			}
		}
	}

	// Stop refreshing the label
	public void stopRefresh()
	{
		refreshLabels = false;
	}

	// ActionPerformed method for detecting button actions
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		// Create a new game
		if( source.equals(newGameButton) )
		{
			int i = JOptionPane.showConfirmDialog(lunarLander,"Are you sure you want\nto start a new game?", "New Game?",JOptionPane.YES_NO_OPTION);

			if( i==0 )
			{
				nextLevelButton.setEnabled(false);
				lunarLander.newGame();
			}
		}

		// Start the next level
		else if( source.equals(nextLevelButton) )
		{
			int level = planet.getLevel() + 1;

			// If all of the levels are complete, call completedGame()
			if( level == 10)
			{
				nextLevelButton.setEnabled(false);
				lunarLander.completedGame();
			}
			// Else, ask if user really wants to go to the next level
			else
			{
				int i = JOptionPane.showConfirmDialog(lunarLander,"Are you sure you want\nto go to Level " + level + "?", "Next Level?",JOptionPane.YES_NO_OPTION);

				if( i==0 )
				{
					nextLevelButton.setEnabled(false);
					lunarLander.startNewLevel();
				}
			}
		}
		// End the game if the user wants to
		else if( source.equals(endGameButton) )
		{
			int level = planet.getLevel();
			int i = JOptionPane.showConfirmDialog(lunarLander,"Are you sure you want\nto quit now at Level " + level + "?", "Next Level?",JOptionPane.YES_NO_OPTION);

			if( i==0 )
			{
				nextLevelButton.setEnabled(false);
				lunarLander.endGame();
			}
		}
	}

	public static void main(String[] args)
	{
		new LunarLander();
	}
}