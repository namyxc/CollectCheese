package com.namyxc.collectcheese.models;

import java.util.ArrayList;

import com.namyxc.collectcheese.vos.SimpleObservable;

public class Deck extends SimpleObservable{

	public static final int CARD_DECK_INITIAL_SIZE = 6;
	public static final int BOARD_DECK_SIZE = 8;
	private ArrayList<Card> cards; 
	private Object owner;
	private int selectedIndex;

	public Deck(Object owner) {
		InitCards();
		this.owner = owner;
	}

	public Deck() {
		cards = new ArrayList<Card>();
	}

	public int size() {
		return cards.size();
	}

	public Object owner() {
		return owner;
	}

	public void InitCards() {
		cards = new ArrayList<Card>();
		for (Card.cardType ctUpside : Card.cardType.values()) {
			for (Card.cardType ctDownside : Card.cardType.values()) {
				  if (ctUpside.compareTo(ctDownside) < 0){
					  cards.add(new Card(ctUpside, ctDownside));
				  }
			}
		}
	}

	public Card get(int i) {
		return cards.get(i);
	}

	public boolean contains(Card card) {
		return cards.contains(card);
	}
	
	public boolean hasSelection(){
		return selectedIndex > -1;
	}

	public void Select(int i) {
		selectedIndex = i;
	}

	public ArrayList<Integer> AvailablePlaces() {
		ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
		availablePlaces.add(0);
		if (cards.size() > 0) 
			availablePlaces.add(cards.size()+1);
		return availablePlaces;
	}

	public Card SelectedCard() {
		return get(selectedIndex);
	}

	public void PlayCardAt(int index, Card selectedCard) {
		int sizeOfCards = cards.size();
		if ((index == 0 && sizeOfCards == 0) || index == sizeOfCards+1) cards.add(selectedCard);
		else {
			/*cards.add(selectedCard);
			for (int i = sizeOfCards; i >0; i-- ){
				cards.set(i, cards.get(i-1));
			}*/
			cards.add(0, selectedCard);
		};
	}

	public void removeSelectedCard() {
		cards.remove(selectedIndex);
	}

	public int SelectedIndex() {
		return selectedIndex;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public void SwapSelectedCard() {
		Card selectedCard = SelectedCard();
		cards.set(selectedIndex, new Card(selectedCard.Downside(), selectedCard.Upside()));
	}

}
