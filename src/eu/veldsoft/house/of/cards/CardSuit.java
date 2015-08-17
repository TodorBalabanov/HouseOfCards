package eu.veldsoft.house.of.cards;

/**
 * This enumeration holds all Suites
 * 
 * @author Panagiotis Peikidis
 * @version 1.1
 */
enum CardSuit {
	/**
	 * The Spades Suite
	 */
	SPADES("Spades", "s"),
	
	/**
	 * The Clubs Suite
	 */
	CLUBS("Clubs", "c"),
	
	/**
	 * The Diamonds Suite
	 */
	DIAMONDS("Diamonds", "d"),
	
	/**
	 * The Hearts Suite
	 */
	HEARTS("Hearts", "h");

	/**
	 * The name of the suite.
	 */
	private String name;

	/**
	 * The short name of the suite. Typically used for printing.
	 */
	private String shortName;

	/**
	 * Default Constructor
	 * 
	 * @param name
	 *            The name of the Suite
	 * @param shortName
	 *            The short name of the Suite
	 */
	private CardSuit(String name, String shortName) {
		this.name = name;
		this.shortName = shortName;
	}

	/**
	 * Returns the Suite's short name.<br>
	 * Spades = s<br>
	 * Clubs = c<br>
	 * Hearts = h<br>
	 * Diamonds = d<br>
	 * 
	 * @return The Suite's short name
	 */
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * Returns the Suite's name
	 * 
	 * @return The Suite's name
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
