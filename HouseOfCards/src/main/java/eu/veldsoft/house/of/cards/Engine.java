package eu.veldsoft.house.of.cards;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Engine is where all the functionality of the game resides.
 * 
 * @author Panagiotis Peikidis
 * @version 1.1
 */
final class Engine implements HouseListener {

	/**
	 * The Rule for the Joker cards.
	 */
	private boolean jokerRule = true;

	/**
	 * The Rule for the Six Cards.
	 */
	private boolean sixCardsRule = true;

	/**
	 * The default point system for various calculations
	 */
	private CardPointSystem pointSystem = new HoCPointSystem();

	/**
	 * Registered listeners to this engine
	 */
	private List<EngineListener> listeners = new CopyOnWriteArrayList<EngineListener>();

	/**
	 * The card the player is holding in his hand.
	 */
	private Card playersHandCard;

	/**
	 * The player's score
	 */
	private int playerScore;

	/**
	 * The list of houses.
	 */
	private House[] houses;

	/**
	 * Dealer is actually a CardDeck.
	 */
	private CardDeck dealer;

	/**
	 * The Hall of Fame.
	 */
	private HallOfFame hallOfFame;

	/**
	 * Holds if the game is over or not.
	 */
	private boolean gameOver;

	/**
	 * The message when the game is over.
	 */
	private String gameOverMessage;

	/**
	 * Indicates that a game has started
	 */
	private boolean started = false;

	/**
	 * For the Rule that gains points when a certain number of Cards a House
	 * reached.
	 */
	private static final int NUM_CARDS_THRESHOLD_RULE = 6;

	/**
	 * For the RUle that gains points when a certain number of points a House
	 * reached.
	 */
	private static final int NUM_POINTS_THRESHOLD_RULE = 31;

	/**
	 * The path where the Hall Of Fame object is stored.
	 */
	private static final String HALL_OF_FAME_URL = "halloffame.sav";

	/**
	 * Default constructor.
	 */
	public Engine() {
		Object obj = Utility.loadObject(HALL_OF_FAME_URL);
		if (obj == null) {
			this.hallOfFame = new HallOfFame();
		} else {
			this.hallOfFame = (HallOfFame) obj;
		}
	}

	/**
	 * Initializes a new game.
	 * 
	 * @param numHouseSets
	 *            the number of house sets (each set is 4 houses of each suit)
	 * @param numDecks
	 *            the number of standard 52 card deck
	 * @param withJokerRule
	 *            <code>true</code> for the Joker Rule to be added, otherwise
	 *            <code>false</code>
	 * @param withNumCardRule
	 *            <code>true</code> for the NumCard Rule to be added, otherwise
	 *            <code>false</code>
	 */
	public void startNewGame(int numHouseSets, int numDecks) {
		this.playersHandCard = null;
		this.playerScore = 0;
		this.started = true;
		this.gameOver = false;
		this.gameOverMessage = "";

		this.houses = new House[numHouseSets * CardSuit.values().length];
		int j = 0;
		for (int i = 0; i < numHouseSets; i++) {
			for (CardSuit suit : CardSuit.values()) {
				this.houses[j] = createNewHouse(suit, jokerRule, sixCardsRule);
				j++;
			}
		}

		int jokerSet = 0;
		if (jokerRule)
			jokerSet = 1;
		this.dealer = new CardDeck(numDecks, jokerSet);

		dealer.shuffle();
		dealer.shuffle();
		dealer.shuffle();
		try {
			deal();
		} catch (HoCException e) {
			System.err.println("WTF??? The game just started!!!");
			e.printStackTrace();
		}

		for (EngineListener lstnr : listeners) {
			lstnr.newGameStarted();
		}
	}

	/**
	 * Deals a Card to the players hand.
	 * 
	 * @throws HoCException
	 *             when no more cards can be dealt
	 */
	private void deal() throws HoCException {
		try {
			this.playersHandCard = dealer.dealCard();
		} catch (CardException e) {
			throw new HoCException(e);
		}
	}

	/**
	 * Moves a card from players hand to the given house.
	 * 
	 * @param house
	 *            the house to add the card
	 * @throws HoCException
	 *             when there is no card in players hand <br/>
	 *             when house is closed <br/>
	 *             when game hasn't started
	 */
	public void addCardToHouse(House house) throws HoCException {
		if (this.playersHandCard == null)
			throw new HoCException("Player doesn't have any card in hand");
		if ((!this.started) || (this.gameOver))
			throw new HoCException("Game hasn't started");

		house.addCard(this.playersHandCard);
	}

	/**
	 * Moves a card from players hand to the given house.
	 * 
	 * @param house
	 *            the house to add the card
	 * @throws HoCException
	 *             when there is no card in players hand <br/>
	 *             when house is closed <br/>
	 *             when game hasn't started
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void addCardToHouse(int index) throws HoCException,
			ArrayIndexOutOfBoundsException {
		if (index < 0 || index >= houses.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		addCardToHouse(houses[index]);
	}

	/**
	 * Returns players hand card (cloned).
	 * 
	 * @return players hand card
	 */
	public Card getPlayersHandCard() {
		return playersHandCard;
	}

	/**
	 * 
	 * @return
	 */
	public CardPointSystem getPointSystem() {
		return pointSystem;
	}

	/**
	 * Returns the current score of the house.
	 * 
	 * @param index
	 *            the index of the house
	 * @return the score
	 */
	public int getHousePoints(int index) {
		return houses[index].getPoints();
	}

	/**
	 * Adds an EngineListener to the Engine.
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addEngineListener(EngineListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes an EngineListener from the Engine.
	 * 
	 * @param listener
	 *            the listener to be removed
	 */
	public void remove(Object listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns the player's score.
	 * 
	 * @return the player's score
	 */
	public int getPlayerScore() {
		return this.playerScore;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isJokerRule() {
		return jokerRule;
	}

	/**
	 * 
	 * @param jokerRule
	 */
	public void setJokerRule(boolean jokerRule) {
		this.jokerRule = jokerRule;
	}

	/**
	 * 
	 */
	public void changeJokerRule() {
		jokerRule = !jokerRule;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSixCardsRule() {
		return sixCardsRule;
	}

	/**
	 * 
	 * @param sixCardsRule
	 */
	public void setSixCardsRule(boolean sixCardsRule) {
		this.sixCardsRule = sixCardsRule;
	}

	/**
	 * 
	 */
	public void changeSixCardsRule() {
		sixCardsRule = !sixCardsRule;
	}

	/**
	 * Returns the array of houses.
	 * 
	 * @return the array of houses
	 */
	public House[] getHouses() {
		return this.houses;
	}

	/**
	 * Returns the number of remaining cards in the dealers hand.
	 * 
	 * @return the number of remaining cards in the dealers hand
	 */
	public int getDealerCardsLeft() {
		return this.dealer.numCards();
	}

	public void stateChanged(List<ActionRule> appliedRules) {

		/*
		 * Update score
		 */
		int appliedScore = 0;
		for (ActionRule aRule : appliedRules) {
			appliedScore += aRule.score();
		}
		this.playerScore += appliedScore;

		/*
		 * Check whether a Rule ended the game
		 */
		for (ActionRule aRule : appliedRules) {
			if (aRule.endsGame() == false) {
				continue;
			}

			this.gameOver = true;
			this.gameOverMessage = aRule.getDescription();
			notifyListeners(0);
			return;
		}

		/*
		 * Check whether all houses are closed
		 */
		boolean allClosed = true;
		for (House house : this.houses) {
			if (house.isOpened())
				allClosed = false;
		}

		if (allClosed) {
			/*
			 * If all closed, then game over
			 */
			this.gameOver = true;
			this.gameOverMessage = "All houses where closed.";
		} else {
			/*
			 * If at least one opened, try to deal
			 */
			try {
				deal();
			} catch (HoCException e) {
				/*
				 * If there are no cards left, game over
				 */
				this.playersHandCard = null;
				this.gameOver = true;
				notifyListenersGameWon(this.hallOfFame.addPlayer(new Player(
						"Enter Name", this.playerScore)));
				return;
			}

		}

		notifyListeners(appliedScore);
	}

	/**
	 * Notifies the Listeners that the player won the game.
	 * 
	 * @param position
	 *            the position in the Hall of Fame (-1 if not in)
	 */
	private void notifyListenersGameWon(int position) {
		if (position != -1)
			saveHallOfFame();

		for (EngineListener lstnr : this.listeners) {
			lstnr.gameWon(position);
		}
	}

	/**
	 * Notifies Listeners for Engine changes
	 * 
	 * @param appliedScore
	 *            the score that applied from the Rules for the EngineListeners
	 */
	private void notifyListeners(int appliedScore) {
		for (EngineListener lstnr : this.listeners) {
			lstnr.stateChanged(appliedScore);
		}
	}

	/**
	 * When the game first starts, this method initializes all new Houses
	 * 
	 * @param suit
	 *            the CardSuit of the House
	 * @return the House that was created
	 * @param withJokerRule
	 *            <code>true</code> for the Joker Rule to be added, otherwise
	 *            <code>false</code>
	 * @param withNumCardRule
	 *            <code>true</code> for the NumCard Rule to be added, otherwise
	 *            <code>false</code>
	 */
	private House createNewHouse(CardSuit suit, boolean withJokerRule,
			boolean withNumCardRule) {
		House house = new House(suit);
		house.addHouseListener(this);

		/*
		 * Add all PermissionRules
		 */
		house.addPermissionRule((new ClosedHouseRule()));

		/*
		 * Add all ActionRules
		 */
		if (withJokerRule)
			house.addActionRule(new JokerRule());
		if (withNumCardRule)
			house.addActionRule(new NumCardRule(NUM_CARDS_THRESHOLD_RULE,
					NUM_POINTS_THRESHOLD_RULE));
		house.addActionRule(new ScoreRule(NUM_POINTS_THRESHOLD_RULE));

		return house;
	}

	/**
	 * Returns <code>true</code> whether the game is over, otherwise
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> whether the game is over, otherwise
	 *         <code>false</code>
	 */
	public boolean isGameOver() {
		return this.gameOver;
	}

	/**
	 * Returns <code>true</code> whether the game has started, otherwise
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> whether the game has started, otherwise
	 *         <code>false</code>
	 */
	public boolean isGameStarted() {
		return this.started;
	}

	/**
	 * Returns the Hall of Fame.
	 * 
	 * @return the Hall of Fame
	 */
	public HallOfFame getHallOfFame() {
		return hallOfFame;
	}

	/**
	 * Quits a running game.
	 * 
	 * @throws HoCException
	 *             if a game is not running
	 */
	public void quitGame() throws HoCException {
		if (!this.started)
			throw new HoCException("There is no game running.");

		this.started = false;
	}

	/**
	 * Saves the Hall of Fame to a file.
	 */
	public void saveHallOfFame() {
		Utility.saveObject(this.hallOfFame, HALL_OF_FAME_URL);
	}

	/**
	 * Returns the game over message.
	 * 
	 * @return the game over message
	 */
	public String getGameOverMessage() {
		return gameOverMessage;
	}
}
