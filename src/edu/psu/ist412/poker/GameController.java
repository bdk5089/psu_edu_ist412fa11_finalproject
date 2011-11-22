package edu.psu.ist412.poker;

import java.util.ArrayList;
/**
 * 
 * @author KennedyBD
 *
 */
public class GameController {

	public static void main(String[] args){
		GameController gc = new GameController();
		gc.createGame();
	}
	
	private ArrayList<Game> games = new ArrayList<Game>();
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public GameController() {
		super();
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
		//TODO: need to actually get current game instead of hard-coding
		return games.get(0);
	}
	
}
