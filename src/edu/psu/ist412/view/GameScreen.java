package edu.psu.ist412.view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.GameController;


public class GameScreen extends JFrame{
	
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("File");
	private final JMenuItem logoutItem = new JMenuItem("Logout");
	
	//TODO:  determine if we're going to have the save option
	//private final JMenuItem saveLogoutItem = new JMenuItem("Save and Logout");
	
	private final JMenuItem statisticsItem = new JMenuItem("Show/Hide Statistics");
	
	private boolean showStatistics = false;
	
	// a reference to the login screen
	private JFrame loginScreen;
	
	GameController gc;

	public GameScreen(JFrame login, GameController controller) {
		super("IST 412 Texas Hold'em");
		
		loginScreen = login;
		
		gc = controller;
		gc.createGame();
		
		getContentPane().setLayout(new BorderLayout());
		
		final JPanel statisticsPanel = statisticsPanel();
		
		add(cpuPanel(), BorderLayout.NORTH);
		add(probabilityPanel(), BorderLayout.EAST);
		add(playerPanel(), BorderLayout.SOUTH);
		add(statisticsPanel, BorderLayout.WEST);
		add(communityPanel(), BorderLayout.CENTER);
		
		statisticsPanel.setVisible(false);
		
		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.add(statisticsItem);
		fileMenu.add(logoutItem);
		//fileMenu.add(saveLogoutItem);
		
		logoutItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					int choice = JOptionPane.showConfirmDialog(null, 
							"Are you sure you want to logout?", 
							"Warning!", JOptionPane.YES_NO_OPTION);
					if (choice == 0) {
						logout();
					}
				}
			}
		);
		
//		saveLogoutItem.addActionListener(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent event) {
//					int choice = JOptionPane.showConfirmDialog(null, 
//							"Are you sure you want to save and logout?", 
//							"Warning!", JOptionPane.YES_NO_OPTION);
//					if (choice == 0) {
//						logout();
//					}
//				}
//			}
//		);
		
		statisticsItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (showStatistics) {
						showStatistics = false;
						statisticsPanel.setVisible(false);
						show();
					} else {
						showStatistics = true;
						statisticsPanel.setVisible(true);
						show();
					}
				}
			}
		);
		
	}
	
	/**
	 * Gets rid of the game window, and brings back the login screen.
	 */
	private void logout() {
		dispose();		
		
		loginScreen.setVisible(true);
		loginScreen.toFront();
	}
	
	private JPanel playerPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		for (Card c : gc.getCurrentGame().getPlayers().get(0).getHand().getCards()) {
			JLabel card = new JLabel();
			card.setIcon(c.getImage());
			panel.add(card);
		}
		
		return panel;
	}
	
	private JPanel cpuPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		JLabel card1 = new JLabel();
		card1.setIcon(new ImageIcon(getClass().getResource("graphics/back.png")));
		
		panel.add(card1);
		
		JLabel card2 = new JLabel();
		card2.setIcon(new ImageIcon(getClass().getResource("graphics/back.png")));
		
		panel.add(card2);
		
		return panel;
	}
	
	private JPanel communityPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		for (Card c : gc.getCurrentGame().getTable().getCards()) {
			JLabel card = new JLabel();
			card.setIcon(c.getImage());
			panel.add(card);
		}
		
		return panel;
	}
	
	private JPanel probabilityPanel() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("win probability"));
		
		return panel;
	}
	
	private JPanel statisticsPanel() {
		JPanel panel = new JPanel();
		
		panel.add(new JLabel("stats"));
		
		return panel;
	}

}
