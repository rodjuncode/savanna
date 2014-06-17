package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import br.com.rodrigojunqueira.savanna.MinusXSquared.MinusXSquared;

public class TravellingSalesmanTest {

	private HashMap<String, HashMap<String, Integer>> map() {

		HashMap<String, HashMap<String, Integer>> testMap = new HashMap<String, HashMap<String, Integer>>();
		
		HashMap<String, Integer> fromA = new HashMap<String, Integer>();
		fromA.put("A", 0);
		fromA.put("B", 2);
		fromA.put("C", 1);
		fromA.put("D", 3);
		testMap.put("A", fromA);
		
		HashMap<String, Integer> fromB = new HashMap<String, Integer>();
		fromB.put("A", 1);
		fromB.put("B", 0);
		fromB.put("C", 1);
		fromB.put("D", 4);
		testMap.put("B", fromB);
		
		HashMap<String, Integer> fromC = new HashMap<String, Integer>();
		fromC.put("A", 10);
		fromC.put("B", 2);
		fromC.put("C", 0);
		fromC.put("D", 1);
		testMap.put("C", fromC);		
		
		HashMap<String, Integer> fromD = new HashMap<String, Integer>();
		fromD.put("A", 5);
		fromD.put("B", 1);
		fromD.put("C", 4);
		fromD.put("D", 0);
		testMap.put("D", fromD);		
		
		return testMap;
	}	
	
	@Test
	public void evaluate() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "A"});
		travel.evaluate();
		assertEquals(10, travel.getTotalDistance());
	}
	
	@Test
	public void theDistanceBetweenTwoCities() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		assertEquals(3,travel.getDistance("A", "D"));
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

	@Test
	public void mutate() {
		TravellingSalesman travel = new TravellingSalesman(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "A"}); // 9
		travel.mutate();
		travel.evaluate();
		assertEquals(9, travel.getTotalDistance());
	}
	
	@Test
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
	 
	
}
