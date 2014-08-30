package com.namyxc.collectcheese.models;

import java.util.ArrayList;

import com.namyxc.collectcheese.models.Card.cardType;

public class Scorer {
	cardType ownerSymbol;
	cardType enemySymbol;

	public Scorer(cardType owner, cardType enemy) {
		this.ownerSymbol = owner;
		this.enemySymbol = enemy;
	}

	public int score(Deck deck) {
		int sumOfScore = 0;
		for (int i = 0; i < deck.size()-2; i++){
			Deck threeCard = new Deck();
			threeCard.addCard(deck.get(i));
			threeCard.addCard(deck.get(i+1));
			threeCard.addCard(deck.get(i+2));
			sumOfScore += scoreThreeCard(threeCard);
		}
		return sumOfScore;
	}
	
	private int scoreThreeCard(Deck deck){

		if (
				deck.get(0).Upside() == cardType.Enemy
				&& deck.get(1).Upside() == enemySymbol
				&& deck.get(2).Upside() == cardType.Enemy) return 1;
		if (
				deck.get(0).Upside() == ownerSymbol
				&& deck.get(1).Upside() == cardType.Reward
				&& deck.get(2).Upside() == ownerSymbol) return 2;
		if (
				deck.get(0).Upside() == cardType.Reward
				&& deck.get(1).Upside() == cardType.Reward
				&& deck.get(2).Upside() == cardType.Reward) return 3;
		return 0;
	}

	public ArrayList<Integer> getScoredCards(Deck boardDeck) {
		ArrayList<Integer> scoredCardIndex = new ArrayList<Integer>();
		for (int i = 0; i < boardDeck.size()-2; i++){
			Deck threeCard = new Deck();
			threeCard.addCard(boardDeck.get(i));
			threeCard.addCard(boardDeck.get(i+1));
			threeCard.addCard(boardDeck.get(i+2));
			if (scoreThreeCard(threeCard) > 0)
				scoredCardIndex.add(i+1);
		}
		return scoredCardIndex;
	}

}
