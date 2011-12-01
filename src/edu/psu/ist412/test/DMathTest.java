package edu.psu.ist412.test;

import edu.psu.ist412.poker.DMath;
import junit.framework.TestCase;

/**
 * 
 * @author KennedyBD
 *
 */

public class DMathTest extends TestCase {

	public void testPermutations() {
		assertTrue(DMath.permutation(5)==120);
		assertTrue(DMath.permutation(52,2)==2652);
		assertTrue(DMath.permutation(48,5)==205476480);
	}	
	
	public void testCombinations() {
		assertTrue(DMath.combination(52,2)==1326);
		assertTrue(DMath.combination(48,5)==1712304);
		assertTrue(DMath.combination(52,5)==2598960);
		assertTrue(DMath.combination(5,0)==1);
		assertTrue(DMath.combination(4,1)==4);
	}	
	
	public void testFactorial() {
		assertTrue(DMath.factorial(5)==120);
		assertTrue(DMath.factorial(0)==1);
	}

}
