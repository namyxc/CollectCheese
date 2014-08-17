package com.namyxc.collectcheese.models;

import java.util.ArrayList;

import com.namyxc.collectcheese.models.Card.cardType;
import com.namyxc.collectcheese.vos.OnChangeListener;
import com.namyxc.collectcheese.vos.SimpleObservable;

public class Game extends SimpleObservable implements OnChangeListener{

	
	public Player player1;
	public Player player2;
	private Deck boardDeck;
	private Deck cardDeck;

	public Game(){
		player1 = new Player(cardType.Player1, new Scorer(cardType.Player1, cardType.Player2));
		player2 = new Player(cardType.Player2, new Scorer(cardType.Player2, cardType.Player1));
		boardDeck = new Deck();
		cardDeck = new Deck(player1);
		cardDeck.addListener(this);
	}

	public void PlaySelectedCardAt(int i) {
		Card selectedCard = cardDeck.SelectedCard();
		PlaySelectedCardAt(i, selectedCard);
	}

	private void PlaySelectedCardAt(int i, Card selectedCard) {
		boardDeck.PlayCardAt(i, selectedCard);
		cardDeck.owner().addCurrectScore(boardDeck);
		cardDeck.owner().collectScoredCards(boardDeck);
		boardDeck.removeCardsAt(cardDeck.owner().getScoredCardsIndex(boardDeck));
		cardDeck.removeSelectedCard();
		cardDeck.setOwner(cardDeck.owner() == player1 ? player2: player1);
		cardDeck.Select(-1);
		boardDeck.ClearSelection();
		//if (cardDeckSize() == 0 && !cardDeck.owner().hasPrivateDeck()) currentPhase = gamePhase.PLAY_FROM_BOARD;
		notifyObservers(this);
	}
	


	private void PlaySelectedPrivateCardAt(int i, Card selectedCard) {
		boardDeck.PlayCardAt(i, selectedCard);
		
		ArrayList<Integer> removeIndex = new ArrayList<Integer>();
		if (cardDeckOwnerIsPlayer1()) {
			removeIndex.add(player1.getPrivateDeck().SelectedIndex());
			player1.getPrivateDeck().removeCardsAt(removeIndex);
		}else{
			removeIndex.add(player2.getPrivateDeck().SelectedIndex());
			player2.getPrivateDeck().removeCardsAt(removeIndex);
		}
		cardDeck.setOwner(cardDeck.owner() == player1 ? player2: player1);
		cardDeck.Select(-1);
		notifyObservers(this);
	}

	public int boardDeckSize() {
		return boardDeck.size();
	}

	public void InitCardDeck() {
		cardDeck.InitCards();
	}

	public void SelectFromCardDeck(int i) {
		cardDeck.Select(i);
		notifyObservers(this);
	}

	public ArrayList<Integer> AvailablePlacesOnBoard() {
		return boardDeck.AvailablePlaces();
	}

	public Card getCardDeckAt(int i) {
		return cardDeck.get(i);
	}

	public int cardDeckSize() {
		return cardDeck.size();
	}

	public Card getBoardDeckAt(int i) {
		return boardDeck.get(i);
	}

	public void onChange(Object model) {
		notifyObservers(this);
	}

	public int SelectedDeckCard() {
		return cardDeck.SelectedIndex();
	}

	public boolean cardDeckOwnerIsPlayer1() {
		return cardDeck.owner() == player1;
	}

	public boolean hasSelection() {
		return cardDeck.hasSelection();
	}

	public void PlaySelectedCardSwappedAt(int index) {
		cardDeck.SwapSelectedCard();
		Card selectedCard = cardDeck.SelectedCard();
		PlaySelectedCardAt(index, selectedCard);
	}

	public void SelectFromPrivateDeck(int i) {
		if (cardDeckOwnerIsPlayer1()) {
			player1.getPrivateDeck().Select(i);
		}else{
			player2.getPrivateDeck().Select(i);}
	}

	public void PlaySelectedPrivateCardAt(int i) {
		Card selectedCard;
		if (cardDeckOwnerIsPlayer1()) {
			selectedCard = player1.getPrivateDeck().SelectedCard();
		}else{
			selectedCard = player2.getPrivateDeck().SelectedCard();
		}		
		PlaySelectedPrivateCardAt(i, selectedCard);
	}

	public void PlaySelectedPrivateCardSwappedAt(int i) {
		Card selectedCard;
		if (cardDeckOwnerIsPlayer1()) {
			player1.getPrivateDeck().SwapSelectedCard();
			selectedCard = player1.getPrivateDeck().SelectedCard();
		}else{
			player2.getPrivateDeck().SwapSelectedCard();
			selectedCard = player2.getPrivateDeck().SelectedCard();
		}
		PlaySelectedPrivateCardAt(i, selectedCard);
	}

	public boolean cardDeckOwnerEmptyPrivateCard() {
		if (cardDeckOwnerIsPlayer1())  return player1.getPrivateDeck().size() == 0;
		else return player2.getPrivateDeck().size() == 0;
	}

	public void selectFromBoard(int i) {
		boardDeck.Select(i);
		notifyObservers(this);
	}

	public ArrayList<Integer> AvailablePlacesOnBoardFromBoard() {
		return boardDeck.AvailablePlacesFromBoard();
	}

	public boolean boardDeckHasSelection() {
		return boardDeck.hasSelection();
	}
}
