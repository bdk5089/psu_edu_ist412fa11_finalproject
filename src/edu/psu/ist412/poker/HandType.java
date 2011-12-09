/**
 * 
 */
package edu.psu.ist412.poker;

/**
 * @author KennedyBD
 *
 */
public enum HandType {
	ROYAL_FLUSH ("Royal Flush",1),
	STRAIGHT_FLUSH ("Straight Flush",2),
	FOUR_KIND ("4 of a Kind",3),
	FULL_HOUSE("Full House",4),
	STRAIGHT("Straight",5),
	FLUSH ("Flush",6),
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
