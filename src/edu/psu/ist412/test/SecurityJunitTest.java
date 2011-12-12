package edu.psu.ist412.test;
import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * A JUnit test case for security question answers that covers the following scenarios:
 * 		too long of an answer
 * 		a blank answer
 * 		four valid answers
 * @author Jeff
 *
 */
public class SecurityJunitTest extends TestCase {
	
	private String blankAnswer = "";
	private String shortAnswer = "Y";
	private String specialAnswer = "$pecial";
	private String spaceAnswer = "space answer";
	private String comboAnswer = "I'd rather not answer.";
	private String longAnswer = "ThisAnswerToTheSecurityQuestionHasTooManyCharacters";
	
	public void testSecurityQuestion() {
		Assert.assertTrue(blankAnswer + " cannot be blank.", 
				!validateSecurityQuestion(blankAnswer));
		Assert.assertTrue(shortAnswer + " is a valid answer.", 
				validateSecurityQuestion(shortAnswer));
		Assert.assertTrue(specialAnswer + " is a valid answer.", 
				validateSecurityQuestion(specialAnswer));
		Assert.assertTrue(spaceAnswer + " is a valid answer.", 
				validateSecurityQuestion(spaceAnswer));
		Assert.assertTrue(comboAnswer + " is a valid answer.", 
				validateSecurityQuestion(comboAnswer));
		Assert.assertTrue(longAnswer + " is too long.", 
				!validateSecurityQuestion(longAnswer));
	}

	/**
	 * Verifies the security question has been answered.
	 * @param answer - the answer (or lackthereof) from the screen
	 * @return - true if answered, false otherwise
	 */
	public boolean validateSecurityQuestion(String answer) {
		if (answer.length() == 0) {
			//JOptionPane.showMessageDialog(null, 
			//		"You must answer the security question.", "Warning!", 
			//		JOptionPane.WARNING_MESSAGE);
			System.out.println(answer + "  -  You must answer the security question.");
			return false;
		} else if (answer.length() > 40) {
			//JOptionPane.showMessageDialog(null, 
			//		"Your answer must be less than forty (40) characters.", 
			//		"Warning!", JOptionPane.WARNING_MESSAGE);
			System.out.println(answer + "  -  Your answer must be less than forty (40) characters.");
			return false;
		} else {
			System.out.println(answer + "  -  This is a valid answer.");
			return true;
		}
	}

}
