package eu.veldsoft.house.of.cards;

/**
 * When a house reaches the given number of score, it is emptied and the player
 * gains points.
 * 
 * @author Panagiotis Peikidis
 * @version 1.1
 *
 */
final class ScoreRule implements ActionRule {

	/**
	 * The score threshold for a House.
	 */
	private int scoreThreshold;

	/**
	 * Depending on the Suit of the House, the score changes.
	 */
	private int score = 0;

	/**
	 * The pointSystem for calculating the House points.
	 */
	private CardPointSystem pointSystem;

	/**
	 * Constructs a ScoreRule with the given numver of score threshold.
	 * 
	 * @param scoreThreshold
	 *            the score threshold
	 */
	public ScoreRule(int scoreThreshold) {
		this.scoreThreshold = scoreThreshold;
		this.pointSystem = new HoCPointSystem();
	}

	public boolean applies(House house) {
		this.score = 0;
		int total = 0;
		for (Card card : house.getCards()) {
			total += pointSystem.getPoints(card);
		}

		if (total == this.scoreThreshold) {
			house.emptyAllCards();

			switch (house.getSuit()) {
			case HEARTS:
				this.score += 10;
			case DIAMONDS:
				this.score += 10;
			case CLUBS:
				this.score += 10;
			case SPADES:
				this.score += 10;
			}

			return true;
		} else if (total > this.scoreThreshold) {
			house.closeHouse();

			return true;
		}

		return false;
	}

	public boolean endsGame() {
		return false;
	}

	public int score() {
		return this.score;
	}

	public String getDescription() {
		return "House had exactly " + this.scoreThreshold
				+ " points so the player gains " + this.score + " points.";
	}

	public String getName() {
		return "Score Rule";
	}
}
