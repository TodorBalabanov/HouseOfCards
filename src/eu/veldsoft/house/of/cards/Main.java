package eu.veldsoft.house.of.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * The main GUI of HouseOfCards<br/>
 * <br/>
 * NOTE: The code is a mess. You have been warned!
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 */
public final class Main extends JFrame implements EngineListener {
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -2044736285422167152L;

	/**
	 * Generic Font for various components.
	 */
	public static Font GENERIC_FONT = new Font("Verdana", Font.BOLD, 12);

	/**
	 * The Game Engine
	 */
	private Engine engine;

	/**
	 * Temporary value for the High Score Player Name.
	 */
	private boolean playerNameSet = true;

	/**
	 * The dealer.
	 */
	private JLabel dealerLabel;

	/**
	 * The card in player's hand.
	 */
	private JLabel handLabel;

	/**
	 * The score.
	 */
	private JLabel scoreLabel;

	/**
	 * The notification label.
	 */
	private JLabel infoLabel;

	/**
	 * The Intro Screen.
	 */
	private JPanel introScreen;

	/**
	 * The Help Screen.
	 */
	private JPanel helpScreen;

	/**
	 * The Game Over Screen.
	 */
	private JPanel gameOverScreen;

	/**
	 * The Winner Screen.
	 */
	private JPanel winnerScreen;

	/**
	 * The High Scores Screen.
	 */
	private JPanel highScoreScreen;

	/**
	 * The Screen that will be next when the Winner Screen ends.
	 */
	private JPanel nextWinnerScreen;

	/**
	 * Executor for the info icon.
	 */
	private ScheduledExecutorService executor = Executors
			.newScheduledThreadPool(1);

	/**
	 * Default Constructor.
	 */
	public Main() {
		/*
		 * Set LookAndFeel to system LnF. We don't care if it throws an
		 * exception.
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		/*
		 * Frame Initialization
		 */
		setTitle("House Of Cards");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(730, 600));
		setResizable(false);
		setIconImage(Utility.getImage("icon.png").getImage());
		getContentPane().setBackground(new Color(57, 137, 47));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent wev) {
				exit();
			}
		});

		/*
		 * Data Initialization
		 */
		this.engine = new Engine();
		this.engine.addEngineListener(this);

		/*
		 * Set the intro screen
		 */
		setScreen(getIntroScreen());

		/*
		 * Frame Cleanup
		 */
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main().setVisible(true);
			}
		});
	}

	/**
	 * Updates all Gui elements when game state has changed.
	 */
	private void updateGui() {
		if (this.engine.getPlayersHandCard() == null) {
			this.handLabel = new JLabel(Utility.getImage("deck.png"));
			this.handLabel.setText("");
		} else {
			this.handLabel.setText(engine.getPointSystem().getPoints(this.engine
					.getPlayersHandCard()) + " pts");
			this.handLabel.setIcon(Utility.getCardImage(this.engine
					.getPlayersHandCard()));
		}
		this.dealerLabel.setText(this.engine.getDealerCardsLeft() + " left");
		this.scoreLabel.setText(this.engine.getPlayerScore() + " pts");

		if (this.engine.isGameOver()) {
			setScreen(getGameOverScreen(this.engine.getGameOverMessage()));
			Utility.playSound("error.wav");
		}
	}

	/**
	 * Initializes all components when a game has started.
	 */
	public void newGameStarted() {
		getGlassPane().setVisible(false);
		getContentPane().removeAll();

		/*
		 * House Panel
		 */
		JPanel housePanel = new JPanel();
		housePanel.setOpaque(false);
		for (House house : engine.getHouses()) {
			JHouse jHouse = new JHouse(house);
			jHouse.addMouseListener(new HouseMouseListener());
			housePanel.add(jHouse);
		}

		/*
		 * Sidebar
		 */
		JPanel sidebar = new JPanel();
		sidebar.setOpaque(false);
		sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

		this.scoreLabel = new InfoLabel(10, "0 pts");
		this.scoreLabel.setFont(GENERIC_FONT);
		this.scoreLabel.setForeground(Color.WHITE);
		this.scoreLabel.setIcon(Utility.getImage("scorebg.png"));
		sidebar.add(this.scoreLabel);

		/*
		 * Info Label
		 */
		this.infoLabel = new InfoLabel(54);
		this.infoLabel.setIcon(Utility.getImage("nil.png"));
		this.infoLabel.setFont(GENERIC_FONT);
		this.infoLabel.setForeground(Color.WHITE);
		sidebar.add(this.infoLabel);

		/*
		 * Dealer
		 */
		this.dealerLabel = new JLabel(Utility.getImage("deck.png"));
		this.dealerLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.dealerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		this.dealerLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.dealerLabel.setFont(GENERIC_FONT);
		this.dealerLabel.setForeground(Color.WHITE);
		this.dealerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sidebar.add(this.dealerLabel);

		/*
		 * Player's hand
		 */
		this.handLabel = new JLabel(Utility.getImage("deck.png"));
		this.handLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.handLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		this.handLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.handLabel.setFont(GENERIC_FONT);
		this.handLabel.setForeground(Color.WHITE);
		sidebar.add(this.handLabel);

		/*
		 * Restart Game
		 */
		JLabel restartGameLabel = new JLabel(Utility.getImage("restart.png"));
		restartGameLabel.setPreferredSize(new Dimension(120, 71));
		restartGameLabel.setBackground(Color.RED);
		sidebar.add(restartGameLabel);

		/*
		 * Quit Game
		 */
		JLabel quitGameLabel = new JLabel(Utility.getImage("quit.png"));
		quitGameLabel.setPreferredSize(new Dimension(120, 71));
		quitGameLabel.setBackground(Color.RED);
		sidebar.add(quitGameLabel);

		/*
		 * The root panel
		 */
		JPanel root = new JPanel(new BorderLayout());
		root.setOpaque(true);
		root.setBackground(new Color(57, 137, 47));
		root.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		root.add(housePanel);
		root.add(sidebar, BorderLayout.WEST);

		getContentPane().add(root);

		/*
		 * Listeners
		 */
		quitGameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mev) {
				int val = JOptionPane.showConfirmDialog(null, "Are you sure?",
						"Quit", JOptionPane.YES_NO_OPTION);
				if (val == JOptionPane.YES_OPTION) {
					try {
						engine.quitGame();
					} catch (HoCException e) {
						System.err.println("WTF??? I cannot quit the game :S");
					}
					setScreen(getIntroScreen());
				}
			}
		});

		restartGameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mev) {
				int val = JOptionPane.showConfirmDialog(null, "Are you sure?",
						"Restart", JOptionPane.YES_NO_OPTION);
				if (val == JOptionPane.YES_OPTION) {
					try {
						engine.quitGame();
						engine.startNewGame(1, 1);
					} catch (HoCException e) {
						System.err.println("WTF??? I cannot quit the game :S");
					}
				}
			}
		});

		updateGui();
	}

	public void stateChanged(int appliedScore) {
		if (appliedScore != 0) {
			Utility.playSound("score.wav");
			showInfo(true, "+" + appliedScore);
		}
		updateGui();
	}

	public void gameWon(int position) {
		Utility.playSound("win.wav");
		if (position != -1) {
			this.nextWinnerScreen = getHighScoresScreen(position);
		} else {
			this.nextWinnerScreen = getIntroScreen();
		}

		setScreen(getWinnerScreen());
	}

	/**
	 * Tries to quit a running game. Returns result depending on players choice.
	 */
	private void exit() {
		if ((!this.engine.isGameStarted()) || (this.engine.isGameOver()))
			System.exit(0);

		int val = JOptionPane.showConfirmDialog(null, "Are you sure?", "Exit",
				JOptionPane.YES_NO_OPTION);
		if (val == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * 
	 * @param panel
	 */
	private void setScreen(JPanel panel) {
		getGlassPane().setVisible(false);
		setGlassPane(panel);
		getGlassPane().setVisible(true);
	}

	/**
	 * Returns the intro glass pane.
	 * 
	 * @return the intro glass pane
	 */
	private JPanel getIntroScreen() {
		if (this.introScreen != null)
			return this.introScreen;

		this.introScreen = new JPanel() {
			/**
			 * Generated serialVersionUID.
			 */
			private static final long serialVersionUID = 4355234844197398835L;

			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(57, 137, 47));
				g2.fillRect(0, 0, getWidth(), getHeight());
				g2.drawImage(Utility.getImage("logo2.png").getImage(), 0, 0,
						165, 400, null);
				super.paint(g);
			}
		};
		this.introScreen.setOpaque(false);

		final JLabel newGameButton = new JLabel(Utility.getImage("newgame.png"));
		final JLabel jokersButton = new JLabel(
				Utility.getImage("jokers_true.png"));
		final JLabel sixCardsButton = new JLabel(
				Utility.getImage("sixcards_true.png"));
		final JLabel highScoresButton = new JLabel(
				Utility.getImage("highscores.png"));
		final JLabel helpButton = new JLabel(Utility.getImage("help.png"));
		final JLabel exitButton = new JLabel(Utility.getImage("exit.png"));

		newGameButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				engine.startNewGame(1, 1);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				newGameButton.setIcon(Utility.getImage("newgame_over.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				newGameButton.setIcon(Utility.getImage("newgame.png"));
			}
		});
		jokersButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				engine.changeJokerRule();
				jokersButton.setIcon(Utility.getImage("jokers_over_"
						+ engine.isJokerRule() + ".png"));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				jokersButton.setIcon(Utility.getImage("jokers_over_"
						+ engine.isJokerRule() + ".png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jokersButton.setIcon(Utility.getImage("jokers_" + engine.isJokerRule()
						+ ".png"));
			}
		});
		sixCardsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				engine.changeSixCardsRule();
				sixCardsButton.setIcon(Utility.getImage("sixcards_over_"
						+ engine.isSixCardsRule() + ".png"));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				sixCardsButton.setIcon(Utility.getImage("sixcards_over_"
						+ engine.isSixCardsRule() + ".png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sixCardsButton.setIcon(Utility.getImage("sixcards_"
						+ engine.isSixCardsRule() + ".png"));
			}
		});
		highScoresButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setScreen(getHighScoresScreen(-1));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				highScoresButton.setIcon(Utility
						.getImage("highscores_over.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				highScoresButton.setIcon(Utility.getImage("highscores.png"));
			}
		});
		helpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setScreen(getHelpScreen());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				helpButton.setIcon(Utility.getImage("help_over.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				helpButton.setIcon(Utility.getImage("help.png"));
			}
		});
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				exit();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(Utility.getImage("exit_over.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(Utility.getImage("exit.png"));
			}
		});

		this.introScreen.add(newGameButton);
		this.introScreen.add(jokersButton);
		this.introScreen.add(sixCardsButton);
		this.introScreen.add(highScoresButton);
		this.introScreen.add(helpButton);
		this.introScreen.add(exitButton);

		return this.introScreen;
	}

	/**
	 * Returns the Game Over Glass Pane.
	 * 
	 * @param gameOverMessage
	 *            the game over message
	 * 
	 * @return the Game Over Glass Pane
	 */
	private JPanel getGameOverScreen(String gameOverMessage) {
		this.gameOverScreen = new JPanel() {
			/**
			 * Generated serialVersionUID
			 */
			private static final long serialVersionUID = -3019362974067223452L;

			@Override
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				g2.setColor(new Color(0, 0, 0, 80));
				g2.fillRect(0, 0, getWidth(), getHeight());
				ImageIcon img = Utility.getImage("gameover.png");
				g2.drawImage(img.getImage(),
						(getWidth() / 2) - (img.getIconWidth() / 2),
						(getHeight() / 2) - (img.getIconHeight() / 2),
						img.getIconWidth(), img.getIconHeight(), null);

				super.paint(g);
			}
		};
		this.gameOverScreen.setLayout(null);
		JLabel gameOverMessageLabel = new JLabel(gameOverMessage);
		gameOverMessageLabel.setFont(new Font("Verdana", Font.BOLD, 26));
		gameOverMessageLabel.setForeground(Color.WHITE);
		gameOverMessageLabel.setBounds(170, 180, 400, 200);
		this.gameOverScreen.add(gameOverMessageLabel);
		ImageIcon againImg = Utility.getImage("again.png");
		final JLabel againLabel = new JLabel(againImg);
		againLabel.setBounds(150, 300, againImg.getIconWidth(),
				againImg.getIconHeight());
		this.gameOverScreen.add(againLabel);
		ImageIcon exitImg = Utility.getImage("exit.png");
		final JLabel exitLabel = new JLabel(exitImg);
		exitLabel.setBounds(400, 300, exitImg.getIconWidth(),
				exitImg.getIconHeight());
		this.gameOverScreen.add(exitLabel);

		againLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mev) {
				engine.startNewGame(1, 1);
				mev.consume();
			}

			@Override
			public void mouseEntered(MouseEvent mev) {
				againLabel.setIcon(Utility.getImage("again_over.png"));
				mev.consume();
			}

			@Override
			public void mouseExited(MouseEvent mev) {
				againLabel.setIcon(Utility.getImage("again.png"));
				mev.consume();
			}
		});

		exitLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mev) {
				setScreen(getIntroScreen());
				mev.consume();
			}

			@Override
			public void mouseEntered(MouseEvent mev) {
				exitLabel.setIcon(Utility.getImage("exit_over.png"));
				mev.consume();
			}

			@Override
			public void mouseExited(MouseEvent mev) {
				exitLabel.setIcon(Utility.getImage("exit.png"));
				mev.consume();
			}
		});
		this.gameOverScreen.setOpaque(false);

		this.gameOverScreen.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent mev) {
				mev.consume();
			}

			public void mouseEntered(MouseEvent mev) {
				mev.consume();
			}

			public void mouseExited(MouseEvent mev) {
				mev.consume();
			}

			public void mouseClicked(MouseEvent mev) {
				mev.consume();
			}

			public void mouseReleased(MouseEvent mev) {
				mev.consume();
			}
		});

		return this.gameOverScreen;
	}

	/**
	 * Returns the Game Over Glass Pane.
	 * 
	 * @return the Game Over Glass Pane
	 */
	private JPanel getHelpScreen() {
		if (this.helpScreen != null)
			return this.helpScreen;

		this.helpScreen = new JPanel() {
			/**
			 * Generated serialVersionUID
			 */
			private static final long serialVersionUID = -3019362974067223452L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;

				ImageIcon img = Utility.getImage("help_text.png");
				g2.drawImage(img.getImage(),
						(getWidth() / 2) - (img.getIconWidth() / 2),
						(getHeight() / 2) - (img.getIconHeight() / 2),
						img.getIconWidth(), img.getIconHeight(), null);

			}
		};
		this.helpScreen.setLayout(null);
		this.helpScreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mev) {
				setScreen(getIntroScreen());
				mev.consume();
			}

			@Override
			public void mouseEntered(MouseEvent mev) {
				mev.consume();
			}

			@Override
			public void mouseExited(MouseEvent mev) {
				mev.consume();
			}
		});
		this.helpScreen.setBackground(new Color(57, 137, 47));
		this.helpScreen.setOpaque(true);

		return this.helpScreen;
	}

	/**
	 * Returns the Winner Glass Pane.
	 * 
	 * @return the Winner Glass Pane
	 */
	private JPanel getWinnerScreen() {
		if (this.winnerScreen != null)
			return this.winnerScreen;

		this.winnerScreen = new JPanel() {
			/**
			 * Generated serialVersionUID
			 */
			private static final long serialVersionUID = -3019362974067223452L;

			@Override
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				g2.setColor(new Color(0, 0, 0, 80));
				g2.fillRect(0, 0, getWidth(), getHeight());
				ImageIcon img = Utility.getImage("winner.png");
				g2.drawImage(img.getImage(),
						(getWidth() / 2) - (img.getIconWidth() / 2),
						(getHeight() / 2) - (img.getIconHeight() / 2),
						img.getIconWidth(), img.getIconHeight(), null);

				super.paint(g);
			}
		};
		this.winnerScreen.setOpaque(false);

		this.winnerScreen.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent mev) {
				setScreen(nextWinnerScreen);
				mev.consume();
			}

			public void mouseEntered(MouseEvent mev) {
				mev.consume();
			}

			public void mouseExited(MouseEvent mev) {
				mev.consume();
			}

			public void mouseClicked(MouseEvent mev) {
				mev.consume();
			}

			public void mouseReleased(MouseEvent mev) {
				mev.consume();
			}
		});

		return this.winnerScreen;
	}

	/**
	 * Returns the High Score Screen.
	 * 
	 * @param position
	 *            the players position or -1 if not in
	 * 
	 * @return the high score screen
	 */
	private JPanel getHighScoresScreen(final int position) {
		this.highScoreScreen = new JPanel() {
			/**
			 * Generated serialVersionUID
			 */
			private static final long serialVersionUID = -3019362974067223452L;

			{

				setOpaque(false);
				int size = engine.getHallOfFame().getPlayers().size();
				final JPanel highScorePanel = new JPanel(new GridLayout(
						HallOfFame.MAX_SIZE_OF_PLAYERS, 2));
				highScorePanel.setBorder(BorderFactory.createEmptyBorder(4, 4,
						4, 4));
				for (int i = 0; i < size; i++) {
					final Player player = engine.getHallOfFame().getPlayers()
							.get(i);
					JComponent playerComponent = null;
					if (position == i) {
						playerComponent = new JTextField(
								player.getPlayerName(), 10);
						playerNameSet = false;
						final JTextField playerTextField = (JTextField) playerComponent;
						final int pos = i * 2;
						playerTextField.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if (!playerTextField.getText().trim()
										.equals("")) {
									player.setPlayerName(playerTextField
											.getText());
									highScorePanel.remove(pos);
									JLabel finalLabel = new JLabel(
											(position + 1) + ". "
													+ player.getPlayerName());
									designPlayerComponent(finalLabel);
									highScorePanel.add(finalLabel, pos);
									highScorePanel.updateUI();
									engine.saveHallOfFame();
									playerNameSet = true;
								}
							}
						});
					} else {
						playerComponent = new JLabel((i + 1) + ". "
								+ player.getPlayerName());
						designPlayerComponent(playerComponent);
					}
					highScorePanel.add(playerComponent);
					JLabel scoreLabel = new JLabel(player.getScore() + " pts");
					designPlayerComponent(scoreLabel);
					scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					highScorePanel.add(scoreLabel);
				}
				for (int i = size; i < HallOfFame.MAX_SIZE_OF_PLAYERS; i++) {
					highScorePanel.add(new JLabel());
					highScorePanel.add(new JLabel());
				}
				highScorePanel.setBorder(BorderFactory.createEmptyBorder(250,
						200, 0, 0));
				highScorePanel.setOpaque(false);
				add(highScorePanel);
			}

			private void designPlayerComponent(JComponent comp) {
				comp.setFont(new Font("Tahoma", Font.BOLD, 20));
				comp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
				comp.setOpaque(false);
				comp.setPreferredSize(new Dimension(160, 20));
			}

			@Override
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				g2.setColor(new Color(57, 137, 47));
				g2.fillRect(0, 0, getWidth(), getHeight());
				ImageIcon img = Utility.getImage("halloffame.png");
				g2.drawImage(img.getImage(),
						(getWidth() / 2) - (img.getIconWidth() / 2),
						(getHeight() / 2) - (img.getIconHeight() / 2),
						img.getIconWidth(), img.getIconHeight(), null);

				super.paint(g);
			}
		};

		this.highScoreScreen.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent mev) {
				mev.consume();
				if (playerNameSet)
					setScreen(getIntroScreen());
			}

			public void mouseEntered(MouseEvent mev) {
				mev.consume();
			}

			public void mouseExited(MouseEvent mev) {
				mev.consume();
			}

			public void mouseClicked(MouseEvent mev) {
				mev.consume();
			}

			public void mouseReleased(MouseEvent mev) {
				mev.consume();
			}
		});

		return this.highScoreScreen;
	}

	/**
	 * Shows an info for a period of time.
	 * 
	 * @param isHappy
	 *            if the info is a happy one
	 * @param msg
	 *            the message to show
	 */
	private void showInfo(boolean isHappy, String msg) {
		try {
			this.executor.shutdownNow();
			this.executor = Executors.newScheduledThreadPool(1);
		} catch (Exception e) {
		}

		if (isHappy) {
			this.infoLabel.setIcon(Utility.getImage("happy.png"));
		} else {
			this.infoLabel.setIcon(Utility.getImage("sad.png"));
		}
		this.infoLabel.setText(msg);

		executor.schedule(new Runnable() {
			public void run() {
				infoLabel.setText("");
				infoLabel.setIcon(Utility.getImage("nil.png"));
			}
		}, 5, TimeUnit.SECONDS);
	}

	/**
	 * This class is used to process mouse events in a House.
	 * 
	 * @author Panagiotis Peikidis
	 * @version 1.0
	 * 
	 */
	class HouseMouseListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent mev) {
			try {
				JHouse jHouse = (JHouse) mev.getSource();
				engine.addCardToHouse(jHouse.getHouse());
			} catch (HoCException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * A label that has a custom paint.
	 * 
	 * @author Panagiotis Peikidis
	 * @version 1.0
	 * 
	 */
	class InfoLabel extends JLabel {
		/**
		 * Generated serialVersionUID.
		 */
		private static final long serialVersionUID = 8624724240207508429L;

		/**
		 * The font for this label.
		 */
		private Font font = new Font("Verdana", Font.BOLD, 16);

		/**
		 * Pixles from the left.
		 */
		private int left;

		/**
		 * The label String.
		 */
		private String theString = "";

		/**
		 * Constructs an InfoLabel.
		 * 
		 * @param left
		 *            the distance of the text from the left side
		 */
		public InfoLabel(int left) {
			this.left = left;
		}

		/**
		 * Constructs an InfoLabel with an initial string.
		 * 
		 * @param left
		 *            the distance of the text from the left side
		 * @param initialString
		 *            the initial String
		 */
		public InfoLabel(int left, String initialString) {
			super(initialString);
			this.left = left;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			if (this.theString.trim().equals(""))
				return;

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.WHITE);
			g2.setFont(this.font);
			g2.drawString(this.theString, this.left, 26);
		}

		@Override
		public void setText(String msg) {
			this.theString = msg;
			repaint();
		}
	}
}
