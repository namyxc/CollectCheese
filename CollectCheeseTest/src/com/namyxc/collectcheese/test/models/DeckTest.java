package com.namyxc.collectcheese.test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.namyxc.collectcheese.models.Card;
import com.namyxc.collectcheese.models.Deck;
import com.namyxc.collectcheese.models.Player;

public class DeckTest {

	Deck deck;
	Player owner;
	
	@Before
	  public void setup()  {
		deck = new Deck(owner);
	  }

	
	@Test
	public void cardIsAtPlayer1(){
		assertEquals(owner, deck.owner());
	}
	
	@Test
	public void cardsInDeck(){
		deck.InitCards();
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE, deck.size());
		for (Card.cardType ctUpside : Card.cardType.values()) {
			for (Card.cardType ctDownside : Card.cardType.values()) {
				  if (ctUpside.compareTo(ctDownside) < 0){
					  assertTrue(deck.contains(new Card(ctUpside, ctDownside)));
				  }else{
					  assertFalse(deck.contains(new Card(ctUpside, ctDownside)));
				  }
			}
		}
	}
	
}
