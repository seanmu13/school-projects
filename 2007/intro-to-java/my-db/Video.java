//--------------------------------------------
//------Video Class with constructor----------
//--------------------------------------------

public class Video extends Recording
{
	protected String director;

	public Video(String t, int m, double s)
	{
		super(t,m,s);
		type = new String("Video");
	}

//Sets the director

	public void setDirector(String dir)
	{
		director = dir;
		author = dir;
	}

//Equals method for video class

	public boolean equals(Video other)
	{
		if((this.title).equals(other.title))
		{
			if((this.director).equals(other.director))
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