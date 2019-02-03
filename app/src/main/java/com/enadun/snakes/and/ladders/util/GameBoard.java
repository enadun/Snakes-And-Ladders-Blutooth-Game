package com.enadun.snakes.and.ladders.util;

import com.enadun.snakes.and.ladders.R;

import android.graphics.Point;
import android.widget.Button;

public class GameBoard {

	private static Point[] positionPoint;
	private BoardPiece bluePiece;
	private BoardPiece redPiece;

	public GameBoard(int boardSize, BoardPiece bluePiece, BoardPiece redPiece){
		this.bluePiece = bluePiece;
		this.redPiece = redPiece;

		int squareSize = boardSize / 6;
		int offset = (squareSize - bluePiece.getWidth()) / 2;

		positionPoint = new Point[37];
		positionPoint[0] = new Point(squareSize * 6, squareSize * 5 + offset);
		for(int y = 0; y < 6; y++){
			for(int x = 0; x < 6; x++){
				if(y % 2 == 0){
					positionPoint[36 - (y * 6 + 5 - x)] = new Point(squareSize * x + offset, squareSize * y + offset);					
				}else{
					positionPoint[36 - (y * 6 + x)] = new Point(squareSize * x + offset, squareSize * y + offset);
				}
			}
		}
		bluePiece.setCurrentPlaceNumber(this, 0);
		redPiece.setCurrentPlaceNumber(this, 0);
	}

	public BoardPiece getBluePiece() {
		return bluePiece;
	}

	public BoardPiece getRedPiece() {
		return redPiece;
	}

	public Point getPointByNumber(int n){
		return positionPoint[n];
	}

	public boolean resetgame(Button rollButton){
		getBluePiece()
		.setCurrentPlaceNumber(this, 0);

		getRedPiece()
		.setCurrentPlaceNumber(this, 0);
		
		rollButton.setEnabled(true);
		rollButton.setBackgroundResource(R.drawable.ui_btn_roll);

		return true;
	}
}
