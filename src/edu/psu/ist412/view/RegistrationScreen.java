package edu.psu.ist412.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.psu.ist412.logon.Account;

/**
 * This class defines the functionality specific to the registration screen.
 * @author Jeff
 *
 */
public class RegistrationScreen extends ResetRegistrationScreen {
	
	/**
	 * Constructor
	 * @param login - reference to the login screen
	 * @param accts - reference to the hash map of usernames and accounts
	 */
	public RegistrationScreen(JFrame login, HashMap <String, Account> accts) {
		super("IST 412 Account Registration", login);
		
		accounts = accts;
		
		SubmitButtonHandler submitHandler = new SubmitButtonHandler();
		
		setSubmitHandler(submitHandler);
	}
	
	/**
	 * Handles the actions for the submit button. Validates user, password,
	 * and security question entries. Creates a new account if all responses
	 * are valid.
	 * @author Jeff
	 *
	 */
	private class SubmitButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String user = userName.getText();
			String pass = password.getText();
			String question = securityAns.getText();
			
			MessageDigest md = null;
			
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch(NoSuchAlgorithmException nsae) {
				
			}
			
			if (validateUser(user) && 
					validatePassword(pass, confPass.getText()) &&
					validateSecurityQuestion(question)) {
				
				Account newAccount = new Account(user, 
						md.digest(pass.getBytes()), 
						md.digest(question.getBytes()));
				
				accounts.put(user, newAccount);
				
				cancel();
			}
		}
	}
	
	/**
	 * Verify username format is followed:
	 *   only letters and/or numbers (no spaces)
	 *   max 20 characters
	 * @param user - desired username
	 * @return - true if both rules pass, false otherwise
	 */
	private boolean validateUser(String user) {
		if (checkUserName(user)) {
			JOptionPane.showMessageDialog(null, 
					"Your user name already exists.", 
					"Warning!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if (user.length() > 20) {
			JOptionPane.showMessageDialog(null, 
					"Your user name must not exceed twenty (20) characters.", 
					"Warning!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		char [] chars = user.toCharArray();
		
		for (char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				JOptionPane.showMessageDialog(null, 
						"Your user name can only use letters or numbers.", 
						"Warning!", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Verifies the security question has been answered.
	 * @param answer - the answer (or lackthereof) from the screen
	 * @return - true if answered, false otherwise
	 */
	private boolean validateSecurityQuestion(String answer) {
		if (answer.length() == 0) {
			JOptionPane.showMessageDialog(null, 
					"You must answer the security question.", "Warning!", 
					JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (answer.length() > 40) {
			JOptionPane.showMessageDialog(null, 
					"Your answer must be less than forty (40) characters.", 
					"Warning!", JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}
	
}
