package edu.psu.ist412.program;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.JFrame;

import edu.psu.ist412.logon.Account;
import edu.psu.ist412.view.LoginScreen;

/**
 * This is the main class for lab 2.
 * @author Jeff
 *
 */
public class Project {

	// username is the key, and associated account is the value
	private static HashMap <String, Account> accountInfo = 
		new HashMap <String, Account> ();
	
	private static File accountFile;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadFile();

		LoginScreen loginScreen = new LoginScreen(accountInfo, accountFile);
		loginScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		loginScreen.setSize(550, 195);
		loginScreen.setVisible(true);
		loginScreen.setLocationRelativeTo(null);
	}
	
	/**
	 * Load the account file into memory
	 */
	private static void loadFile() {
		try {
			accountFile = new File("accountFile.txt");
			accountFile.createNewFile();
			
			FileInputStream fis = new FileInputStream(accountFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				String [] info = line.split(":");
				
				Account acct = new Account();
				acct.setUserName(info[0]);
				acct.setPassword(getByteArray(info[1]));
				acct.setSecQuestion(getByteArray(info[2]));
				
				if (info[3].equals("Y")) {
					acct.setLocked(true);
				}
				
				accountInfo.put(info[0], acct);
			}
			
			fis.close();
		} catch (IOException io) {
			
		}
	}
	
	/**
	 * Takes in the string, which is an array of byte values, and returns
	 * a byte array that contains the same values.
	 * @param str - the array of byte values contained in a string
	 * @return - the converted byte array
	 */
	private static byte [] getByteArray(String str) {
		String [] string = str.substring(1, str.length()-1).split(",");
		byte [] bytes = new byte [string.length];
		
		for (int i = 0; i < string.length; i++) {
			bytes[i] = new Byte(string[i]);
		}
		
		return bytes;
	}

}
