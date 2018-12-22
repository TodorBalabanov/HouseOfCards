package eu.veldsoft.house.of.cards;

/**
 * General House of Games Exception to specialize general game exception
 * handling and generally to separate from other Exceptions.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
class HoCException extends Exception {
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 * 
	 * @param msg
	 *            the message of the Exception
	 */
	public HoCException(String msg) {
		super(msg);
	}

	/**
	 * Default constructor.
	 */
	public HoCException() {
		super();
	}

	/**
	 * Constructs a CardException with the given throwble.
	 * 
	 * @param cause
	 *            the throwbale
	 */
	public HoCException(Throwable cause) {
		super(cause);
	}
}
