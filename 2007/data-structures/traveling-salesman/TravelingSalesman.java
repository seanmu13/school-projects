import java.math.*;

public class TravelingSalesman{

	private Map tourMap = new Map();

	public TravelingSalesman(){
	}

//------------------------
//	Adds a city to the map
//------------------------

	public void addCity(String c, int x, int y){
		tourMap.add(c,x,y);
	}

//----------------------------------------
//	Begins a tour starting at city named c
//----------------------------------------

	public MyArrayList<City> tour(String c){

		MyArrayList<City> tourOfCities = new MyArrayList<City>();
		MyArrayList<City> citiesLeft = tourMap.getCitiesOnMap();

//--------------------------------
//	Finds the city with the name c
//--------------------------------

		for(int i=0;i<citiesLeft.size();i++){
			if(citiesLeft.get(i).name.equals(c)){
				tourOfCities.add(citiesLeft.get(i));
				citiesLeft.remove(i);
				break;
			}
		}

//-----------------------------------------------------------------------------------------------
//	Finds the minimum distance to the next stop from the remaining cities and adds it to the tour
//-----------------------------------------------------------------------------------------------

		while(citiesLeft.size()>0){
			City sCity = tourOfCities.get(tourOfCities.size()-1);
			int startX = sCity.x;
			int startY = sCity.y;
			int endX,endY,minIndex=0;
			double val;

			double minVal = Math.sqrt(Math.pow(startX-citiesLeft.get(0).x,2) + Math.pow(startY-citiesLeft.get(0).y,2));

			for(int i=1;i<citiesLeft.size();i++){
				val = Math.sqrt(Math.pow(startX-citiesLeft.get(i).x,2) + Math.pow(startY-citiesLeft.get(i).y,2));
				if(val < minVal){
					minVal = val;
					minIndex = i;
				}
			}

			tourOfCities.add(citiesLeft.get(minIndex));
			citiesLeft.remove(minIndex);
		}

		tourOfCities.add(tourOfCities.get(0));

		return tourOfCities;
	}

	public static void main(String[] args){

	}
}