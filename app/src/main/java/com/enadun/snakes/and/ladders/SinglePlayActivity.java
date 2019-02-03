package com.enadun.snakes.and.ladders;

import java.util.Random;

import com.enadun.snakes.and.ladders.util.MyAnimations;
import com.enadun.snakes.and.ladders.util.MyString;
import com.enadun.snakes.and.ladders.util.MyViewManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class SinglePlayActivity extends GameActivity implements
		AnimationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player1.setText(MyString.getStringById(R.string.game_dash_board_single_player_one_name));
		player2.setText(MyString.getStringById(R.string.game_dash_board_single_player_two_name));
		mTitle.setText(MyString.getStringById(R.string.game_dash_board_sub_title_single_player));
	}

	public void rollTheDice(View v) {
		super.rollTheDice(v);
		currentValue = new Random().nextInt(6) + 1;
		moveBluePiece();
	}

	// Override Methods
	@Override
	public void onAnimationEnd(Animation arg0) {
				
		if (GameActivity.myTurn) {
			GameActivity.myTurn = false;
			MyAnimations.finalCheck(gameBoard, currentPiece, currentValue,rollButton);
			currentValue = new Random().nextInt(6) + 1;
						
			moveRedPiece();
		} else {
			GameActivity.myTurn = true;
			MyAnimations.finalCheck(gameBoard, currentPiece, currentValue,rollButton);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu_single_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.newGame:
				// Launch the DeviceListActivity to see devices and do scan
				MyViewManager.showNewGameAlertDialog(gameBoard, rollButton);
				return true;
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		MyViewManager.showExitAlertDialog();
	}
	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub

	}
}
