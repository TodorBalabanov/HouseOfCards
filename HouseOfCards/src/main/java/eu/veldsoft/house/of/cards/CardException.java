package eu.veldsoft.house.of.cards;

/**
 * A generic Exception for the cards package.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
class CardException extends Exception {
	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 2382046940072000613L;

	/**
	 * Constructs a CardException with the given message.
	 * 
	 * @param msg
	 *            the message of the Exception
	 */
	public CardException(String msg) {
		super(msg);
	}

}
