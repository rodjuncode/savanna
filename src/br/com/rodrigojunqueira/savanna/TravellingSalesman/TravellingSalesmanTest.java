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
		return map;
	}
	
	@Test
	public void evaluate() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "A"});
		travel.evaluate();
		assertEquals(10, travel.getTotalDistance());
	}
	
	@Test
	public void crossover() {
		TravellingSalesman travel1 = new TravellingSalesman(this.map());
		travel1.setRoute(new String[]{"A", "D", "C", "B", "A"});
		TravellingSalesman travel2 = new TravellingSalesman(this.map());
		travel2.setRoute(new String[]{"A", "C", "B", "D", "A"});
		TravellingSalesman travel3 = (TravellingSalesman) travel1.crossover(travel2);
		travel3.evaluate();
		assertEquals(12, travel3.getTotalDistance());		
	}

	//@Test
	public void mutate() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "A"}); // 9
		travel.mutate();
		travel.evaluate();
		assertEquals(9, travel.getTotalDistance());
	}
	
	//@Test
	public void comparingFitness() {
		TravellingSalesman travel1 = new TravellingSalesman(this.map());
		travel1.setRoute(new String[]{"A", "D", "C", "B", "A"}); // 9
		travel1.evaluate();
		TravellingSalesman travel2 = new TravellingSalesman(this.map());
		travel2.setRoute(new String[]{"A", "C", "B", "D", "A"}); // 12
		travel2.evaluate();
		assertEquals(true, travel1.moreFitThan(travel2));
		assertEquals(true, travel2.lessFitThan(travel1));
		assertEquals(true, travel1.asFitAs(travel1));		
	}	
	
	@Test
	public void checkingForTermination() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		TravellingSalesman.setGoal(4);
		travel.setRoute(new String[]{"A", "C", "D", "B", "A"}); // 4
		travel.evaluate();
		assertEquals(true, travel.isGoodEnough());
	}	
	
	@Test
	public void checkRoute() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "F", "E", "G", "A"}); 	// Valid. Starts and ends at A, no repeated cities and all cities visited.
		assertEquals("After valid route", true, travel.checkRoute());
		travel.setRoute(new String[]{"A", "D", "C", "B"}); 						// Invalid. Ends at B.
		assertEquals("After invalid route, ending on city B", false, travel.checkRoute());		
		travel.setRoute(new String[]{"A", "D", "C", "B", "G", "E", "G", "A"});	// Invalid. Repeated cities.
		assertEquals("After invalid route, with repeated cities", false, travel.checkRoute());	
		travel.setRoute(new String[]{"A", "D", "C", "B", "A"}); 				// Invalid. Not all cities visited.
		assertEquals("After invalid route, not all cities visited", false, travel.checkRoute());		
	
	}
	 
	
}
