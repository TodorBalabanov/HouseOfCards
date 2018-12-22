package eu.veldsoft.house.of.cards;

/**
 * Interface used by the engine in order for classes to listen for events raised
 * by it.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
interface EngineListener {
	/**
	 * Event raised when a new game has started.
	 */
	public void newGameStarted();

	/**
	 * Event raised when the state of the game has changed.
	 * 
	 * @param appliedScore
	 *            the score applied from the rules
	 */
	public void stateChanged(int appliedScore);

	/**
	 * Event raised when a game has been won.
	 * 
	 * @param hallOfFamePosition
	 *            the position in the Hall of Fame (-1 if not in)
	 */
	public void gameWon(int hallOfFamePosition);
}
