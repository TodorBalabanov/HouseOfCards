package eu.veldsoft.house.of.cards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements EngineListener {

	private SoundPool sounds = null;

	private int clickSoundId = -1;

	private int closedSoundId = -1;

	private int errorSoundId = -1;

	private int scoreSoundId = -1;

	private int winSoundId = -1;

	private ImageView houses[][] = new ImageView[4][8];

	private ImageView blocked[] = new ImageView[4];

	private Map<Card, Integer> cardToDrawable = new HashMap<Card, Integer>();

	/**
	 * The Game Engine
	 */
	private Engine engine = new Engine();

	private void updateViews() {
		for (ImageView house[] : houses) {
			for (ImageView view : house) {
				view.setAlpha(0.0F);
				view.setImageBitmap(null);
			}
		}
		for (ImageView view : blocked) {
			view.setVisibility(View.INVISIBLE);
		}

		for (int i = 0; i < engine.getHouses().length; i++) {
			List<Card> cards = engine.getHouses()[i].getCards();
			for (int j = cards.size() - 1; j >= 0; j--) {
				houses[i][j].setImageResource(cardToDrawable.get(cards.get(j)));
			}

			if (engine.getHouses()[i].getState() == House.State.CLOSED) {
				blocked[i].setVisibility(View.VISIBLE);
				for (ImageView view : houses[i]) {
					view.setAlpha(0.5F);
				}
			} else if (engine.getHouses()[i].getState() == House.State.OPENED) {
				for (ImageView view : houses[i]) {
					view.setAlpha(1.0F);
				}
			}
		}

		((TextView) findViewById(R.id.spades_points_text)).setText(""
				+ engine.getHouses()[0].getPoints() + " pts");
		((TextView) findViewById(R.id.clubs_points_text)).setText(""
				+ engine.getHouses()[1].getPoints() + " pts");
		((TextView) findViewById(R.id.diamonds_points_text)).setText(""
				+ engine.getHouses()[2].getPoints() + " pts");
		((TextView) findViewById(R.id.hearts_points_text)).setText(""
				+ engine.getHouses()[3].getPoints() + " pts");

		if (engine.getPlayersHandCard() == null) {
			((ImageView) findViewById(R.id.deck_view))
					.setImageResource(R.drawable.empty);
			((ImageView) findViewById(R.id.current_card_view))
					.setImageResource(R.drawable.empty);
			((TextView) findViewById(R.id.card_value_text)).setText("");
		} else {
			((ImageView) findViewById(R.id.deck_view))
					.setImageResource(R.drawable.deck);
			((ImageView) findViewById(R.id.current_card_view))
					.setImageResource(cardToDrawable.get(engine
							.getPlayersHandCard()));
			((TextView) findViewById(R.id.card_value_text)).setText(engine
					.getPointSystem().getPoints(
							this.engine.getPlayersHandCard())
					+ " pts");
		}
		((TextView) findViewById(R.id.cards_left_text)).setText(engine
				.getDealerCardsLeft() + " left");
		((TextView) findViewById(R.id.points_text)).setText(engine
				.getPlayerScore() + " pts");

		if (engine.isGameOver() == true) {
			Toast.makeText(this, engine.getGameOverMessage(), Toast.LENGTH_LONG)
					.show();
			sounds.play(errorSoundId, 0.99f, 0.99f, 0, 0, 1);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		clickSoundId = sounds.load(GameActivity.this, R.raw.click, 1);
		closedSoundId = sounds.load(GameActivity.this, R.raw.closed, 1);
		errorSoundId = sounds.load(GameActivity.this, R.raw.error, 1);
		scoreSoundId = sounds.load(GameActivity.this, R.raw.score, 1);
		winSoundId = sounds.load(GameActivity.this, R.raw.win, 1);

		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.TWO),
				R.drawable.s2);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.THREE),
				R.drawable.s3);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.FOUR),
				R.drawable.s4);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.FIVE),
				R.drawable.s5);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.SIX),
				R.drawable.s6);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.SEVEN),
				R.drawable.s7);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.EIGHT),
				R.drawable.s8);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.NINE),
				R.drawable.s9);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.TEN),
				R.drawable.s10);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.JACK),
				R.drawable.sj);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.QUEEN),
				R.drawable.sq);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.KING),
				R.drawable.sk);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.ACE),
				R.drawable.sa);
		cardToDrawable.put(Card.instanceOf(CardSuit.SPADES, CardRank.JOKER),
				R.drawable.sjo);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.TWO),
				R.drawable.c2);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.THREE),
				R.drawable.c3);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.FOUR),
				R.drawable.c4);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.FIVE),
				R.drawable.c5);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.SIX),
				R.drawable.c6);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.SEVEN),
				R.drawable.c7);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.EIGHT),
				R.drawable.c8);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.NINE),
				R.drawable.c9);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.TEN),
				R.drawable.c10);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.JACK),
				R.drawable.cj);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.QUEEN),
				R.drawable.cq);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.KING),
				R.drawable.ck);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.ACE),
				R.drawable.ca);
		cardToDrawable.put(Card.instanceOf(CardSuit.CLUBS, CardRank.JOKER),
				R.drawable.cjo);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.TWO),
				R.drawable.d2);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.THREE),
				R.drawable.d3);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.FOUR),
				R.drawable.d4);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.FIVE),
				R.drawable.d5);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.SIX),
				R.drawable.d6);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.SEVEN),
				R.drawable.d7);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.EIGHT),
				R.drawable.d8);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.NINE),
				R.drawable.d9);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.TEN),
				R.drawable.d10);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.JACK),
				R.drawable.dj);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.QUEEN),
				R.drawable.dq);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.KING),
				R.drawable.dk);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.ACE),
				R.drawable.da);
		cardToDrawable.put(Card.instanceOf(CardSuit.DIAMONDS, CardRank.JOKER),
				R.drawable.djo);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.TWO),
				R.drawable.h2);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.THREE),
				R.drawable.h3);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.FOUR),
				R.drawable.h4);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.FIVE),
				R.drawable.h5);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.SIX),
				R.drawable.h6);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.SEVEN),
				R.drawable.h7);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.EIGHT),
				R.drawable.h8);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.NINE),
				R.drawable.h9);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.TEN),
				R.drawable.h10);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.JACK),
				R.drawable.hj);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.QUEEN),
				R.drawable.hq);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.KING),
				R.drawable.hk);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.ACE),
				R.drawable.ha);
		cardToDrawable.put(Card.instanceOf(CardSuit.HEARTS, CardRank.JOKER),
				R.drawable.hjo);

		blocked[0] = (ImageView) findViewById(R.id.stop0);
		blocked[1] = (ImageView) findViewById(R.id.stop1);
		blocked[2] = (ImageView) findViewById(R.id.stop2);
		blocked[3] = (ImageView) findViewById(R.id.stop3);

		houses[0][0] = (ImageView) findViewById(R.id.house00);
		houses[0][1] = (ImageView) findViewById(R.id.house01);
		houses[0][2] = (ImageView) findViewById(R.id.house02);
		houses[0][3] = (ImageView) findViewById(R.id.house03);
		houses[0][4] = (ImageView) findViewById(R.id.house04);
		houses[0][5] = (ImageView) findViewById(R.id.house05);
		houses[0][6] = (ImageView) findViewById(R.id.house06);
		houses[0][7] = (ImageView) findViewById(R.id.house07);
		houses[1][0] = (ImageView) findViewById(R.id.house10);
		houses[1][1] = (ImageView) findViewById(R.id.house11);
		houses[1][2] = (ImageView) findViewById(R.id.house12);
		houses[1][3] = (ImageView) findViewById(R.id.house13);
		houses[1][4] = (ImageView) findViewById(R.id.house14);
		houses[1][5] = (ImageView) findViewById(R.id.house15);
		houses[1][6] = (ImageView) findViewById(R.id.house16);
		houses[1][7] = (ImageView) findViewById(R.id.house17);
		houses[2][0] = (ImageView) findViewById(R.id.house20);
		houses[2][1] = (ImageView) findViewById(R.id.house21);
		houses[2][2] = (ImageView) findViewById(R.id.house22);
		houses[2][3] = (ImageView) findViewById(R.id.house23);
		houses[2][4] = (ImageView) findViewById(R.id.house24);
		houses[2][5] = (ImageView) findViewById(R.id.house25);
		houses[2][6] = (ImageView) findViewById(R.id.house26);
		houses[2][7] = (ImageView) findViewById(R.id.house27);
		houses[3][0] = (ImageView) findViewById(R.id.house30);
		houses[3][1] = (ImageView) findViewById(R.id.house31);
		houses[3][2] = (ImageView) findViewById(R.id.house32);
		houses[3][3] = (ImageView) findViewById(R.id.house33);
		houses[3][4] = (ImageView) findViewById(R.id.house34);
		houses[3][5] = (ImageView) findViewById(R.id.house35);
		houses[3][6] = (ImageView) findViewById(R.id.house36);
		houses[3][7] = (ImageView) findViewById(R.id.house37);

		((ImageButton) findViewById(R.id.spades_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(engine.getHouses()[0].isOpened() == false) {
							sounds.play(closedSoundId, 0.99f, 0.99f, 0, 0, 1);
						} else {
							sounds.play(clickSoundId, 0.99f, 0.99f, 0, 0, 1);
						}
						
						try {
							engine.addCardToHouse(0);
							updateViews();
						} catch (HoCException e) {
							e.printStackTrace();
						}
					}
				});

		((ImageButton) findViewById(R.id.clubs_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(engine.getHouses()[1].isOpened() == false) {
							sounds.play(closedSoundId, 0.99f, 0.99f, 0, 0, 1);
						} else {
							sounds.play(clickSoundId, 0.99f, 0.99f, 0, 0, 1);
						}
						
						try {
							engine.addCardToHouse(1);
							updateViews();
						} catch (HoCException e) {
							e.printStackTrace();
						}
					}
				});

		((ImageButton) findViewById(R.id.diamonds_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(engine.getHouses()[2].isOpened() == false) {
							sounds.play(closedSoundId, 0.99f, 0.99f, 0, 0, 1);
						} else {
							sounds.play(clickSoundId, 0.99f, 0.99f, 0, 0, 1);
						}
						
						try {
							engine.addCardToHouse(2);
							updateViews();
						} catch (HoCException e) {
							e.printStackTrace();
						}
					}
				});

		((ImageButton) findViewById(R.id.hearts_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(engine.getHouses()[3].isOpened() == false) {
							sounds.play(closedSoundId, 0.99f, 0.99f, 0, 0, 1);
						} else {
							sounds.play(clickSoundId, 0.99f, 0.99f, 0, 0, 1);
						}
						
						try {
							engine.addCardToHouse(3);
							updateViews();
						} catch (HoCException e) {
							e.printStackTrace();
						}
					}
				});

		engine.setJokerRule(getSharedPreferences(
				LobbyActivity.OPTIONS_PREFS_NAME, MODE_PRIVATE).getBoolean(
				LobbyActivity.JOKERS_KEY, true));
		engine.setSixCardsRule(getSharedPreferences(
				LobbyActivity.OPTIONS_PREFS_NAME, MODE_PRIVATE).getBoolean(
				LobbyActivity.SIX_KEY, true));

		engine.startNewGame(1, 1);
		updateViews();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		sounds.release();
		sounds = null;
	}

	@Override
	public void newGameStarted() {
	}

	@Override
	public void stateChanged(int appliedScore) {
		if (appliedScore != 0) {
			sounds.play(scoreSoundId, 0.99f, 0.99f, 0, 0, 1);
		}
		updateViews();
	}

	@Override
	public void gameWon(int position) {
		sounds.play(winSoundId, 0.99f, 0.99f, 0, 0, 1);
	}
}
