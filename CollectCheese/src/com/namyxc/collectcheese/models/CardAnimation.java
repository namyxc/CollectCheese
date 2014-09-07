package com.namyxc.collectcheese.models;

import java.util.ArrayList;

import com.namyxc.collectcheese.models.Game.CardActions;

public class CardAnimation {

	public CardActions action;
	public int index;
	public Player player;
	public ArrayList<Integer> mustRemove;

	public CardAnimation(CardActions action, int index, Player player, ArrayList<Integer> mustRemove) {
		this.action = action;
		this.index = index;
		this.player = player;
		this.mustRemove = mustRemove;
	}

}
