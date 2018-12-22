package eu.veldsoft.house.of.cards;

/**
 * Common interface for Rules.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
interface Rule {
	/**
	 * Returns the name of the Rule.
	 * 
	 * @return the name of the Rule
	 */
	public String getName();

	/**
	 * Returns the description of the Rule.
	 * 
	 * @return the description of the Rule
	 */
	public String getDescription();
}
