package com.namyxc.collectcheese.test.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowImageView;

import android.view.View;
import android.widget.ImageButton;

import com.namyxc.collectcheese.activities.MainActivity;
import com.namyxc.collectcheese.models.Deck;

@RunWith(RobolectricTestRunner.class)
public class MainActivitySetupTest {
	
	private MainActivity activity;

	@Before
	public void setup() {
	  activity = Robolectric.buildActivity(MainActivity.class).create().get();
	  assertNotNull(activity);
	}
	

	@Test
	public void DeckIsAtPlayer1(){
		for(int i = 0; i < activity.game.cardDeckSize(); i++){
			ImageButton player1ImageButton = (ImageButton)activity.findViewById(i);
			assertNotNull(player1ImageButton);		
			ShadowImageView ib_player1ImageButton = Robolectric.shadowOf_(player1ImageButton);
			assertEquals(ib_player1ImageButton.getImageResourceId(), activity.game.getCardDeckAt(i).UpsideImage());
			assertEquals(View.VISIBLE, player1ImageButton.getVisibility());
		}	
	}
	
	@Test
	public void BoardDeckIsInvisible(){
		for(int i = 0; i < Deck.BOARD_DECK_SIZE; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertNotNull(boardImageButton);		
			assertEquals(View.INVISIBLE, boardImageButton.getVisibility());
		}	
	}

}
