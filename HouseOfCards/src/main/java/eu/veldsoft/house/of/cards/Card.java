package eu.veldsoft.house.of.cards;

/**
 * An Abstract Data Type that holds information about a Playing Card
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
final class Card implements Cloneable {
	/**
	 * The suite of the card.
	 */
	private CardSuit suit;

	/**
	 * The rank of the card.
	 */
	private CardRank rank;

	private static final Card cards[] = {
			new Card(CardSuit.SPADES, CardRank.ACE),
			new Card(CardSuit.SPADES, CardRank.KING),
			new Card(CardSuit.SPADES, CardRank.QUEEN),
			new Card(CardSuit.SPADES, CardRank.JACK),
			new Card(CardSuit.SPADES, CardRank.TEN),
			new Card(CardSuit.SPADES, CardRank.NINE),
			new Card(CardSuit.SPADES, CardRank.EIGHT),
			new Card(CardSuit.SPADES, CardRank.SEVEN),
			new Card(CardSuit.SPADES, CardRank.SIX),
			new Card(CardSuit.SPADES, CardRank.FIVE),
			new Card(CardSuit.SPADES, CardRank.FOUR),
			new Card(CardSuit.SPADES, CardRank.THREE),
			new Card(CardSuit.SPADES, CardRank.TWO),

			new Card(CardSuit.CLUBS, CardRank.ACE),
			new Card(CardSuit.CLUBS, CardRank.KING),
			new Card(CardSuit.CLUBS, CardRank.QUEEN),
			new Card(CardSuit.CLUBS, CardRank.JACK),
			new Card(CardSuit.CLUBS, CardRank.TEN),
			new Card(CardSuit.CLUBS, CardRank.NINE),
			new Card(CardSuit.CLUBS, CardRank.EIGHT),
			new Card(CardSuit.CLUBS, CardRank.SEVEN),
			new Card(CardSuit.CLUBS, CardRank.SIX),
			new Card(CardSuit.CLUBS, CardRank.FIVE),
			new Card(CardSuit.CLUBS, CardRank.FOUR),
			new Card(CardSuit.CLUBS, CardRank.THREE),
			new Card(CardSuit.CLUBS, CardRank.TWO),

			new Card(CardSuit.DIAMONDS, CardRank.ACE),
			new Card(CardSuit.DIAMONDS, CardRank.KING),
			new Card(CardSuit.DIAMONDS, CardRank.QUEEN),
			new Card(CardSuit.DIAMONDS, CardRank.JACK),
			new Card(CardSuit.DIAMONDS, CardRank.TEN),
			new Card(CardSuit.DIAMONDS, CardRank.NINE),
			new Card(CardSuit.DIAMONDS, CardRank.EIGHT),
			new Card(CardSuit.DIAMONDS, CardRank.SEVEN),
			new Card(CardSuit.DIAMONDS, CardRank.SIX),
			new Card(CardSuit.DIAMONDS, CardRank.FIVE),
			new Card(CardSuit.DIAMONDS, CardRank.FOUR),
			new Card(CardSuit.DIAMONDS, CardRank.THREE),
			new Card(CardSuit.DIAMONDS, CardRank.TWO),

			new Card(CardSuit.HEARTS, CardRank.ACE),
			new Card(CardSuit.HEARTS, CardRank.KING),
			new Card(CardSuit.HEARTS, CardRank.QUEEN),
			new Card(CardSuit.HEARTS, CardRank.JACK),
			new Card(CardSuit.HEARTS, CardRank.TEN),
			new Card(CardSuit.HEARTS, CardRank.NINE),
			new Card(CardSuit.HEARTS, CardRank.EIGHT),
			new Card(CardSuit.HEARTS, CardRank.SEVEN),
			new Card(CardSuit.HEARTS, CardRank.SIX),
			new Card(CardSuit.HEARTS, CardRank.FIVE),
			new Card(CardSuit.HEARTS, CardRank.FOUR),
			new Card(CardSuit.HEARTS, CardRank.THREE),
			new Card(CardSuit.HEARTS, CardRank.TWO),

			new Card(CardSuit.SPADES, CardRank.JOKER),
			new Card(CardSuit.CLUBS, CardRank.JOKER),
			new Card(CardSuit.DIAMONDS, CardRank.JOKER),
			new Card(CardSuit.HEARTS, CardRank.JOKER), };

	static Card instanceOf(CardSuit suit, CardRank rank) {
		for (Card card : cards) {
			if (card.suit == suit && card.rank == rank) {
				return card;
			}
		}

		return null;
	}

	/**
	 * Default Constructor.
	 * 
	 * @param suit
	 *            The cards suit
	 * @param rank
	 *            The cards rank
	 */
	Card(CardSuit suit, CardRank rank) {
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
		return instanceOf(suit, rank);
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
