package edu.psu.ist412.poker;

public class HandData {
	private HandType handType;
	private int rank;
	private static double probability;
	private boolean hasHand;
	
	
	public HandData(HandType handType) {
		super();
		this.handType = handType;
	}


		
	@Override
	public String toString() {
		return "HandData [handType=" + handType.getLabel() + ", rank=" + rank
				+ ", probability=" + probability + "]";
	}


	public HandType getHandType() {
		return handType;
	}


	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public double getProbability() {
		return probability;
	}

	public static void setProbability(double prob) {
		probability = prob;
	}

	public boolean hasHand() {
		return hasHand;
	}

	public void hasHand(boolean hasHand) {
		this.hasHand = hasHand;
	}

	
	
}
