package br.com.rodrigojunqueira.savanna;

public class MinusXSquaredFactory implements DnaFactory {

	/**
	 * -1 < x < -1
	 */
	public Dna generate() {
		MinusXSquared newDna = new MinusXSquared();
		newDna.setX(Math.random() * 2 - 1);
		return newDna;
	}
	
}
