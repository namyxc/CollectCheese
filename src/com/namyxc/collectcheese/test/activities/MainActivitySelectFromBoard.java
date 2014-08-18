package com.namyxc.collectcheese.test.activities;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
import com.namyxc.collectcheese.models.Game;

@RunWith(RobolectricTestRunner.class)
public class MainActivitySelectFromBoard {

	private MainActivity activity;

	@Before
	public void setup() {
	  activity = Robolectric.buildActivity(MainActivity.class).create().get();
	}

	@Test
	public void SelectFroamBoardTest(){

		int SELECTED_INDEX = 3;
		playAllCards();
		
		assertFalse(activity.game.boardDeckHasSelection());
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertTrue(boardImageButton.isEnabled());
			assertEquals(View.VISIBLE, boardImageButton.getVisibility());
			
			
		}
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButton.performClick();
		activity.game.selectFromBoard(SELECTED_INDEX); 
		assertTrue(activity.game.boardDeckHasSelection());
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertTrue(boardImageButton.isEnabled());
			assertEquals(View.VISIBLE, boardImageButton.getVisibility());			
		}
		
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);
		assertEquals(shadow_boardSelectedImageButton.getImageResourceId(), activity.game.getBoardDeckAt(SELECTED_INDEX).flipableUpsideImage());
		int downside = activity.game.getBoardDeckAt(SELECTED_INDEX).DownsideImage();
		
		boardSelectedImageButton.performClick();
		
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE-1; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertTrue(boardImageButton.isEnabled());
			assertEquals(View.VISIBLE, boardImageButton.getVisibility());			
		}
		ImageButton lastButton = (ImageButton)activity.findViewById(10 + Deck.CARD_DECK_INITIAL_SIZE-1);
		assertEquals(View.INVISIBLE, lastButton.getVisibility());
		
		ImageButton firstButton = (ImageButton)activity.findViewById(10 + 0);
		firstButton.performClick();
		firstButton.performClick();
		
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE-1; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertFalse(boardImageButton.isEnabled());			
		}
	}

	private void playAllCards() {
		// R  R  R P1 P1 P2
		// P1 P2 E P2 E  E
		activity.game.InitCardDeck();
		activity.game.SelectFromCardDeck(0); 
		activity.game.PlaySelectedCardAt(0);
		activity.game.SelectFromCardDeck(0);
		activity.game.PlaySelectedCardAt(0);
		activity.game.SelectFromCardDeck(0); 
		activity.game.PlaySelectedCardSwappedAt(0);
		activity.game.SelectFromCardDeck(0); 
		activity.game.PlaySelectedCardAt(0);
		activity.game.SelectFromCardDeck(0);
		activity.game.PlaySelectedCardAt(0);
		activity.game.SelectFromCardDeck(0); 
		activity.game.PlaySelectedCardSwappedAt(0);
	}


}
