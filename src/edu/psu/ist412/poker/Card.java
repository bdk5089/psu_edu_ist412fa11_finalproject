package edu.psu.ist412.poker;

import javax.swing.ImageIcon;

/**
 * 
 * @author KennedyBD
 *
 */
public class Card {

	private CardSuit suit;
	private CardValue value;
	
	/**
	 * Card Constructor is used to instantiate a card in a deck
	 * Must set the suit and value.
	 * @param suit
	 * @param value
	 */
	public Card(CardSuit suit, CardValue value) {
		this.suit = suit;
		this.value = value;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Card ["+value+" of "+ suit + "]";
	}

	/**
	 * @return the suit
	 */
	public CardSuit getSuit() {
		return suit;
	}
	/**
	 * @return the value
	 */
	public CardValue getValue() {
		return value;
	}

	/**
	 * @return the image associated with the card
	 */
	public ImageIcon getImage() {
		String cardName = "/edu/psu/ist412/view/graphics/" + value.toString() + 
			suit.toString() + ".png";
		
		return new ImageIcon(getClass().getResource(cardName));
	}
	
}
