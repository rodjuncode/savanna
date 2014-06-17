package br.com.rodrigojunqueira.savanna;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class TravellingSalesManTest {

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
		TravellingSalesMan travel = new TravellingSalesMan(this.map());
		travel.setRoute(new String[]{"A", "D", "C", "B", "A"});
		travel.evaluate();
		assertEquals(10, travel.getTotalDistance());
	}
	
	@Test
	public void theDistanceBetweenTwoCities() {
		TravellingSalesMan travel = new TravellingSalesMan(this.map());
		assertEquals(3,travel.getDistance("A", "D"));
	}
	
	@Test
	public void crossover() {
		TravellingSalesMan travel1 = new TravellingSalesMan(this.map());
		travel1.setRoute(new String[]{"A", "D", "C", "B", "A"});
		TravellingSalesMan travel2 = new TravellingSalesMan(this.map());
		travel2.setRoute(new String[]{"A", "C", "B", "D", "A"});
		TravellingSalesMan travel3 = (TravellingSalesMan) travel1.crossover(travel2);
		travel3.evaluate();
		assertEquals(20, travel3.getTotalDistance());		
	}
	
}
