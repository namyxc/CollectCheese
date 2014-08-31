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

import android.view.View;
import android.widget.ImageButton;

import com.namyxc.collectcheese.activities.MainActivity;
import com.namyxc.collectcheese.models.Deck;


@RunWith(RobolectricTestRunner.class)
public class MainActivityPrivateDeckTest {

	private MainActivity activity;

	@Before
	public void setup() {
	  activity = Robolectric.buildActivity(MainActivity.class).create().get();
	}
	
	
	@Test
	public void PlayPrivateDeckTest(){
		
		// R  R  R P1 P1 P2
		// P1 P2 E P2 E  E
		activity.game.SelectFromCardDeck(5); 
		activity.game.PlaySelectedCardSwappedAt(0);
		activity.game.SelectFromCardDeck(3);
		activity.game.PlaySelectedCardSwappedAt(0);
		activity.game.SelectFromCardDeck(2); 
		activity.game.PlaySelectedCardSwappedAt(0);
		activity.game.SelectFromCardDeck(1); 
		activity.game.PlaySelectedCardSwappedAt(0);
		
		
		for(int i = 0; i < activity.game.player1.getPrivateDeck().size(); i++){
			ImageButton player1ImageButton = (ImageButton)activity.findViewById(i);
				ShadowImageView ib_player1ImageButton = Robolectric.shadowOf_(player1ImageButton);
				assertTrue(player1ImageButton.isEnabled());
				assertEquals(View.VISIBLE, player1ImageButton.getVisibility());
				assertEquals(activity.game.player1.getPrivateDeck().get(i).UpsideImage(), ib_player1ImageButton.getImageResourceId());
			
		}
		activity.game.SelectFromPrivateDeck(0);
		activity.game.PlaySelectedPrivateCardSwappedAt(0);
		
	}
}
