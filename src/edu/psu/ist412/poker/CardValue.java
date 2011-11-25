package edu.psu.ist412.poker;

public class CardValue {
	private String value;
	private String abbrv;
	private int rank;
	private int rank2;
	/**
	 * @param value
	 * @param abbrv
	 * @param rank
	 */
	public CardValue(String value, String abbrv, int rank) {
		this.value = value;
		this.abbrv = abbrv;
		this.rank = rank;
	}
	public CardValue(String value, String abbrv, int rank, int rank2) {
		this.value = value;
		this.abbrv = abbrv;
		this.rank = rank;
		this.rank2 = rank2;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return abbrv;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the abbrv
	 */
	public String getAbbrv() {
		return abbrv;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}	
	
	public int getRank(boolean alt){
		if (alt){
			return rank2;
		}else{
			return rank;
		}
	}
	
}
