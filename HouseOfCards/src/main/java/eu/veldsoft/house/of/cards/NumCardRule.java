package eu.veldsoft.house.of.cards;

/**
 * When a House contains the given card threshold with score lower than the
 * given score threshold, then the house empties and the player gains points.
 * 
 * @author Panagiotis Peikidis
 * @version 1.1
 *
 */
final class NumCardRule implements ActionRule {

	/**
	 * The score of the Num Card Rule when applied.
	 */
	public static final int NUM_CARD_SCORE = 50;

	/**
	 * The number of Cards for the House in order for this Rule to apply.
	 */
	private int numCards;

	/**
	 * The score threshold for this Rule.
	 */
	private int scoreThreshold;

	/**
	 * Constructs the Rule with the given number of maximum Cards for a House.
	 * 
	 * @param numCards
	 *            the number of cards threshold for this Rule
	 * @param scoreThreshold
	 *            the score threshold for this Rule
	 * 
	 */
	public NumCardRule(int numCards, int scoreThreshold) {
		this.numCards = numCards;
		this.scoreThreshold = scoreThreshold;
	}

	public boolean applies(House house) {
		if (house.getCards().size() == this.numCards) {
			if (house.getPoints() <= this.scoreThreshold) {
				house.emptyAllCards();

				return true;
			}
		}

		return false;
	}

	public boolean endsGame() {
		return false;
	}

	public int score() {
		return NUM_CARD_SCORE;
	}

	public String getDescription() {
		return "House closed because it past the threshold of " + this.numCards
				+ " cards.";
	}

	public String getName() {
		return this.numCards + " Cards Rule";
	}
}
