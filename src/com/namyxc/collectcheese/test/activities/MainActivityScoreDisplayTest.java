package com.namyxc.collectcheese.test.activities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.widget.TextView;

import com.namyxc.collectcheese.R;
import com.namyxc.collectcheese.activities.MainActivity;


@RunWith(RobolectricTestRunner.class)
public class MainActivityScoreDisplayTest {

	private MainActivity activity;

	@Before
	public void setup() {
	  activity = Robolectric.buildActivity(MainActivity.class).create().get();
	}
	
	
	@Test
	public void Play(){
		TextView Player1Score = (TextView)activity.findViewById(R.id.Player1Score);
		TextView Player2Score = (TextView)activity.findViewById(R.id.Player2Score);
		int Player1ScoreValue = Integer.valueOf(Player1Score.getText().toString());
		int Player2ScoreValue = Integer.valueOf(Player2Score.getText().toString());
		assertEquals(0, Player1ScoreValue);
		assertEquals(0, Player2ScoreValue);
		
		// R  R  R P1 P1 P2
		// P1 P2 E P2 E  E
		activity.game.SelectFromCardDeck(5); 
		activity.game.PlaySelectedCardSwappedAt(0);
		activity.game.SelectFromCardDeck(3);
		activity.game.PlaySelectedCardSwappedAt(0);
		activity.game.SelectFromCardDeck(2); 
		activity.game.PlaySelectedCardSwappedAt(0);
		

		Player1ScoreValue = Integer.valueOf(Player1Score.getText().toString());
		Player2ScoreValue = Integer.valueOf(Player2Score.getText().toString());
		assertEquals(1, activity.game.player1.Score());
		assertEquals(0, activity.game.player2.Score());
		assertEquals(1, Player1ScoreValue);
		assertEquals(0, Player2ScoreValue);
		
	}

}
