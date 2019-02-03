package com.enadun.snakes.and.ladders.util;

import com.enadun.snakes.and.ladders.R;

import android.app.Activity;
import android.graphics.Point;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BoardPiece {
	
	public static int BLUE_PIECE = 1;
	public static int RED_PIECE = 2;
	
	private ImageView imageView;
	private Point currentPosition;
	private int currentPlaceNumber;
	private int width;
	private int id;

	public BoardPiece(Activity activity, int imgId, int id){
		this.id = id;
		setImageView(activity, imgId);
	}

	public ImageView getImageView() {
		return imageView;
	}

	private void setImageView(Activity activity, int id) {
		RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.boardLayout);
		width = MyViewManager.getValueOfDP(activity, 35);
		MarginLayoutParams params = new MarginLayoutParams(width, width);
		imageView = new ImageView(activity);
		imageView.setImageResource(id);
		imageView.setLayoutParams(params);
	    layout.addView(imageView);
	}

	public Point getCurrentPosition() {
		return currentPosition;
	}
	
	public int getCurrentPlaceNumber() {
		return currentPlaceNumber;
	}

	public void setCurrentPlaceNumber(GameBoard gameBoard, int placeNumber) {
		this.currentPlaceNumber = placeNumber;
		this.currentPosition = gameBoard.getPointByNumber(placeNumber);
		MyViewManager.changePosition(imageView, currentPosition.x, currentPosition.y);
	}
	
	public int getWidth(){
		return this.width;
	}

	public int getId() {
		return id;
	}
}
