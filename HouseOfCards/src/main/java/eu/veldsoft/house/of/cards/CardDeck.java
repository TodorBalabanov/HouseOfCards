package eu.veldsoft.house.of.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CardDeck holds a standard 52 card deck. Depending on the constructor
 * parameters it can also contain jokers and more than one deck of cards.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
final class CardDeck {
	/**
	 * The list of cards inside the deck
	 */
	private List<Card> cards;

	/**
	 * Constructs a CardDeck with given number of standard 52 card decks and 4
	 * joker cards.
	 * 
	 * @param numDecks
	 *            the number of standard 52 card decks
	 * @param jokerSets
	 *            the number of joker sets (four jokers for each suit)
	 */
	public CardDeck(int numDecks, int jokerSets) {
		cards = new ArrayList<Card>();

		for (int i = 0; i < numDecks; i++) {
			for (CardSuit suite : CardSuit.values()) {
				for (CardRank rank : CardRank.values()) {
					if (rank != CardRank.JOKER)
						cards.add(Card.instanceOf(suite, rank));
				}
			}
		}

		for (int i = 0; i < jokerSets; i++) {
			for (CardSuit suit : CardSuit.values()) {
				cards.add(Card.instanceOf(suit, CardRank.JOKER));
			}
		}
	}

	/**
	 * Shuffles the list of cards.
	 * 
	 * NOTE: Java is known to not have a sophisticated random generator. It is
	 * better to use an external library.
	 */
	public void shuffle() {
		Collections.shuffle(this.cards);
	}

	/**
	 * Deals card from bottom.
	 * 
	 * @return the card dealt
	 * @throws CardException
	 *             when no more cards can be dealt
	 */
	public Card dealCard() throws CardException {
		if (cards.size() < 1)
			throw new CardException("No more cards to deal.");

		return cards.remove(0);
	}

	/**
	 * Returns the number of cards in the deck.
	 * 
	 * @return the number of cards in the deck
	 */
	public int numCards() {
		return this.cards.size();
	}
}
