package com.namyxc.collectcheese.models;

import com.namyxc.collectcheese.models.Card.cardType;

public class Scorer {
	Player owner;
	
	public Scorer(Player owner) {
		this.owner = owner;
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
				&& deck.get(1).Upside() == owner.EnemySymbol
				&& deck.get(2).Upside() == cardType.Enemy) return 1;
		if (
				deck.get(0).Upside() == owner.Symbol
				&& deck.get(1).Upside() == cardType.Reward
				&& deck.get(2).Upside() == owner.Symbol) return 2;
		if (
				deck.get(0).Upside() == cardType.Reward
				&& deck.get(1).Upside() == cardType.Reward
				&& deck.get(2).Upside() == cardType.Reward) return 3;
		return 0;
	}

	public Object owner() {
		// TODO Auto-generated method stub
		return null;
	}

}
