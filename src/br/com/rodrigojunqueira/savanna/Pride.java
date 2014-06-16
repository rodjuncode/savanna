package br.com.rodrigojunqueira.savanna;

/**
 * 
 * @author Rodrigo Junqueira
 * A Pride is a collection of Lions.
 * Each Pride has a King, its best fit individual.
 * The Pride needs to be provided with a Dna Factory (Factory Design Pattern) so specific details of the problem are abstracted.
 *
 */
public class Pride {

	private int generation;
	private Lion[] lions;
	private DnaFactory dnaFactory;
	private Lion lionKing;
	private Lion lionNomad;

	/**
	 * A new Pride is on its 1st generation.
	 */
	public Pride(DnaFactory dnaFactory) {
		this.generation = 1;
		this.dnaFactory = dnaFactory;
	}
	
	public int getGeneration() {
		return this.generation;
	}

	/**
	 * @return The number of Lions in this Pride.
	 */
	public int getPopulation() {
		return this.lions.length;
	}

	/**
	 * Instantiate the Lions on the Pride
	 * @param n The number of Lions for this Pride.
	 */
	public void populate(int n) {
		this.lions = new Lion[n];
		for (int i = 0; i < n; i++) {
			this.lions[i] = new Lion(this.dnaFactory.generate());
		}
	}

	/**
	 * The Pride evolves into its next generation.
	 */
	public void nextGeneration() {
		this.generation++;		
	}

	public Lion[] getLions() {
		return this.lions;
	}

	public Lion getKing() {
		return this.lionKing;
	}

	public Lion getNomad() {
		return this.lionNomad;
	}

	public void rank() {

		this.lionKing = this.lions[0];
		this.lionNomad = this.lions[1];
		
		for (int i = 0; i < this.lions.length; i++) {
			if (this.lions[i].compareTo(this.lionKing) == 1) {
				this.lionKing = this.lions[i];				
			} else if (this.lions[i].compareTo(this.lionNomad) == 1) {
				this.lionNomad = this.lions[i];
			}
		}
		
	}
	
}

