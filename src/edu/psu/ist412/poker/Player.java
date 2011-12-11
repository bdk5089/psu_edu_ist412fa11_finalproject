package edu.psu.ist412.poker;

/**
 * 
 * @author KennedyBD, AaronD
 *
 */
public class Player {
	private int position = 0;
	private Hand hand;
	private int amount = 0;
	private boolean isHuman = false;
	private int[] stats = new int[21];
	
	private PlayerStatus status;

	/**
	 * @param position
	 * @param amount
	 * @param isHuman
	 */
	public Player(int position, int amount, boolean isHuman) {
		super();
		this.position = position;
		this.amount = amount;
		this.isHuman = isHuman;
	}

	/**
	 * Returns string representation of Player.
	 */
	@Override
	public String toString() {
		return "Player [position=" + position + "]";
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the isHuman
	 */
	public boolean isHuman() {
		return isHuman;
	}

	/**
	 * @param isHuman the isHuman to set
	 */
	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}

	/**
	 * @return the status
	 */
	public PlayerStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

	/** 
	 * @return the hand
	 */
	public Hand clearHand() {
		hand = new Hand();
		return hand;
	}
	
	/**
	 * @return the hand
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	
	public void call(){
		//TODO
	}
	
	public void raise(int amount){
		//TODO
	}
	
	public void fold(){
		//TODO
	}
	
	public void allIn(){
		//TODO
	}

	/**
	 * @return the stats
	 */
	public int[] getStats() {
		return stats;
	}

	/**
	 * @param stats to set array to, overwrites data.
	 */
	public void setStats(int[] stats) {
		this.stats = stats;
	}
	/**
	 * @param stats to be added to current stat array.
	 */
	public void addStats(int[] stats){
		for(int i =0; i<stats.length;i++){
			this.stats[i]+=stats[i];
		}
	}
	
	/**
	 * @param fold if the game added to stats was a fold or a completed game
	 */
	public void addGame(boolean fold){
		if(fold){
			stats[20]++;
		}
		else{
			stats[0]++;
		}
	}	
}