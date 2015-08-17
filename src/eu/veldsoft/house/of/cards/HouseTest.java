package eu.veldsoft.house.of.cards;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit Test Case for the House class
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
class HouseTest {

	/**
	 * Various tests
	 */
	@Test
	public void testHouse() {
		House house = new House(CardSuit.SPADES);
		Card card = new Card(CardSuit.SPADES, CardRank.ACE);

		assertTrue("The Suit of the house should be Spades",
				house.getSuit() == CardSuit.SPADES);

		try {
			house.addCard(card);
			fail("This should throw an exception because no rules are added to the house");
		} catch (HoCException e) {
		}

		assertTrue("When a house is first started, it should be opened",
				house.isOpened());
		assertTrue("When a house is newly created, it has no cards", house
				.getCards().size() == 0);

		house.addActionRule(new NumCardRule(6, 31));
		try {
			house.addCard(card);
		} catch (HoCException e) {
			fail("Now that there is a rule, the card should normally enter");
		}
		assertTrue("I have added a card, where is it?",
				house.getCards().size() == 1);
		house.emptyAllCards();
		assertTrue("I removed all cards. There should be no cards", house
				.getCards().size() == 0);

		house.closeHouse();
		assertTrue("Since I closed the House, it should stay closed",
				!house.isOpened());
	}

	/**
	 * Various tests for the Rules
	 */
	@Test
	public void testRules() {
		House house = new House(CardSuit.SPADES);
		Card card = new Card(CardSuit.SPADES, CardRank.ACE);

		// Closed Rule
		ClosedHouseRule closed = new ClosedHouseRule();
		house.addPermissionRule(closed);

		try {
			house.addCard(card);
		} catch (HoCException e) {
			fail("The house isn't closed, so I should be able to add a card.");
		}
		house.closeHouse();
		try {
			house.addCard(card);
			fail("The house is closed, so I shouldn't be able to add a card.");
		} catch (HoCException e) {
		}

		house.removePermissionRule(closed);

		// Joker Rule
		JokerRule joker = new JokerRule();
		house.addActionRule(joker);

		try {
			house.addCard(new Card(CardSuit.SPADES, CardRank.JOKER));
			assertTrue("This should not have ended the game", !joker.endsGame());
			assertTrue("This should return points",
					joker.score() == JokerRule.JOKER_SCORE);
		} catch (HoCException e) {
			e.printStackTrace();
			fail("The Joker is of the same suit, I should be able to add it.");
		}

		try {
			house.addCard(new Card(CardSuit.CLUBS, CardRank.JOKER));
			assertTrue(
					"This rule should end the game because it is of another suit.",
					joker.endsGame());
			assertTrue("This should not return points", joker.score() == 0);
		} catch (HoCException e) {
			fail("This should not throw an exception");
		}

		// NumCard Rule
		NumCardRule numCard = new NumCardRule(6, 31);
		house = new House(CardSuit.SPADES);
		house.addActionRule(numCard);

		try {
			for (int i = 0; i < 5; i++) {
				house.addCard(new Card(CardSuit.SPADES, CardRank.TWO));
			}
			assertTrue("House should have 5 cards",
					house.getCards().size() == 5);
		} catch (HoCException e) {
			fail("The threshold of the house is 6");
		}

		try {
			house.addCard(new Card(CardSuit.SPADES, CardRank.TWO));
			assertTrue("House should have no cards",
					house.getCards().size() == 0);
		} catch (HoCException e) {
		}

		assertTrue("NumCard Rule never ends game", !numCard.endsGame());
		assertTrue("NumCard Rule never closes a house", house.isOpened());

		house.removeActionRule(numCard);

		// Score Rule
		ScoreRule score = new ScoreRule(4);
		house.emptyAllCards();
		house.addActionRule(score);

		try {
			house.addCard(new Card(CardSuit.SPADES, CardRank.TWO));
			assertTrue("House should still be opened", house.isOpened());
			assertTrue("House should have one card",
					house.getCards().size() == 1);
		} catch (HoCException e) {
		}

		try {
			house.addCard(new Card(CardSuit.SPADES, CardRank.TWO));
			assertTrue("House should still be opened", house.isOpened());
			assertTrue("House should have no card",
					house.getCards().size() == 0);
		} catch (HoCException e) {
		}

		try {
			house.addCard(new Card(CardSuit.SPADES, CardRank.TWO));
			house.addCard(new Card(CardSuit.SPADES, CardRank.THREE));
			assertTrue("House should not be opened", !house.isOpened());
			assertTrue("House should have two cards",
					house.getCards().size() == 2);
		} catch (HoCException e) {
		}
	}
}
