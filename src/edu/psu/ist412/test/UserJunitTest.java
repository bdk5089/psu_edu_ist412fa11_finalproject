package edu.psu.ist412.test;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * A JUnit test case for user names that covers the following scenarios:
 * 		too long of a name
 * 		a name with a space
 * 		a name with a special character
 * 		two valid user names
 * @author Jeff
 *
 */
public class UserJunitTest extends TestCase {
	
	private String longName = "thisnamehastoomanycharacters";
	private String spaceName = "thisName HasASpace";
	private String specialName = "thisNameIs$pecial";
	private String charName = "lettersOnly";
	private String numName = "1number";
	
	public void testName() {
		Assert.assertTrue(longName + " is too long.", !validateUser(longName));
		Assert.assertTrue(spaceName + " can't have a space.", !validateUser(spaceName));
		Assert.assertTrue(specialName + " can't use special characters.", 
				!validateUser(specialName));
		Assert.assertTrue(charName + " is a valid username.", validateUser(charName));
		Assert.assertTrue(numName + " is a valid username.", validateUser(numName));
	}
	
	/**
	 * Verify username format is followed:
	 *   only letters and/or numbers (no spaces)
	 *   max 20 characters
	 * @param user - desired username
	 * @return - true if both rules pass, false otherwise
	 */
	private static boolean validateUser(String user) {
//		if (checkUserName(user)) {
//			JOptionPane.showMessageDialog(null, 
//					"Your user name already exists.", 
//					"Warning!", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
		
		if (user.length() > 20) {
//			JOptionPane.showMessageDialog(null, 
//					"Your user name must not exceed twenty (20) characters.", 
//					"Warning!", JOptionPane.WARNING_MESSAGE);
			System.out.println(user + "  -  Your user name must not " +
					"exceed 20 characters.");
			return false;
		}
		
		char [] chars = user.toCharArray();
		
		for (char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
//				JOptionPane.showMessageDialog(null, 
//						"Your user name can only use letters or numbers.", 
//						"Warning!", JOptionPane.WARNING_MESSAGE);
				System.out.println(user + "  -  Your user name can only " +
						"use letters or numbers.");
				return false;
			}
		}
		
		System.out.println(user + "  -  This user name is valid.");
		return true;
	}

}
