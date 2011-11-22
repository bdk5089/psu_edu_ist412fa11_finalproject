package edu.psu.ist412.view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.psu.ist412.logon.Account;
import edu.psu.ist412.poker.GameController;

/**
 * This class builds the login screen.
 * @author Jeff
 *
 */
public class LoginScreen extends JFrame{

	private static final JLabel RESET_LABEL = 
		new JLabel("Forgotten password/locked account:");
	private static final JLabel REGISTER_LABEL = 
		new JLabel("Register for a new account:");
	private static final JLabel USER_LABEL = new JLabel("  Username:  ");
	private static final JLabel PASS_LABEL = new JLabel("  Password:  ");
	
	private static final JButton LOGIN_BUTTON = new JButton("Login");
	private static final JButton QUIT_BUTTON = new JButton("Quit");
	private static final JButton REGISTER_BUTTON = new JButton("Register");
	private static final JButton RESET_BUTTON = new JButton("Reset Password");
	
	private JTextField userField = new JTextField();
	private JPasswordField passField = new JPasswordField();
	
	// reference to an instance of the login screen
	private JFrame loginScreen;
	
	// username is the key, and associated account is the value
	private HashMap <String, Account> accounts;
	
	// the file where the login information is stored
	File accountFile;
	
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("File");
	private final JMenuItem quitItem = new JMenuItem("Quit");
	
	GameController controller = new GameController();
	
	/**
	 * Constructor
	 * @param accts - reference to the accounts loaded from the account file
	 */
	public LoginScreen(HashMap <String, Account> accts, File file) {
		super("IST 412 Login");
		
		accounts = accts;
		accountFile = file;
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(leftPanel(), BorderLayout.WEST);
		getContentPane().add(rightPanel(), BorderLayout.EAST);
		
		loginScreen = this;
		
		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.add(quitItem);
		
		quitItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					writeFile();
					dispose();
				}
			}
		);
		
		passField.addActionListener(new LoginHandler());
	}
	
	private JPanel rightPanel() {	
		ResetButtonHandler resetHandler = new ResetButtonHandler();
		RESET_BUTTON.addActionListener(resetHandler);
		
		RegisterButtonHandler registerHandler = new RegisterButtonHandler();
		REGISTER_BUTTON.addActionListener(registerHandler);
		
		JPanel resetPanel = new JPanel();
		resetPanel.setLayout(new BorderLayout());
		resetPanel.add(RESET_BUTTON, BorderLayout.WEST);
		
		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(new BorderLayout());
		registerPanel.add(REGISTER_BUTTON, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		panel.add(REGISTER_LABEL);
		panel.add(registerPanel);
		
		panel.add(new JPanel());
		
		panel.add(RESET_LABEL);
		panel.add(resetPanel);
		
		return panel;
	}
	
	private JPanel leftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,2));
		
		panel.add(USER_LABEL);
		panel.add(userField);
		panel.add(PASS_LABEL);
		panel.add(passField);
		
		panel.add(new JPanel());
		panel.add(new JPanel());
		
		panel.add(leftButtonPanel());
		
		return panel;
	}
	
	private JPanel leftButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		LoginHandler loginHandler = new LoginHandler();
		LOGIN_BUTTON.addActionListener(loginHandler);
		
		QuitButtonHandler quitHandler = new QuitButtonHandler();
		QUIT_BUTTON.addActionListener(quitHandler);
		
		panel.add(LOGIN_BUTTON);
		panel.add(QUIT_BUTTON);
		
		return panel;
	}
	
	/**
	 * Verifies the provided username/password combination is correct
	 * @param user - the user name
	 * @param password - the password
	 * @return - true if yes, false if no
	 */
	private boolean login(String user, String password) {
		Account curAcct = accounts.get(user);
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch(NoSuchAlgorithmException nsae) {
			
		}
		
		byte [] passHash = md.digest(password.getBytes());
		
		if (curAcct != null) {
			if (curAcct.isLocked()) {
				JOptionPane.showMessageDialog(null, 
						"Account is locked. You must reset your account.", 
						"Warning!", JOptionPane.WARNING_MESSAGE);
				
				passField.setText(null);
				
				return false;
			}
			else {
				if (MessageDigest.isEqual(passHash, curAcct.getPassword())) {
					curAcct.successfulAttempt();
					
					userField.setText(null);
					passField.setText(null);
					
					return true;
				} else {
					curAcct.failedAttempt();
					
					passField.setText(null);
					
					if (curAcct.getAttempts() == 3) {
						curAcct.setLocked(true);
					}
					
					if (curAcct.isLocked()) {
						JOptionPane.showMessageDialog(null, 
								"Account is locked. You must reset your account.", 
								"Warning!", JOptionPane.WARNING_MESSAGE);
						
						return false;
					} else {
						JOptionPane.showMessageDialog(null, 
								"Entered password is incorrect.", 
								"Warning!", JOptionPane.WARNING_MESSAGE);
					}
					
					return false;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Username not found.", 
					"Warning!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	/**
	 * Write the account file to disk
	 */
	private void writeFile() {
		try {
			FileOutputStream fos = new FileOutputStream("accountFile.txt");
			
			String line;

			for (Account acct : accounts.values()) {
				StringBuffer sb = new StringBuffer();
				
				sb.append(acct.getUserName());
				sb.append(":");
				sb.append(getString(acct.getPassword()));
				sb.append(":");
				sb.append(getString(acct.getSecQuestion()));
				sb.append(":");
				
				if (acct.isLocked()) {
					sb.append("Y");
				} else {
					sb.append("N");
				}
				
				line = sb.toString();
				
				new PrintStream(fos).println (line);
			}
			
			fos.close();
		} catch (IOException io) {
			
		}
	}
	
	/**
	 * Handles the actions associated with clicking on the login button. If the
	 * login is successful, the calculation screen is displayed. Otherwise,
	 * the appropriate warning message is shown, and the user may try again.
	 * @author Jeff
	 *
	 */
	private class LoginHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setVisible(false);
			
			if (login(userField.getText(), passField.getText())) {
				GameScreen gameScreen = new GameScreen(loginScreen, controller);
				gameScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				gameScreen.setSize(625, 375);
				gameScreen.setVisible(true);
				gameScreen.setLocationRelativeTo(null);
			} else {
				loginScreen.setVisible(true);
			}
		}
	}
	
	/**
	 * Handles the actions associated with clicking on the quit button. In this
	 * case, the application is exited.
	 * @author Jeff
	 *
	 */
	private class QuitButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			writeFile();
			dispose();
		}
	}
	
	/**
	 * Handles the actions associated with the reset button. This opens the
	 * account reset screen.
	 * @author Jeff
	 *
	 */
	private class ResetButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setVisible(false);
			
			ResetScreen resetScreen = new ResetScreen(loginScreen, accounts);
			resetScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			resetScreen.setSize(335, 275);
			resetScreen.setVisible(true);
			resetScreen.setLocationRelativeTo(null);
		}
	}
	
	/**
	 * Handles the actions associated with the register button. this opens the
	 * account registration screen.
	 * @author Jeff
	 *
	 */
	private class RegisterButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setVisible(false);
			
			RegistrationScreen registerScreen = 
				new RegistrationScreen(loginScreen, accounts);
			registerScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			registerScreen.setSize(335, 275);
			registerScreen.setVisible(true);
			registerScreen.setLocationRelativeTo(null);
		}
	}
	
	/**
	 * Takes in the hash value (in bytes), and returns a string that contains
	 * the provided array of bytes.
	 * @param bytes - byte array that contains the hash value
	 * @return - the converted string
	 */
	private String getString(byte [] bytes) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		for (int i = 0; i < bytes.length; i++) {
			sb.append(bytes[i]);
			
			if (i+1 != bytes.length) {
				sb.append(",");
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
}
