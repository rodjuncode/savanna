package br.com.rodrigojunqueira.savanna.MinusXSquared;

import br.com.rodrigojunqueira.savanna.core.Dna;
import br.com.rodrigojunqueira.savanna.core.DnaFactory;

public class MinusXSquaredFactory implements DnaFactory {

	/**
	 * Generates a random value for x, for the function y = -x^2, with:
	 * -1 < x < -1
	 */
	public Dna generate() {
		MinusXSquared newDna = new MinusXSquared();
		newDna.setX(Math.random() * 2 - 1);
		return newDna;
	}

}
