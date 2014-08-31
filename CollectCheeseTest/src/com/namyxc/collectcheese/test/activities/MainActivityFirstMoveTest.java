package com.namyxc.collectcheese.test.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.namyxc.collectcheese.models.Game.CardActions;

@RunWith(RobolectricTestRunner.class)
public class MainActivityFirstMoveTest {

	private MainActivity activity;

	@Before
	public void setup() {
	  activity = Robolectric.buildActivity(MainActivity.class).create().get();
	}
	
	@Test
	public void updateViewOnTwoSteps(){
		updateViewOnSelectPlayer1();
		updateViewOnSelectPlayer2();
	}
	
	@Test
	public void selectAndselectAgain(){
		int SELECTED_CARD = 0;
		Card selectedCard = activity.game.getCardDeckAt(SELECTED_CARD);
		ImageButton selectedImageButton = (ImageButton)activity.findViewById(SELECTED_CARD);
		selectedImageButton.performClick();
		for(int i = 0; i < activity.game.cardDeckSize(); i++){
			ImageButton player1ImageButton = (ImageButton)activity.findViewById(i);
			assertTrue(player1ImageButton.isEnabled() == (i != SELECTED_CARD));
		}
		
		justOneQuestionMark();
		

		int SELECTED_CARD2 = 2;
		selectedCard = activity.game.getCardDeckAt(SELECTED_CARD2);
		selectedImageButton = (ImageButton)activity.findViewById(SELECTED_CARD2);
		selectedImageButton.performClick();
		

		
		justOneQuestionMark();
	}

	private void justOneQuestionMark() {
		ImageButton availableImageButton = (ImageButton)activity.findViewById(10 + 0);
		assertNotNull(availableImageButton);
		assertEquals(View.VISIBLE, availableImageButton.getVisibility());
		ShadowImageView ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), R.drawable.question);
		

		for(int i = 1; i < 10; i++){
			assertNull(activity.findViewById(10 + i));
		}
	}
	
	private void updateViewOnSelectPlayer2() {
		int SELECTED_CARD = 0;
		Card selectedCard = activity.game.getCardDeckAt(SELECTED_CARD);
		int selectedImage = selectedCard.UpsideImage();
		ImageButton selectedImageButton = (ImageButton)activity.findViewById(20 + 0);
		selectedImageButton.performClick();
		for(int i = 0; i < activity.game.cardDeckSize(); i++){
			ImageButton player2ImageButton = (ImageButton)activity.findViewById(20 + i);
			assertTrue(player2ImageButton.isEnabled() == (i != SELECTED_CARD));
		}
		
		ImageButton availableImageButton = (ImageButton)activity.findViewById(10 + 0);
		assertNotNull(availableImageButton);
		assertEquals(View.VISIBLE, availableImageButton.getVisibility());
		ShadowImageView ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), R.drawable.question);
		
		availableImageButton = (ImageButton)activity.findViewById(10 + 2);
		assertNotNull(availableImageButton);
		assertEquals(View.VISIBLE, availableImageButton.getVisibility());
		ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), R.drawable.question);
		
		availableImageButton.performClick();
		assertFalse(activity.game.hasSelection());	


		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			assertNull(activity.findViewById(20 + i));
		}

		
		for(int i = 0; i < activity.game.cardDeckSize(); i++){
			ImageButton player1ImageButton = (ImageButton)activity.findViewById(0 + i);
			assertNotNull(player1ImageButton);
				assertEquals(View.VISIBLE, player1ImageButton.getVisibility());

				ShadowImageView ib_player2ImageButton = Robolectric.shadowOf_(player1ImageButton);
				assertEquals(ib_player2ImageButton.getImageResourceId(), activity.game.getCardDeckAt(i).UpsideImage());
				assertTrue(player1ImageButton.isEnabled());
		}
		

		for(int i = 0; i < activity.game.boardDeckSize(); i++){
			ImageButton BoardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertFalse(BoardImageButton.isEnabled());
			assertEquals(View.VISIBLE, BoardImageButton.getVisibility());
		}
		
		availableImageButton = (ImageButton)activity.findViewById(10 + 0);
		ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), selectedImage);
	}
	public void updateViewOnSelectPlayer1(){
		int SELECTED_CARD = 0;
		Card selectedCard = activity.game.getCardDeckAt(SELECTED_CARD);
		int selectedImage = selectedCard.UpsideImage();
		ImageButton selectedImageButton = (ImageButton)activity.findViewById(SELECTED_CARD);
		selectedImageButton.performClick();
		for(int i = 0; i < activity.game.cardDeckSize(); i++){
			ImageButton player1ImageButton = (ImageButton)activity.findViewById(i);
			assertTrue(player1ImageButton.isEnabled() == (i != SELECTED_CARD));
		}
		
		ImageButton availableImageButton = (ImageButton)activity.findViewById(10 + 0);
		assertNotNull(availableImageButton);
		assertEquals(View.VISIBLE, availableImageButton.getVisibility());
		ShadowImageView ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), R.drawable.question);
		
		availableImageButton.performClick();
		assertFalse(activity.game.hasSelection());

		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			assertNull(activity.findViewById(i));
		}

		
		for(int i = 0; i < activity.game.cardDeckSize(); i++){
			ImageButton player2ImageButton = (ImageButton)activity.findViewById(20 + i);
			assertNotNull(player2ImageButton);
				assertEquals(View.VISIBLE, player2ImageButton.getVisibility());

				ShadowImageView ib_player2ImageButton = Robolectric.shadowOf_(player2ImageButton);
				assertEquals(ib_player2ImageButton.getImageResourceId(), activity.game.getCardDeckAt(i).UpsideImage());
				assertTrue(player2ImageButton.isEnabled());
		}
		

		for(int i = 0; i < activity.game.boardDeckSize(); i++){
			ImageButton BoardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertFalse(BoardImageButton.isEnabled());
			assertEquals(View.VISIBLE, BoardImageButton.getVisibility());
		}
		
		availableImageButton = (ImageButton)activity.findViewById(10 + 0);
		ib_firstStep = Robolectric.shadowOf_(availableImageButton);
		assertEquals(ib_firstStep.getImageResourceId(), selectedImage);
		
	}
}
