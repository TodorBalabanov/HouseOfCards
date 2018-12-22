package eu.veldsoft.house.of.cards;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit Test Case for the Engine class
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
class EngineTest {

	/**
	 * Various tests for the Engine.
	 */
	@Test
	public void testEngine() {
		Engine engine = new Engine();
		try {
			engine.addCardToHouse(new House(CardSuit.SPADES));
			fail("This should throw an Exception for two reasons: House doesn't exist in the Engine and the game hasn't started");
		} catch (HoCException e) {
		}

		try {
			engine.quitGame();
			fail("There is no running game, so this should throw an exception.");
		} catch (HoCException e) {
		}

		engine.startNewGame(1, 1);
		assertTrue("At least one card should be dealt",
				engine.getDealerCardsLeft() == 55);
		assertTrue("Player should have a card",
				engine.getPlayersHandCard() != null);
		assertTrue("The game normally should have started",
				engine.isGameStarted());
		assertTrue("The game should not be ended", !engine.isGameOver());

		try {
			engine.quitGame();
		} catch (HoCException e) {
			fail("There is a running game, so this should not throw an exception.");
		}
	}

	// public void testStartNewGame() {}
	// public void testAddCardToHouse() {}
	// public void testIsGameOver() {}
	// public void testIsGameStarted() { }
	// public void testQuitGame() { }

}
