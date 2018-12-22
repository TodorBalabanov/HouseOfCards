package eu.veldsoft.house.of.cards;

/**
 * A CardPointSystem implementation for House Of Cards.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
class HoCPointSystem implements CardPointSystem {

	public int getPoints(Card card) {
		switch (card.getRank()) {
		case ACE:
			return 11;
		case KING:
		case QUEEN:
		case JACK:
		case TEN:
			return 10;
		case NINE:
			return 9;
		case EIGHT:
			return 8;
		case SEVEN:
			return 7;
		case SIX:
			return 6;
		case FIVE:
			return 5;
		case FOUR:
			return 4;
		case THREE:
			return 3;
		case TWO:
			return 2;
		case JOKER:
			return 0;
		}
		// FIXME Better check this out.
		return -1;
	}

}
