package eu.veldsoft.house.of.cards;

/**
 * Rules that apply for actions that add score to the game or ends it.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
interface ActionRule extends Rule {

	/**
	 * If the Rule indicates that after applying the game shoud be over, the
	 * this should return <code>true</code>, otherwise <code>false</code>
	 * 
	 * @return <code>true</code> if game ends after this rule, otherwise
	 *         <code>false</code>
	 */
	public boolean endsGame();

	/**
	 * Returns the number of points this Rule affected when applied (0 if not
	 * affected).
	 * 
	 * @return the number of points this Rule affected when applied (0 if not
	 *         affected).
	 */
	public int score();

	/**
	 * If this rule applies to the given House, then it returns
	 * <code>true</code>, otherwise <code>false</code>. This method is meant to
	 * be used inside the House class. It could cause problems otherwise.
	 * 
	 * @param house
	 *            the House to examine for this Rule
	 * @return <code>true</code> if Rule applied, otherwise <code>false</code>
	 */
	public boolean applies(House house);
}
