package edu.psu.ist412.test;
import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * A JUnit test case for passwords that covers the following scenarios:
 * 		too long of a password
 * 		too short of a password
 * 		a password with a space
 * 		a password without a special character
 * 		a password without a number
 * 		a password without an upper case character
 * 		four valid passwords
 * @author Jeff
 *
 */
public class PassJunitTest extends TestCase {

	// string used to identify acceptable special characters
	private static final String specialString = "!@#$%^&*()";
	
	private String nineChars = "nineChar5";
	private String tenChars = "Password1!";
	private String elevenChars = "ItIsGood*23";
	private String nineteenChars = "1tHasNineteenChars^";
	private String twentyChars = "This1sAGoodPassword%";
	private String twentyOneChars = "ThisHasTwenty0neChar$";
	private String space = "This^ hasA5pace";
	private String noNumber = "noNumberHere!";
	private String noUpper = "n0uppercase*";
	private String noSpecial = "N0specialChars";
	
	public void testPasswordValidation() {
		Assert.assertTrue(nineChars + " is too short.", 
				!validatePassword(nineChars));
		Assert.assertTrue(tenChars + " is a valid password.", 
				validatePassword(tenChars));
		Assert.assertTrue(elevenChars + " is a valid password.", 
				validatePassword(elevenChars));
		Assert.assertTrue(nineteenChars + " is a valid password.", 
				validatePassword(nineteenChars));
		Assert.assertTrue(twentyChars + " is a valid password.", 
				validatePassword(twentyChars));
		Assert.assertTrue(twentyOneChars + " is too long.", 
				!validatePassword(twentyOneChars));
		Assert.assertTrue(space + " cannot have a space.", 
				!validatePassword(space));
		Assert.assertTrue(noNumber + " doesn't have a number.", 
				!validatePassword(noNumber));
		Assert.assertTrue(noUpper + " doesn't have an upper case letter.", 
				!validatePassword(noUpper));
		Assert.assertTrue(noSpecial + " doesn't have a special character.", 
				!validatePassword(noSpecial));
	}
	
	/**
	 * Verify password format is followed and both passwords match:
	 *   min length of 10 characters
	 *   max length of 20 characters
	 *   must use the following: 
	 *     uppercase, number, special character
	 * @param password - first password
	 * @param confirmPass - confirmation password
	 * @return - true if all rules pass, false otherwise
	 */
	private static boolean validatePassword(String password) {
		boolean upper = false;
		boolean number = false;
		boolean special = false;
		
		if (password.length() < 10) {
			//JOptionPane.showMessageDialog(null, 
			//		"Your password must be at least ten (10) characters.", 
			//		"Warning!", JOptionPane.WARNING_MESSAGE);
			System.out.println(password + "  -  Your password must be at least ten (10) characters.");
			return false;
		} else if (password.length() > 20) {
			//JOptionPane.showMessageDialog(null, 
			//		"Your password must not exceed twenty (20) characters.", 
			//		"Warning!", JOptionPane.WARNING_MESSAGE);
			System.out.println(password + "  -  Your password must not exceed twenty (20) characters.");
			return false;
		}
		
		char [] chars = password.toCharArray();
		
		for (char c : chars) {
			if (!number && Character.isDigit(c)) {
				number = true;
			} else if (!upper && Character.isUpperCase(c)) {
				upper = true;
			} else if (!special && 
					specialString.contains(new Character(c).toString())) {
				special = true;
			} else if (Character.isWhitespace(c)) {
				System.out.println(password + "  -  Your password cannot contain a space.");
				return false;
			}
		}
		
		if (!number || !upper || !special) {
			System.out.println(password + "  -  This password does not meet at least one of the 3 rules.");
			return false;
		} else {
			System.out.println(password + "  -  This is a valid password.");
			return true;
		}
	}

}
