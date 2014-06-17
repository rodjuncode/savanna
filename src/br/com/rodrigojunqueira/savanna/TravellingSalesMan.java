package br.com.rodrigojunqueira.savanna;

import java.util.HashMap;

public class TravellingSalesMan implements Dna {

	private static HashMap<String, HashMap<String, Integer>> map;
	private String[] route;
	private int totalDistance;
	
	public TravellingSalesMan(HashMap<String, HashMap<String, Integer>> newMap) {
		this.map = newMap;
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Dna crossover(Dna dna) {
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	public boolean moreFitThan(Dna defiant) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lessFitThan(Dna defiant) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean asFitAs(Dna defiant) {
		// TODO Auto-generated method stub
		return false;
	}	

	@Override
	public void evaluate() {
		int totalDistance = 0;
		for (int i = 0; i < (this.route.length - 1); i++) {
			totalDistance += this.getDistance(this.route[i], this.route[i+1]);
		}
		this.totalDistance = totalDistance;		
	}

	@Override
	public boolean isGoodEnough() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRoute(String[] newRoute) {
		this.route = newRoute;
		
	}

	public int getDistance(String cityFrom, String cityTo) {
		return this.map.get(cityFrom).get(cityTo);
	}


	public int getTotalDistance() {
		return this.totalDistance;
	}

}
