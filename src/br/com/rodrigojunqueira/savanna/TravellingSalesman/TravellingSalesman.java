package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import java.util.HashMap;

import br.com.rodrigojunqueira.savanna.core.Dna;

public class TravellingSalesman implements Dna {

	private HashMap<String, HashMap<String, Integer>> map;
	private String[] route;
	private int totalDistance;
	private static int goal;
	
	public TravellingSalesman(HashMap<String, HashMap<String, Integer>> newMap) {
		this.map = newMap;
	}
	
	public void mutate() {
		this.setRoute(new String[]{
									this.route[0], 
									this.route[3], 
									this.route[2], 
									this.route[1], 
									this.route[4]
									});
	}
	
	public Dna crossover(Dna dna) {
		TravellingSalesman travel = (TravellingSalesman) dna;
		TravellingSalesman newTravel = new TravellingSalesman(this.map);
		newTravel.setRoute(travel.route);
		return newTravel;
	}	

	public boolean moreFitThan(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() < d.getTotalDistance()) {
			return true;
		} else return false;
	}

	public boolean lessFitThan(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() > d.getTotalDistance()) {
			return true;
		} else return false;
	}

	public boolean asFitAs(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() == d.getTotalDistance()) {
			return true;
		} else return false;
	}	

	public void evaluate() {
		int totalDistance = 0;
		for (int i = 0; i < (this.route.length - 1); i++) {
			totalDistance += this.getDistance(this.route[i], this.route[i+1]);
		}
		this.totalDistance = totalDistance;		
	}

	public boolean isGoodEnough() {
		if (this.getTotalDistance() <= TravellingSalesman.goal) {
			return true;
		} else return false;
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

	public static void setGoal(int newGoal) {
		TravellingSalesman.goal = newGoal;
	}

}
