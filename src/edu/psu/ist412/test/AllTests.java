package edu.psu.ist412.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		
		//System Logic Tests
		suite.addTestSuite(CardTest.class);
		suite.addTestSuite(DeckTest.class);
		suite.addTestSuite(DMathTest.class);
		suite.addTestSuite(GameControllerTest.class);
		suite.addTestSuite(GameTest.class);
		suite.addTestSuite(HandTest.class);
		suite.addTestSuite(PlayerTest.class);
		suite.addTestSuite(TableTest.class);
		
		//Logon/Authentication Logic Tests
		suite.addTestSuite(PassJunitTest.class);
		suite.addTestSuite(UserJunitTest.class);
		suite.addTestSuite(SecurityJunitTest.class);
		
		//$JUnit-END$
		return suite;
	}

}
