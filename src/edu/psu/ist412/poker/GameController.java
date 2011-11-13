package edu.psu.ist412.poker;

import java.util.ArrayList;
/**
 * 
 * @author KennedyBD
 *
 */
public class GameController {

	
	private ArrayList<Game> games = new ArrayList<Game>();

	public GameController() {
		super();
	}
	/**
	 * createGame()
	 * Generates a new game that can be played during this session.
	 * A game is only stored in memory for the length of the session
	 * and can be accessed to display statistical information.
	 * 
	 * @return Game
	 */
	public Game createGame(){
		Game newGame = new Game();
		games.add(newGame);
		return newGame;
	}
	
}
