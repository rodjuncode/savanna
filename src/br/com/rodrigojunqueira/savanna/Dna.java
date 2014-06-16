package br.com.rodrigojunqueira.savanna;

/**
 * 
 * @author Rodrigo Junqueira
 * Each Dna is the bottom abstraction layer of a solution for the problem we want to solve.
 * Interfacing this layer allows easily extension for different scenarios.
 * The Dna class needs to take care of 4 aspects of the problem:
 * 1) It needs to map the problem's possible solutions into parameters;
 * 2) It needs to be able to, given a set of values for those parameters, calculate a final result;
 * 3) It needs to be able to compare to given possible solutions and tell which one is best or if they are equal;
 * 4) It needs to implement the two basic Genetic Algorithm operations: mutation and crossover. 
 * Each Dna for a given problem may handle its basic operations in different ways. One a specific case, we may want to execute the mutation on a specific manner, for example. Hence, a Dna should implement how it performs the basic Genetic Algorithms operations, such as mutation and crossover.
 * It's important to notice that Dna doesn't have "social" skills. It's focused only in the solution, leaving all the rest for the upper abstraction layers.
 * 
 */
public interface Dna {
		
	/**
	 * Basic Genetic Algorithm operation: crossover. Two solutions combine themselves in order to produce a new solution. 
	 * @param dna	the Dna to combine with
	 * @return		the new Dna
	 */
	public Dna crossover(Dna dna);
	
	/**
	 * Basic Genetic Algorithm operation: mutation. The solution applies small changes in its own state.
	 */
	public void mutate();
	
	/**
	 * A Dna should be able to compare itself to another Dna and tell who is the fittest.
	 * @param defiant	the Dna to be compared with
	 * @return			true if it's more fit, false otherwise
	 */
	public boolean moreFitThan(Dna defiant);

	
	/**
	 * A Dna should be able to compare itself to another Dna and tell who is the fittest.
	 * @param defiant	the Dna to be compared with
	 * @return			true if it's less fit, false otherwise
	 */	
	public boolean lessFitThan(Dna defiant);
	
	/**
	 * A Dna should be able to compare itself to another Dna and tell if they have the same fitness.
	 * @param defiant	the Dna to be compared with
	 * @return			true if it's as fit as the defiant, false otherwise
	 */	
	public boolean asFitAs(Dna defiant);	

	public void evaluate();	
	
	public boolean isGoodEnough();
	
}
