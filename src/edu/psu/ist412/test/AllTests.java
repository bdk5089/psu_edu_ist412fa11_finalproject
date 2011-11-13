package edu.psu.ist412.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(CardTest.class);
		suite.addTestSuite(DeckTest.class);
		suite.addTestSuite(GameControllerTest.class);
		suite.addTestSuite(GameTest.class);
		suite.addTestSuite(HandTest.class);
		suite.addTestSuite(PlayerTest.class);
		suite.addTestSuite(TableTest.class);
		//$JUnit-END$
		return suite;
	}

}
