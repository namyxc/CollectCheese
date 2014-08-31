package com.namyxc.collectcheese.models;

import com.namyxc.collectcheese.models.Game.CardActions;

public class CardAnimation {

	public CardActions action;
	public int index;
	public Player player;

	public CardAnimation(CardActions action, int index, Player player) {
		this.action = action;
		this.index = index;
		this.player = player;
	}

}
