package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Rodrigo Junqueira
 * The TravellingSalesmanMap class keeps a list of cities (A, B, C, etc) 
 * and the distances between each pair of those cities (A->B, B->C, etc).
 * The correct flow for creating a map before using it in a Travelling Salesman problem is:
 * 1) Instantiate the object;
 * 2) Add each of the cities;
 * 3) Set the distances between the cities. It's possible to feed this information individually,
 * for each pair (A->B are distant by 2 units of distance, for example), using the setDistance
 * method; or you can use the method setAllDistancesFor, where is possible to set an origin city
 * and then inform an array with the distances for all the other cities. The array size should be
 * equals to the number of cities on the map. It's important to notice that the distance between
 * A and B must be the same of the distance between B and A;
 * 4) Ideally, the map should be validated before use. The validate method will check if all the infos
 * are consistent;
 * 5) Optionally, cities can be removed from the map. All the distances relative to that city will be
 * ignored in the next distance queries;
 * 6) From now one the object can be queried abour distances between two given cities.
 * A current limitation of this class is the name of the cities. Right now it requires a unique
 * one character name. Soon I will make the city itself a class, so it can store more data.
 */
public class TravellingSalesmanMap {

	private ArrayList<String> cities; 			// The list of cities
	private HashMap<String, Integer> distances; // The distances between each two pair of cities
	
	public TravellingSalesmanMap() {
		this.cities = new ArrayList<String>();
		this.distances = new HashMap<String, Integer>();
	}
	
	/**
	 * Adds a new city to the map.
	 * @param newCity the city to be added to the map.
	 */
	public void add(String newCity) {
		this.cities.add(newCity);		
	}
	
	/**
	 * @return a list with all the cities on this map.
	 */
	public ArrayList<String> getCities() {
		return this.cities;
	}

	/**
	 * Informs the distance between two cities.
	 * @param cityA
	 * @param cityB
	 * @return the distance between cityA and cityB. Both cities needs to be different, be part of the map and the distance must have been informed. Otherwise, returns 0.
	 */
	public int getDistance(String cityA, String cityB) {
		String move = cityA.concat(cityB);
		if (cityA != cityB &&					// CityA must be different from CityB
			this.cities.contains(cityA) && 		// CityA must be part of the map
			this.cities.contains(cityB) &&		// CityB must be part of the map
			this.distances.containsKey(move)) {	// The distance between the two cities must have been informed
			return this.distances.get(move);
		} else return 0;
	}
	
	/**
	 * @return the number of cities in the map.
	 */
	public int getSize() {
		return this.cities.size();
	}
	
	/**
	 * @return true if there are at least two cities in the map, false otherwise.
	 */
	private boolean mapHasAtLeastTwoCities() {
		if (this.getSize() < 2) {
			return false;
		} else return true;
	}

	/**
	 * @return true if all the necessary distance information was filled, false otherwise.
	 */
	private boolean mapHasAllDistancesFilled() {
		for (String cityA : this.cities) {	
			for (String cityB : this.cities) {
				if (cityA != cityB) {
					if (!this.distances.containsKey(cityA.concat(cityB))){	// Hence the only way to set a distance is through the setDistance method, if the path A->B is informed, then B->A is informed as well
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * @param city the city to be removed from the map.
	 */
	public void remove(String city) {
		int i = this.cities.indexOf(city);
		this.cities.remove(i);	
	}
	
	/**
	 * Informs the distances between a given city and all other cities in the map.
	 * @param city 		the city of origin.
	 * @param distances	an array with the distances from the city informed as origin, to all the other cities.
	 */
	public void setAllDistancesFor(String city, int[] distances) {
		if (distances.length == this.cities.size()) {
			for (int i = 0; i < distances.length; i++) {
				this.setDistance(city, this.cities.get(i), distances[i]);
			}
		}
	}
	
	/**
	 * @param cityA		origin city.
	 * @param cityB		destiny city.
	 * @param distance	the distance between the two cities.
	 */
	public void setDistance(String cityA, String cityB, int distance) {
		String forward = cityA.concat(cityB);
		String backward = cityB.concat(cityA);
		if (cityA != cityB &&
			this.cities.contains(cityA) && 
			this.cities.contains(cityB) &&
			!this.distances.containsKey(forward)) {
			this.distances.put(forward, distance);
			this.distances.put(backward, distance);
		}
	}
	
	/**
	 * @return true if the given map is valid and have all the necessary info, false otherwise.
	 */
	public boolean validate() {
		if (!this.mapHasAtLeastTwoCities()) return false;
		if (!this.mapHasAllDistancesFilled()) return false;
		return true;
	}
	
}

