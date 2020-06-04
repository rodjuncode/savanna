package br.com.rodrigojunqueira.savanna.core;

/** 
 * 
 * @author Rodrigo Junqueira
 * Here we use the Factory Design Pattern to fully abstract the problem specific characteristics from the upper layers of the Savanna Genetic Algorithm Stack.
 * The class implementing the DnaFactory will be responsible for generating new Dna's objects, i.e., sets of random values for the Dna's parameters.  
 *
 */
public interface DnaFactory {

	/**
	 * This method should create a new possible solution for the given problem with preferably random generated parameters.
	 * @return	A new possible solution for the problem
	 */
	public Dna generate();
	
}
