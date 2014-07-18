package com.namyxc.collectcheese.models;

import java.util.ArrayList;

public class Game {

	public static final int CARD_DECK_INITIAL_SIZE = 6;
	public Object player1;
	public Object player2;
	public ArrayList<Object> boardDeck;
	public ArrayList<Object> cardDeck;

	public Game(){
		player1 = new Object();
		player2 = new Object();
		boardDeck = new ArrayList<Object>();
		cardDeck = new ArrayList<Object>();
		for (int i = 0; i < Game.CARD_DECK_INITIAL_SIZE;i++){
			cardDeck.add(new Object());
		}
	}
}
