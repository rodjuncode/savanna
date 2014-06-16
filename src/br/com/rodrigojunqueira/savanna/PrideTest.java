package br.com.rodrigojunqueira.savanna;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PrideTest {

	 
	@Test
	public void firstGenerationCount() {
		Pride pride = new Pride(new MinusXSquaredFactory());
		assertEquals("After creating a Pride", 1, pride.getGeneration());
	}
	
	@Test
	public void nextGenerationCount() {
		Pride pride = new Pride(new MinusXSquaredFactory());
		int generation = pride.getGeneration();
		pride.nextGeneration();
		assertEquals("After the next generation of the Pride", generation + 1, pride.getGeneration());		
	}
	
	@Test
	public void populatingPride() {
		Pride pride = new Pride(new MinusXSquaredFactory());
		pride.populate(5);
		assertEquals("After populating the Pride", 5, pride.getPopulation());
	}
	
	@Test
	public void rankingThePride() {
		Pride pride = new Pride(new MinusXSquaredFactory());
		pride.populate(5);
		Lion[] lions = new Lion[5];
		lions = pride.getLions();
		Arrays.sort(lions);
		pride.rank();
		assertEquals("When looking for the king", lions[0], pride.getKing());
		assertEquals("When looking for the nomad", lions[1], pride.getNomad());
	}
	
	
	

	
	
}
