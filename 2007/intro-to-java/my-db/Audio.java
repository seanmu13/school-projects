//--------------------------------------------
//------Audio Class with constructor----------
//--------------------------------------------

public class Audio extends Recording
{
	protected String artist;

	public Audio(String t, int m, double s)
	{
		super(t,m,s);
		type = new String("Audio");
	}

//Sets the artist

	public void setArtist(String art)
	{
		artist = art;
		author = art;
	}

//Equals method for audio class

	public boolean equals(Audio other)
	{
		if((this.title).equals(other.title))
		{
			if((this.artist).equals(other.artist))
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

