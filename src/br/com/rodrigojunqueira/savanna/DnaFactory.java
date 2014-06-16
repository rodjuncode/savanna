package br.com.rodrigojunqueira.savanna;

/** 
 * 
 * @author Rodrigo Junqueira
 * Here we use the Factory Design Pattern to fully abstract the problem specific characteristics from the upper layers of the Genetic Algorithm.
 * The class implementing the DnaFactory will be responsible to generate new Dna's.  
 *
 */
public interface DnaFactory {

	/**
	 * This method should create a new possible solution for the given problem with random parameters.
	 * @return	The new Dna
	 */
	public Dna generate();
	
}
