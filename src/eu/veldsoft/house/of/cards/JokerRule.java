package eu.veldsoft.house.of.cards;

/**
 * When a Joker is added in a House, either the game is over or the players gain
 * points depending if the Joker is of the same Suit as the House.
 * 
 * @author Panagiotis Peikidis
 * @version 1.1
 *
 */
final class JokerRule implements ActionRule {

	/**
	 * The score of the Joker when it applies.
	 */
	public static final int JOKER_SCORE = 100;

	/**
	 * Holds the result of this Rule.
	 */
	private boolean endsGame = false;

	/**
	 * Holds the score of this Rule.
	 */
	private int score = 0;

	public boolean applies(House house) {
		/* 
		 * Get last card
		 */
		Card card = house.getCards().get(house.getCards().size() - 1);
		this.score = 0;
		if (card.getRank() == CardRank.JOKER) {
			if (card.getSuit() == house.getSuit()) {
				house.emptyAllCards();
				this.score = JOKER_SCORE;
			} else {
				this.endsGame = true;
			}

			return true;
		}

		return false;
	}

	public boolean endsGame() {
		return this.endsGame;
	}

	public int score() {
		return this.score;
	}

	public String getDescription() {
		return "Joker Card in wrong House";
	}

	public String getName() {
		return "Joker Rule";
	}
}
