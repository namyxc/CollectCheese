package com.namyxc.collectcheese.models;

import com.namyxc.collectcheese.vos.SimpleObservable;
import com.namyxc.collectcheese.R.drawable;



public class Card extends SimpleObservable{
	private cardType upside;
	private cardType downside;
	
	public enum cardType {
		Reward,Player1,Player2, Enemy//, Empty
	}
	
	public Card(cardType upside, cardType downside) {
		this.upside = upside;
		this.downside = downside;
	}

	public cardType Upside(){
		return upside;
	}

	public cardType Downside() {
		return downside;
	}
	

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Card))return false;
	    Card otherMyClass = (Card)other;
	    return this.upside == otherMyClass.upside && this.downside == otherMyClass.downside; 
	}

	public int UpsideImage() {
		switch (upside){
		case Reward:
			return drawable.cheese;
		case Player1:
			return drawable.player1;
		case Player2:
			return drawable.player2;
		case Enemy:
			return drawable.cat;
		default:
			return 0;
		}
	}

	public int flipableUpsideImage() {
		switch (upside){
		case Reward:
			return drawable.cheese_flip;
		case Player1:
			return drawable.player1_flip;
		case Player2:
			return drawable.player2_flip;
		case Enemy:
			return drawable.cat_flip;
		default:
			return 0;
		}
	}

	public int DownsideImage() {
		switch (downside){
		case Reward:
			return drawable.cheese;
		case Player1:
			return drawable.player1;
		case Player2:
			return drawable.player2;
		case Enemy:
			return drawable.cat;
		default:
			return 0;
		}
	}
}
