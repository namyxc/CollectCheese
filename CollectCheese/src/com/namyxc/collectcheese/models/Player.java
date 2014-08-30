package com.namyxc.collectcheese.models;

import java.util.ArrayList;

import com.namyxc.collectcheese.models.Card.cardType;

public class Player {
	private Scorer scorer;
	private int score = 0;
	private Deck privateDeck = new Deck();
	
	public Deck getPrivateDeck(){
		return privateDeck;
	}
	
	public boolean hasCardInPrivateDeckAt(int index){
		return privateDeck.size() > index;
	}
	
	public ArrayList<Integer> getScoredCardsIndex(Deck boardDeck){
		return scorer.getScoredCards(boardDeck);
	}
	
	public Player(cardType playerCardType, Scorer scorer) {
		Symbol = playerCardType;
		EnemySymbol = Symbol == cardType.Player1 ? cardType.Player2 : cardType.Player1;
		this.scorer = scorer;
	}

	public cardType Symbol;
	public cardType EnemySymbol;
	public int Score() {
		return score;
	}
	public void addCurrectScore(Deck deck) {
		score += scorer.score(deck);
	}
	public void collectScoredCards(Deck boardDeck) {
		privateDeck = new Deck();
		ArrayList<Integer> scoredCardsIndex = getScoredCardsIndex(boardDeck);
		for (int i = 0; i < scoredCardsIndex.size(); i++){
			privateDeck.addCard(boardDeck.get(scoredCardsIndex.get(i)));
		}
	}

	public boolean hasPrivateDeck() {
		return privateDeck.size() > 0;
	}

}
