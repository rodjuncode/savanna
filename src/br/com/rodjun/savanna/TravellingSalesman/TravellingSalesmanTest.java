package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import static org.junit.Assert.*;

import org.junit.Test;

public class TravellingSalesmanTest {
	
	private TravellingSalesmanMap map() {
		TravellingSalesmanMap map = new TravellingSalesmanMap();
		map.add("A");
		map.add("B");
		map.add("C");
		map.add("D");
		map.add("E");
		map.add("F");	
		map.add("G");
		map.setAllDistancesFor("A", new int[]{0, 1, 2, 4, 5, 8, 1});
		map.setAllDistancesFor("B", new int[]{1, 0, 1, 3, 8, 2, 5});		
		map.setAllDistancesFor("C", new int[]{2, 1, 0, 1, 2, 2, 1});
		map.setAllDistancesFor("D", new int[]{4, 3, 1, 0, 1, 4, 4});
		map.setAllDistancesFor("E", new int[]{5, 8, 2, 1, 0, 1, 2});
		map.setAllDistancesFor("F", new int[]{8, 2, 2, 4, 1, 0, 1});
		map.setAllDistancesFor("G", new int[]{1, 5, 1, 4, 2, 1, 0});
		return map;
	}
	
	@Test
	public void evaluate() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "E", "F", "G", "A"}); // 17
		travel.evaluate();
		assertEquals(17, travel.getTotalDistance());
	}
	
	//@Test
	public void crossover() {
		TravellingSalesman travel1 = new TravellingSalesman(this.map());
		travel1.setRoute(new String[]{"A", "C", "G", "B", "F", "D", "E", "A"});
		TravellingSalesman travel2 = new TravellingSalesman(this.map());
		travel2.setRoute(new String[]{"A", "B", "E", "C", "G", "D", "F", "A"});
		TravellingSalesman travel3 = (TravellingSalesman) travel1.crossover(travel2);
		travel3.evaluate();
		assertEquals(16, travel3.getTotalDistance());		
	}

	// @Test
	// We might want to split this in two methods: mutateMore e mutateLess
	public void mutate() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "G", "C", "D", "E", "F", "B", "A"}); 
		travel.mutate();
		travel.evaluate();
		assertEquals(7, travel.getTotalDistance());
	}
	
	@Test
	public void comparingFitness() {
		TravellingSalesman travel1 = new TravellingSalesman(this.map());
		travel1.setRoute(new String[]{"A", "B", "C", "D", "E", "F", "G", "A"}); // 7
		travel1.evaluate();
		TravellingSalesman travel2 = new TravellingSalesman(this.map());
		travel2.setRoute(new String[]{"A", "D", "C", "B", "E", "F", "G", "A"}); // 17
		travel2.evaluate();
		assertEquals(true, travel1.moreFitThan(travel2));
		assertEquals(true, travel2.lessFitThan(travel1));
		assertEquals(true, travel1.asFitAs(travel1));		
	}	
	
	@Test
	public void checkingForTermination() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		TravellingSalesman.setGoal(7);
		travel.setRoute(new String[]{"A", "B", "C", "D", "E", "F", "G", "A"}); 
		travel.evaluate();
		assertEquals(true, travel.isGoodEnough());
	}	
	
	@Test
	public void checkRoute() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "F", "E", "G", "A"}); 	// Valid. Starts and ends at A, no repeated cities and all cities visited.
		assertEquals("After valid route", true, travel.validate());
		travel.setRoute(new String[]{"A", "D", "C", "B"}); 						// Invalid. Ends at B.
		assertEquals("After invalid route, ending on city B", false, travel.validate());		
		travel.setRoute(new String[]{"A", "D", "C", "B", "G", "E", "G", "A"});	// Invalid. Repeated cities.
		assertEquals("After invalid route, with repeated cities", false, travel.validate());	
		travel.setRoute(new String[]{"A", "D", "C", "B", "A"}); 				// Invalid. Not all cities visited.
		assertEquals("After invalid route, not all cities visited", false, travel.validate());		
	}
	
	@Test
	public void checkIfRouteHasCertainMove() {
		TravellingSalesman travel =  new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "F", "E", "G", "A"});
		assertEquals(true, travel.hasMove("G", "A"));
	}

	@Test
	public void shortestMove() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "B", "C", "E", "G", "F", "A"});
		assertEquals("BC", travel.shortestMove());
	}
	
	@Test
	public void findingShortestMoveOnAMissingOnB() {
		TravellingSalesman travelA = new TravellingSalesman(this.map());
		travelA.setRoute(new String[]{"A", "C", "G", "B", "F", "D", "E", "A"});
		TravellingSalesman travelB = new TravellingSalesman(this.map());
		travelB.setRoute(new String[]{"A", "B", "E", "C", "G", "D", "F", "A"});
		assertEquals("DE", travelA.getShortestMoveMissingOn(travelB));
		//travelA.setRoute(new String[]{"A", "G", "F", "C", "E", "D", "B", "A"});
		//travelB.setRoute(new String[]{"A", "D", "B", "F", "C", "E", "G", "A"});
		//assertEquals("AG", travelA.getShortestMoveMissingOn(travelB));
	}
	
	@Test
	public void swapingMoves() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		//travel.setRoute(new String[]{"A", "B", "E", "C", "G", "D", "F", "A"});
		//assertEquals(new String[]{"A", "B", "F", "C", "G", "D", "E", "A"}, travel.getRouteSwapingMoves("DE"));
		//travel.setRoute(new String[]{"A", "B", "E", "C", "G", "D", "F", "A"});
		//assertEquals(new String[]{"A", "B", "E", "F", "G", "D", "C", "A"}, travel.getRouteSwapingMoves("FG"));
		//travel.setRoute(new String[]{"A", "B", "E", "C", "G", "D", "F", "A"});
		//assertEquals(new String[]{"A", "C", "E", "B", "G", "D", "F", "A"}, travel.getRouteSwapingMoves("AC"));
		//travel.setRoute(new String[]{"A", "B", "E", "C", "G", "D", "F", "A"});
		//assertEquals(new String[]{"A", "B", "F", "C", "G", "D", "E", "A"}, travel.getRouteSwapingMoves("BF"));
		//travel.setRoute(new String[]{"A", "B", "E", "C", "G", "D", "F", "A"});
		//assertEquals(new String[]{"A", "B", "E", "C", "G", "F", "D", "A"}, travel.getRouteSwapingMoves("DA"));		
		travel.setRoute(new String[]{"A", "D", "C", "B", "F", "G", "E", "A"});
		assertEquals(new String[]{"A", "G", "C", "B", "F", "D", "E", "A"}, travel.getRouteSwapingMoves("AG"));		
		
	}
	
}
