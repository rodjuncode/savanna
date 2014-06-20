package br.com.rodrigojunqueira.savanna.core;

/**
 * 
 * @author Rodrigo Junqueira
 * A Lion is the Savanna Genetic Algorithm Stack layer that takes care of the social skills for the possible solutions.
 * Through this layer, the generated solutions are going to interact with each other, in order to generate better solutions. 
 * The Lion class knows nothing about the solution details of the problem.
 * The Lion class also needs to implement the Comparable<Lion> interface, so we may store its instances into Java Collections.
 */
public class Lion implements Comparable<Lion> {
	
	private Dna dna;

	/**
	 * The only way to set the Dna of a Lion is through the class constructor.
	 * We a new instance of Lion is created, it automatically evaluates its dna.
	 * @param dna the Dna of the new created Lion 
	 */
	public Lion(Dna dna) {
		this.dna = dna;   
		this.dna.evaluate();
	}
	
	public Lion() {	} // if no Dna is supplied, generates a Lion without a Dna. This should be fixed. If no Dna is provided, than it should create a new Dna.

	/**
	 * An individual mates with another Lion, generating a cub.
	 * @param female	the Lion to mate with
	 * @return			a new Lion (defined cub)
	 */
	public Lion mate(Lion female) {
		Lion male = this;
		Dna cubDna = male.dna.crossover(female.dna);
		Lion cub = new Lion(cubDna);
		return cub;
	}
	
	/**
	 * An individual mutates itself, applying small changes in its parameters.
	 */
	public void mutate() {
		this.dna.mutate();
		this.dna.evaluate();
	}

	/**
	 * This method guarantees the implementation of the Comparable<Lion> interface.
	 * @param defiant	the Lion to be compared with 
	 * @return			1 if this Lion is more fit (should be placed before)
	 * 					-1 if this Lion is less fit (should be placed after)
	 * 					0 if this Lion is as fit as the defiant Lion 
	 */
	public int compareTo(Lion defiant) {
		if (this.dna.moreFitThan(defiant.dna)) return 1;	
		if (this.dna.lessFitThan(defiant.dna)) return -1;
		if (this.dna.asFitAs(defiant.dna)) return 0;
		return 0; // Make sure it returns something
	}

	/**
	 * A Lion should be able to tell if its Dna is a good enough solution for our goals.
	 * @return
	 */
	public boolean isGoodEnough() {
		return this.dna.isGoodEnough();
	}
		
	public void show() {
		this.dna.show();
	}
	

}
