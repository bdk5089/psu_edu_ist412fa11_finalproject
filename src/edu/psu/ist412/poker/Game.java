package edu.psu.ist412.poker;

import java.util.ArrayList;

/**
 * 
 * @author KennedyBD
 *
 */
public class Game {
	
	public enum GameState {START, FLOP, TURN, RIVER, END};
	
	private GameState state = GameState.START;
	private ArrayList<Card> tableCards = new ArrayList<Card>();
	
	private ArrayList<Player> players;
	private Table table;
	private Deck deck;
	private Player currentPlayer;
	/**
	 * 
	 */
	public Game(ArrayList<Player> players) {
		super();	
		this.players = players;
	}
	
	/**
	 * Returns string representation of Game.  How many players, the cards on the table,
	 * and the current player.
	 */
	@Override
	public String toString() {
		return "Game [players=" + players + ", table=" + table
				+ ", currentPlayer=" + currentPlayer + "]";
	}
	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}
	
	public void startGame(){
		startRound();
		dealHands();
	}
	
	public void startRound(){
		table = new Table();	
		deck = new Deck();	
		for (int i=0;i<players.size();i++){
			Hand h = players.get(i).clearHand();
			h.setDeck(deck);
			h.setTable(table);
		}
	}
	
	public void dealHands(){
		for (int i=0;i<players.size();i++){
			Hand h = players.get(i).getHand();
			deck.dealCard(h);
			deck.dealCard(h);
		}
		
		for (int i = 1; i < 6; i++) {
			tableCards.add(deck.getCardAt(i));
		}
	}
	
	public void dealFlop(){
		deck.dealCard(table);
		deck.dealCard(table);
		deck.dealCard(table);
	}
	
	public void dealTurn(){
		deck.dealCard(table);
	}
	
	public void dealRiver(){
		deck.dealCard(table);
	}
	
	public void gotoNextPlayer(){
		//TODO
	}
	
	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}
	public ArrayList<Card> getTableCards() {
		return tableCards;
	}
	
}