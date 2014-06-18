package br.com.rodrigojunqueira.savanna.core;

/**
 * 
 * @author Rodrigo Junqueira
 * A Pride is a group of Lions.
 * Each Pride has a King, its best fit individual.
 * Each Pride has a Nomad, the second most fit individual. Nomads are going to leave the Pride, trying to be the Kings of other Prides.
 * The Pride needs to be provided with a Dna Factory (Factory Design Pattern) so it can populate itself with new Lions, each one carrying a possible solution for our problem.
 * To rank itself, the Pride runs once through each individual on its population, searching for the King (first most fit) and the Nomad (second most fit).
 * In order to create a new generation of Lions, the King mates with all the other Lions in the Pride. All the Lions are overwritten, except the King.
 * WITH LIST:
 * In the future, the way the Pride executes the mating between Lions is going to be fully abstracted, allowing easy changes on the algorithm strategies. The Strategy Design Pattern is going to be used for this goal.
 * 
 */
public class Pride {

	private int generation;
	private Lion[] lions;
	private DnaFactory dnaFactory;
	private Lion lionKing;
	private Lion lionNomad;


	/**
	 * A new Pride is created, empty and on generation 0.
	 * @param dnaFactory The object that will create new Dna's for the Lions born in this Pride
	 */
	public Pride(DnaFactory dnaFactory) {
		this.generation = 0;
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
	 * Instantiate the Lions on the Pride and sets it to its 1st generation. When all Lions are created, finds the King and the Nomad
	 * @param n The number of Lions for this Pride.
	 */
	public void populate(int n) {
		this.generation = 1;
		this.lions = new Lion[n];
		for (int i = 0; i < n; i++) {
			this.lions[i] = new Lion(this.dnaFactory.generate());
		}
		this.rank();
	}

	/**
	 * The Pride evolves into its next generation, following the steps:
	 * 1) Mates the Lions;
	 * 2) Evokes mutations to the Lions;
	 * 3) Finds the new King and Nomad;
	 * 4) Increments the generation counter.
	 */
	public void nextGeneration() {
		this.mate();
		this.mutate();
		this.rank();
		this.generation++;
	}

	/**
	 * @return the group of Lion in this Pride
	 */
	public Lion[] getLions() {
		return this.lions;
	}

	/**
	 * @return the King of the Pride
	 */
	public Lion getKing() {
		return this.lionKing;
	}

	/**
	 * @return the Noma of the Pride
	 */
	public Lion getNomad() {
		return this.lionNomad;
	}

	/**
	 * Runs once through each Lion on the Pride and finds the first and second most fit individuals.
	 * They will be set as the King and Nomad of this Pride.
	 */
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

	/**
	 * Mates the King of the Pride with every other individual, overwriting them in the process.
	 * The King is not overwritten and goes to the next generation.
	 */
	public void mate() {
		for (int i = 0; i < this.lions.length; i++) {
			if (this.lions[i] != this.lionKing) {
				this.lions[i] = this.lionKing.mate(this.lions[i]);
			}
		}
	}
	
	/**
	 * Evokes the mutation operation for every single Lion, except the King of the Pride.
	 */
	public void mutate() {
		for (int i = 0; i < this.lions.length; i++) {
			if (this.lions[i] != this.lionKing) {
				this.lions[i].mutate();
			}
		}		
	}
	
}

