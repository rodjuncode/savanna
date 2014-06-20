package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import static org.junit.Assert.*;

import org.junit.Test;

public class TravellingSalesmanMapTest {

	@Test
	public void addAndRemoveCities() {
		TravellingSalesmanMap map = new TravellingSalesmanMap();
		assertEquals("After creating a new map", 0, map.getSize());
		map.add("A");
		assertEquals("After adding 1 city", 1, map.getSize());
		map.remove("A");
		assertEquals("After removing 1 city", 0, map.getSize());		
	}
	
	@Test
	public void playingWithDistances() {
		TravellingSalesmanMap map = new TravellingSalesmanMap();
		map.add("A");
		map.add("B");
		map.add("C");
		map.setDistance("A","B", 2);
		assertEquals("After setting distance from A to B", 2, map.getDistance("A", "B"));
		assertEquals("After setting distance from A to B", 2, map.getDistance("B", "A"));
		assertEquals("Calling for a path that doesn't exist", 0, map.getDistance("C", "A"));
		map.remove("B");
		assertEquals("After removing B", 0, map.getDistance("B", "A"));
	}
	
	@Test
	public void validantingMap() {
		TravellingSalesmanMap map = new TravellingSalesmanMap();
		map.add("A");
		assertEquals("Only one city", false, map.validate());
		map.add("B");
		assertEquals("Two cities with no distance", false, map.validate());
		map.setDistance("A", "B", 2);
		assertEquals("After adding distance between two existant cities", true, map.validate());		
	}
	
	@Test
	public void settingDistancesWithArray() {
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
		assertEquals("After setting up distances with an Array", true, map.validate());
	}

}
