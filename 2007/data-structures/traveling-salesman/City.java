//-----------------------------------------------------------------------
//	City class simply contains the name and coordinates <x,y> of the city
//-----------------------------------------------------------------------

public class City{

	public int x;
	public int y;
	public String name;

	public City(String cName, int xC, int yC){
		x = xC;
		y = yC;
		name = cName;
	}

	public String toString(){
		return "" + name;
	}
}