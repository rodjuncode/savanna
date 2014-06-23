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
		this.moves = new HashMap<String, Integer>();
	}
	
	public boolean asFitAs(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() == d.getTotalDistance()) {
			return true;
		} else return false;
	}

	private void copyRoute(TravellingSalesman travelToCopyFrom) {
		this.route = new String[travelToCopyFrom.route.length];
		System.arraycopy(travelToCopyFrom.route, 0, this.route, 0, travelToCopyFrom.route.length);
	}

	public Dna crossover(Dna dna) {
		TravellingSalesman travelToCrossoverWith = (TravellingSalesman) dna;
		TravellingSalesman newTravel = new TravellingSalesman(this.map);
		if (!this.sameRoute(travelToCrossoverWith)) {
			String moveToSwap = this.getShortestMoveMissingOn(travelToCrossoverWith);
			newTravel.setRoute(travelToCrossoverWith.getRouteSwapingMoves(moveToSwap));
		} else {
			newTravel.copyRoute(travelToCrossoverWith);
		}
		return newTravel;
	}	

	public void evaluate() {
		int totalDistance = 0;
		for (int i = 0; i < (this.route.length - 1); i++) {
			totalDistance += this.map.getDistance(this.route[i], this.route[i+1]);
		}
		this.totalDistance = totalDistance;		
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

	public int getTotalDistance() {
		return this.totalDistance;
	}

	public boolean hasMove(String cityA, String cityB) {
		if (this.moves.containsKey(cityA.concat(cityB))) return true;
		else return false;
	}

	public boolean isGoodEnough() {
		if (this.getTotalDistance() <= TravellingSalesman.goal) {
			return true;
		} else return false;
	}
	
	public boolean lessFitThan(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() > d.getTotalDistance()) {
			return true;
		} else return false;
	}
	
	public boolean moreFitThan(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() < d.getTotalDistance()) {
			return true;
		} else return false;
	}
	
	public void mutate() {
		for (int i = 4; i > 0; i--) {
			if (Math.random() < i*0.15) { // 60% - 45% - 30% - 15% 
				int randomPositionFrom = 1 + (int)(Math.random() * ((Math.round(this.route.length/2) - 2) + 1));
				int randomPositionTo = randomPositionFrom + Math.round(this.route.length/2) - 1;
				String aux = this.route[randomPositionFrom];
				this.route[randomPositionFrom] = this.route[randomPositionTo];
				this.route[randomPositionTo] = aux;
			}
		}
		this.evaluate();
		this.updateMoves();
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

	private boolean routeStartsAndEndsAtCityA() {
		if (!this.route[0].equals("A") || !this.route[this.route.length-1].equals("A")) {
			return false;
		} else return true;
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

	private boolean sameRoute(TravellingSalesman travelToCompareRoutes) {
		boolean sameRoute = true;
		for (int i = 0; i < this.route.length; i++) {
			if (!this.route[i].equals(travelToCompareRoutes.route[i])) {
				sameRoute = false;
			}
		}
		return sameRoute;
		//return Arrays.equals(this.route, travelToCompareRoutes.route);
	}
	
	public void setMap(TravellingSalesmanMap newMap) {
		this.map = newMap;		
	}
	
	public void setRoute(String[] newRoute) {
		this.route = newRoute;
		this.updateMoves();
	}
	
	private void updateMoves() {
		this.moves.clear();
		for (int i = 0; i < this.route.length - 1; i++) {
			this.moves.put(this.route[i].concat(this.route[i+1]), this.map.getDistance(this.route[i], this.route[i+1]));
		}
		
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

	public void show() {
		System.out.println(this.getTotalDistance() + " - " + this.route[0] + ", " + this.route[1] + ", " + this.route[2] + ", " + this.route[3] + ", " + this.route[4] + ", " + this.route[5] + ", " + this.route[6] + ", " + this.route[7]);
	}

	public boolean validate() {
		if (!this.routeStartsAndEndsAtCityA()) return false;
		if (!this.routeHasCityAOnlyOnTheEdges()) return false;
		if (!this.routeHasNoRepeatedCities()) return false;
		if (!this.routeVisitedAllCities()) return false;
		return true;
	}
	
	public static void setGoal(int newGoal) {
		TravellingSalesman.goal = newGoal;
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
	
}
