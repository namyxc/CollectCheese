package com.namyxc.collectcheese.test.models;

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
	}
}
