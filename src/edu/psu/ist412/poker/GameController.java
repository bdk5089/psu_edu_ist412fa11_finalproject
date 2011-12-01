package edu.psu.ist412.poker;

import java.util.ArrayList;

/**
 * 
 * @author KennedyBD, JRockwell
 *
 */
public class GameController {
	
	private ArrayList<Game> games;
	private ArrayList<Player> players;
	
	public static void main(String[] args){
		GameController gc = new GameController();
		gc.createGame();
	}
	
	public GameController() {
		super();
	    games = new ArrayList<Game>();
		players = new ArrayList<Player>();
		players.add(new Player(1,100,true));
		//players.get(players.size()-1).setTablePosition(JPanel panel1);
		//players.add(new Player(2,100,false));
		//players.get(players.size()-1).setTablePosition(JPanel panel2);
	}
	
	/**
	 * createGame()
	 * Generates a new game that can be played during this session.
	 * A game is only stored in memory for the length of the session
	 * and can be accessed to display statistical information.
	 */
	public void createGame(){
		games.add(new Game(players));
		games.get(games.size()-1).startGame();
	}
	
	public Game getCurrentGame() {
		return games.get(games.size()-1);
	}
	
}