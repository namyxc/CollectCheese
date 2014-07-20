package com.namyxc.collectcheese.test.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.namyxc.collectcheese.models.Card;
import com.namyxc.collectcheese.models.Card.cardType;
import com.namyxc.collectcheese.models.Deck;
import com.namyxc.collectcheese.models.Game;
import com.namyxc.collectcheese.models.Player;
import com.namyxc.collectcheese.models.Scorer;

public class ScoreTest {
	Scorer scorer;
	Player Player1 = new Player(cardType.Player1);
	
	@Before
	  public void setup()  {
		scorer = new Scorer(Player1);
	  }
	
	@Test
	public void emptyDeck(){
		Deck deck = new Deck();
		assertEquals(0,scorer.score(deck));
	}
	

	@Test
	public void ThreeCardDeckNoPoint(){
		Deck deck = new Deck();
		deck.addCard(new Card(cardType.Reward, null));
		deck.addCard(new Card(cardType.Player2, null));
		deck.addCard(new Card(cardType.Enemy, null));
		assertEquals(0,scorer.score(deck));
	}
	

	@Test
	public void ThreeCardDeckNoPoint2(){
		Deck deck = new Deck();
		deck.addCard(new Card(cardType.Reward, null));
		deck.addCard(new Card(cardType.Reward, null));
		deck.addCard(new Card(cardType.Enemy, null));
		assertEquals(0,scorer.score(deck));
	}
	

	@Test
	public void ThreeCardDeckWin1(){
		Deck deck = new Deck();
		deck.addCard(new Card(cardType.Enemy, null));
		deck.addCard(new Card(cardType.Player2, null));
		deck.addCard(new Card(cardType.Enemy, null));
		assertEquals(1,scorer.score(deck));
	}	

	@Test
	public void ThreeCardDeckWin2(){
		Deck deck = new Deck();
		deck.addCard(new Card(cardType.Player1, null));
		deck.addCard(new Card(cardType.Reward, null));
		deck.addCard(new Card(cardType.Player1, null));
		assertEquals(2,scorer.score(deck));
	}	

	@Test
	public void ThreeCardDeckWin3(){
		Deck deck = new Deck();
		deck.addCard(new Card(cardType.Reward, null));
		deck.addCard(new Card(cardType.Reward, null));
		deck.addCard(new Card(cardType.Reward, null));
		assertEquals(3,scorer.score(deck));
	}	

	@Test
	public void FourCardDeckWin1(){
		Deck deck = new Deck();
		deck.addCard(new Card(cardType.Player1, null));
		deck.addCard(new Card(cardType.Enemy, null));
		deck.addCard(new Card(cardType.Player2, null));
		deck.addCard(new Card(cardType.Enemy, null));
		assertEquals(1,scorer.score(deck));
	}	

	@Test
	public void DoubleScoreWin(){
		Deck deck = new Deck();
		deck.addCard(new Card(cardType.Player1, null));
		deck.addCard(new Card(cardType.Reward, null));
		deck.addCard(new Card(cardType.Player1, null));
		deck.addCard(new Card(cardType.Enemy, null));
		deck.addCard(new Card(cardType.Player2, null));
		deck.addCard(new Card(cardType.Enemy, null));
		assertEquals(3,scorer.score(deck));
	}
	
	

}
