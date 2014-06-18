package br.com.rodrigojunqueira.savanna.core;

/**
 * 
 * @author Rodrigo Junqueira
 * When modeling the problem we want to solve via Savanna Genetic Algorithm Stack, the Dna is the deepest layer of implementation.  
 * Each Dna corresponds to a possible solution for this problem.
 * Interfacing this layer allows easily extension for different scenarios.
 * The Dna class needs to take care of 5 aspects of the problem:
 * 1) It needs to map the problem's possible solutions through parameters;
 * 2) It needs to be able to, given a set of values for those parameters, calculate a final result (it can be anything: from a double value, result of a function y = f(x); to an enumerated type, like 'hot').
 * 3) It needs to be able to compare two given possible solutions and tell which one is the best or if they are equivalent as the solution of the problem;
 * 4) It needs to implement the two basic Genetic Algorithm operations: mutation and crossover;
 * 5) It needs to be able to answer if a given solution is good enough for our goals (i.e. the criteria for termination).
 * It's important to notice that Dna doesn't have "social" skills. It's focused only in the solution, leaving all the rest for the upper stack layers.
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
	 * A Dna should be able to compare itself to another Dna and tell if it is the best solution.
	 * @param defiant	the Dna to be compared with
	 * @return			true if it's more fit, false otherwise
	 */
	public boolean moreFitThan(Dna defiant);

	
	/**
	 * A Dna should be able to compare itself to another Dna and tell if it is the worse solution.
	 * @param defiant	the Dna to be compared with
	 * @return			true if it's less fit, false otherwise
	 */	
	public boolean lessFitThan(Dna defiant);
	
	/**
	 * A Dna should be able to compare itself to another Dna and tell if they are equivalent as a solution for the problem.
	 * @param defiant	the Dna to be compared with
	 * @return			true if it's as fit as the defiant, false otherwise
	 */	
	public boolean asFitAs(Dna defiant);	

	/**
	 *  A Dna should be able to, using its parameters, reach a final result. It can be, for example, a double value y = f(x), or even a enumerated type, as 'hot' or 'cold'.
	 *  This method should not return this value. Instead, we strongly recommend storing this value in a private attribute, since it will save processing efforts.
	 */
	public void evaluate();	

	/**
	 * The Dna needs to be able if its set of parameters is a solution good enough for our goals.
	 * @return	true if it's good enough
	 */
	public boolean isGoodEnough();
	
}
