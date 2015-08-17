package eu.veldsoft.house.of.cards;

/**
 * A type of Rule that returns whether a given card can be added in a House
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
interface PermissionRule extends Rule {

	/**
	 * Returns <code>true</code> if a card can be added in a house, otherwise
	 * <code>false</code>.
	 * 
	 * @param house
	 *            the house that will be investigated
	 * @param card
	 *            the card that will be added
	 * 
	 * @return <code>true</code> if a card can be added in a house, otherwise
	 *         <code>false</code>.
	 */
	public boolean isAllowed(House house, Card card);
}
