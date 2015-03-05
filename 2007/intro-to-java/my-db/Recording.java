//--------------------------------------------------------------------
//-----Recording class with constructor that implements comparable----
//--------------------------------------------------------------------

public class Recording implements Comparable
{
	protected String title;
	protected int minutes;
	protected double seconds;
	protected String subType;
	protected String type;
	protected String author;

	public Recording(String t, int m, double s)
	{
		title = t;
		minutes = m;
		seconds = s;
	}

//compareTo method for Recording class

	public int compareTo(Object other)
	{
		if((this.title).equals(((Recording)other).title))
			return (this.author).compareTo(((Recording)other).author);
		else
			return (this.title).compareTo(((Recording)other).title);
	}

//toString method for Recording class and all sub classes

	public String toString()
	{
		String out = new String(
			"\nTitle:  " + this.title +
			"\nLength:  " + this.minutes + ":" + this.seconds +
			"\nType:  " + type +
			"\nSubtype:  " + subType);

		if(type.equals("Audio"))
			out = out + "\nArtist:  " + ((Audio)this).artist + "\n";
		else
			out = out + "\nDirector:  " + ((Video)this).director + "\n";

		return out;
	}

	public boolean equals(Recording other)
	{
		if((this.type).equals(other.type) && (this.subType).equals(other.subType))
		{
			if((this.title).equals(other.title) && (this.author).equals(other.author))
				return true;
			else
				return false;
		}
		else
			return false;
	}

    public static void main(String[] args)
    {
	}

}