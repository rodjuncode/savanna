package br.com.rodrigojunqueira.savanna.MinusXSquared;

import static org.junit.Assert.*;

import org.junit.Test;


public class MinusXSquaredFactoryTest {

	@Test
	public void generate() {
		MinusXSquaredFactory mxsFactory = new MinusXSquaredFactory();
		MinusXSquared mxs = (MinusXSquared) mxsFactory.generate();
		assertTrue((-1 < mxs.getX()) && (mxs.getX() < 1));
	}


}
