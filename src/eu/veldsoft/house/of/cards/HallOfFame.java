package eu.veldsoft.house.of.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class holds a list of the top 10 players and their score.
 * 
 * This class implements serializable because it is intended to be stored to a
 * file.
 * 
 * NOTE: The best way is to store the player information depending on which
 * rules they played. For simplicity reasons, this is not implemented.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 *
 */
final class HallOfFame implements Serializable {
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -9117800128898318055L;

	/**
	 * The list of players with the highest scores.
	 */
	private List<Player> players;

	/**
	 * The number of players the Hall of Fame will store.
	 */
	public static final int MAX_SIZE_OF_PLAYERS = 10;

	/**
	 * Default constructor.
	 */
	public HallOfFame() {
		this.players = new ArrayList<Player>();
	}

	/**
	 * Add a player in the hall of fame. The calculation of the position is done
	 * automatically.
	 * 
	 * @param player
	 *            the player to be added
	 * @return the position of the player in the Hall of Fame (-1 if not
	 *         entered)
	 */
	public int addPlayer(Player player) {
		this.players.add(player);

		Collections.sort(this.players);

		for (int i = MAX_SIZE_OF_PLAYERS; i < this.players.size(); i++) {
			this.players.remove(i);
		}

		return this.players.indexOf(player);
	}

	/**
	 * Returns the list of players.
	 * 
	 * @return the list of players
	 */
	public List<Player> getPlayers() {
		return players;
	}
}
