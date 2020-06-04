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
	
	/**
	 * Compare two routes to check if they have the same total distance.
	 * @param defiant the route to compared with.
	 * @return true if the two routes have the same total distance, false otherwise.
	 */
	public boolean asFitAs(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() == d.getTotalDistance()) {
			return true;
		} else return false;
	}

	/**
	 * Copies the route from the specified solution to the current solution.
	 * @param travelToCopyFrom the solution to copy the route from.
	 */
	private void copyRoute(TravellingSalesman travelToCopyFrom) {
		this.route = new String[travelToCopyFrom.route.length];
		System.arraycopy(travelToCopyFrom.route, 0, this.route, 0, travelToCopyFrom.route.length);
		this.updateMoves();
		this.evaluate();
	}

	/**
	 * To performe the crossover operation the current solution will try to insert its shortest moves into a new route.
	 * @param the solution to be combined with.
	 * @return the new solution
	 */
	public Dna crossover(Dna dna) {
		TravellingSalesman travelToCrossoverWith = (TravellingSalesman) dna;
		TravellingSalesman newTravel = null;
		/* Each interaction of the crossover process will try to find the lowest cost 
		 * move in the current route that doesn't exist on the second route, and apply 
		 * it in the later. Right now it tries for a number of times equals to one
		 * third of the total of cities in the current route.
		 */
		int numberOfInteractions = Math.round(this.route.length/4); // one third of cities of the current route
		for (int i = 0; i < numberOfInteractions; i++) {
			newTravel = new TravellingSalesman(this.map);
			// Check if at this point both solutions are the same
			if (!this.sameRoute(travelToCrossoverWith)) { 
				String moveToSwap = this.getShortestMoveMissingOn(travelToCrossoverWith);	// Finds the shortest move on the current route that doesn't exist on the second route. 
				newTravel.setRoute(travelToCrossoverWith.getRouteSwapingMoves(moveToSwap)); // Creates a new route, including the move found in the last instruction.
			} else {
				// If the routes are the same at this point, there is no more crossover interaction and the loop may end. 
				newTravel.copyRoute(travelToCrossoverWith);
				i = numberOfInteractions;
			}
			travelToCrossoverWith = newTravel;
		}
		return newTravel;
	}	

	/**
	 * Calculates the total distance for the current route.
	 */
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
	 * replacing the specified new move and making the necessary corrections to keep
	 * the route valid. 
	 * @param newMove the move to be inserted in the current solution route.
	 * @return a new route containing the specified move and the needed corrections in order to keep it valid.
	 */
	public String[] getRouteSwapingMoves(String newMove) {
		String startCity = Character.toString(newMove.charAt(0));			// the city where the new move starts from.	
		String endCity = Character.toString(newMove.charAt(1));				// the city where the new move goes to.
		String[] newRoute = new String[this.route.length];
		System.arraycopy(this.route, 0, newRoute, 0, this.route.length);	// copies the current route. This copy will be modified.
		int startCityIndex, endCityIndex;
		startCityIndex = endCityIndex = -1;									
		for (int i = 0; i < newRoute.length; i++) {
			/* This loop searches for the positions of both start and end cities in the current route.
			 * If city "A" is one of the two cities, it makes sure it will find it only on the first time (the route array has the A city two times, always, on the very first and last position).
			 */
			if (newRoute[i].equals(startCity) && startCityIndex == -1) { startCityIndex = i; }		
			if (newRoute[i].equals(endCity) && endCityIndex == -1) { endCityIndex = i; }			
		}
		/*
		 * There are basically four cases to deal with when trying to insert a new move into a route. 
		 * One of them is a general rule, the most common case, and the other three are the exceptions.
		 * We test first if the current case fits into one of the exceptions and if not, we apply the 
		 * general rule.
		 */
		if (endCityIndex == 0) { 
			/*
			 * Exception 1:
			 * When the end city in the new move is the "A" city, we need to work only with the last but one city in the route.
			 * We need to put the start city of the new move just before the last "A" city in the route, and the city that was
			 * previously in that position needs to be moved to the previous position of the start city. Example:
			 * Inserting the T->A move (start city = T, end city = A) in the following route 
			 * [A, L, B, T, C, F, Q, R, O, M, J, S, I, N, P, K, G, E, H, D, A], gives us the route
			 * [A, C, Q, M, J, L, I, P, T, F, B, E, D, O, H, R, G, S, N, T, A] 
			 */
			String oldStartCity = newRoute[newRoute.length-2];
			newRoute[newRoute.length-2] = startCity;
			newRoute[startCityIndex] = oldStartCity;
		} else if (startCityIndex + 1 == newRoute.length - 1) { 
			/*
			 * The next two exceptions are very similar.
			 * Both have the following condition in common: if the start city of the new move is just before the "A" city in the current route.
			 */
			if (endCityIndex == 1) { 
				/*
				 * Exception 2:
				 * If the start city in the new move is just before the "A" city in the current route, but also the 
				 * end city in the new move is just after the "A" city in the current route, we will switch the order of the
				 * new move and replace as the last but one move in the route. Example:
				 * Inserting the E->D move (start city = E, end city = D) in the following route:
				 * [A, D, T, B, C, F, K, S, O, N, R, J, I, H, P, M, Q, L, G, E, A], and switching the new move to D->E, give us the route
				 * [A, G, T, B, C, F, K, S, O, N, R, J, I, H, P, M, Q, L, D, E, A]
				 */
				String oldEndCity = newRoute[startCityIndex-1];
				newRoute[startCityIndex-1] = endCity;
				newRoute[endCityIndex] = oldEndCity;
			} else { 
				/*
				 * Exception 3:
				 * If the start city in the new move is just before the "A" city in the current route, and the 
				 * end city in the new move IS NOT just after the "A" city in the current route, we need to insert
				 * the start city of the new move just before the end city of the new move, in the current route, 
				 * switching its position with the previous city on that index. Example:
				 * Inserting the T->B (start city = T, end city = B) move in the following route:
				 * [A, G, C, N, H, B, R, M, Q, P, F, O, L, E, S, I, D, J, K, T, A], gives us the route
				 * [A, G, C, N, T, B, R, M, Q, P, F, O, L, E, S, I, D, J, K, H, A]
				 */
				String oldStartCity = newRoute[endCityIndex-1]; 
				newRoute[endCityIndex-1] = startCity;
				newRoute[startCityIndex] = oldStartCity;			
			}
		} else {
			/*
			 * General case:
			 * In the general case we switch positions between the end city of the new move, with the city just after
			 * the star city in the new move, in the current route. Example:
			 * Inserting the B->L move (start city = B, end city = L) into the following route:
			 * [A, D, I, S, O, R, T, C, K, F, L, E, P, J, N, M, B, Q, G, H, A], gives us the route
			 * [A, D, I, S, O, R, T, C, K, F, Q, E, P, J, N, M, B, L, G, H, A]
			 */
			String oldEndCity = newRoute[startCityIndex+1];
			newRoute[startCityIndex+1] = endCity;
			newRoute[endCityIndex] = oldEndCity;
		}
		return newRoute;
	}

	/**
	 * Returns the shortest move on the current solution that doesn't exist in the second solution.
	 * @param travel the solution where the shortest move shouldn't be contained.
	 * @return the shortest move
	 */
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

	/**
	 * The sum of all the distances for the given solution.
	 * @return the total distance.
	 */
	public int getTotalDistance() {
		return this.totalDistance;
	}

	/**
	 * Checks if the current route has the specified move.
	 * @param cityA the start city of the wanted move.
	 * @param cityB the end city of the wanted move.
	 * @return true if the route has the wanted move, false otherwise.
	 */
	public boolean hasMove(String cityA, String cityB) {
		if (this.moves.containsKey(cityA.concat(cityB))) return true;
		else return false;
	}

	/**
	 * Checks if the current route is good enough.
	 * @return true if the total distance is shorter or equal to the goal, false otherwise.
	 */
	public boolean isGoodEnough() {
		if (this.getTotalDistance() <= TravellingSalesman.goal) {
			return true;
		} else return false;
	}
	
	/**
	 * Checks if the current solution has a longer route than the specified solution.
	 * @param the solution to compare with.
	 * @return true if the current solution has a longer route, false otherwise.
	 */
	public boolean lessFitThan(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() > d.getTotalDistance()) {
			return true;
		} else return false;
	}

	/**
	 * Checks if the current solution has a shorter route than the specified solution.
	 * @param the solution to compare with.
	 * @return true if the current solution has a shorter route, false otherwise.
	 */
	public boolean moreFitThan(Dna defiant) {
		TravellingSalesman d = (TravellingSalesman) defiant;
		if (this.getTotalDistance() < d.getTotalDistance()) {
			return true;
		} else return false;
	}
	
	/**
	 * To mutate itself, the current solution:
	 * (A) has a 10% chance of nothing happening;
	 * (B) has a 25% chance of switching one city from the first half of the route with a city of the second half, just one time;
	 * (C) has a 65% chance of inverting all the positions on the route.
	 */
	public void mutate() {
		double mutationChance = Math.random();
		if (mutationChance < 0.25) { // (B)
			int randomPositionFrom = 1 + (int)(Math.random() * ((Math.round(this.route.length/2) - 2) + 1));
			int randomPositionTo = randomPositionFrom + Math.round(this.route.length/2) - 1;
			String aux = this.route[randomPositionFrom];
			this.route[randomPositionFrom] = this.route[randomPositionTo];
			this.route[randomPositionTo] = aux;
		} else if (mutationChance < 0.9){ // (C)
			List<String> reverseRoute = Arrays.asList(this.route);
			Collections.reverse(reverseRoute);
			this.route = (String[]) reverseRoute.toArray();
		}
		this.updateMoves(); // Updates the moves list.
	}

	/**
	 * Checks if city "A" is only in the edges of the route.
	 * @return true if city "A" is not in the middle of the route, false otherwise.
	 */
	private boolean routeHasCityAOnlyOnTheEdges() {
		for (int i = 1; i < this.route.length - 1; i++) {
			if (this.route[i].equals("A")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the city has no repeated cities.
	 * @return true if the route has no repeated cities, false otherwise.
	 */
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

	/**
	 * Check if the city "A" is on both edges of the route.
	 * @return true if the route starts and ends on city "A".
	 */
	private boolean routeStartsAndEndsAtCityA() {
		if (!this.route[0].equals("A") || !this.route[this.route.length-1].equals("A")) {
			return false;
		} else return true;
	}
	
	/**
	 * Checks if the current route visited all the cities in the map.
	 * @return true if the route visited all cities, false otherwise.
	 */
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

	/**
	 * Compare two solutions and check if they have the same route.
	 * @param travelToCompareRoutes the solution to compare to.
	 * @return true if both solutions have the same route, false otherwise.
	 */
	private boolean sameRoute(TravellingSalesman travelToCompareRoutes) {
		boolean sameRoute = true;
		for (int i = 0; i < this.route.length; i++) {
			if (!this.route[i].equals(travelToCompareRoutes.route[i])) {
				sameRoute = false;
			}
		}
		return sameRoute;
	}
	
	/**
	 * Sets the map for the current solution.
	 * @param newMap the new map for the current solution.
	 */
	public void setMap(TravellingSalesmanMap newMap) {
		this.map = newMap;		
	}
	
	/**
	 * Sets the route for this solution.
	 * @param newRoute the new route for this solution.
	 */
	public void setRoute(String[] newRoute) {
		this.route = newRoute;
		this.updateMoves();
	}
	
	/**
	 * Finds the shortest move in the current route.
	 * @return a string with two cities, representing the shortest move in the current route. Ex.: BD.
	 */
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

	/**
	 * Shows the current route, presenting the order of the cities visited, and its total distance.
	 */
	public void show() {
		for (int i = 0; i < this.route.length; i++) {
			System.out.print(this.route[i] + " - ");
		}
		System.out.println(this.getTotalDistance());
		// System.out.println(this.getTotalDistance() + ", Validated: " + this.validate());
	}

	/**
	 * Re-builds the list of moves based on the current route.
	 */
	private void updateMoves() {
		this.moves.clear();
		for (int i = 0; i < this.route.length - 1; i++) {
			this.moves.put(this.route[i].concat(this.route[i+1]), this.map.getDistance(this.route[i], this.route[i+1]));
		}
		
	}

	/**
	 * Makes sure the current route is a valid one.
	 * @return true if the route is valid, false otherwise.
	 */
	public boolean validate() {
		if (!this.routeStartsAndEndsAtCityA()) return false;
		if (!this.routeHasCityAOnlyOnTheEdges()) return false;
		if (!this.routeHasNoRepeatedCities()) return false;
		if (!this.routeVisitedAllCities()) return false;
		return true;
	}
	
	/**
	 * Sets the maximum distance to consider the solution good enough.
	 * @param newGoal the maximum distance under which a route is considered good enough.
	 */
	public static void setGoal(int newGoal) {
		TravellingSalesman.goal = newGoal;
	}
	
	/**
	 * Returns the shortest move contained in the list of moves.
	 * @param copyOfMoves the list of moves to be examined.
	 * @return a string representing the shortest move found. Ex.: FB.
	 */
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
