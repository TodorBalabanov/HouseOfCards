package eu.veldsoft.house.of.cards;

import java.io.Serializable;

/**
 * This class is meant to be used in the Hall of Fame. It stores the name of the
 * player and the rules by which he played.
 * 
 * This class implements serializable because it is intended to be stored to a
 * file.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
final class Player implements Serializable, Comparable<Player> {
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -3430231826310073555L;

	/**
	 * The players name.
	 */
	private String playerName;

	/**
	 * The players score.
	 */
	private int score;

	/**
	 * Constructs a Player class with all it's attributes.
	 * 
	 * @param playerName
	 *            the players name
	 * @param score
	 *            the players score
	 */
	public Player(String playerName, int score) {
		super();
		this.playerName = playerName;
		this.score = score;
	}

	/**
	 * Returns the players name.
	 * 
	 * @return the players name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Returns the players score.
	 * 
	 * @return the players score
	 */
	public int getScore() {
		return score;
	}

	public int compareTo(Player player) {
		if (player.score == this.score) {
			return 0;
		} else if (player.score > this.score) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Sets the player name.
	 * 
	 * @param playerName
	 *            the new player's name
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
