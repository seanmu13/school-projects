//--------------------------------------------
//------DVD Class with constructor------------
//--------------------------------------------

public class DVD extends Video
{
	public DVD(String t, int m, double s)
	{
		super(t,m,s);
		subType = new String("DVD");
	}

    public static void main(String[] args)
    {
	}
}