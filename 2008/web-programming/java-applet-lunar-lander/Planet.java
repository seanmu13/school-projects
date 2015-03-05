import java.awt.*;
import java.util.*;
import java.awt.geom.*;

// Defines the planet with which the ship intersects
public class Planet
{
	// Declare global variables
	public int panelWidth;
	public int panelHeight;
	public static int level = 0;
	public int pads[] = new int[10];
	public int widths[] = new int[10];
	public int points[] = new int[10];
	public int times[] = new int[10];
	public int minHeight;
	public int maxHeight;
	public double oldX;
	public double oldY;
	public ArrayList<PlanetLine> planetLines;
	public ArrayList<Integer> indexes;

	// Make a new planet with the defined boundaries
	public Planet(int pw, int ph)
	{
		panelWidth = pw;
		panelHeight = ph;

		// Find boundaries for planet
		minHeight = panelHeight;
		maxHeight = panelHeight/2;

		// Sets the coordinates for the first line of the planet
		oldX = 0;
		oldY = minHeight;

		// Sets the arrays that define the # of pads, the size of pads, the points, and countdown times for each level
		initializeArrays();

		// Sets where the pads will be located on the planet
		indexes = new ArrayList<Integer>();
		makeIndexes(level);

		// Makes the planet
		makePlanet();
	}

	// Returns the lines in the planet for use in detecting intersections
	public ArrayList<PlanetLine> getPlanetLines()
	{
		return planetLines;
	}

	// Sets the arrays that define the # of pads, the size of pads, the points, and countdown times for each level
	public void initializeArrays()
	{
		// Pad Numbers
		for(int i=0, j=5; i < pads.length; i++,j--)
		{
			if(j < 1)
			{
				j = 1;
			}

			pads[i] = j;
		}

		// Pad Width
		for(int i=0, j=54; i < widths.length; i++,j-=2)
		{
			widths[i] = j;
		}

		// Points
		for(int i=0, j=100; i < points.length; i++, j+=100)
		{
			points[i] = j;
		}

		// Countdown times
		for(int i=0, j=75; i < times.length; i++, j-=5)
		{
			times[i] = j;
		}

		planetLines = new ArrayList<PlanetLine>();
	}

	// Sets the level in the game
	public void setLevel(int i)
	{
		level = i;
	}

	// Gets the level for the game
	public int getLevel()
	{
		return level;
	}

	// Gets the countdown time in milliseconds
	public int getTimeMS()
	{
		return times[level]*1000;
	}

	// Gets the point number for the level
	public int getPoints()
	{
		return points[level];
	}

	// Increases level for game
	public boolean increaseLevel()
	{
		level++;

		if(level == 9)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// Clears the arraylist that contains the indexes where the pads are on the planet
	public void clearIndexes()
	{
		if(! indexes.isEmpty() )
		{
			for(int i=0; i < indexes.size(); i++)
			{
				indexes.remove(i);
			}
		}
	}

	// Clears the lines on the planet
	public void clearLines()
	{
		if(! planetLines.isEmpty() )
		{
			for(int i=0; i < planetLines.size(); i++)
			{
				planetLines.remove(i);
			}
		}
	}

	// Defines where the pads are placed on the planet
	public void makeIndexes(int lvl)
	{
		clearIndexes();
		if(lvl==0)
		{
			indexes.add((int)2);
			indexes.add((int)4);
			indexes.add((int)9);
			indexes.add((int)11);
			indexes.add((int)14);
		}
		else if(lvl==1)
		{
			indexes.add((int)(Math.ceil(Math.random()*2)));
			indexes.add((int)(Math.floor(Math.random()*2)+4));
			indexes.add((int)(Math.floor(Math.random()*2)+8));
			indexes.add((int)(Math.floor(Math.random()*2)+12));
		}
		else if(lvl==2)
		{
			indexes.add((int)(Math.ceil(Math.random()*4)));
			indexes.add((int)(Math.floor(Math.random()*3)+6));
			indexes.add((int)(Math.floor(Math.random()*3)+11));
		}
		else if(lvl==3)
		{
			indexes.add((int)(Math.ceil(Math.random()*2)));
			indexes.add((int)(Math.floor(Math.random()*3)+7));
		}
		else if(lvl>=4)
		{
			indexes.add((int)(Math.ceil(Math.random()*8)+1));
		}
	}

	// Makes the planet
	public void makePlanet()
	{
		// Clear any previous planet
		clearLines();

		// Calculate some coordinates and widths
		double newX, newY;
		double widthRemaining = panelWidth - pads[level] * widths[level];
		int nonPads = (int)(Math.floor(Math.random()*4) + 16);
		double deltaX = widthRemaining/( (double) nonPads);
		boolean pad = false;

		// Iterate through the pads and nonpads and place them on the panel
		for(int i=0; i < (pads[level]+nonPads); i++ )
		{
			if( indexes.contains( (Integer)i) )
			{
				newX = oldX + widths[level];
				newY = oldY;
				pad = true;
			}
			else
			{
				newX = oldX + deltaX;
				newY = Math.random() * (minHeight-maxHeight) + maxHeight;
				pad = false;
			}
			planetLines.add( new PlanetLine(oldX,oldY,newX,newY,pad) );
			oldX = newX;
			oldY = newY;
		}
	}

	// Draw the lines of the planet
	public void draw(Graphics2D g2d)
	{
		for(PlanetLine line: planetLines)
		{
			g2d.setColor( line.getColor() );
			g2d.draw(line);
		}
	}

	public static void main(String args[])
	{
		new LunarLander();
	}

	// PlanetLine is just a line2d with a boolean value and color attached
	class PlanetLine extends Line2D.Double
	{
		boolean isPad = false;

		public PlanetLine(double x1, double y1, double x2, double y2, boolean ip)
		{
			super(x1,y1,x2,y2);
			isPad = ip;
		}

		public boolean isPad()
		{
			return isPad;
		}

		public Color getColor()
		{
			if(isPad)
			{
				return Color.red;
			}
			else
			{
				return Color.black;
			}
		}
	}
}