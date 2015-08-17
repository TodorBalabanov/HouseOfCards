package eu.veldsoft.house.of.cards;

/**
 * An Abstract Data Type that holds information about a Playing Card
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
public final class Card implements Cloneable {
	/**
	 * The suite of the card.
	 */
	private CardSuit suit;

	/**
	 * The rank of the card.
	 */
	private CardRank rank;

	/**
	 * Default Constructor.
	 * 
	 * @param suit
	 *            The cards suit
	 * @param rank
	 *            The cards rank
	 */
	public Card(CardSuit suit, CardRank rank) {
		super();
		this.suit = suit;
		this.rank = rank;
	}

	/**
	 * Returns the cards suite.
	 * 
	 * @return the cards suite
	 */
	public CardSuit getSuit() {
		return suit;
	}

	/**
	 * Returns that cards rank.
	 * 
	 * @return the cards rank
	 */
	public CardRank getRank() {
		return rank;
	}

	/**
	 * Returns the suite and rank short names.
	 * 
	 * @return the suite and rank short names.
	 */
	@Override
	public String toString() {
		return this.rank + " of " + this.suit;
	}

	/**
	 * Returns the shortName of the card. Typically to be used for loading
	 * resources (images etc.).
	 * 
	 * @return the shortName of the card
	 */
	public String getShortName() {
		return this.suit.getShortName() + this.rank.getShortName();
	}

	/**
	 * 
	 */
	public Card clone() {
		return new Card(this.suit, this.rank);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Card other = (Card) obj;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (suit == null) {
			if (other.suit != null)
				return false;
		} else if (!suit.equals(other.suit))
			return false;
		return true;
	}
}
