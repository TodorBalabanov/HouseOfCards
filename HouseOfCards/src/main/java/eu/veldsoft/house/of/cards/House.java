package eu.veldsoft.house.of.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An Abstract Data Type that holds information about a House of the game
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
final class House {
	/**
	 * The states the House can have
	 */
	enum State {
		/**
		 * Indicates when the house is closed.
		 */
		CLOSED,

		/**
		 * Indicates when the house is opened.
		 */
		OPENED,
	}

	/**
	 * A list of cards that the house has.
	 */
	private ArrayList<Card> cards;

	/**
	 * Indicates the current state of the house.
	 */
	private State state;

	/**
	 * A CardPointSystem implementation for calculating the card points.
	 */
	private CardPointSystem pointSystem;

	/**
	 * The House's suit
	 */
	private CardSuit suit;

	/**
	 * The Permission Rules
	 */
	private List<PermissionRule> permissionRules = new CopyOnWriteArrayList<PermissionRule>();

	/**
	 * The ActionRules
	 */
	private List<ActionRule> actionRules = new CopyOnWriteArrayList<ActionRule>();

	/**
	 * The House Listeners
	 */
	private List<HouseListener> listeners = new CopyOnWriteArrayList<HouseListener>();

	/**
	 * Constructs a house of the given suit.
	 * 
	 * @param suit
	 *            the suit of the house
	 */
	public House(CardSuit suit) {
		this.cards = new ArrayList<Card>();
		this.state = State.OPENED;
		this.pointSystem = new HoCPointSystem();
		this.suit = suit;
	}

	/**
	 * Adds a card to the house.
	 * 
	 * @param card
	 *            the card to be added
	 * @throws HoCException
	 *             when the house is closed
	 */
	public void addCard(Card card) throws HoCException {
		if ((this.permissionRules.size() == 0)
				&& (this.actionRules.size() == 0)) {
			throw new HoCException(
					"At least one rule should added to the House. Cannot calculate.");
		}

		for (PermissionRule pRule : this.permissionRules) {
			if (!pRule.isAllowed(this, card)) {
				throw new HoCException(pRule.getDescription());
			}
		}

		this.cards.add(card);

		List<ActionRule> appliedRules = new ArrayList<ActionRule>();
		for (ActionRule aRule : this.actionRules) {
			if (aRule.applies(this))
				appliedRules.add(aRule);
		}

		informListeners(appliedRules);
	}

	/**
	 * 
	 * @return
	 */
	public State getState() {
		return state;
	}

	/**
	 * Returns the total points of the cards in the house depending on the
	 * CardPointSystem implementation
	 * 
	 * @return the total points of the cards in the house
	 */
	public int getPoints() {
		// TODO: Should this be calculated every time? Maybe there is a better
		// way.
		int points = 0;
		for (Card card : cards) {
			points += pointSystem.getPoints(card);
		}
		return points;
	}

	/**
	 * Returns <code>true</code> if the house is opened, otherwise
	 * <code>false</code>
	 * 
	 * @return <code>true</code> if the house is opened, otherwise
	 *         <code>false</code>
	 */
	public boolean isOpened() {
		return (this.state == State.OPENED);
	}

	/**
	 * Returns the suit of this house
	 * 
	 * @return the suit of this house
	 */
	public CardSuit getSuit() {
		return suit;
	}

	/**
	 * Adds a PermissionRule.
	 * 
	 * @param pRule
	 *            the Rule to be added
	 */
	public void addPermissionRule(PermissionRule pRule) {
		this.permissionRules.add(pRule);
	}

	/**
	 * Removes a PermissionRule.
	 * 
	 * @param pRule
	 *            the PermissionRule to remove
	 */
	public void removePermissionRule(PermissionRule pRule) {
		this.permissionRules.remove(pRule);
	}

	/**
	 * Adds an ActionRule
	 * 
	 * @param aRule
	 *            the ActionRule to be added
	 */
	public void addActionRule(ActionRule aRule) {
		this.actionRules.add(aRule);
	}

	/**
	 * Removes an ActionRule.
	 * 
	 * @param aRule
	 *            the ActionRule to be removed
	 */
	public void removeActionRule(ActionRule aRule) {
		this.actionRules.remove(aRule);
	}

	/**
	 * Add a HouseListener.
	 * 
	 * @param listnr
	 *            the HouseListener to be added
	 */
	public void addHouseListener(HouseListener listnr) {
		listeners.add(listnr);
	}

	/**
	 * Removes a HouseListener
	 * 
	 * @param listnr
	 *            the HouseListener to be removed
	 */
	public void removeHouseListener(HouseListener listnr) {
		listeners.remove(listnr);
	}

	/**
	 * Informs Listeners for state changed event.
	 * 
	 * @param appliedRules
	 *            ActionRules that applied
	 */
	private void informListeners(List<ActionRule> appliedRules) {
		for (HouseListener lstnr : this.listeners) {
			lstnr.stateChanged(appliedRules);
		}
	}

	/**
	 * Returns the list of cards this house contains.
	 * 
	 * @return the list of cards this house contains
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * Empties the house's cards
	 */
	public void emptyAllCards() {
		this.cards = new ArrayList<Card>();
	}

	/**
	 * Closes this House
	 */
	public void closeHouse() {
		this.state = State.CLOSED;
	}
}
