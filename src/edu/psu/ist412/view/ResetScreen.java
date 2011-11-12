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
 * This class defines the functionality specific to the reset screen.
 * @author Jeff
 *
 */
public class ResetScreen extends ResetRegistrationScreen{
	
	/**
	 * Constructor
	 * @param login - reference to the login screen
	 * @param accts - reference to the hash map of usernames and accounts
	 */
	public ResetScreen(JFrame login, HashMap <String, Account> accts) {
		super("IST 412 Password Reset", login);
		
		accounts = accts;
		
		SubmitButtonHandler submitHandler = new SubmitButtonHandler();
		
		setSubmitHandler(submitHandler);
	}
	
	/**
	 * Handles the actions associated with the submit button. Verifies user
	 * exists, the new password is acceptable, and the security question is
	 * answered correctly.
	 * @author Jeff
	 *
	 */
	private class SubmitButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String user = userName.getText();
			String pass = password.getText();
			String answer = securityAns.getText();
			
			MessageDigest md = null;
			
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch(NoSuchAlgorithmException nsae) {
				
			}
			
			if (checkUserName(user) && 
					validatePassword(pass, confPass.getText()) &&
					validateSecurityQuestion(user, md.digest(answer.getBytes()))) {
				Account curAccount = accounts.get(user);
				
				curAccount.setPassword(md.digest(pass.getBytes()));
				curAccount.setLocked(false);
				
				cancel();
			}
		}
	}
	
	/**
	 * Verifies the entered security question answer matches what was provided
	 * during account registration.
	 * @param user - the user whose account is being reset
	 * @param answer - the entered security question answer
	 * @return - true if the answers match, false otherwise
	 */
	private boolean validateSecurityQuestion(String user, byte [] answer) {
		if (answer.length == 0) {
			JOptionPane.showMessageDialog(null, 
					"You must answer the security question.", "Warning!", 
					JOptionPane.WARNING_MESSAGE);
			
			securityAns.setText(null);
			
			return false;
		} else if (!MessageDigest.isEqual(answer, 
				accounts.get(user).getSecQuestion())) {
			JOptionPane.showMessageDialog(null, 
					"Security question answer is incorrect.", "Warning!", 
					JOptionPane.WARNING_MESSAGE);
			
			securityAns.setText(null);
			
			return false;
		} else {
			return true;
		}
	}

}
