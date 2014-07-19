package com.namyxc.collectcheese.models;

import java.util.ArrayList;

import com.namyxc.collectcheese.vos.OnChangeListener;
import com.namyxc.collectcheese.vos.SimpleObservable;

public class Game extends SimpleObservable implements OnChangeListener{

	public Object player1;
	public Object player2;
	private Deck boardDeck;
	private Deck cardDeck;

	public Game(){
		player1 = new Object();
		player2 = new Object();
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
		cardDeck.removeSelectedCard();
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
}
