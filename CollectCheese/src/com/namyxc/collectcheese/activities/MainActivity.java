package com.namyxc.collectcheese.activities;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
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
import com.namyxc.collectcheese.models.CardAnimation;
import com.namyxc.collectcheese.models.Deck;
import com.namyxc.collectcheese.models.Game;
import com.namyxc.collectcheese.models.Game.CardActions;
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
		// createBoardDeckImageButtons();
		createPlayer2DeckImageButtons();

	}

	private void createPlayer2DeckImageButtons() {
		for (int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++) {
			ImageButton Player2ImageButtonI = new ImageButton(this);
			Player2ImageButtonI.setId(20 + i);
			Player2ImageButtonI.setLayoutParams(new LinearLayout.LayoutParams(
					(int) getResources().getDimension(R.dimen.card_width), LinearLayout.LayoutParams.MATCH_PARENT));
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

	private void playAnim(View v, int animId) {
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				getApplicationContext(), animId);
		v.startAnimation(hyperspaceJumpAnimation);
	}

	private void createBoardDeckImageButtons() {
		for (int i = 0; i < Deck.BOARD_DECK_SIZE; i++) {
			ImageButton boardImageButton = new ImageButton(this);
			boardImageButton.setId(10 + i);
			boardImageButton.setVisibility(View.INVISIBLE);
			boardImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
			boardImageButton.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.card_width),
					LinearLayout.LayoutParams.MATCH_PARENT));
			boardImageButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					int index = v.getId() - 10;
					if (game.hasSelection()) {
						if (game.cardDeckOwnerEmptyPrivateCard())
							game.PlaySelectedCardAt(index);
						else
							game.PlaySelectedPrivateCardAt(index);
					} else if (game.boardDeckHasSelection()) {
						if (game.boardDeckSelectedIndex() == game
								.boardDeckSize() - 1)
							index--;
						if (game.boardDeckSelectedIndex() == index) {
							playAnim(v, R.anim.shrink);
							game.flipSelectedBoardCard();
							playAnim(v, R.anim.grow);
						} else if (index - game.boardDeckSelectedIndex() == 1) {
							game.swapSelectedWithNext();
						} else if (game.boardDeckSelectedIndex() - index == 1) {
							game.swapSelectedWithPrev();
						} else if (index == -1 || index == game.boardDeckSize()) {
							game.moveSelectedToOtherEnd();
						} else {
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
					(int) getResources().getDimension(R.dimen.card_width), LinearLayout.LayoutParams.MATCH_PARENT));
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
		ShowAnimations(game.getLastAnimations());
		/*
		 * int selectedIndex = game.SelectedDeckCard();
		 * 
		 * for (int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++) { ImageButton
		 * Player1ImageButtonI = (ImageButton) findViewById(i); if
		 * (game.cardDeckOwnerIsPlayer1()) { if
		 * (game.player1.hasCardInPrivateDeckAt(i)) {
		 * showImageButton(Player1ImageButtonI, game.player1
		 * .getPrivateDeck().get(i).UpsideImage()); } else if
		 * (game.player1.getPrivateDeck().size() > 0) {
		 * hideImageButton(Player1ImageButtonI); } else { if (i <
		 * game.cardDeckSize()) { showImageButton(Player1ImageButtonI, game
		 * .getCardDeckAt(i).UpsideImage()); } else {
		 * hideImageButton(Player1ImageButtonI); }
		 * Player1ImageButtonI.setEnabled(game .cardDeckOwnerIsPlayer1() && i !=
		 * selectedIndex); } } else { hideImageButton(Player1ImageButtonI); } }
		 * 
		 * for (int i = 0; i < Deck.CARD_DECK_INITIAL_SIZE; i++) { ImageButton
		 * Player2ImageButtonI = (ImageButton) findViewById(20 + i); if
		 * (!game.cardDeckOwnerIsPlayer1()) { if
		 * (game.player2.hasCardInPrivateDeckAt(i)) {
		 * showImageButton(Player2ImageButtonI, game.player2
		 * .getPrivateDeck().get(i).UpsideImage()); } else if
		 * (game.player2.getPrivateDeck().size() > 0) {
		 * hideImageButton(Player2ImageButtonI); } else { if (i <
		 * game.cardDeckSize()) { showImageButton(Player2ImageButtonI, game
		 * .getCardDeckAt(i).UpsideImage()); } else {
		 * hideImageButton(Player2ImageButtonI); }
		 * Player2ImageButtonI.setEnabled(!game .cardDeckOwnerIsPlayer1() && i
		 * != selectedIndex); } } else { hideImageButton(Player2ImageButtonI); }
		 * }
		 * 
		 * int modifier = game.hasSelection() ? 1 : 0; for (int i = 0; i <
		 * Deck.BOARD_DECK_SIZE - modifier; i++) { ImageButton boardImageButtonI
		 * = (ImageButton) findViewById(10 + modifier + i); if (i <
		 * game.boardDeckSize()) { showImageButton(boardImageButtonI,
		 * game.getBoardDeckAt(i) .UpsideImage()); } else {
		 * hideImageButton(boardImageButtonI); } } for (int i = 0; i <
		 * Deck.BOARD_DECK_SIZE; i++) { ImageButton boardImageButtonI =
		 * (ImageButton) findViewById(10 + i);
		 * boardImageButtonI.setEnabled(false);
		 * boardImageButtonI.setSelected(false);
		 * boardImageButtonI.setPressed(false); }
		 * 
		 * if (game.hasSelection()) { ArrayList<Integer> AvailablePlaces =
		 * game.AvailablePlacesOnBoard(); for (int i = 0; i <
		 * AvailablePlaces.size(); i++) { ImageButton boardImageButtonI =
		 * (ImageButton) findViewById(10 + AvailablePlaces .get(i));
		 * 
		 * showImageButton(boardImageButtonI, R.drawable.question); } } else if
		 * ((game.cardDeckSize() == 0) && game.boardDeckHasSelection()) { for
		 * (int i = 0; i < game.boardDeckSize(); i++) { ImageButton
		 * boardImageButtonI = (ImageButton) findViewById(10 + i);
		 * boardImageButtonI.setEnabled(true); } ImageButton
		 * selectedboardImageButtonI = (ImageButton) findViewById(10 + game
		 * .boardDeckSelectedIndex()); addFlipIcon(selectedboardImageButtonI,
		 * game.getBoardDeckAt(game.boardDeckSelectedIndex()) .UpsideImage());
		 * 
		 * if (game.boardDeckSelectedIndex() < game.boardDeckSize() - 1) {
		 * ImageButton selectedboardImageButtonNext = (ImageButton)
		 * findViewById(10 + game .boardDeckSelectedIndex() + 1);
		 * addSwapIcon(selectedboardImageButtonNext,
		 * game.getBoardDeckAt(game.boardDeckSelectedIndex() + 1)
		 * .UpsideImage()); } else { ImageButton boardImageButtonFirst =
		 * (ImageButton) findViewById(10);
		 * showImageButton(boardImageButtonFirst, R.drawable.question); for (int
		 * i = 1; i < game.boardDeckSize(); i++) { ImageButton boardImageButtonI
		 * = (ImageButton) findViewById(10 + i);
		 * showImageButton(boardImageButtonI, game.getBoardDeckAt(i -
		 * 1).UpsideImage()); }
		 * 
		 * ImageButton boardImageButtonPrev = (ImageButton) findViewById(10 +
		 * game .boardDeckSize() - 1); addSwapIcon(boardImageButtonPrev,
		 * game.getBoardDeckAt(game.boardDeckSelectedIndex() - 1)
		 * .UpsideImage()); ImageButton boardImageButtonSelected = (ImageButton)
		 * findViewById(10 + game .boardDeckSize());
		 * addFlipIcon(boardImageButtonSelected,
		 * game.getBoardDeckAt(game.boardDeckSelectedIndex()) .UpsideImage()); }
		 * 
		 * if (game.boardDeckSelectedIndex() > 0 &&
		 * game.boardDeckSelectedIndex() != game.boardDeckSize() - 1) {
		 * ImageButton selectedboardImageButtonPrev = (ImageButton)
		 * findViewById(10 + game .boardDeckSelectedIndex() - 1);
		 * addSwapIcon(selectedboardImageButtonPrev,
		 * game.getBoardDeckAt(game.boardDeckSelectedIndex() - 1)
		 * .UpsideImage()); } else if (game.boardDeckSelectedIndex() !=
		 * game.boardDeckSize() - 1) { ImageButton selectedboardImageButtonPrev
		 * = (ImageButton) findViewById(10 + game .boardDeckSize());
		 * showImageButton(selectedboardImageButtonPrev, R.drawable.question); }
		 * } else if (game.cardDeckSize() == 0 &&
		 * game.cardDeckOwnerEmptyPrivateCard()) {
		 * 
		 * for (int i = 0; i < game.boardDeckSize(); i++) { ImageButton
		 * boardImageButtonI = (ImageButton) findViewById(10 + i);
		 * boardImageButtonI.setEnabled(true); }
		 * 
		 * }
		 * 
		 * UpdateScore();
		 */
	}

	private void ShowAnimations(CardAnimation lastAnimation) {
		switch (lastAnimation.action) {
		case SELECT_FROM_DECK:
			int viewIdModificatorSelect = lastAnimation.player == game.player1 ? 0 : 20;
			for (int i = 0; i < game.cardDeckSize(); i++) {
				ImageButton PlayerImageButtonI = (ImageButton) findViewById(i
						+ viewIdModificatorSelect);
				if (PlayerImageButtonI.isEnabled() && i == lastAnimation.index)
					PlayerImageButtonI.setEnabled(false);
				else if (!PlayerImageButtonI.isEnabled()
						&& i != lastAnimation.index)
					PlayerImageButtonI.setEnabled(true);
			}
			ArrayList<Integer> AvailablePlaces = game.AvailablePlacesOnBoard();

			for (int i = 0; i < AvailablePlaces.size(); i++) {
				if(!isQuestionMark(10 + AvailablePlaces.get(i))){
				ImageButton boardImageButton = new ImageButton(this);
				boardImageButton.setId(10 + AvailablePlaces.get(i));
				boardImageButton.setVisibility(View.INVISIBLE);
				boardImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
				boardImageButton.setLayoutParams(new LinearLayout.LayoutParams(
						(int) getResources().getDimension(R.dimen.card_width), LinearLayout.LayoutParams.MATCH_PARENT));
				boardImageButton.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {

						int index = v.getId() - 10;
						if (game.hasSelection()) {
							if (game.cardDeckOwnerEmptyPrivateCard())
								game.PlaySelectedCardAt(index);
							else
								game.PlaySelectedPrivateCardAt(index);
						} else if (game.boardDeckHasSelection()) {
							if (game.boardDeckSelectedIndex() == game
									.boardDeckSize() - 1)
								index--;
							if (game.boardDeckSelectedIndex() == index) {
								playAnim(v, R.anim.shrink);
								game.flipSelectedBoardCard();
								playAnim(v, R.anim.grow);
							} else if (index - game.boardDeckSelectedIndex() == 1) {
								game.swapSelectedWithNext();
							} else if (game.boardDeckSelectedIndex() - index == 1) {
								game.swapSelectedWithPrev();
							} else if (index == -1
									|| index == game.boardDeckSize()) {
								game.moveSelectedToOtherEnd();
							} else {
								game.selectFromBoard(index);
							}
						} else {
							game.selectFromBoard(index);
						}
					}
				});
				boardImageButton
						.setOnLongClickListener(new OnLongClickListener() {

							public boolean onLongClick(View v) {
								int index = v.getId() - 10;
								if (game.cardDeckOwnerEmptyPrivateCard())
									game.PlaySelectedCardSwappedAt(index);
								else
									game.PlaySelectedPrivateCardSwappedAt(index);
								return true;
							}
						});

				boardDeck.addView(boardImageButton, AvailablePlaces.get(i));

				showImageButton(boardImageButton, R.drawable.question);
			}}
			break;
		case SELECT_FROM_BOARD:
			if (game.currentPlayerReSelectFromBoard()){
				CardAnimation ca = game.getPreviousAnimations();
				int fixedIndex = ca.index == game.boardDeckSize()-1 ? ca.index + 1 : ca.index;
				ImageButton lastSelectedBoardImage = (ImageButton)boardDeck.getChildAt(fixedIndex);
				lastSelectedBoardImage.setImageResource(game.getBoardDeckAt(ca.index).UpsideImage());
				if (ca.index  < game.boardDeckSize()-1){
					ImageButton nextBoardImage = (ImageButton)boardDeck.getChildAt(ca.index + 1);
					nextBoardImage.setImageResource(game.getBoardDeckAt(ca.index + 1).UpsideImage());
				}
				if (ca.index > 0){
					ImageButton nextBoardImage = (ImageButton)boardDeck.getChildAt(fixedIndex - 1);
					nextBoardImage.setImageResource(game.getBoardDeckAt(ca.index - 1).UpsideImage());
				}
				if (ca.index == game.boardDeckSize()-1){
					boardDeck.removeViewAt(0);
				}
				if (ca.index == 0){
					boardDeck.removeViewAt(boardDeck.getChildCount()-1);
				}
			}

			ImageButton selectedBoardImage = (ImageButton)boardDeck.getChildAt(lastAnimation.index);
			addFlipIcon(selectedBoardImage,game.getBoardDeckAt(game.boardDeckSelectedIndex()) .UpsideImage());
			
			if (lastAnimation.index < game.boardDeckSize()-1){
				ImageButton nextBoardImage = (ImageButton)boardDeck.getChildAt(lastAnimation.index + 1);
				addSwapIcon(nextBoardImage,game.getBoardDeckAt(game.boardDeckSelectedIndex() + 1) .UpsideImage());				
			}
			if (lastAnimation.index > 0){
				ImageButton prevBoardImage = (ImageButton)boardDeck.getChildAt(lastAnimation.index - 1);
				addSwapIcon(prevBoardImage,game.getBoardDeckAt(game.boardDeckSelectedIndex() - 1) .UpsideImage());				
			}
			
			if (lastAnimation.index == game.boardDeckSize()-1){
			
			ImageButton boardFirstImageButton = new ImageButton(this);
			boardFirstImageButton.setId(10 + 0);
			boardFirstImageButton.setVisibility(View.VISIBLE);
			boardFirstImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
			boardFirstImageButton.setLayoutParams(new LinearLayout.LayoutParams(
					(int) getResources().getDimension(R.dimen.card_width), LinearLayout.LayoutParams.MATCH_PARENT));
			boardFirstImageButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					int index = v.getId() - 10;
					if (game.hasSelection()) {
						if (game.cardDeckOwnerEmptyPrivateCard())
							game.PlaySelectedCardAt(index);
						else
							game.PlaySelectedPrivateCardAt(index);
					} else if (game.boardDeckHasSelection()) {
						if (game.boardDeckSelectedIndex() == game
								.boardDeckSize() - 1)
							index--;
						if (game.boardDeckSelectedIndex() == index) {
							playAnim(v, R.anim.shrink);
							game.flipSelectedBoardCard();
							playAnim(v, R.anim.grow);
						} else if (index - game.boardDeckSelectedIndex() == 1) {
							game.swapSelectedWithNext();
						} else if (game.boardDeckSelectedIndex() - index == 1) {
							game.swapSelectedWithPrev();
						} else if (index == -1
								|| index == game.boardDeckSize()) {
							game.moveSelectedToOtherEnd();
						} else {
							game.selectFromBoard(index);
						}
					} else {
						game.selectFromBoard(index);
					}
				}
			});
			boardFirstImageButton
					.setOnLongClickListener(new OnLongClickListener() {

						public boolean onLongClick(View v) {
							int index = v.getId() - 10;
							if (game.cardDeckOwnerEmptyPrivateCard())
								game.PlaySelectedCardSwappedAt(index);
							else
								game.PlaySelectedPrivateCardSwappedAt(index);
							return true;
						}
					});

			boardDeck.addView(boardFirstImageButton, 0);

			showImageButton(boardFirstImageButton, R.drawable.question);
			}else if (lastAnimation.index == 0){
			ImageButton boardLastImageButton = new ImageButton(this);
			boardLastImageButton.setId(10 + game.boardDeckSize());
			boardLastImageButton.setVisibility(View.VISIBLE);
			boardLastImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
			boardLastImageButton.setLayoutParams(new LinearLayout.LayoutParams(
					(int) getResources().getDimension(R.dimen.card_width), LinearLayout.LayoutParams.MATCH_PARENT));
			boardLastImageButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					int index = v.getId() - 10;
					if (game.hasSelection()) {
						if (game.cardDeckOwnerEmptyPrivateCard())
							game.PlaySelectedCardAt(index);
						else
							game.PlaySelectedPrivateCardAt(index);
					} else if (game.boardDeckHasSelection()) {
						if (game.boardDeckSelectedIndex() == game
								.boardDeckSize() - 1)
							index--;
						if (game.boardDeckSelectedIndex() == index) {
							playAnim(v, R.anim.shrink);
							game.flipSelectedBoardCard();
							playAnim(v, R.anim.grow);
						} else if (index - game.boardDeckSelectedIndex() == 1) {
							game.swapSelectedWithNext();
						} else if (game.boardDeckSelectedIndex() - index == 1) {
							game.swapSelectedWithPrev();
						} else if (index == -1
								|| index == game.boardDeckSize()) {
							game.moveSelectedToOtherEnd();
						} else {
							game.selectFromBoard(index);
						}
					} else {
						game.selectFromBoard(index);
					}
				}
			});
			boardLastImageButton
					.setOnLongClickListener(new OnLongClickListener() {

						public boolean onLongClick(View v) {
							int index = v.getId() - 10;
							if (game.cardDeckOwnerEmptyPrivateCard())
								game.PlaySelectedCardSwappedAt(index);
							else
								game.PlaySelectedPrivateCardSwappedAt(index);
							return true;
						}
					});

			boardDeck.addView(boardLastImageButton);

			showImageButton(boardLastImageButton, R.drawable.question);
			}
			
			reNumberBoardDeck();
			break;
		case PLAY_FROM_BOARD_TO_END:
			if (lastAnimation.index == 0){
				boardDeck.removeViewAt(0);
				int lastIndex = boardDeck.getChildCount()-1;
				ImageButton lastImage = (ImageButton)boardDeck.getChildAt(lastIndex);
				lastImage.setImageResource(game.getBoardDeckAt(lastIndex).UpsideImage());
				lastImage.setTag(game.getBoardDeckAt(lastIndex).UpsideImage());
			}else{
				boardDeck.removeViewAt(boardDeck.getChildCount()-1);				
				
				ImageButton firstImage = (ImageButton)boardDeck.getChildAt(0);
				firstImage.setImageResource(game.getBoardDeckAt(0).UpsideImage());
				firstImage.setTag(game.getBoardDeckAt(0).UpsideImage());
				
			}
			reNumberBoardDeck();
			break;

		case PLAY:
			int viewIdModificatorPlay = lastAnimation.player == game.player1 ? 20 : 0;
				player1Deck.removeAllViews();
				player2Deck.removeAllViews();
				disableBoardDeck();

				int endOfDeck = 0;
				if (lastAnimation.player == game.player1 && game.player2.hasPrivateDeck()){
					endOfDeck = game.player2.getPrivateDeck().size();
				}else if (lastAnimation.player == game.player2 && game.player1.hasPrivateDeck()){
					endOfDeck = game.player1.getPrivateDeck().size();
				}else{
					endOfDeck = game.cardDeckSize();
				}
					
				for (int i = 0; i < endOfDeck; i++) {
					ImageButton PlayerImageButtonI = new ImageButton(this);
					PlayerImageButtonI.setId(viewIdModificatorPlay + i);
					PlayerImageButtonI
							.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.card_width),
									LinearLayout.LayoutParams.MATCH_PARENT));
					PlayerImageButtonI
							.setScaleType(ImageView.ScaleType.FIT_CENTER);
					PlayerImageButtonI.setVisibility(View.VISIBLE);
					
					int image = 0;
					if (lastAnimation.player == game.player1 && game.player2.hasPrivateDeck()){
						image = game.player2.getPrivateDeck().get(i).UpsideImage();
					}else if (lastAnimation.player == game.player2 && game.player1.hasPrivateDeck()){
						image = game.player1.getPrivateDeck().get(i).UpsideImage();
					}else{
						image = game.getCardDeckAt(i).UpsideImage();
					}
					
					showImageButton(PlayerImageButtonI, image);
					if (lastAnimation.player == game.player1) {
						PlayerImageButtonI
								.setOnClickListener(new OnClickListener() {

									public void onClick(View v) {
										int index = v.getId() - 20;
										game.SelectFromCardDeck(index);
									}
								});
						player2Deck.addView(PlayerImageButtonI);
					} else {
						PlayerImageButtonI
								.setOnClickListener(new OnClickListener() {

									public void onClick(View v) {
										int index = v.getId();
										game.SelectFromCardDeck(index);
									}
								});
						player1Deck.addView(PlayerImageButtonI);

					}
				}
				ImageButton selected = (ImageButton) boardDeck
						.getChildAt(lastAnimation.index);
				if (lastAnimation.index != 0) lastAnimation.index -= 1;
				selected.setImageResource(game.getBoardDeckAt(
						lastAnimation.index).UpsideImage());
				selected.setTag(game.getBoardDeckAt(
						lastAnimation.index).UpsideImage());
				removeQuestionMarks();
				reNumberBoardDeck();
				
				if (game.cardDeckSize() == 0 && game.cardDeckOwnerEmptyPrivateCard()) {
					for (int i = 0; i < game.boardDeckSize(); i++) { 
						ImageButton boardImageButtonI = (ImageButton) findViewById(10 + i);
						boardImageButtonI.setEnabled(true); 
					}
				}
			break;
		case FLIP_CARD:
			ImageButton lastImage = (ImageButton)boardDeck.getChildAt(lastAnimation.index);
			lastImage.setImageResource(game.getBoardDeckAt(lastAnimation.index).UpsideImage());
			lastImage.setTag(game.getBoardDeckAt(lastAnimation.index).UpsideImage());
			
			removeScoredCards(lastAnimation);
		
			break;
		case SWAP_WITH_NEXT:
			ImageButton thisImage = (ImageButton)boardDeck.getChildAt(lastAnimation.index);
			thisImage.setImageResource(game.getBoardDeckAt(lastAnimation.index).UpsideImage());
			thisImage.setTag(game.getBoardDeckAt(lastAnimation.index).UpsideImage());
			
			ImageButton nextImage = (ImageButton)boardDeck.getChildAt(lastAnimation.index + 1);
			nextImage.setImageResource(game.getBoardDeckAt(lastAnimation.index + 1).UpsideImage());
			nextImage.setTag(game.getBoardDeckAt(lastAnimation.index + 1).UpsideImage());			

			removeScoredCards(lastAnimation);
			break;
		case SWAP_WITH_PREV:
			ImageButton thisImage2 = (ImageButton)boardDeck.getChildAt(lastAnimation.index);
			thisImage2.setImageResource(game.getBoardDeckAt(lastAnimation.index).UpsideImage());
			thisImage2.setTag(game.getBoardDeckAt(lastAnimation.index).UpsideImage());
			
			ImageButton prevImage = (ImageButton)boardDeck.getChildAt(lastAnimation.index - 1);
			prevImage.setImageResource(game.getBoardDeckAt(lastAnimation.index - 1).UpsideImage());
			prevImage.setTag(game.getBoardDeckAt(lastAnimation.index - 1).UpsideImage());

			removeScoredCards(lastAnimation);
			break;
		default:
			break;
		}
		UpdateScore();
	}

	private void removeScoredCards(CardAnimation lastAnimation) {
		for (int i = lastAnimation.mustRemove.size()-1; i >= 0; i--){
			boardDeck.removeViewAt(lastAnimation.mustRemove.get(i));
		}
		reNumberBoardDeck();
	}

	private boolean isQuestionMark(int i) {
		ImageButton ibFirts = (ImageButton)findViewById(i);
		if (ibFirts == null) return false;
		if (ibFirts.getTag() == null) return false;
		if (Integer.valueOf(ibFirts.getTag().toString()) == R.drawable.question) return true;
		return false;
	}

	private void removeQuestionMarks() {
		ImageButton ibFirts = (ImageButton)boardDeck.getChildAt(0);
		if (ibFirts.getTag() != null && Integer.valueOf(ibFirts.getTag().toString()) == R.drawable.question){
			boardDeck.removeViewAt(0);
		}
		if (boardDeck.getChildCount() > 0){
		ImageButton ibLats = (ImageButton)boardDeck.getChildAt(boardDeck.getChildCount() - 1);
		if (ibFirts.getTag() != null && Integer.valueOf(ibLats.getTag().toString()) == R.drawable.question){
			boardDeck.removeViewAt(boardDeck.getChildCount() - 1);
		}
		}
	}

	private void reNumberBoardDeck() {
		for (int i = 0; i < boardDeck.getChildCount(); i++) {
			ImageButton ib = (ImageButton)boardDeck.getChildAt(i);
			ib.setId(i+10);
		}
	}

	private void disableBoardDeck() {
		for (int i = 0; i < boardDeck.getChildCount(); i++) {
			boardDeck.getChildAt(i).setEnabled(false);
		}
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
		imageButton.setTag(resourceId);
		imageButton.setVisibility(View.VISIBLE);
		imageButton.setEnabled(true);
	}

	private void hideImageButton(ImageButton Player1ImageButtonI) {
		/*
		 * Player1ImageButtonI.setVisibility(View.INVISIBLE);
		 * Player1ImageButtonI.setEnabled(false);
		 */
	}

}
