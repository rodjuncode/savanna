package br.com.rodrigojunqueira.savanna.MinusXSquared;

import static org.junit.Assert.*;

import org.junit.Test;

public class MinusXSquaredTest {

	private static final double EPSILON = 1e-15;	
	
	@Test
	public void evaluate() {
		MinusXSquared mxs = new MinusXSquared();
		mxs.setX(0.5);
		mxs.evaluate();
		assertEquals(-0.25, mxs.getY(), EPSILON);
	}
	
	@Test
	public void comparingFitness() {
		MinusXSquared mxs1 = new MinusXSquared();
		mxs1.setX(0.25);
		mxs1.evaluate();
		MinusXSquared mxs2 = new MinusXSquared();
		mxs2.setX(0.5);
		mxs2.evaluate();
		assertEquals(true, mxs1.moreFitThan(mxs2));
		assertEquals(true, mxs2.lessFitThan(mxs1));
		assertEquals(true, mxs1.asFitAs(mxs1));		
	}
	
	@Test
	public void crossover() {
		MinusXSquared mxs1 = new MinusXSquared();
		mxs1.setX(1);
		MinusXSquared mxs2 = new MinusXSquared();
		mxs2.setX(0.0);
		MinusXSquared mxs3 = (MinusXSquared) mxs1.crossover(mxs2);
		mxs3.evaluate();
		assertEquals(-0.25, mxs3.getY(), EPSILON);
	}
	
	//@Test
	public void mutate() {
		MinusXSquared mxs = new MinusXSquared();
		mxs.setX(0.55);
		mxs.mutate();
		mxs.evaluate();
		assertEquals(-0.25, mxs.getY(), EPSILON);
	}
	
	/*
	@Test
	public void isDnaEvaluated() {
		MinusXSquared mxs = new MinusXSquared();
		mxs.setX(0);
		assertEquals(false, mxs.evaluated());
		mxs.evaluate();
		assertEquals(true, mxs.evaluated());
	}
	*/
	
	@Test
	public void checkingForTermination() {
		MinusXSquared mxs = new MinusXSquared();
		MinusXSquared.setGoal(-0.005);
		mxs.setX(0);
		mxs.evaluate();
		assertEquals(true, mxs.isGoodEnough());
	}
	
	
}
