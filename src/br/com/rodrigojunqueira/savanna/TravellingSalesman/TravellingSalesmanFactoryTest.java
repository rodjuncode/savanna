package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import static org.junit.Assert.*;

import org.junit.Test;

public class TravellingSalesmanFactoryTest {

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
	public void generate() {
		TravellingSalesmanFactory dnaFactory = new TravellingSalesmanFactory();
		dnaFactory.setMap(this.map());
		TravellingSalesman dna = (TravellingSalesman) dnaFactory.generate();
		assertEquals(true, dna.checkRoute());
		
	}

}
