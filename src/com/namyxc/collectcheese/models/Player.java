package com.namyxc.collectcheese.models;

import com.namyxc.collectcheese.models.Card.cardType;

public class Player {

	public Player(cardType playerCardType) {
		Symbol = playerCardType;
		EnemySymbol = Symbol == cardType.Player1 ? cardType.Player2 : cardType.Player1;
	}

	public cardType Symbol;
	public cardType EnemySymbol;

}
