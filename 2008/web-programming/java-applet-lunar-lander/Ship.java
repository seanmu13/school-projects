import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

// The ship class is responsible for the geometry and movement of the ship
// It contains the motion thread which moves the ship around

public class Ship implements Runnable
{
	public Ellipse2D.Double circle;
	public Line2D.Double line;

	public double diameter;
	public double pi = Math.PI;
	public int panelWidth;
	public int panelHeight;
	public Planet planet;
	public ArrayList<Planet.PlanetLine> planetLines;
	public ArrayList<Line2D.Double> bottomOfCircle;
	public String message = "Welcome!";
	public static int totalScore = 0;

	public static int GAME_OVER = 0;
	public static int LANDED = 1;
	public static int MOVING = 2;
	public int status = 2;

	// Ship created with a circle of diameter d
	public Ship(double d, int pw, int ph, Planet pl)
	{
		// initialize variables
		panelWidth = pw;
		panelHeight = ph;
		planet = pl;
		diameter = d;

		// Get the lines of the planet so we can detect and intersection
		// BottomOfcircle is used to intersect the bottom of the ship with the planet
		planetLines = planet.getPlanetLines();
		bottomOfCircle = new ArrayList<Line2D.Double>();

		// Create a circle for the ship and a line for the thruster
		double startX = Math.random() * 300 + 200;
		circle = new Ellipse2D.Double(startX,diameter*2,diameter,diameter);
		line = new Line2D.Double( circle.getCenterX() , circle.getCenterY() , circle.getCenterX(), circle.getCenterY() + diameter);
	}

	// Resets the score back to 0
	public void resetScore()
	{
		totalScore = 0;
	}

	// Sets the status of the ship to MOVING, LANDER, or GAME_OVER
	public void setStatus(int i)
	{
		status = i;
	}

	// Returns the status
	public int getStatus()
	{
		return status;
	}

	// Returns the message of the status
	public String getStatusMessage()
	{
		switch(status)
		{
			case 0: return "End Of Game";
			case 1: return "Landed";
			case 2: return "Moving";
		}
		return "Unknown";
	}

	// Gets score
	public int getTotalScore()
	{
		return totalScore;
	}

	// Get x velocity of ship
	public double getxVelocity()
	{
		return xVelocity;
	}

	// Get y velocity of ship
	public double getyVelocity()
	{
		return yVelocity;
	}

	// Get x location of ship
	public double getxLocation()
	{
		return circle.getX() + diameter/2;
	}

	// Get y location of ship
	public double getyLocation()
	{
		return circle.getY() + diameter/2;
	}

	// Draw the ship with the circle for the ship and lnie for the thrust
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.blue);
		g2d.draw(circle);
		g2d.fill(circle);

		g2d.setColor(Color.red);
		g2d.draw(line);
	}

	// Gets the circle part of the ship
	public Ellipse2D.Double getCircle()
	{
		return circle;
	}

	// Rotates the ship by using Linear Algebra to move the thruster line by 22.5 degrees left or right
	public void rotate(boolean left)
	{
		double oldX = line.getX2() - line.getX1();
		double oldY = line.getY2() - line.getY1();
		double newX, newY;

		if( left )
		{
			newX = oldX * Math.cos(-pi/8) + oldY * Math.sin(-pi/8) + circle.getCenterX();
			newY = oldY * Math.cos(-pi/8) - oldX * Math.sin(-pi/8) + circle.getCenterY();
		}
		else
		{
			newX = oldX * Math.cos(pi/8) + oldY * Math.sin(pi/8) + circle.getCenterX();
			newY = oldY * Math.cos(pi/8) - oldX * Math.sin(pi/8) + circle.getCenterY();
		}
		line.setLine( circle.getCenterX(), circle.getCenterY(), newX, newY);
	}

	// Gets the slope of the thrust line
	public double getSlope()
	{
		return ( line.getY1() - line.getY2() )/( line.getX2() - line.getX1() );
	}

	// Gets the angle of the thrust line
	public double getAngle()
	{
		double slope = getSlope();
		double angle;

		// If the slope is infinity the angle is either 90 or 270 degrees
		if( !(slope == 0) && !(slope < 0) && !(slope > 0) )
		{
			if( (line.getY2() - line.getY1()) > 0 )
			{
				angle = 3*pi/2;
			}
			else
			{
				angle = pi/2;
			}
		}
		// If the slope is 0, the angle is 0 or 180 degrees
		else if(slope == 0)
		{
			if( (line.getX2() - line.getX1() ) > 0 )
			{
				angle = 0;
			}
			else
			{
				angle = pi;
			}
		}
		// Find the angle using the slope and quadrant of the thruster line
		else
		{
			angle = Math.atan( slope );

			if( angle > 0 && quadrant() == 3)
			{
				angle += pi;
			}
			else if( angle < 0 && quadrant() == 2)
			{
				angle += pi;
			}
			else if( angle < 0 && quadrant() == 4)
			{
				angle += 2*pi;
			}
		}

		return angle;
	}

	// Returns in which quadrant the thrust line lies
	public int quadrant()
	{
		if( (line.getY2() - line.getY1() < 0) && (line.getX2() - line.getX1() > 0) )
		{
			return 1;
		}
		else if( (line.getY2() - line.getY1() < 0) && (line.getX2() - line.getX1() < 0) )
		{
			return 2;
		}
		else if( (line.getY2() - line.getY1() > 0) && (line.getX2() - line.getX1() < 0) )
		{
			return 3;
		}
		else
		{
			return 4;
		}
	}

	// Gets the lines that outline the bottom half of the circle - used for detecting intersection with planet
	public void getBottomOfCircleLines()
	{
		if(! bottomOfCircle.isEmpty())
		{
			for(int i=0; i < bottomOfCircle.size(); i++)
			{
				bottomOfCircle.remove(i);
			}
		}

		// Makes a series of 6 lines that outline the bottom half of the circle and puts them in an arraylist
		Line2D.Double line1, line2, line3, line4, line5, line6;
		double startX = circle.getX();
		double startY = circle.getY();

		line1 = new Line2D.Double(startX,        startY+diameter/2,   startX+diameter/6,   startY+2*diameter/3);
		line2 = new Line2D.Double(line1.getX2(), line1.getY2(), startX+diameter/3,   startY+5*diameter/6);
		line3 = new Line2D.Double(line2.getX2(), line2.getY2(), startX+diameter/2,   startY+diameter);
		line4 = new Line2D.Double(line3.getX2(), line3.getY2(), startX+2*diameter/3, startY+5*diameter/6);
		line5 = new Line2D.Double(line4.getX2(), line4.getY2(), startX+5*diameter/6, startY+2*diameter/3);
		line6 = new Line2D.Double(line5.getX2(), line5.getY2(), startX+diameter,     startY+diameter/2);

		bottomOfCircle.add(line1);
		bottomOfCircle.add(line2);
		bottomOfCircle.add(line3);
		bottomOfCircle.add(line4);
		bottomOfCircle.add(line5);
		bottomOfCircle.add(line6);
	}

	// Gets message that will be posted on the buttonPanel
	public String getMessage()
	{
		return message;
	}

	// Motion Thread
	public boolean moving = true;
	public boolean thrusting = false;
	public int duration = 20;
	public final double aGrav = 9.8;
	public final double aThrust = 29.4;
	public double xVelocity = 0;
	public double yVelocity = 0;

	// Starts to move the ship
	public void run()
	{
		setStatus(Ship.MOVING);
		moving = true;

		while(moving)
		{
			try
			{
				Thread.sleep(duration);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			move();
		}
	}

	public void move()
	{
		double currentThrust;

		// If we are thrusting, the acceleration due to the thrust is 29.4, else it is 0 and only gravity acts on it
		if( thrusting )
		{
			currentThrust = aThrust;
		}
		else
		{
			currentThrust = 0;
		}

		// Gets x and y coordinates of the circle
		double oldX = circle.getX();
		double oldY = circle.getY();

		// Calculates the horizontal and vertical thrusts
		double aHorizontal = -currentThrust * Math.cos( getAngle() );
		double aVertical = Math.sin( getAngle() ) * currentThrust + aGrav;

		// Calculates the distances moved during the duration time
		double deltaX = xVelocity * duration/1000 + .5 * aHorizontal * Math.pow(duration/1000,2);
		double deltaY = yVelocity * duration/1000 + .5 * aVertical * Math.pow(duration/1000,2);

		// Recalculates velocity based on the accelartion
		xVelocity += aHorizontal * duration/1000;
		yVelocity += aVertical * duration/1000;

		// Finds new x and y coordinates for ship
		double newX = circle.getX() + deltaX;
		double newY = circle.getY() + deltaY;

		// If the circle is close to the wall, just make the velocities 0
		if( newX < 5 || newX > (panelWidth - 25) || newY < 5 || newY > (panelHeight - 25) )
		{
			xVelocity = 0;
			yVelocity = 0;
			return;
		}

		// Moves the thrust line
		double newX1 = line.getX1() + deltaX;
		double newX2 = line.getX2() + deltaX;
		double newY1 = line.getY1() + deltaY;
		double newY2 = line.getY2() + deltaY;

		circle.setFrame(newX,newY,diameter,diameter);
		line.setLine( newX1, newY1, newX2, newY2);

		getBottomOfCircleLines();

		// Checking for intersections between planet and circle
		for(Planet.PlanetLine pLine: planetLines)
		{
			for(Line2D.Double cLine: bottomOfCircle)
			{
				// If there is an intersection
				if(cLine.intersectsLine(pLine))
				{
					// See if the ship is completely contained within the pad and is going slow enough for a good score
					if( circle.getX() >= pLine.getX1() && (circle.getX() + circle.getWidth()) <= pLine.getX2() && pLine.isPad() && Math.abs(xVelocity) < 10 && yVelocity < 10 && getAngle() > pi && getAngle() < 2*pi)
					{
						message = "Good Landing! " + planet.getPoints() + " Points";
						totalScore += planet.getPoints();
						setStatus(Ship.LANDED);
					}
					// See if the ship is completely contained within the pad and is going slow enough for an OK score
					else if(circle.getX() >= pLine.getX1() && (circle.getX() + circle.getWidth()) <= pLine.getX2() && pLine.isPad() && Math.abs(xVelocity) < 20 && yVelocity < 20 && getAngle() > pi && getAngle() < 2*pi)
					{
						message = "OK Landing! " + planet.getPoints() + " Points";
						totalScore += planet.getPoints()/2;
						setStatus(Ship.LANDED);
					}
					// The ship has crashed
					else
					{
						message = "Poor Landing! Crash! O Points";
						setStatus(Ship.GAME_OVER);
					}

					moving = false;
					break;
				}
			}

			if(!moving)
			{
				break;
			}
		}
	}

	// Stop the ship from moving
	public void stopMotion()
	{
		moving = false;
	}

	// Turn thrusting on
	public void startThrusting()
	{
		thrusting = true;
	}

	// Turn thrusting off
	public void stopThrusting()
	{
		thrusting = false;
	}

	public static void main(String[] args)
	{
		new LunarLander();
	}
}