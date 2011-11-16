package edu.psu.ist412.logon;

/**
 * This class is used to store the specific account information which includes
 * user name, password, the security question answer and whether the account
 * is locked or not.
 * @author Jeff
 *
 */
public class Account {

	private String userName;
	private byte [] password;
	private byte [] secQuestion;
	private boolean isLocked = false;
	
	// number of failed attempts
	private int attempts = 1;
	
	/**
	 * Default constructor
	 */
	public Account () {
		
	}
	
	/**
	 * Create an account with the user, password, and secQuestion fields set
	 * @param user - the user name
	 * @param pass - the password
	 * @param question - the answer to the security question
	 */
	public Account (String user, byte [] pass, byte [] question) {
		userName = user;
		password = pass;
		secQuestion = question;
	}
	
	/**
	 * Gets the user name for the current account
	 * @return - the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name for the current account
	 * @param userName - the user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password for the current account
	 * @return - the password
	 */
	public byte [] getPassword() {
		return password;
	}

	/**
	 * Sets the password for the current account
	 * @param password - the password
	 */
	public void setPassword(byte [] password) {
		this.password = password;
	}

	/**
	 * Gets the answer to the security question for the current account
	 * @return - the security question answer
	 */
	public byte [] getSecQuestion() {
		return secQuestion;
	}

	/**
	 * Sets the answer to the security question for the current account
	 * @param secQuestion - the security question answer
	 */
	public void setSecQuestion(byte [] secQuestion) {
		this.secQuestion = secQuestion;
	}

	/**
	 * Tells whether the account is locked or not
	 * @return - true if yes, false if no
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * Sets the locked status of the current account
	 * @param isLocked - true if yes, false if no
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * Increases number of attempts if a failed attempt occurs
	 */
	public void failedAttempt() {
		attempts++;
	}

	/**
	 * Resets the number of attempts if a successful attempt occurs
	 */
	public void successfulAttempt() {
		attempts = 0;
	}

	/**
	 * Gets the number of incorrect attempts that have been made
	 * @return - the number of attempts
	 */
	public int getAttempts() {
		return attempts;
	}
	
}
