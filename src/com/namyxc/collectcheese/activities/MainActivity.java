package com.namyxc.collectcheese.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namyxc.collectcheese.R;
import com.namyxc.collectcheese.models.Deck;
import com.namyxc.collectcheese.models.Game;
import com.namyxc.collectcheese.vos.OnChangeListener;

public class MainActivity extends Activity implements OnChangeListener{

	private static final String TAG = MainActivity.class.getSimpleName();
	public Game game;

	private LinearLayout player1Deck;
	private TextView Player1Score;
	private LinearLayout boardDeck;
	private LinearLayout player2Deck;
	private TextView Player2Score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		player1Deck = (LinearLayout)findViewById(R.id.player1Deck);
		boardDeck = (LinearLayout)findViewById(R.id.boardDeck);
		player2Deck = (LinearLayout)findViewById(R.id.player2Deck);
		
		game = new Game();
		game.addListener(this);
		
		createDeckImageButtons();
		

		UpdateScore();
	}

	private void UpdateScore() {
		Player1Score = (TextView)findViewById(R.id.Player1Score);
		Player2Score = (TextView)findViewById(R.id.Player2Score);
		
		Player1Score.setText(String.valueOf(game.player1.Score()));
		Player2Score.setText(String.valueOf(game.player2.Score()));
	}

	private void createDeckImageButtons() {
		createPlayer1DeckImageButtons();
		createBoardDeckImageButtons();
		createPlayer2DeckImageButtons();
		
	}

	private void createPlayer2DeckImageButtons() {
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton Player2ImageButtonI = new ImageButton(this);
			Player2ImageButtonI.setId(20 + i);
			Player2ImageButtonI.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f/Deck.CARD_DECK_INITIAL_SIZE));
			Player2ImageButtonI.setScaleType(ImageView.ScaleType.FIT_CENTER);
			Player2ImageButtonI.setVisibility(View.INVISIBLE);
			Player2ImageButtonI.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					int index = v.getId() - 20;
					game.SelectFromCardDeck(index);
				}
			});
			player2Deck.addView(Player2ImageButtonI);
		}
	}

	private void createBoardDeckImageButtons() {
		for(int i = 0; i < Deck.BOARD_DECK_SIZE; i++){
			ImageButton boardImageButton = new ImageButton(this);
			boardImageButton.setId(10 + i); 
			boardImageButton.setVisibility(View.INVISIBLE);
			boardImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
			boardImageButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f/Deck.BOARD_DECK_SIZE));
			boardImageButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {

					int index = v.getId() - 10;
					if (game.cardDeckOwnerEmptyPrivateCard())
						game.PlaySelectedCardAt(index);
					else
						game.PlaySelectedPrivateCardAt(index);
				}
			});
			boardImageButton.setOnLongClickListener(new OnLongClickListener() {
				
				public boolean onLongClick(View v) {
					int index = v.getId() - 10;
					if (game.cardDeckOwnerEmptyPrivateCard())
						game.PlaySelectedCardSwappedAt(index);
					else
						game.PlaySelectedPrivateCardSwappedAt(index);
					return true;
				}
			});
			boardDeck.addView(boardImageButton);
		}	
	}

	private void createPlayer1DeckImageButtons() {
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton Player1ImageButtonI = new ImageButton(this);
			Player1ImageButtonI.setId(i);
			Player1ImageButtonI.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f/Deck.CARD_DECK_INITIAL_SIZE));
			Player1ImageButtonI.setScaleType(ImageView.ScaleType.FIT_CENTER);
			Player1ImageButtonI.setImageResource(game.getCardDeckAt(i).UpsideImage());
			Player1ImageButtonI.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					int index = v.getId();
					game.SelectFromCardDeck(index);
				}
			});
			player1Deck.addView(Player1ImageButtonI);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onChange(Object model) {
		runOnUiThread(new Runnable() {
			public void run() {
				updateView();
			}            
        });
	}

	protected void updateView() {
		
		int selectedIndex = game.SelectedDeckCard();
		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton Player1ImageButtonI = (ImageButton)findViewById(i);
			if (game.cardDeckOwnerIsPlayer1()){
				if (game.player1.getPrivateDeck().size() > 0 && i < game.player1.getPrivateDeck().size()){
					Player1ImageButtonI.setImageResource(game.player1.getPrivateDeck().get(i).UpsideImage());
					Player1ImageButtonI.setVisibility(View.VISIBLE);
					Player1ImageButtonI.setEnabled(true);
				}
				else if (game.player1.getPrivateDeck().size() > 0){
					Player1ImageButtonI.setVisibility(View.INVISIBLE);
					Player1ImageButtonI.setEnabled(false);
				}
				else{

					
					if (i < game.cardDeckSize()){
						Player1ImageButtonI.setImageResource(game.getCardDeckAt(i).UpsideImage());
						Player1ImageButtonI.setVisibility(game.cardDeckOwnerIsPlayer1() ? View.VISIBLE : View.INVISIBLE);
					}else{
						Player1ImageButtonI.setVisibility(View.INVISIBLE);
					}
					Player1ImageButtonI.setEnabled(game.cardDeckOwnerIsPlayer1() && i != selectedIndex);
				}
				
			}else{
				Player1ImageButtonI.setVisibility(View.INVISIBLE);
				Player1ImageButtonI.setEnabled(false);
			}
		}

		for(int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++){
			ImageButton Player2ImageButtonI = (ImageButton)findViewById(20 + i);
			if (!game.cardDeckOwnerIsPlayer1()){
				if (game.player2.getPrivateDeck().size() > 0 && i < game.player2.getPrivateDeck().size()){
					Player2ImageButtonI.setImageResource(game.player2.getPrivateDeck().get(i).UpsideImage());
					Player2ImageButtonI.setVisibility(View.VISIBLE);
					Player2ImageButtonI.setEnabled(true);
				}
				else if (game.player2.getPrivateDeck().size() > 0){
					Player2ImageButtonI.setVisibility(View.INVISIBLE);
					Player2ImageButtonI.setEnabled(false);
				}
				else{

					
					if (i < game.cardDeckSize()){
						Player2ImageButtonI.setImageResource(game.getCardDeckAt(i).UpsideImage());
						Player2ImageButtonI.setVisibility(!game.cardDeckOwnerIsPlayer1() ? View.VISIBLE : View.INVISIBLE);
					}else{
						Player2ImageButtonI.setVisibility(View.INVISIBLE);
					}
					Player2ImageButtonI.setEnabled(!game.cardDeckOwnerIsPlayer1() && i != selectedIndex);
				}
				
			}else{
				Player2ImageButtonI.setVisibility(View.INVISIBLE);
				Player2ImageButtonI.setEnabled(false);
			}
			/*Player2ImageButtonI.setEnabled(!game.cardDeckOwnerIsPlayer1() && i != selectedIndex);
			if (i < game.cardDeckSize()){
				Player2ImageButtonI.setImageResource(game.getCardDeckAt(i).UpsideImage());
				Player2ImageButtonI.setVisibility(game.cardDeckOwnerIsPlayer1() ? View.INVISIBLE : View.VISIBLE);
			}else{
				Player2ImageButtonI.setVisibility(View.INVISIBLE);
			}*/
		}
		
		int modifier = game.hasSelection() ? 1 : 0;
		for(int i = 0; i < Deck.BOARD_DECK_SIZE - modifier; i++){
			ImageButton boardImageButtonI = (ImageButton)findViewById(10 + modifier + i);
			if (i < game.boardDeckSize()){
				boardImageButtonI.setImageResource(game.getBoardDeckAt(i).UpsideImage());
				boardImageButtonI.setVisibility(View.VISIBLE);
			}else{
				boardImageButtonI.setVisibility(View.INVISIBLE);
			}
		}
		for(int i = 0; i < Deck.BOARD_DECK_SIZE ; i++){
			ImageButton boardImageButtonI = (ImageButton)findViewById(10 +  i);
			boardImageButtonI.setEnabled(false);
			boardImageButtonI.setSelected(false);
			boardImageButtonI.setPressed(false);
		}
		
		if (game.hasSelection()){
		ArrayList<Integer> AvailablePlaces = game.AvailablePlacesOnBoard();
		for(int i = 0; i < AvailablePlaces.size(); i++){
			ImageButton boardImageButtonI = (ImageButton)findViewById(10 + AvailablePlaces.get(i));

			boardImageButtonI.setEnabled(true);
			boardImageButtonI.setVisibility(View.VISIBLE);
			boardImageButtonI.setImageResource(R.drawable.question);
		}
		}

		UpdateScore();
	}

}
