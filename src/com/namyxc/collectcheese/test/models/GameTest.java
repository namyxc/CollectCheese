package com.namyxc.collectcheese.test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.namyxc.collectcheese.models.Game;

@RunWith(RobolectricTestRunner.class)
public class GameTest {
	Game game;
	
	@Before
	  public void setup()  {
	    game = new Game();
	  }
	
	@Test
	public void hasPlayers(){
		assertNotNull(game.player1);
		assertNotNull(game.player2);
	}
	
	@Test
	public void hasDecks(){
		assertNotNull(game.boardDeck);
		assertNotNull(game.cardDeck);
	}
	
	@Test
	public void boardDeckIsEmpty(){
		assertEquals(0, game.boardDeck.size());
	}
	
	@Test
	public void cardDeckIsFull(){
		assertEquals(game.CARD_DECK_INITIAL_SIZE, game.cardDeck.size());
	}
}
