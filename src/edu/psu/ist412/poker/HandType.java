/**
 * 
 */
package edu.psu.ist412.poker;

/**
 * @author KennedyBD
 * Provides information about the order/rank of 
 * each type of poker hand combination.
 * Royal Flush has a rank of 1.
 * ...
 * Two of a Kind has a rank of 9.
 */
public enum HandType {
	
	ROYAL_FLUSH ("Royal Flush",1),
	STRAIGHT_FLUSH ("Straight Flush",2),
	FOUR_KIND ("4 of a Kind",3),
	FULL_HOUSE("Full House",4),
	FLUSH ("Flush",5),
	STRAIGHT("Straight",6),
	THREE_KIND("3 of a Kind",7),
	TWO_PAIR("2 Pair",8),
	TWO_KIND("2 of a Kind",9);
	
	
	private final String label;
	private final int rank;
	
	private HandType(String label, int rank){
		this.label = label;
		this.rank = rank;
	}

	public String getLabel() {
		return label;
	}

	public int getRank() {
		return rank;
	}
	
	
}
