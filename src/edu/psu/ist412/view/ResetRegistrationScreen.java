package edu.psu.ist412.view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.psu.ist412.logon.Account;

/**
 * This class builds the shared parts for the registration and reset screens. 
 * Each screen defines specific functionality separately.
 * @author Jeff
 *
 */
public class ResetRegistrationScreen extends JFrame {
	
	private static final JLabel USERNAME_LABEL = new JLabel("Username:  ");
	private static final JLabel PASSWORD_LABEL = new JLabel("Password:  ");
	private static final JLabel CONFIRM_LABEL = new JLabel("Confirm Password:  ");

	protected JTextField userName = new JTextField();
	protected JPasswordField password = new JPasswordField();
	protected JPasswordField confPass = new JPasswordField();
	protected JTextField securityAns = new JTextField();
	
	private static final JLabel SEC_HEADING = new JLabel("Security Question:");
	private static final JLabel QUESTION = 
		new JLabel("Where did you study for your undergraduate education?");
	
	private static JButton SUBMIT_BUTTON;
	private static JButton CANCEL_BUTTON;
	
	// a reference to the login screen
	private JFrame loginScreen;
	
	// username is the key, and associated account is the value
	protected HashMap <String, Account> accounts;
	
	// string used to identify acceptable special characters
	private static final String specialString = "!@#$%^&*()";
	
	/**
	 * Constructor
	 * @param windowTitle - the words to display for the window's title
	 * @param login - reference to the login screen
	 */
	public ResetRegistrationScreen(String windowTitle, JFrame login) {
		super(windowTitle);
		
		loginScreen = login;
		
		SUBMIT_BUTTON = new JButton("Submit");
		CANCEL_BUTTON = new JButton("Cancel");
		
		setLayout(new BorderLayout());
		
		add(buttonPanel(), BorderLayout.SOUTH);
		add(leftPanel(), BorderLayout.WEST);
	}
	
	private JPanel leftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3,1));
		
		leftPanel.add(userPassPanel());
		leftPanel.add(questionPanel());
		
		return leftPanel;
	}
	
	private JPanel buttonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		CancelButtonHandler cancelHandler = new CancelButtonHandler();
		CANCEL_BUTTON.addActionListener(cancelHandler);
		
		buttonPanel.add(SUBMIT_BUTTON);
		buttonPanel.add(CANCEL_BUTTON);
		
		return buttonPanel;
	}
	
	private JPanel userPassPanel() {
		JPanel userPassPanel = new JPanel();
		userPassPanel.setLayout(new GridLayout(3,2));
		
		userPassPanel.add(USERNAME_LABEL);
		userPassPanel.add(userName);
		userPassPanel.add(PASSWORD_LABEL);
		userPassPanel.add(password);
		userPassPanel.add(CONFIRM_LABEL);
		userPassPanel.add(confPass);
		
		return userPassPanel;
	}
	
	private JPanel questionPanel() {
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(3,1));
		
		questionPanel.add(SEC_HEADING);
		questionPanel.add(QUESTION);
		questionPanel.add(securityAns);
		
		return questionPanel;
	}
	
	/**
	 * Handles the actions associated with the cancel button.
	 * @author Jeff
	 *
	 */
	private class CancelButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cancel();
		}
	}
	
	/**
	 * Exit the current screen and bring back the login screen.
	 */
	protected void cancel() {
		dispose();		
		
		loginScreen.setVisible(true);
		loginScreen.toFront();
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
	protected boolean validatePassword(String password, String confirmPass) {
		boolean upper = false;
		boolean number = false;
		boolean special = false;
		
		if (password.length() < 10) {
			JOptionPane.showMessageDialog(null, 
					"Your password must be at least ten (10) characters.", 
					"Warning!", JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (password.length() > 20) {
			JOptionPane.showMessageDialog(null, 
					"Your password must not exceed twenty (20) characters.", 
					"Warning!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if (!password.equals(confirmPass)) {
			JOptionPane.showMessageDialog(null, 
					"Both passwords must match.", "Warning!", 
					JOptionPane.WARNING_MESSAGE);
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
				JOptionPane.showMessageDialog(null, 
						"You cannot put a space in your password.", "Warning!", 
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		if (!number || !upper || !special) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Allows subclasses to define their own functionality for the submit button.
	 * @param handler
	 */
	protected void setSubmitHandler(ActionListener handler) {
		SUBMIT_BUTTON.addActionListener(handler);
	}
	
	/**
	 * Check if username exists
	 * @param user - desired username
	 * @return - true if the name doesn't already exist, false if it does
	 */
	protected boolean checkUserName(String user) {
		if (accounts.containsKey(user)) {
			return true;
		} else {
			return false;
		}
	}
	
}
