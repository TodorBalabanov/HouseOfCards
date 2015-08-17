package eu.veldsoft.house.of.cards;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit Test Case for the Card class
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
class CardTest {

	/**
	 * Various tests for the card
	 */
	@Test
	public void testCard() {
		CardRank rank = CardRank.ACE;
		CardSuit suit = CardSuit.SPADES;
		Card card = new Card(suit, rank);
		assertTrue(rank == card.getRank());
		assertTrue(suit == card.getSuit());
		Card cloned = card.clone();
		assertTrue(card.equals(cloned));
	}

}
