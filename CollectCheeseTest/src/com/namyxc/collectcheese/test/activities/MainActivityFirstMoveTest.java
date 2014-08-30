package com.namyxc.collectcheese.test.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowImageView;

import com.namyxc.collectcheese.R;
import android.view.View;
import android.widget.ImageButton;

import com.namyxc.collectcheese.activities.MainActivity;
import com.namyxc.collectcheese.models.Card;
import com.namyxc.collectcheese.models.Deck;

@RunWith(RobolectricTestRunner.class)
public class MainActivityFirstMoveTest {

	private MainActivity activity;

	@Before
	public void setup() {
	  activity = Robolectric.buildActivity(MainActivity.class).create().get();
	}
	
	@Test
	public void updateViewOnSelectPlayer1(){
		activity.game.SelectFromCardDeck(0);
		Card selectedCard = activity.game.getCardDeckAt(activity.game.SelectedDeckCard());
		int selectedImage = selectedCard.UpsideImage();
		ImageButton selectedImageButton = (ImageButton)activity.findViewById(0);
		selectedImageButton.performClick();
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton player1ImageButton = (ImageButton)activity.findViewById(i);
			assertTrue(player1ImageButton.isEnabled() == (i != 0));
		}
		
		ImageButton availableImageButton = (ImageButton)activity.findViewById(10 + 0);
		assertNotNull(availableImageButton);
		assertEquals(View.VISIBLE, availableImageButton.getVisibility());
		ShadowImageView ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), R.drawable.question);
		
		availableImageButton.performClick();
		assertFalse(activity.game.hasSelection());

		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton player1ImageButton = (ImageButton)activity.findViewById(i);
			assertEquals(View.INVISIBLE, player1ImageButton.getVisibility());
		}

		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton player2ImageButton = (ImageButton)activity.findViewById(20 + i);
			if (i < activity.game.cardDeckSize()){
				assertEquals(activity.game.cardDeckOwnerIsPlayer1() ? View.INVISIBLE : View.VISIBLE, player2ImageButton.getVisibility());

				ShadowImageView ib_player2ImageButton = Robolectric.shadowOf_(player2ImageButton);
				assertEquals(ib_player2ImageButton.getImageResourceId(), activity.game.getCardDeckAt(i).UpsideImage());
			}else{
				assertEquals(View.INVISIBLE, player2ImageButton.getVisibility());
			}
			
			assertTrue(player2ImageButton.isEnabled());
		}
		

		for(int i = 0; i < Deck.BOARD_DECK_SIZE; i++){
			ImageButton BoardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertFalse(BoardImageButton.isEnabled());
			assertEquals(i<activity.game.boardDeckSize() ? View.VISIBLE : View.INVISIBLE, BoardImageButton.getVisibility());
		}
		
		availableImageButton = (ImageButton)activity.findViewById(10 + 0);
		ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), selectedImage);
		
	}
}
