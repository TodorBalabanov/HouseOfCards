package eu.veldsoft.house.of.cards;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class LobbyActivity extends Activity {

	static final String OPTIONS_PREFS_NAME = "eu.veldsoft.house.of.cards.options";
	static final String JOKERS_KEY = "eu.veldsoft.house.of.cards.jokers.key";
	static final String SIX_KEY = "eu.veldsoft.house.of.cards.six.key";

	private boolean jokers = true;
	private boolean six = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby);

		((ImageButton) findViewById(R.id.jokers_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						jokers = !jokers;

						SharedPreferences.Editor editor = getSharedPreferences(
								OPTIONS_PREFS_NAME, MODE_PRIVATE).edit();
						editor.putBoolean(JOKERS_KEY, jokers);
						editor.commit();

						if (jokers) {
							((ImageButton) view)
									.setImageResource(R.drawable.jokers_true);
						} else {
							((ImageButton) view)
									.setImageResource(R.drawable.jokers_false);
						}
					}
				});

		((ImageButton) findViewById(R.id.six_cards_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						six = !six;

						SharedPreferences.Editor editor = getSharedPreferences(
								OPTIONS_PREFS_NAME, MODE_PRIVATE).edit();
						editor.putBoolean(SIX_KEY, six);
						editor.commit();

						if (six) {
							((ImageButton) view)
									.setImageResource(R.drawable.sixcards_true);
						} else {
							((ImageButton) view)
									.setImageResource(R.drawable.sixcards_false);
						}
					}
				});

		((ImageButton) findViewById(R.id.help_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LobbyActivity.this.startActivity(new Intent(
								LobbyActivity.this, HelpActivity.class));
					}
				});

		((ImageButton) findViewById(R.id.highscores_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LobbyActivity.this.startActivity(new Intent(
								LobbyActivity.this, HighscoresActivity.class));
					}
				});

		((ImageButton) findViewById(R.id.new_game_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LobbyActivity.this.startActivity(new Intent(
								LobbyActivity.this, GameActivity.class));
					}
				});
	}
}
