package com.namyxc.collectcheese.test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.namyxc.collectcheese.models.Card;
import com.namyxc.collectcheese.models.Card.cardType;
import com.namyxc.collectcheese.models.Deck;
import com.namyxc.collectcheese.models.Game;

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
	public void boardDeckIsEmpty(){
		assertEquals(0, game.boardDeckSize());
	}
	
	@Test
	public void selectFirstCard(){
		game.InitCardDeck();
		game.SelectFromCardDeck(0);
		ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
		availablePlaces.add(0);
		assertEquals(availablePlaces, game.AvailablePlacesOnBoard());
		
	}
	@Test
	public void placeFirstCard(){
		game.InitCardDeck();
		game.SelectFromCardDeck(0);
		Card selectedCard = game.getCardDeckAt(0);
		game.PlaySelectedCardAt(0);
		
		assertFalse(game.hasSelection());
		assertEquals(1, game.boardDeckSize());
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE-1, game.cardDeckSize());
		assertEquals(selectedCard, game.getBoardDeckAt(0));
		assertFalse(game.cardDeckOwnerIsPlayer1());
		
		ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
		availablePlaces.add(0);
		availablePlaces.add(2);
		assertEquals(availablePlaces, game.AvailablePlacesOnBoard());
	}
	@Test
	public void placeFirstCardSwapped(){
		game.InitCardDeck();
		game.SelectFromCardDeck(0);
		Card selectedCard = game.getCardDeckAt(0);
		Card swappedCard = new Card(selectedCard.Downside(), selectedCard.Upside());
		game.PlaySelectedCardSwappedAt(0);
		
		assertFalse(game.hasSelection());
		assertEquals(1, game.boardDeckSize());
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE-1, game.cardDeckSize());
		assertEquals(swappedCard, game.getBoardDeckAt(0));
		assertFalse(game.cardDeckOwnerIsPlayer1());
		
		ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
		availablePlaces.add(0);
		availablePlaces.add(2);
		assertEquals(availablePlaces, game.AvailablePlacesOnBoard());
	}
	
	@Test
	public void removeCardOnScore(){
		game.InitCardDeck();
		game.SelectFromCardDeck(5); 
		game.PlaySelectedCardSwappedAt(0);
		game.SelectFromCardDeck(3);
		game.PlaySelectedCardSwappedAt(0);
		game.SelectFromCardDeck(2); 
		game.PlaySelectedCardSwappedAt(0);
		
		assertEquals(1, game.player1.getPrivateDeck().size());
		assertEquals(cardType.Player2, game.player1.getPrivateDeck().get(0).Upside());
		
		assertEquals(2, game.boardDeckSize());
	}
	
	@Test
	public void selectFromBoard1(){
		game.InitCardDeck();
		playAllCardFromDeck();
		assertTrue(game.cardDeckOwnerIsPlayer1());
		assertEquals(0, game.cardDeckSize());
		
		game.selectFromBoard(1);
		
		ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
		availablePlaces.add(0);
		availablePlaces.add(1);
		availablePlaces.add(2);
		assertEquals(availablePlaces, game.AvailablePlacesOnBoardFromBoard());
	}
	
	@Test
	public void selectFromBoard0(){
		game.InitCardDeck();
		playAllCardFromDeck();

		game.selectFromBoard(0);
		
		ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
		availablePlaces.add(0);
		availablePlaces.add(1);
		availablePlaces.add(Deck.CARD_DECK_INITIAL_SIZE-1);
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE, game.boardDeckSize());
		assertEquals(availablePlaces, game.AvailablePlacesOnBoardFromBoard());
	}
	
	@Test
	public void selectFromBoard3AndFlipIt(){

		int SELECTED_INDEX = 3;
		game.InitCardDeck();
		playAllCardFromDeck();
		cardType downSide = game.getBoardDeckAt(SELECTED_INDEX).Downside();

		game.selectFromBoard(SELECTED_INDEX);
		game.flipSelectedBoardCard();
		
		assertEquals(downSide, game.getBoardDeckAt(SELECTED_INDEX).Upside());
		assertFalse(game.boardDeckHasSelection());
		assertFalse(game.cardDeckOwnerIsPlayer1());
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE-1, game.boardDeckSize());
	}
	
	@Test
	public void selectFromBoard0AndSwapWith1(){

		int SELECTED_INDEX = 0;
		int SWAP_INDEX = 1;
		game.InitCardDeck();
		playAllCardFromDeck();
		cardType upSideSelected = game.getBoardDeckAt(SELECTED_INDEX).Upside();
		cardType upSideSwap = game.getBoardDeckAt(SWAP_INDEX).Upside();

		game.selectFromBoard(SELECTED_INDEX);
		game.swapSelectedWithNext();
		
		assertEquals(upSideSwap, game.getBoardDeckAt(SELECTED_INDEX).Upside());
		assertEquals(upSideSelected, game.getBoardDeckAt(SWAP_INDEX).Upside());
		assertFalse(game.boardDeckHasSelection());
		assertFalse(game.cardDeckOwnerIsPlayer1());
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE, game.boardDeckSize());
	}
	
	@Test
	public void selectFromBoard0AndMoveToEnd(){

		int SELECTED_INDEX = 0;
		game.InitCardDeck();
		playAllCardFromDeck();
		cardType upSideSelected = game.getBoardDeckAt(SELECTED_INDEX).Upside();

		game.selectFromBoard(SELECTED_INDEX);
		game.moveSelectedToOtherEnd();
		
		assertEquals(upSideSelected, game.getBoardDeckAt(game.boardDeckSize()-1).Upside());
		assertFalse(game.boardDeckHasSelection());
		assertFalse(game.cardDeckOwnerIsPlayer1());
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE, game.boardDeckSize());
	}
	
	@Test
	public void selectFromBoardLastAndMoveToEnd(){

		int SELECTED_INDEX = Deck.CARD_DECK_INITIAL_SIZE-1;
		game.InitCardDeck();
		playAllCardFromDeck();
		cardType upSideSelected = game.getBoardDeckAt(SELECTED_INDEX).Upside();

		game.selectFromBoard(SELECTED_INDEX);
		game.moveSelectedToOtherEnd();
		
		assertEquals(upSideSelected, game.getBoardDeckAt(0).Upside());
		assertFalse(game.boardDeckHasSelection());
		assertFalse(game.cardDeckOwnerIsPlayer1());
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE, game.boardDeckSize());
	}
	
	@Test
	public void selectFromBoard1AndSwapWith0(){

		int SELECTED_INDEX = 1;
		int SWAP_INDEX = 0;
		game.InitCardDeck();
		playAllCardFromDeck();
		cardType upSideSelected = game.getBoardDeckAt(SELECTED_INDEX).Upside();
		cardType upSideSwap = game.getBoardDeckAt(SWAP_INDEX).Upside();

		game.selectFromBoard(SELECTED_INDEX);
		game.swapSelectedWithPrev();
		
		assertEquals(upSideSwap, game.getBoardDeckAt(SELECTED_INDEX).Upside());
		assertEquals(upSideSelected, game.getBoardDeckAt(SWAP_INDEX).Upside());
		assertFalse(game.boardDeckHasSelection());
		assertFalse(game.cardDeckOwnerIsPlayer1());
		assertEquals(Deck.CARD_DECK_INITIAL_SIZE, game.boardDeckSize());
	}
	
	@Test
	public void selectFromBoard5(){
		game.InitCardDeck();
		playAllCardFromDeck();

		game.selectFromBoard(5);
		
		ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
		availablePlaces.add(0);
		availablePlaces.add(Deck.CARD_DECK_INITIAL_SIZE-2);
		availablePlaces.add(Deck.CARD_DECK_INITIAL_SIZE-1);
		assertEquals(availablePlaces, game.AvailablePlacesOnBoardFromBoard());
	}

	private void playAllCardFromDeck() {
		// R  R  R P1 P1 P2
		// P1 P2 E P2 E  E
		game.SelectFromCardDeck(0); 
		game.PlaySelectedCardAt(0);
		game.SelectFromCardDeck(0); 
		game.PlaySelectedCardAt(0);
		game.SelectFromCardDeck(0); 
		game.PlaySelectedCardSwappedAt(0);
		game.SelectFromCardDeck(0); 
		game.PlaySelectedCardAt(0);
		game.SelectFromCardDeck(0); 
		game.PlaySelectedCardAt(0);
		game.SelectFromCardDeck(0); 
		game.PlaySelectedCardSwappedAt(0);
		// E  P1 P1 E R  R
		// P2 E  P2 R P2 P1
	}
}
