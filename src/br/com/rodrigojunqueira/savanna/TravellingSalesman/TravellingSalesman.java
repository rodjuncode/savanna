package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import br.com.rodrigojunqueira.savanna.core.Dna;

/**
 * 
 * @author Rodrigo Junqueira
 * This is the implementation for the Travelling Salesman problem so the
 * Savanna Genetic Algorithm Stack can solve it. The Travelling Salesman 
 * problem tries to, given a map of cities and the distances between each
 * other, define the shorter route that visits every single city.
 * Each instance of the TravellingSalesman represents a possible solution
 * for the problem, hence a route.
 */
public class TravellingSalesman implements Dna {

	private String[] route;					// Holds the proposed solution for the problem. Each route is an array of Strings, each position representing a city. Each route needs to start and end on the "A" city.
	private int totalDistance;				// The total distance of the given route.
	private static int goal;				// The maximum distance to consider a solution good enough.
	private TravellingSalesmanMap map;		// The map used to evaluate the route.
	private HashMap<String, Integer> moves;	// Another way of representing the solution. It keeps the moves to achieve the given route. So on a route {A, D, E ... A}, we would have AD, DE, etc on this HashMap.

	/**
	 * Constructs a new possible solution for the Travelling Salesman problem, with a map to evaluate the route.
	 * @param newMap the map used to evaluate the route.
	 */
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

	/**
	 * Copies the route of another solution to the current solution.
	 * @param travelToCopyFrom the solution to copy the route from.
	 */
	private void copyRoute(TravellingSalesman travelToCopyFrom) {
		this.route = new String[travelToCopyFrom.route.length];
		System.arraycopy(travelToCopyFrom.route, 0, this.route, 0, travelToCopyFrom.route.length);
		this.updateMoves();
		this.evaluate();
	}

	public Dna crossover(Dna dna) {
		TravellingSalesman travelToCrossoverWith = (TravellingSalesman) dna;
		TravellingSalesman newTravel = null;
		/* Each interaction of the crossover process will try to find a low cost move 
		 * in the current route that doesn't exist on the second route, and apply it
		 * in the later. Right now it tries for a number of times equals to one
		 * third of the total of cities in the current route.
		 */
		int numberOfInteractions = Math.round(this.route.length/3);
		for (int i = 0; i < numberOfInteractions; i++) {
			newTravel = new TravellingSalesman(this.map);
			// Check if at this point both solutions are the same
			if (!this.sameRoute(travelToCrossoverWith)) { 
				String moveToSwap = this.getShortestMoveMissingOn(travelToCrossoverWith);	// Finds the shortest move on the current route that doesn't exist on the second route. 
				newTravel.setRoute(travelToCrossoverWith.getRouteSwapingMoves(moveToSwap)); // Creates a new route, including the move found in the last instruction.
			} else {
				// If the routes are the same at this point, there is no crossover interaction and the proccess may end. 
				newTravel.copyRoute(travelToCrossoverWith);
				i = numberOfInteractions;
			}
			travelToCrossoverWith = newTravel;
		}
		return newTravel;
	}	

	public void evaluate() {
		/*
		 * Sums all the distances of this route.
		 */
		int totalDistance = 0;
		for (int i = 0; i < (this.route.length - 1); i++) {
			totalDistance += this.map.getDistance(this.route[i], this.route[i+1]);
		}
		this.totalDistance = totalDistance;		
	}

	/**
	 * Returns a new route, built from the route of the current solution but 
	 * replacing the specified move and making the necessary corrections to keep
	 * the route valid. 
	 * @param newMove the move to be inserted in the current solution route.
	 * @return a new route containing the specified move.
	 */
	public String[] getRouteSwapingMoves(String newMove) {
		String startCity = Character.toString(newMove.charAt(0));			// the city from where the new move starts.	
		String endCity = Character.toString(newMove.charAt(1));				// the city to where the new move goes to.
		String[] newRoute = new String[this.route.length];
		System.arraycopy(this.route, 0, newRoute, 0, this.route.length);	// copies the current route so it can be modified.
		int startCityIndex, endCityIndex;
		startCityIndex = endCityIndex = -1;									
		for (int i = 0; i < newRoute.length; i++) {
			/* Both cities on the new move will be searched in the current route and 
			 * their positions stored. They will be found only once.
			 */
			if (newRoute[i].equals(startCity) && startCityIndex == -1) { startCityIndex = i; }		
			if (newRoute[i].equals(endCity) && endCityIndex == -1) { endCityIndex = i; }			
		}
		if (endCityIndex == 0) { 
			String oldStartCity = newRoute[newRoute.length-2];
			newRoute[newRoute.length-2] = startCity;
			newRoute[startCityIndex] = oldStartCity;
		} else if (startCityIndex + 1 == newRoute.length - 1) { 
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
		double mutationChance = Math.random();
		if (mutationChance < 0.25) {
			//int n = 2;
			//for (int i = n; i > 0; i--) {
			//	if (Math.random() < i*0.5) {
			//		for (int j = n - i; j < n; j++) {
						int randomPositionFrom = 1 + (int)(Math.random() * ((Math.round(this.route.length/2) - 2) + 1));
						int randomPositionTo = randomPositionFrom + Math.round(this.route.length/2) - 1;
						String aux = this.route[randomPositionFrom];
						this.route[randomPositionFrom] = this.route[randomPositionTo];
						this.route[randomPositionTo] = aux;
			//		}
			//	}
			//}
		} else if (mutationChance < 0.9){
			List<String> reverseRoute = Arrays.asList(this.route);
			Collections.reverse(reverseRoute);
			this.route = (String[]) reverseRoute.toArray();
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
		for (int i = 0; i < this.route.length; i++) {
			System.out.print(this.route[i] + " - ");
		}
		System.out.println(this.getTotalDistance());
		// System.out.println(this.getTotalDistance() + ", Validated: " + this.validate());
	}

	private void updateMoves() {
		this.moves.clear();
		for (int i = 0; i < this.route.length - 1; i++) {
			this.moves.put(this.route[i].concat(this.route[i+1]), this.map.getDistance(this.route[i], this.route[i+1]));
		}
		
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
