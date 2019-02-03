package com.enadun.snakes.and.ladders.util;

import com.enadun.snakes.and.ladders.GameActivity;
import com.enadun.snakes.and.ladders.R;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;

public class MyAnimations extends Animation implements AnimationListener {

	private static final int AMIMATION_TIME_PER_UNIT = 500;
	
	private PathMeasure measure;
	private float[] pos = new float[2];

	public MyAnimations(Path path) {
		measure = new PathMeasure(path, false);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		measure.getPosTan(measure.getLength() * interpolatedTime, pos, null);
		t.getMatrix().setTranslate(pos[0], pos[1]);
	}

	// Static Methods
	public static Animation fadeInAnimation(Context context, int startOffset,
			int duration) {
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setStartOffset(startOffset);
		fadeIn.setDuration(duration);
		return fadeIn;
	}

	public static Animation fadeOutAnimation(Context context, int startOffset,
			int duration) {
		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setStartOffset(startOffset);
		fadeOut.setDuration(duration);
		return fadeOut;
	}

	public static boolean movePiece(GameBoard gameBoard,
		BoardPiece piece, int value, int offset) {

		MyViewManager.showCustomToast(MyString.getStringById(R.string.dice_value, value), piece.getId(), value);
		
		Path path = new Path();
		path.moveTo(piece.getCurrentPosition().x, piece.getCurrentPosition().y);

		int n = piece.getCurrentPlaceNumber() + value;
		boolean moveBack = false;

		if (n > 36) {
			n = 36;
			moveBack = true;
		}

		for (int i = piece.getCurrentPlaceNumber() + 1; i < n + 1; i++) {
			path.lineTo(gameBoard.getPointByNumber(i).x,
					gameBoard.getPointByNumber(i).y);
		}

		if (moveBack) {
			for (int i = 1; i < piece.getCurrentPlaceNumber() + value + 1 - n; i++) {
				path.lineTo(gameBoard.getPointByNumber(36 - i).x,
						gameBoard.getPointByNumber(36 - i).y);
			}
		}

		MyAnimations mA = new MyAnimations(path);
		MyViewManager.changePosition(piece.getImageView(), 0, 0);
		mA.setStartOffset(offset);
		mA.setDuration(AMIMATION_TIME_PER_UNIT * value);
		mA.setAnimationListener((AnimationListener) GameActivity.activity);
		piece.getImageView().startAnimation(mA);
		return true;
	}

	public static void finalCheck(GameBoard gameBoard, BoardPiece currentPiece,
			int currentValue, Button rollButton) {
		if (currentPiece.getCurrentPlaceNumber() + currentValue > 36) {
			currentValue = 2 * (36 - currentPiece.getCurrentPlaceNumber())
					- currentValue;
		}else if(currentPiece.getCurrentPlaceNumber() + currentValue == 36){
			if(currentPiece == gameBoard.getBluePiece()){
				MyViewManager.showGameFinishAlertDialog(gameBoard, true, rollButton);
			}else{
				MyViewManager.showGameFinishAlertDialog(gameBoard, false, rollButton);				
			}
		}

		int lastValue = currentPiece.getCurrentPlaceNumber() + currentValue;

		switch (lastValue) {
		case 3:
			MyViewManager.showCustomToast(MyString.getStringById(R.string.found_a_ladder), currentPiece.getId() + 2, 9);
			lastValue = 29;
			break;
		case 14:
			MyViewManager.showCustomToast(MyString.getStringById(R.string.found_a_ladder), currentPiece.getId() + 2, 9);
			lastValue = 27;
			break;
		case 18:
			MyViewManager.showCustomToast(MyString.getStringById(R.string.met_a_snake), currentPiece.getId() + 2, 10);
			lastValue = 4;
			break;
		case 19:
			MyViewManager.showCustomToast(MyString.getStringById(R.string.found_a_ladder), currentPiece.getId() + 2, 9);
			lastValue = 32;
			break;
		case 26:
			MyViewManager.showCustomToast(MyString.getStringById(R.string.met_a_snake), currentPiece.getId() + 2, 10);
			lastValue = 10;
			break;
		case 35:
			MyViewManager.showCustomToast(MyString.getStringById(R.string.met_a_snake), currentPiece.getId() + 2, 10);
			lastValue = 5;
			break;
		default:
			;
		}

		currentPiece.setCurrentPlaceNumber(gameBoard, lastValue);
		if(gameBoard.getBluePiece().getCurrentPlaceNumber() == gameBoard.getRedPiece().getCurrentPlaceNumber()){
			
			
			if(currentPiece == gameBoard.getBluePiece()){
				MyViewManager.showCustomToast(MyString.getStringById(R.string.overlapped), currentPiece.getId() + 2, 7);
				gameBoard.getRedPiece().setCurrentPlaceNumber(gameBoard, 1);
			}else{
				MyViewManager.showCustomToast(MyString.getStringById(R.string.overlapped), currentPiece.getId() + 2, 8);
				gameBoard.getBluePiece().setCurrentPlaceNumber(gameBoard, 1);
			}
		}
		
		if(GameActivity.myTurn){
			rollButton.setEnabled(true);
			rollButton.setBackgroundResource(R.drawable.ui_btn_roll);
		}
	}

	// Override Methods
	@Override
	public void onAnimationEnd(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}
}
