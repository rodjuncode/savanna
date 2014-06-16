package br.com.rodrigojunqueira.savanna;

import static org.junit.Assert.*;

import org.junit.Test;

public class LionTest {

	@Test
	public void makingLove() {
		MinusXSquaredFactory dnaFactory = new MinusXSquaredFactory();
		Lion male = new Lion(dnaFactory.generate());
		Lion female = new Lion(dnaFactory.generate());		
		Lion cub = male.mate(female);
		assertTrue(cub instanceof Lion);
	}

	@Test
	public void isGoodEnough() {
		MinusXSquared mxs = new MinusXSquared();
		mxs.setX(0);
		MinusXSquared.setGoal(-0.005);
		Lion lion = new Lion(mxs);
		assertEquals(true, lion.isGoodEnough());
	}
	
}


