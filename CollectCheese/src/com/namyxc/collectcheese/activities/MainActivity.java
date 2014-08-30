package com.namyxc.collectcheese.activities;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namyxc.collectcheese.R;
import com.namyxc.collectcheese.R.drawable;
import com.namyxc.collectcheese.models.Deck;
import com.namyxc.collectcheese.models.Game;
import com.namyxc.collectcheese.vos.OnChangeListener;

public class MainActivity extends Activity implements OnChangeListener {

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

		player1Deck = (LinearLayout) findViewById(R.id.player1Deck);
		boardDeck = (LinearLayout) findViewById(R.id.boardDeck);
		player2Deck = (LinearLayout) findViewById(R.id.player2Deck);

		game = new Game();
		game.addListener(this);

		createDeckImageButtons();

		UpdateScore();
	}

	private void UpdateScore() {
		Player1Score = (TextView) findViewById(R.id.Player1Score);
		Player2Score = (TextView) findViewById(R.id.Player2Score);

		Player1Score.setText(String.valueOf(game.player1.Score()));
		Player2Score.setText(String.valueOf(game.player2.Score()));
	}

	private void createDeckImageButtons() {
		createPlayer1DeckImageButtons();
		createBoardDeckImageButtons();
		createPlayer2DeckImageButtons();

	}

	private void createPlayer2DeckImageButtons() {
		for (int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++) {
			ImageButton Player2ImageButtonI = new ImageButton(this);
			Player2ImageButtonI.setId(20 + i);
			Player2ImageButtonI.setLayoutParams(new LinearLayout.LayoutParams(
					0, LinearLayout.LayoutParams.MATCH_PARENT,
					1.0f / Deck.CARD_DECK_INITIAL_SIZE));
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
	
	private void playAnim(View v, int animId){
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), animId);
		v.startAnimation(hyperspaceJumpAnimation);
	}

	private void createBoardDeckImageButtons() {
		for (int i = 0; i < Deck.BOARD_DECK_SIZE; i++) {
			ImageButton boardImageButton = new ImageButton(this);
			boardImageButton.setId(10 + i);
			boardImageButton.setVisibility(View.INVISIBLE);
			boardImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
			boardImageButton.setLayoutParams(new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.MATCH_PARENT,
					1.0f / Deck.BOARD_DECK_SIZE));
			boardImageButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					
					int index = v.getId() - 10;
					if (game.hasSelection()) {
						if (game.cardDeckOwnerEmptyPrivateCard())
							game.PlaySelectedCardAt(index);
						else
							game.PlaySelectedPrivateCardAt(index);
					} else if (game.boardDeckHasSelection()) {
						if (game.boardDeckSelectedIndex() == game.boardDeckSize() - 1) index--;
						if (game.boardDeckSelectedIndex() == index) {
							playAnim(v,R.anim.shrink);
							game.flipSelectedBoardCard();
							playAnim(v,R.anim.grow);				
						} else if (index - game.boardDeckSelectedIndex() == 1 ) {
							game.swapSelectedWithNext();
						} else if (game.boardDeckSelectedIndex() - index == 1) {
							game.swapSelectedWithPrev();
						}else if (index == -1 || index == game.boardDeckSize()){
							game.moveSelectedToOtherEnd();
						}else {
							game.selectFromBoard(index);
						}
					} else {
						game.selectFromBoard(index);
					}
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
		for (int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++) {
			ImageButton Player1ImageButtonI = new ImageButton(this);
			Player1ImageButtonI.setId(i);
			Player1ImageButtonI.setLayoutParams(new LinearLayout.LayoutParams(
					0, LinearLayout.LayoutParams.MATCH_PARENT,
					1.0f / Deck.CARD_DECK_INITIAL_SIZE));
			Player1ImageButtonI.setScaleType(ImageView.ScaleType.FIT_CENTER);
			Player1ImageButtonI.setImageResource(game.getCardDeckAt(i)
					.UpsideImage());
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

		for (int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++) {
			ImageButton Player1ImageButtonI = (ImageButton) findViewById(i);
			if (game.cardDeckOwnerIsPlayer1()) {
				if (game.player1.hasCardInPrivateDeckAt(i)) {
					showImageButton(Player1ImageButtonI, game.player1
							.getPrivateDeck().get(i).UpsideImage());
				} else if (game.player1.getPrivateDeck().size() > 0) {
					hideImageButton(Player1ImageButtonI);
				} else {
					if (i < game.cardDeckSize()) {
						showImageButton(Player1ImageButtonI, game
								.getCardDeckAt(i).UpsideImage());
					} else {
						hideImageButton(Player1ImageButtonI);
					}
					Player1ImageButtonI.setEnabled(game
							.cardDeckOwnerIsPlayer1() && i != selectedIndex);
				}
			} else {
				hideImageButton(Player1ImageButtonI);
			}
		}

		for (int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++) {
			ImageButton Player2ImageButtonI = (ImageButton) findViewById(20 + i);
			if (!game.cardDeckOwnerIsPlayer1()) {
				if (game.player2.hasCardInPrivateDeckAt(i)) {
					showImageButton(Player2ImageButtonI, game.player2
							.getPrivateDeck().get(i).UpsideImage());
				} else if (game.player2.getPrivateDeck().size() > 0) {
					hideImageButton(Player2ImageButtonI);
				} else {
					if (i < game.cardDeckSize()) {
						showImageButton(Player2ImageButtonI, game
								.getCardDeckAt(i).UpsideImage());
					} else {
						hideImageButton(Player2ImageButtonI);
					}
					Player2ImageButtonI.setEnabled(!game
							.cardDeckOwnerIsPlayer1() && i != selectedIndex);
				}
			} else {
				hideImageButton(Player2ImageButtonI);
			}
		}

		int modifier = game.hasSelection() ? 1 : 0;
		for (int i = 0; i < Deck.BOARD_DECK_SIZE - modifier; i++) {
			ImageButton boardImageButtonI = (ImageButton) findViewById(10
					+ modifier + i);
			if (i < game.boardDeckSize()) {
				showImageButton(boardImageButtonI, game.getBoardDeckAt(i)
						.UpsideImage());
			} else {
				hideImageButton(boardImageButtonI);
			}
		}
		for (int i = 0; i < Deck.BOARD_DECK_SIZE; i++) {
			ImageButton boardImageButtonI = (ImageButton) findViewById(10 + i);
			boardImageButtonI.setEnabled(false);
			boardImageButtonI.setSelected(false);
			boardImageButtonI.setPressed(false);
		}

		if (game.hasSelection()) {
			ArrayList<Integer> AvailablePlaces = game.AvailablePlacesOnBoard();
			for (int i = 0; i < AvailablePlaces.size(); i++) {
				ImageButton boardImageButtonI = (ImageButton) findViewById(10 + AvailablePlaces
						.get(i));

				showImageButton(boardImageButtonI, R.drawable.question);
			}
		} else if ((game.cardDeckSize() == 0) && game.boardDeckHasSelection()) {
			for (int i = 0; i < game.boardDeckSize(); i++) {
				ImageButton boardImageButtonI = (ImageButton) findViewById(10 + i);
				boardImageButtonI.setEnabled(true);
			}
			ImageButton selectedboardImageButtonI = (ImageButton) findViewById(10 + game
					.boardDeckSelectedIndex());
			addFlipIcon(selectedboardImageButtonI,
					game.getBoardDeckAt(game.boardDeckSelectedIndex())
							.UpsideImage());

			if (game.boardDeckSelectedIndex() < game.boardDeckSize() - 1) {
				ImageButton selectedboardImageButtonNext = (ImageButton) findViewById(10 + game
						.boardDeckSelectedIndex() + 1);
				addSwapIcon(selectedboardImageButtonNext,
						game.getBoardDeckAt(game.boardDeckSelectedIndex() + 1)
								.UpsideImage());
			} else {
				ImageButton boardImageButtonFirst = (ImageButton) findViewById(10);
				showImageButton(boardImageButtonFirst, R.drawable.question);
				for (int i = 1; i < game.boardDeckSize(); i++) {
					ImageButton boardImageButtonI = (ImageButton) findViewById(10 + i);
					showImageButton(boardImageButtonI,
							game.getBoardDeckAt(i - 1).UpsideImage());
				}

				ImageButton boardImageButtonPrev = (ImageButton) findViewById(10 + game
						.boardDeckSize() - 1);
				addSwapIcon(boardImageButtonPrev,
						game.getBoardDeckAt(game.boardDeckSelectedIndex() - 1)
								.UpsideImage());
				ImageButton boardImageButtonSelected = (ImageButton) findViewById(10 + game
						.boardDeckSize());
				addFlipIcon(boardImageButtonSelected,
						game.getBoardDeckAt(game.boardDeckSelectedIndex())
								.UpsideImage());
			}

			if (game.boardDeckSelectedIndex() > 0
					&& game.boardDeckSelectedIndex() != game.boardDeckSize() - 1) {
				ImageButton selectedboardImageButtonPrev = (ImageButton) findViewById(10 + game
						.boardDeckSelectedIndex() - 1);
				addSwapIcon(selectedboardImageButtonPrev,
						game.getBoardDeckAt(game.boardDeckSelectedIndex() - 1)
								.UpsideImage());
			} else if (game.boardDeckSelectedIndex() != game.boardDeckSize() - 1) {
				ImageButton selectedboardImageButtonPrev = (ImageButton) findViewById(10 + game
						.boardDeckSize());
				showImageButton(selectedboardImageButtonPrev,
						R.drawable.question);
			}
		} else if (game.cardDeckSize() == 0
				&& game.cardDeckOwnerEmptyPrivateCard()) {

			for (int i = 0; i < game.boardDeckSize(); i++) {
				ImageButton boardImageButtonI = (ImageButton) findViewById(10 + i);
				boardImageButtonI.setEnabled(true);
			}

		}

		UpdateScore();
	}

	private void addIcon(ImageButton imageButton, int resourceId,
			int iconResourceId) {
		Resources r = getResources();
		Drawable[] layers = new Drawable[2];
		layers[0] = r.getDrawable(resourceId);
		layers[1] = r.getDrawable(iconResourceId);
		LayerDrawable layerDrawable = new LayerDrawable(layers);
		imageButton.setImageDrawable(layerDrawable);
		imageButton.setVisibility(View.VISIBLE);
		imageButton.setEnabled(true);
	}

	public void addFlipIcon(ImageButton imageButton, int resourceId) {
		addIcon(imageButton, resourceId, drawable.flip);
	}

	public void addSwapIcon(ImageButton imageButton, int resourceId) {
		addIcon(imageButton, resourceId, drawable.swap);
	}

	private void showImageButton(ImageButton imageButton, int resourceId) {
		imageButton.setImageResource(resourceId);
		imageButton.setVisibility(View.VISIBLE);
		imageButton.setEnabled(true);
	}

	private void hideImageButton(ImageButton Player1ImageButtonI) {
		Player1ImageButtonI.setVisibility(View.INVISIBLE);
		Player1ImageButtonI.setEnabled(false);
	}

}
