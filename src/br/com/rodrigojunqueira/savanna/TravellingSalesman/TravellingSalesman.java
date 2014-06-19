package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import java.util.ArrayList;

import br.com.rodrigojunqueira.savanna.core.Dna;

public class TravellingSalesman implements Dna {

	private String[] route;
	private int totalDistance;
	private static int goal;
	private TravellingSalesmanMap map;
	
	public TravellingSalesman(TravellingSalesmanMap newMap) {
		this.map = newMap;
	}
	
	public void mutate() {
		// (3) Needs refactoring. It needs to contemplate any number of cities (the main goal here is not the Travel Salesman)
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
			totalDistance += this.map.getDistance(this.route[i], this.route[i+1]);
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

	public int getTotalDistance() {
		return this.totalDistance;
	}

	public static void setGoal(int newGoal) {
		TravellingSalesman.goal = newGoal;
	}

	public boolean checkRoute() {
		if (!this.routeStartsAndEndsAtCityA()) return false;
		if (!this.routeHasCityAOnlyOnTheEdges()) return false;
		if (!this.routeHasNoRepeatedCities()) return false;
		if (!this.routeVisitedAllCities()) return false;
		return true;
	}
	
	private boolean routeStartsAndEndsAtCityA() {
		if (this.route[0] != "A" || this.route[this.route.length-1] != "A") {
			return false;
		} else return true;
	}
	
	private boolean routeHasCityAOnlyOnTheEdges() {
		for (int i = 1; i < this.route.length - 1; i++) {
			if (this.route[i] == "A") {
				return false;
			}
		}
		return true;
	}
	
	private boolean routeHasNoRepeatedCities() {
		for (int i = 1; i < this.route.length - 1; i++) {
			for (int j = 1; j < this.route.length - 1; j++) {
				if (i != j) {
					if (this.route[i] == this.route[j]) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean routeVisitedAllCities() {
		ArrayList<String> cities = this.map.getCities();
		Boolean visited;
		for (String cityToVisit : cities) {
			visited = false;
			for (int i = 0; i < this.route.length; i++) {
				visited = visited || (this.route[i] == cityToVisit);
			}
			if (!visited) {
				return false;
			}
		}
		return true;
	}
	
	public void setMap(TravellingSalesmanMap newMap) {
		this.map = newMap;		
	}

}
