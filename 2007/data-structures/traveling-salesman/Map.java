public class Map{

	private MyArrayList<City> cities = new MyArrayList<City>();

	public Map(){
	}

//----------------------------
//	Adds a new city to the map
//----------------------------

	public void add(String c, int x, int y){

		cities.add(new City(c,x,y));
	}

//--------------------------------------------------
//	Returns the MyArrayList of the cities on the map
//--------------------------------------------------

	public MyArrayList<City> getCitiesOnMap(){
		return cities;
	}
}