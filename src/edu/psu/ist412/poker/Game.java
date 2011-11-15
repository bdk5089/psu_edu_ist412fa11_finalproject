package edu.psu.ist412.poker;

import java.util.ArrayList;

public class Game {
	private ArrayList<Player> players = new ArrayList<Player>();
	private Table table = new Table();
	private Player currentPlayer;
	/**
	 * 
	 */
	public Game() {
		super();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
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
		//TODO
	}
	
	public void startRound(){
		//TODO
	}
	
	public void dealFlop(){
		//TODO
	}
	
	public void dealTurn(){
		//TODO
	}
	
	public void dealRiver(){
		//TODO
	}
	
	public void gotoNextPlayer(){
		//TODO
	}
	
	
	
}
