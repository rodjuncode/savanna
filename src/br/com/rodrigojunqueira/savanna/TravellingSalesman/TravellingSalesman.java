package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import br.com.rodrigojunqueira.savanna.core.Dna;

public class TravellingSalesman implements Dna {

	private String[] route;
	private int totalDistance;
	private static int goal;
	private TravellingSalesmanMap map;
	private HashMap<String, Integer> moves;
	
	public TravellingSalesman(TravellingSalesmanMap newMap) {
		this.map = newMap;
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
		this.moves = new HashMap<String, Integer>();
		for (int i = 0; i < this.route.length - 1; i++) {
			this.moves.put(this.route[i].concat(this.route[i+1]), this.map.getDistance(this.route[i], this.route[i+1]));
		}
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
		if (!this.route[0].equals("A") || !this.route[this.route.length-1].equals("A")) {
			return false;
		} else return true;
	}
	
	private boolean routeHasCityAOnlyOnTheEdges() {
		for (int i = 1; i < this.route.length - 1; i++) {
			if (this.route[i].equals("A")) {
				return false;
			}
		}
		return true;
	}
	
	private boolean routeHasNoRepeatedCities() {
		for (int i = 1; i < this.route.length - 1; i++) {
			for (int j = 1; j < this.route.length - 1; j++) {
				if (i != j) {
					if (this.route[i].equals(this.route[j])) {
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
				visited = visited || (this.route[i].equals(cityToVisit));
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

	public boolean hasMove(String cityA, String cityB) {
		if (this.moves.containsKey(cityA.concat(cityB))) return true;
		else return false;
	}
	
	public void show() {
		System.out.println(this.getTotalDistance() + " - " + this.route[0] + ", " + this.route[1] + ", " + this.route[2] + ", " + this.route[3] + ", " + this.route[4] + ", " + this.route[5] + ", " + this.route[6] + ", " + this.route[7]);
	}

	public String shortestMove() {
		String shortestMove = new String();
		int shortestMoveCost = Collections.max(this.moves.values());
		for (Entry<String, Integer> move : this.moves.entrySet()) {
			if (move.getValue() <= shortestMoveCost) {
				shortestMove = move.getKey();
				shortestMoveCost = move.getValue();
			}
		}
		return shortestMove;
	}
	
	public static String shortestMoveOn(HashMap<String, Integer> copyOfMoves) {
		String shortestMove = new String();
		int shortestMoveCost = Collections.max(copyOfMoves.values());
		for (Entry<String, Integer> move : copyOfMoves.entrySet()) {
			if (move.getValue() <= shortestMoveCost) {
				shortestMove = move.getKey();
				shortestMoveCost = move.getValue();
			}
		}
		return shortestMove;
	}
	
	public Dna crossover(Dna dna) {
		TravellingSalesman travelToCrossoverWith = (TravellingSalesman) dna;
		String moveToSwap = this.getShortestMoveMissingOn(travelToCrossoverWith);
		TravellingSalesman newTravel = new TravellingSalesman(this.map);
		newTravel.setRoute(travelToCrossoverWith.getRouteSwapingMoves(moveToSwap));
		return newTravel;
	}
	
	public String getShortestMoveMissingOn(TravellingSalesman travel) {
		String shortestMove = new String();
		HashMap<String, Integer> copyOfMyMoves = new HashMap<String, Integer>(this.moves);
		boolean moveNotFound = true;
		while (moveNotFound && !copyOfMyMoves.isEmpty()) {
			String shortMove = TravellingSalesman.shortestMoveOn(copyOfMyMoves);
			if (travel.moves.containsKey(shortMove)) {
				 copyOfMyMoves.remove(shortMove);
			} else {
				shortestMove = shortMove;
				moveNotFound = false;
			}
		}
		return shortestMove;
	}
	
	public void mutate() {
		if (Math.random() > 0.5) {
			// (3) Needs refactoring. It needs to contemplate any number of cities
			this.setRoute(new String[]{
					this.route[0], 
					this.route[4], 
					this.route[1], 
					this.route[3], 
					this.route[6],
					this.route[5],											
					this.route[2],
					this.route[7]
			});
		} else {
			this.setRoute(new String[]{
					this.route[0], 
					this.route[6], 
					this.route[2], 
					this.route[3], 
					this.route[5],
					this.route[4],											
					this.route[1],
					this.route[7]
			});			
		}
	}
	
	public String[] getRouteSwapingMoves(String newMove) {
		String startCity = Character.toString(newMove.charAt(0));	
		String endCity = Character.toString(newMove.charAt(1));		
		String[] newRoute = new String[this.route.length];
		System.arraycopy(this.route, 0, newRoute, 0, this.route.length);
		int startCityIndex, endCityIndex;
		startCityIndex = endCityIndex = -1;
		for (int i = 0; i < newRoute.length; i++) {
			if (newRoute[i].equals(startCity) && startCityIndex == -1) { startCityIndex = i; }		
			if (newRoute[i].equals(endCity) && endCityIndex == -1) { endCityIndex = i; }			
		}
		if (endCityIndex == 0) {
			String oldStartCity = newRoute[newRoute.length-2];
			newRoute[newRoute.length-2] = startCity;
			newRoute[startCityIndex] = oldStartCity;
		} else if (startCityIndex + 1 == newRoute.length - 1) { // problem is here
			if (endCityIndex == 1) {
				String oldEndCity = newRoute[startCityIndex-1];
				newRoute[startCityIndex-1] = endCity;
				newRoute[endCityIndex] = oldEndCity;
			} else {
				String oldStartCity = newRoute[endCityIndex-1]; 
				newRoute[endCityIndex-1] = startCity;
				newRoute[startCityIndex] = oldStartCity;
			}
		} else {
			String oldEndCity = newRoute[startCityIndex+1];
			newRoute[startCityIndex+1] = endCity;
			newRoute[endCityIndex] = oldEndCity;
		}
		return newRoute;
	}		
	
}
