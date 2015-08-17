package eu.veldsoft.house.of.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is a House wrapper. It contains a house and uses it for various
 * reasons.
 * 
 * @author Panagiotis Peikidis
 * @version 1.1
 *
 */
final class JHouse extends JPanel implements HouseListener, MouseListener {
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 4624882647171399883L;

	/**
	 * The JPanel in which all cards will be added.
	 */
	private JPanel cardsPanel;

	/**
	 * The Label that holds the House's score.
	 */
	private JLabel scoreLabel;

	/**
	 * The score of this house depending on the Suit.
	 */
	private int houseScore;

	/**
	 * The House this class wraps
	 */
	private House house;

	/**
	 * The top distance between two cards
	 */
	private static final int CARD_DISTANSE = 30;

	/**
	 * The default background color.
	 */
	private static final Color BG_COLOR = new Color(161, 216, 139);

	/**
	 * The mouse over background color.
	 */
	private static final Color BG_COLOR_OVER = new Color(181, 243, 156);

	/**
	 * This is used when the house is closed and a different paint method is
	 * used.
	 */
	private boolean overrideRepaint = false;

	/**
	 * Constructs a JHouse wrapping the given House.
	 * 
	 * @param house
	 *            the house to wrap
	 */
	public JHouse(House house) {
		this.house = house;
		this.house.addHouseListener(this);

		switch (house.getSuit()) {
		case HEARTS:
			this.houseScore += 10;
		case DIAMONDS:
			this.houseScore += 10;
		case CLUBS:
			this.houseScore += 10;
		case SPADES:
			this.houseScore += 10;
		}

		Dimension size = new Dimension(140, 550);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(BG_COLOR, 2));
		setBackground(BG_COLOR);

		setLayout(new BorderLayout());
		this.cardsPanel = new JPanel(null);
		this.cardsPanel.setOpaque(false);
		add(this.cardsPanel);
		JPanel infoPanel = new JPanel(new BorderLayout());
		infoPanel.setOpaque(false);
		switch (house.getSuit()) {
		case SPADES:
			infoPanel.add(
					new JLabel(Utility.getImage("spades_small.png")),
					BorderLayout.WEST);
			break;
		case CLUBS:
			infoPanel.add(
					new JLabel(Utility.getImage("clubs_small.png")),
					BorderLayout.WEST);
			break;
		case HEARTS:
			infoPanel.add(
					new JLabel(Utility.getImage("hearts_small.png")),
					BorderLayout.WEST);
			break;
		case DIAMONDS:
			infoPanel.add(
					new JLabel(Utility.getImage("diamonds_small.png")),
					BorderLayout.WEST);
			break;
		}
		this.scoreLabel = new JLabel("<html>0 pts<br/>(+" + this.houseScore
				+ ")</html>");
		this.scoreLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.scoreLabel.setFont(Main.GENERIC_FONT);
		infoPanel.add(scoreLabel);
		add(infoPanel, BorderLayout.SOUTH);

		addMouseListener(this);
	}

	/**
	 * Returns the house this class wraps.
	 * 
	 * @return the house this class wraps
	 */
	public House getHouse() {
		return this.house;
	}

	/**
	 * This draws the cards in the House.
	 */
	public void drawHouse() {
		this.cardsPanel.removeAll();

		List<Card> cards = this.house.getCards();
		int size = cards.size();
		int y = size;
		for (int i = size - 1; i >= 0; i--) {
			ImageIcon cardImg = Utility.getCardImage(cards.get(i));
			JLabel cardLabel = new JLabel(cardImg);
			cardLabel.setBounds(4, ((y * CARD_DISTANSE) - (CARD_DISTANSE - 4)),
					cardImg.getIconWidth(), cardImg.getIconHeight());

			this.cardsPanel.add(cardLabel);
			y--;
		}

		this.scoreLabel.setText("<html>" + this.house.getPoints()
				+ " pts<br/>(+" + this.houseScore + ")</html>");

		repaint();
	}

	public void stateChanged(List<ActionRule> appliedRules) {
		drawHouse();

		if (!this.house.isOpened()) {
			closeHouse();
			Utility.playSound("closed.wav");
		}
	}

	/**
	 * This method is called when the House is closed.
	 */
	private void closeHouse() {
		this.house.removeHouseListener(this);
		for (MouseListener lstnr : getMouseListeners()) {
			removeMouseListener(lstnr);
		}

		setBackground(BG_COLOR);
		setBorder(BorderFactory.createLineBorder(BG_COLOR, 2));
		this.overrideRepaint = true;
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
		setBackground(BG_COLOR_OVER);
		setBorder(BorderFactory.createLineBorder(BG_COLOR_OVER, 2));
	}

	public void mouseExited(MouseEvent arg0) {
		setBackground(BG_COLOR);
		setBorder(BorderFactory.createLineBorder(BG_COLOR, 2));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (this.overrideRepaint) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(new Color(0, 0, 0, 80));
			g2.fillRect(0, 0, getWidth(), getHeight());
			ImageIcon img = Utility.getImage("closed.png");
			g2.drawImage(img.getImage(), 4, 4, img.getIconWidth(),
					img.getIconHeight(), null);
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		Utility.playSound("click.wav");
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}
