package edu.psu.ist412.view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.Game;
import edu.psu.ist412.poker.GameController;


public class GameScreen extends JFrame{
	
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("File");
	private final JMenuItem logoutItem = new JMenuItem("Logout");
	
	private JButton nextButton = new JButton("Next");
	private JButton newGameButton = new JButton("New Game");
	
	private JLabel card1 = new JLabel();
	private JLabel card2 = new JLabel();
	private JLabel card3 = new JLabel();
	private JLabel card4 = new JLabel();
	private JLabel card5 = new JLabel();
	
	//TODO:  determine if we're going to have the save option
	//private final JMenuItem saveLogoutItem = new JMenuItem("Save and Logout");
	
	private final JMenuItem statisticsItem = new JMenuItem("Show/Hide Statistics");
	
	final JPanel statisticsPanel = statisticsPanel();
	
	private boolean showStatistics = false;
	
	// a reference to the login screen
	private JFrame loginScreen;
	
	private JPanel gamePanel;
	
	GameController gc;

	public GameScreen(JFrame login, GameController controller) {
		super("IST 412 Texas Hold'em");
		
		loginScreen = login;
		
		gc = controller;
		
		newGame();
		
		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.add(statisticsItem);
		fileMenu.add(logoutItem);
		//fileMenu.add(saveLogoutItem);
		
		newGameButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						newGame();
					}
				}
			);
			
			nextButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						Game currentGame = gc.getCurrentGame();
						
						switch (currentGame.getState()) {
						case START:
							currentGame.dealFlop();
							card1.setVisible(true);
							card2.setVisible(true);
							card3.setVisible(true);
							show();
							currentGame.setState(Game.GameState.FLOP);
							break;
						case FLOP:
							currentGame.dealTurn();
							card4.setVisible(true);
							show();
							currentGame.setState(Game.GameState.TURN);
							break;
						case TURN:
							currentGame.dealRiver();
							card5.setVisible(true);
							show();
							currentGame.setState(Game.GameState.RIVER);
							nextButton.setEnabled(false);
							break;
						case RIVER:
							break;
						}
					}
				}
			);
		
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
	
	private JPanel gamePanel() {
		JPanel gamePanel = new JPanel();
		
		gc.createGame();
		
		gamePanel.setLayout(new BorderLayout());
		
		gamePanel.add(cpuPanel(), BorderLayout.NORTH);
		gamePanel.add(probabilityPanel(), BorderLayout.EAST);
		gamePanel.add(southPanel(), BorderLayout.SOUTH);
		gamePanel.add(statisticsPanel, BorderLayout.WEST);
		gamePanel.add(communityPanel(), BorderLayout.CENTER);
		
		statisticsPanel.setVisible(false);
		
		return gamePanel;
	}
	
	private void newGame() {
		getContentPane().removeAll();
		gamePanel = gamePanel();
		add(gamePanel);
		show();
		
		gc.getCurrentGame().setState(Game.GameState.START);
		nextButton.setEnabled(true);
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
	
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		panel.add(newGameButton);
		panel.add(nextButton);
		
		return panel;
	}
	
	private JPanel southPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2,1));
		
		panel.add(playerPanel());
		panel.add(buttonPanel());
		
		return panel;
	}
	
	private JPanel cpuPanel() {		
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		for (Card c : gc.getCurrentGame().getPlayers().get(1).getHand().getCards()) {
			JLabel card = new JLabel();
			card.setIcon(c.getImage());
			panel.add(card);
		}
		
		return panel;
	}
	
	private JPanel communityPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		ArrayList<Card> cards = gc.getCurrentGame().getTableCards();
		
		card1.setIcon(cards.get(0).getImage());
		panel.add(card1);
		card1.setVisible(false);
		
		card2.setIcon(cards.get(1).getImage());
		panel.add(card2);
		card2.setVisible(false);
		
		card3.setIcon(cards.get(2).getImage());
		panel.add(card3);
		card3.setVisible(false);
		
		card4.setIcon(cards.get(3).getImage());
		panel.add(card4);
		card4.setVisible(false);
		
		card5.setIcon(cards.get(4).getImage());
		panel.add(card5);
		card5.setVisible(false);
		
//		for (Card c : gc.getCurrentGame().getTableCards()) {
//			JLabel card = new JLabel();
//			card.setIcon(c.getImage());
//			panel.add(card);
//		}
		
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
