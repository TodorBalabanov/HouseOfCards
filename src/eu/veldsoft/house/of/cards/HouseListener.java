package eu.veldsoft.house.of.cards;

import java.util.List;

/**
 * This interface is used when various events pop up inside a House (i.e. a
 * specific amount of points are reached).
 * 
 * @version 1.0
 *
 */
interface HouseListener {
	/**
	 * When the state of the House's change this method is called.
	 * 
	 * @param appliedRules
	 *            applied ActionRules
	 * */
	public void stateChanged(List<ActionRule> appliedRules);
}
