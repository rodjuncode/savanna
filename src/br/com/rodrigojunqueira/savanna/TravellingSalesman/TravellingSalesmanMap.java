package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import java.util.ArrayList;
import java.util.HashMap;

public class TravellingSalesmanMap {

	private ArrayList<String> cities;
	private HashMap<String, Integer> distances;
	
	public TravellingSalesmanMap() {
		this.cities = new ArrayList<String>();
		this.distances = new HashMap<String, Integer>();
	}
	
	public int getSize() {
		return this.cities.size();
	}

	public void add(String newCity) {
		this.cities.add(newCity);		
	}

	public void remove(String city) {
		int i = this.cities.indexOf(city);
		this.cities.remove(i);	
	}

	public void setDistance(String cityA, String cityB, int distance) {
		if (cityA != cityB &&
			this.cities.contains(cityA) && 
			this.cities.contains(cityB)) {
			String forward = cityA.concat(cityB);
			String backward = cityB.concat(cityA);
			this.distances.put(forward, distance);
			this.distances.put(backward, distance);
		}
	}

	public int getDistance(String cityA, String cityB) {
		String path = cityA.concat(cityB);
		if (cityA != cityB &&
			this.cities.contains(cityA) && 
			this.cities.contains(cityB) &&
			this.distances.containsKey(path)) {
			return this.distances.get(path);
		} else return 0;
	}

	public boolean validate() {
		if (!this.mapHasAtLeastTwoCities()) return false;
		if (!this.mapHasAllDistancesFilled()) return false;
		return true;
	}
	
	private boolean mapHasAtLeastTwoCities() {
		if (this.getSize() < 2) {
			return false;
		} else return true;
	}

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

	public ArrayList<String> getCities() {
		return this.cities;
	}

	public void setAllDistancesFor(String city, int[] distances) {
		
	}

}
