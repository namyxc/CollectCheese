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
		
		// R  R  R P1 P1 P2
		// P1 P2 E P2 E  E
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
		
		assertFalse(activity.game.boardDeckHasSelection());
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertTrue(boardImageButton.isEnabled());
			assertEquals(View.VISIBLE, boardImageButton.getVisibility());
			
			
		}

		activity.game.selectFromBoard(0); 
		assertTrue(activity.game.boardDeckHasSelection());
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			if (i == 0 || i == 1 || i == Deck.CARD_DECK_INITIAL_SIZE-1){
				assertTrue(boardImageButton.isEnabled());
			}else{
				assertFalse(boardImageButton.isEnabled());
			}
			assertEquals(View.VISIBLE, boardImageButton.getVisibility());
			
			
		}
		
	}


}
