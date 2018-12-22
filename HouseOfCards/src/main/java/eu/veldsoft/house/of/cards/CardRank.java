package eu.veldsoft.house.of.cards;

/**
 * CardRank holds all valid Card Ranks and the scores for each.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
enum CardRank {
	/**
	 * The Ace Rank
	 */
	ACE("Ace", "a"),
	/**
	 * The King Rank
	 */
	KING("King", "k"),
	/**
	 * The Queen Rank
	 */
	QUEEN("Queen", "q"),
	/**
	 * The Jack Rank
	 */
	JACK("Jack", "j"),
	/**
	 * The 10 Rank
	 */
	TEN("Ten", "10"),
	/**
	 * The 9 Rank
	 */
	NINE("Nine", "9"),
	/**
	 * The 8 Rank
	 */
	EIGHT("Eight", "8"),
	/**
	 * The 7 Rank
	 */
	SEVEN("Seven", "7"),
	/**
	 * The 6 Rank
	 */
	SIX("Six", "6"),
	/**
	 * The 5 Rank
	 */
	FIVE("Five", "5"),
	/**
	 * The 4 Rank
	 */
	FOUR("Four", "4"),
	/**
	 * The 3 Rank
	 */
	THREE("Three", "3"),
	/**
	 * The 2 Rank
	 */
	TWO("Two", "2"),
	/**
	 * The Joker Rank. A Jokers Suite is always Spades.
	 */
	JOKER("Joker", "jo");

	/**
	 * The name of the Rank.
	 */
	private String name;

	/**
	 * The short name of the Rank. Typically used for printing.
	 */
	private String shortName;

	/**
	 * Constructs a CardRank with the given short name
	 * 
	 * @param name
	 *            The name of the Rank
	 * @param shortName
	 *            The short name of the Rank
	 */
	private CardRank(String name, String shortName) {
		this.name = name;
		this.shortName = shortName;
	}

	/**
	 * Returns the Rank's short name.<br>
	 * 
	 * @return The Rank's short name
	 */
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * Returns the Rank's name
	 * 
	 * @return The Rank's name
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
