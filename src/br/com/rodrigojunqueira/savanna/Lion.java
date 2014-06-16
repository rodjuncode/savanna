package br.com.rodrigojunqueira.savanna;

/**
 * 
 * @author Rodrigo Junqueira
 * A Lion is the abstraction layer that takes care of the social skills of a possible solution. 
 * The Lion class knows nothing about the solution details of the problem.
 * The Lion class also need to implement the Comparable interface, so the sort method can be used to a Collection of Lions (used in the upper layers).
 */

public class Lion implements Comparable<Lion> {
	
	private Dna dna;

	/**
	 * 
	 * @param dna the Dna of the new created Lion 
	 */
	public Lion(Dna dna) {
		this.dna = dna; // The only way to set the Dna is through the constructor. 
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
	
	public void mutate() {
		this.dna.mutate();
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
	
	
	

}
