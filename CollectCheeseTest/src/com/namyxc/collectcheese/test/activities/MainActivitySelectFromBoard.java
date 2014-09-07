package com.namyxc.collectcheese.test.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowImageView;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageButton;

import com.namyxc.collectcheese.R;
import com.namyxc.collectcheese.R.drawable;
import com.namyxc.collectcheese.activities.MainActivity;
import com.namyxc.collectcheese.models.Deck;

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
		assertTrue(activity.game.boardDeckHasSelection());
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton boardImageButton = (ImageButton)activity.findViewById(10 + i);
			assertTrue(boardImageButton.isEnabled());
			assertEquals(View.VISIBLE, boardImageButton.getVisibility());			
		}
		
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
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
	
	@Test
	public void SwapNextTest(){

		int SELECTED_INDEX = 0;
		playAllCards();
		
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButton.performClick();
		
		ImageButton boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SELECTED_INDEX + 1);
		boardSelectedImageButtonNext.performClick();
		
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SELECTED_INDEX + 1);
		
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);
		ShadowImageView shadow_boardSelectedImageButtonNext = Robolectric.shadowOf_(boardSelectedImageButtonNext);
		
		assertEquals(activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage(), shadow_boardSelectedImageButton.getImageResourceId());
		assertEquals(activity.game.getBoardDeckAt(SELECTED_INDEX + 1).UpsideImage(), shadow_boardSelectedImageButtonNext.getImageResourceId());
		
		assertFalse(activity.game.boardDeckHasSelection());
	}
	

	@Test
	public void SwapPrevTest(){

		int SELECTED_INDEX = 1;
		playAllCards();
		
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButton.performClick();
		
		ImageButton boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SELECTED_INDEX - 1);
		boardSelectedImageButtonNext.performClick();
		
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SELECTED_INDEX - 1);
		
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);
		ShadowImageView shadow_boardSelectedImageButtonNext = Robolectric.shadowOf_(boardSelectedImageButtonNext);
		
		assertEquals(activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage(), shadow_boardSelectedImageButton.getImageResourceId());
		assertEquals(activity.game.getBoardDeckAt(SELECTED_INDEX - 1).UpsideImage(), shadow_boardSelectedImageButtonNext.getImageResourceId());
		
		assertFalse(activity.game.boardDeckHasSelection());
	}
	
	@Test
	public void SwapPrevTestOnLast(){

		int SELECTED_INDEX = 5;
		int SWAP_INDEX = 5;
		playAllCards2();
		
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButton.performClick();
		
		ImageButton boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SWAP_INDEX);
		boardSelectedImageButtonNext.performClick();
		
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SELECTED_INDEX - 1);
		
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);
		ShadowImageView shadow_boardSelectedImageButtonNext = Robolectric.shadowOf_(boardSelectedImageButtonNext);
		
		assertEquals(activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage(), shadow_boardSelectedImageButton.getImageResourceId());
		assertEquals(activity.game.getBoardDeckAt(SELECTED_INDEX - 1).UpsideImage(), shadow_boardSelectedImageButtonNext.getImageResourceId());
		
		assertFalse(activity.game.boardDeckHasSelection());
	}
	
	@Test
	public void FlipTestOnLast(){

		int SELECTED_INDEX = 5;
		int FLIP_INDEX = 6;
		playAllCards();
		
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		int selected_image = activity.game.getBoardDeckAt(SELECTED_INDEX).DownsideImage();
		boardSelectedImageButton.performClick();
		
		ImageButton boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + FLIP_INDEX);
		boardSelectedImageButtonNext.performClick();
		
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);
		
		assertEquals(selected_image, shadow_boardSelectedImageButton.getImageResourceId());
		
		assertFalse(activity.game.boardDeckHasSelection());
	}
	
	@Test
	public void SelectLastThenOther(){

		int SELECTED_INDEX = 5;
		playAllCards();
		
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButton.performClick();
		
		int FIRST_INDEX= 1;
		ImageButton firstAvailableButton = (ImageButton)activity.findViewById(10 + FIRST_INDEX);
		firstAvailableButton.performClick();
		
		ImageButton firstbutton = (ImageButton)activity.findViewById(10 + 0);
		ShadowImageView ib_firstbutton = Robolectric.shadowOf_(firstbutton);
		Resources r = activity.getResources();
		Drawable[] layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(0).UpsideImage());
		layers[1] = r.getDrawable(drawable.flip);
		LayerDrawable layerDrawable = new LayerDrawable(layers);

		assertEquals(layerDrawable, ib_firstbutton.getDrawable());
		

		ImageButton secondbutton = (ImageButton)activity.findViewById(10 + 1);
		ShadowImageView ib_secondbutton = Robolectric.shadowOf_(secondbutton);
		layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(1).UpsideImage());
		layers[1] = r.getDrawable(drawable.swap);
		layerDrawable = new LayerDrawable(layers);

		assertEquals(layerDrawable, ib_secondbutton.getDrawable());
		
		for(int i = FIRST_INDEX+1; i < activity.game.boardDeckSize(); i++){
			ImageButton boardButton = (ImageButton)activity.findViewById(10 + i);
			ShadowImageView ib_boardButton = Robolectric.shadowOf_(boardButton);
			assertEquals(ib_boardButton.getImageResourceId(), activity.game.getBoardDeckAt(i).UpsideImage());			
			assertTrue(boardButton.isEnabled());
		}
	}
	
	@Test
	public void SelectLastThenMoveToFirst(){

		int SELECTED_INDEX = 5;
		playAllCards();
		
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		int lastImage = activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage();
		boardSelectedImageButton.performClick();
		
		int FIRST_INDEX= 0;
		ImageButton firstAvailableButton = (ImageButton)activity.findViewById(10 + FIRST_INDEX);
		firstAvailableButton.performClick();
		
		ImageButton firstButton = (ImageButton)activity.findViewById(10);
		ShadowImageView ib_firstButton = Robolectric.shadowOf_(firstButton);
		assertEquals(lastImage, ib_firstButton.getImageResourceId());	
		
		
		for(int i = 0; i < activity.game.boardDeckSize(); i++){
			ImageButton boardButton = (ImageButton)activity.findViewById(10 + i);
			ShadowImageView ib_boardButton = Robolectric.shadowOf_(boardButton);
			assertEquals(ib_boardButton.getImageResourceId(), activity.game.getBoardDeckAt(i).UpsideImage());			
			assertTrue(boardButton.isEnabled());
		}
		
		assertFalse(activity.game.boardDeckHasSelection());
	}
	@Test
	public void SelectFirstThenMoveToLast(){

		int SELECTED_INDEX = 0;
		playAllCards();
		
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		int firstImage = activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage();
		boardSelectedImageButton.performClick();
		
		int LAST_INDEX= 6;
		ImageButton firstAvailableButton = (ImageButton)activity.findViewById(10 + LAST_INDEX);
		firstAvailableButton.performClick();
		
		ImageButton lastButton = (ImageButton)activity.findViewById(10 + LAST_INDEX - 1);
		ShadowImageView ib_firstButton = Robolectric.shadowOf_(lastButton);
		assertEquals(firstImage, ib_firstButton.getImageResourceId());	
		
		
		for(int i = 0; i < activity.game.boardDeckSize(); i++){
			ImageButton boardButton = (ImageButton)activity.findViewById(10 + i);
			ShadowImageView ib_boardButton = Robolectric.shadowOf_(boardButton);
			assertEquals(ib_boardButton.getImageResourceId(), activity.game.getBoardDeckAt(i).UpsideImage());			
			assertTrue(boardButton.isEnabled());
		}
		
		assertFalse(activity.game.boardDeckHasSelection());
	}
	
	

	@Test
	public void SelectFroamBoardOverlayTest(){

		int SELECTED_INDEX = 3;
		playAllCards();
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButton.performClick();
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);		

		Resources r = activity.getResources();
		Drawable[] layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage());
		layers[1] = r.getDrawable(drawable.flip);
		LayerDrawable layerDrawable = new LayerDrawable(layers);
		
		
		assertEquals(layerDrawable, shadow_boardSelectedImageButton.getDrawable());
		
		
		

		ImageButton boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SELECTED_INDEX + 1);
		ShadowImageView shadow_boardSelectedImageButtonNext = Robolectric.shadowOf_(boardSelectedImageButtonNext);
		layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(SELECTED_INDEX+1).UpsideImage());
		layers[1] = r.getDrawable(drawable.swap);
		layerDrawable = new LayerDrawable(layers);
		assertEquals(layerDrawable, shadow_boardSelectedImageButtonNext.getDrawable());
		


		ImageButton boardSelectedImageButtonPrev = (ImageButton)activity.findViewById(10 + SELECTED_INDEX - 1);
		ShadowImageView shadow_boardSelectedImageButtonPrev = Robolectric.shadowOf_(boardSelectedImageButtonPrev);
		layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(SELECTED_INDEX+1).UpsideImage());
		layers[1] = r.getDrawable(drawable.swap);
		layerDrawable = new LayerDrawable(layers);
		assertEquals(layerDrawable, shadow_boardSelectedImageButtonPrev.getDrawable());
	}
	
	@Test
	public void SelectFroamBoardFirstOverlayTest(){

		int SELECTED_INDEX = 0;
		playAllCards();
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		boardSelectedImageButton.performClick();
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);		

		Resources r = activity.getResources();
		Drawable[] layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage());
		layers[1] = r.getDrawable(drawable.flip);
		LayerDrawable layerDrawable = new LayerDrawable(layers);		
		
		assertEquals(layerDrawable, shadow_boardSelectedImageButton.getDrawable());
				

		ImageButton boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + SELECTED_INDEX + 1);
		ShadowImageView shadow_boardSelectedImageButtonNext = Robolectric.shadowOf_(boardSelectedImageButtonNext);
		layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(SELECTED_INDEX+1).UpsideImage());
		layers[1] = r.getDrawable(drawable.swap);
		layerDrawable = new LayerDrawable(layers);
		assertEquals(layerDrawable, shadow_boardSelectedImageButtonNext.getDrawable());
		
		ImageButton boardSelectedImageButtonPrev = (ImageButton)activity.findViewById(10 + activity.game.boardDeckSize());
		ShadowImageView shadow_boardSelectedImageButtonPrev = Robolectric.shadowOf_(boardSelectedImageButtonPrev);
		assertEquals(R.drawable.question, shadow_boardSelectedImageButtonPrev.getImageResourceId());
	}
	
	@Test
	public void SelectFroamBoardLastOverlayTest(){

		playAllCards();
		int SELECTED_INDEX = activity.game.boardDeckSize()-1;
		ImageButton boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		//01234S
		boardSelectedImageButton.performClick();
		//?01234S
		boardSelectedImageButton = (ImageButton)activity.findViewById(10 + SELECTED_INDEX+1);
		ShadowImageView shadow_boardSelectedImageButton = Robolectric.shadowOf_(boardSelectedImageButton);		

		Resources r = activity.getResources();
		Drawable[] layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(SELECTED_INDEX).UpsideImage());
		layers[1] = r.getDrawable(drawable.flip);
		LayerDrawable layerDrawable = new LayerDrawable(layers);		
		
		assertEquals(layerDrawable, shadow_boardSelectedImageButton.getDrawable());
				

		ImageButton boardSelectedImageButtonPrev = (ImageButton)activity.findViewById(10 + SELECTED_INDEX);
		ShadowImageView shadow_boardSelectedImageButtonPrev = Robolectric.shadowOf_(boardSelectedImageButtonPrev);
		layers = new Drawable[2];
		layers[0] = r.getDrawable(activity.game.getBoardDeckAt(SELECTED_INDEX-1).UpsideImage());
		layers[1] = r.getDrawable(drawable.swap);
		layerDrawable = new LayerDrawable(layers);
		assertEquals(layerDrawable, shadow_boardSelectedImageButtonPrev.getDrawable());
		
		ImageButton boardSelectedImageButtonNext = (ImageButton)activity.findViewById(10 + 0);
		ShadowImageView shadow_boardSelectedImageButtonNext = Robolectric.shadowOf_(boardSelectedImageButtonNext);
		assertEquals(R.drawable.question, shadow_boardSelectedImageButtonNext.getImageResourceId());
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

	private void playAllCards2() {
		// R  R  R P1 P1 P2
		// P1 P2 E P2 E  E
		activity.game.InitCardDeck();
		activity.game.SelectFromCardDeck(0); 
		activity.game.PlaySelectedCardSwappedAt(0);
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
	}


}
