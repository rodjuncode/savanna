package br.com.rodrigojunqueira.savanna.MinusXSquared;

import br.com.rodrigojunqueira.savanna.core.Dna;

/**
 * 
 * @author Rodrigo Junqueira
 * This class implements Dna to solve the problem of finding the maximum of the function y = x^2.
 * Although the problem is trivial, this class intends to play as a mock for development and tests of the Savanna Genetic Algorithm Stack.
 * 
 */
public class MinusXSquared implements Dna {

	private double x;
	private double y;
	private static double goal;
	
	public MinusXSquared() {
		this.x = 0;
		this.y = 0;
	}
	
	public double getX() {
		return this.x;
	}	
	
	public void setX(double newX) {
		this.x = newX;
	}
	
	public double getY() {
		return this.y;
	}		
	
	public static void setGoal(double newGoal) {
		MinusXSquared.goal = newGoal;		
	}	
	
	/**
	 * y = -x^2
	 */
	public void evaluate() {
		this.y = -1*this.x*this.x;
	}
	
	/**
	 * The crossover operation consists of calculating the simple mean between the x's of each dna involved. 
	 */
	public Dna crossover(Dna dna) {
		MinusXSquared mxs = (MinusXSquared) dna;
		MinusXSquared newMxs = new MinusXSquared();
		double newX = (this.x + mxs.getX()) / 2;
		newMxs.setX(newX);
		return newMxs;
	}

	/**
	 * The mutation operation adds m, with -0.05 < m < 0.05
	 */
	public void mutate() {
		this.x -= Math.random() * 0.1 - 0.05;		
	}

	/**
	 * As we want the maximum of the function, higher y's are more fit
	 */
	public boolean moreFitThan(Dna defiant) {
		MinusXSquared d = (MinusXSquared) defiant;
		if (this.getY() > d.getY()) {
			return true;
		} 
		return false;
	}

	/**
	 * As we want the maximum of the function, lower y's are less fit
	 */
	public boolean lessFitThan(Dna defiant) {
		MinusXSquared d = (MinusXSquared) defiant;
		if (this.getY() < d.getY()) {
			return true;
		} 
		return false;
	}

	/**
	 * Detects if the solutions are the same
	 */
	public boolean asFitAs(Dna defiant) {
		MinusXSquared d = (MinusXSquared) defiant;
		if (this.getY() == d.getY()) {
			return true;
		} 
		return false;
	}

	/**
	 * As we are trying to find the maximum of the function, a good enough solution needs to be higher than our goal
	 */
	public boolean isGoodEnough() {
		if (this.getY() > MinusXSquared.goal) {
			return true;
		} else return false;
	}

	public void show() {
		
	}


}
