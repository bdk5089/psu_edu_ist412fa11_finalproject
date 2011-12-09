package edu.psu.ist412.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.Game;
import edu.psu.ist412.poker.GameController;
import edu.psu.ist412.poker.HandData;
import edu.psu.ist412.poker.HandType;
import edu.psu.ist412.poker.Player;

/**
 * This class builds the main game screen, and allows the game to be played.
 * @author Jeff
 *
 */
public class GameScreen extends JFrame{
	
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("File");
	private final JMenuItem logoutItem = new JMenuItem("Logout");
	
	private JButton nextButton = new JButton("Next");
	private JButton foldButton = new JButton("Fold");
	
	// used to hold the 5 community cards
	private JLabel card1 = new JLabel();
	private JLabel card2 = new JLabel();
	private JLabel card3 = new JLabel();
	private JLabel card4 = new JLabel();
	private JLabel card5 = new JLabel();
	
	private final JMenuItem statisticsItem = new JMenuItem();
	
	final JPanel statisticsPanel = statisticsPanel();
	JPanel probabilityPanel;
	final JPanel cpuPanel = cpuPanelHidden();
	
	private boolean showStatistics = false;
	
	// a reference to the login screen
	private JFrame loginScreen;
	
	private JPanel gamePanel;
	
	// a reference to the game controller
	GameController gc;

	/**
	 * This constructor builds the JFrame that holds the main game components.
	 * 
	 * @param login - the login screen that created this screen
	 * @param controller - the game controller that controls the back end
	 */
	public GameScreen(JFrame login, GameController controller) {
		super("IST 412 Texas Hold'em");
		
		loginScreen = login;
		
		gc = controller;
		
		newGame();
		
		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.add(statisticsItem);
		fileMenu.add(logoutItem);
		
		foldButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						gamePanel.remove(cpuPanel);
						gamePanel.add(cpuPanelRevealed(), BorderLayout.NORTH);
						show();
						
						Player player = gc.getCurrentGame().getPlayers().get(0);
						
						try {
							if (gc.getCurrentGame().getWinner().equals(player)) {
								JOptionPane.showMessageDialog(null, 
										"You made a bad decision.", 
										"FYI", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, 
										"You made a good decision.", 
										"FYI", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
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
						
						gamePanel.remove(probabilityPanel);
						probabilityPanel = probabilityPanel();
						gamePanel.add(probabilityPanel, BorderLayout.EAST);
						
						show();
						
						currentGame.setState(Game.GameState.FLOP);
						break;
					case FLOP:
						currentGame.dealTurn();
						card4.setVisible(true);
						
						gamePanel.remove(probabilityPanel);
						probabilityPanel = probabilityPanel();
						gamePanel.add(probabilityPanel, BorderLayout.EAST);
						
						show();
						
						currentGame.setState(Game.GameState.TURN);
						break;
					case TURN:
						currentGame.dealRiver();
						card5.setVisible(true);
	
						currentGame.setState(Game.GameState.RIVER);
						
						gamePanel.remove(probabilityPanel);
						probabilityPanel = probabilityPanel();
						gamePanel.add(probabilityPanel, BorderLayout.EAST);
						
						show();
						break;
					case RIVER:
						currentGame.setState(Game.GameState.END);
						
						foldButton.setEnabled(false);
						nextButton.setText("New Game");
						gamePanel.remove(cpuPanel);
						gamePanel.add(cpuPanelRevealed(), BorderLayout.NORTH);
						
						show();
						break;
					case END:
						newGame();
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
		
		statisticsItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (showStatistics) {
						showStatistics = false;
						statisticsPanel.setVisible(false);
						statisticsItem.setText("Show Statistics");
						show();
					} else {
						showStatistics = true;
						statisticsPanel.setVisible(true);
						statisticsItem.setText("Hide Statistics");
						show();
					}
				}
			}
		);
		
	}
	
	/**
	 * Creates the main panel for holding the player's cards, the cpu's cards,
	 * the community cards, the user statistics, and the hand probability.
	 * @return - the created panel
	 */
	private JPanel gamePanel() {
		JPanel gamePanel = new JPanel();
		
		gc.createGame();
		
		probabilityPanel = probabilityPanel();
		
		gamePanel.setLayout(new BorderLayout());
		
		gamePanel.add(cpuPanel, BorderLayout.NORTH);
		gamePanel.add(probabilityPanel, BorderLayout.EAST);
		gamePanel.add(southPanel(), BorderLayout.SOUTH);
		gamePanel.add(statisticsPanel, BorderLayout.WEST);
		gamePanel.add(communityPanel(), BorderLayout.CENTER);
		
		statisticsPanel.setVisible(false);
		
		return gamePanel;
	}
	
	/**
	 * Reinitializes the game.
	 */
	private void newGame() {
		statisticsItem.setText("Show Statistics");
		showStatistics = false;
		
		getContentPane().removeAll();
		gamePanel = gamePanel();
		add(gamePanel);
		show();
		
		gc.getCurrentGame().setState(Game.GameState.START);
		nextButton.setText("Next");
		foldButton.setEnabled(true);
	}
	
	/**
	 * Gets rid of the game window, and brings back the login screen.
	 */
	private void logout() {
		dispose();		
		
		loginScreen.setVisible(true);
		loginScreen.toFront();
	}
	
	/**
	 * Creates the panel that holds the player's cards.
	 * @return - the player panel
	 */
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
	
	/**
	 * Creates the panel that holds the game buttons.
	 * @return - the button panel
	 */
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		foldButton.setText("Fold");
		
		panel.add(foldButton);
		panel.add(nextButton);
		
		return panel;
	}
	
	/**
	 * Creates the panel that holds the players cards and the game buttons.
	 * @return - the bottom most panel
	 */
	private JPanel southPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2,1));
		
		panel.add(playerPanel());
		panel.add(buttonPanel());
		
		return panel;
	}
	
	/**
	 * Creates the panel that holds the cpu's cards (face up)
	 * @return - the cpu panel that shows the cpu's cards
	 */
	private JPanel cpuPanelRevealed() {		
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		for (Card c : gc.getCurrentGame().getPlayers().get(1).getHand().getCards()) {
			JLabel card = new JLabel();
			card.setIcon(c.getImage());
			panel.add(card);
		}
		
		return panel;
	}
	
	/**
	 * Creates the panel that holds the cpu's cards (face down)
	 * @return - the cpu panel that shows the back of the cpu's cards
	 */
	private JPanel cpuPanelHidden() {
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
	
	/**
	 * Creates the panel that holds the community cards.
	 * @return - the community card panel
	 */
	private JPanel communityPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		ArrayList<Card> cards = gc.getCurrentGame().getTableCards();
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource("graphics/blank.png")));
		
		panel.add(label);
		
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
		
		return panel;
	}
	
	/**
	 * Creates the panel that holds the hand probability.
	 * @return - the probability panel
	 */
	private JPanel probabilityPanel() {
		JPanel panel = new JPanel();
		
		Map<HandType, HandData> probability = 
			gc.getCurrentGame().getPlayers().get(0).getHand().getProbability();
		
		Set <HandType> keys = probability.keySet();
		
		JTextArea area = new JTextArea(10,1);
		area.append("Hand Probability:");
		area.append("\n\n");
		
		for (HandType key : keys) {
			if (probability.get(key).getProbability() != 0) {
				area.append(key.getLabel() + ":");
				area.append("     " + String.format("%3.2f", 
						probability.get(key).getProbability() * 100) + "% \n");
			}
		}
		
		area.setEditable(false);
		area.setBackground(new Color(238,238,238));
		panel.add(area);
		
		return panel;
	}
	
	/**
	 * Creates the panel that holds the user statistics.
	 * @return - the statistics panel
	 */
	private JPanel statisticsPanel() {
		JPanel panel = new JPanel();
		
		panel.add(new JLabel("stats"));
		
		return panel;
	}

}
