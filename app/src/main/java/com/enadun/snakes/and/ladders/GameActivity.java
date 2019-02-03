package com.enadun.snakes.and.ladders;

import com.enadun.snakes.and.ladders.util.BoardPiece;
import com.enadun.snakes.and.ladders.util.GameBoard;
import com.enadun.snakes.and.ladders.util.MyAnimations;
import com.enadun.snakes.and.ladders.util.MyString;
import com.enadun.snakes.and.ladders.util.MyViewManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {

	private static final int OFFSET_TIME_FOR_RED_PIECE = 500;
	private static final int OFFSET_TIME_FOR_BLUE_PIECE = 0;
	public static Activity activity;
	public static boolean myTurn = true;
	
	protected Button rollButton;
	protected TextView player1, player2, mTitle;

	protected BoardPiece currentPiece;
	protected int currentValue;
	protected GameBoard gameBoard;

	private ImageView boardView;
	private int boardSize;
	private boolean sdkBelow11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(android.os.Build.VERSION.SDK_INT < 11){
		// Set up the window layout
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			sdkBelow11 = true;
        }
		super.onCreate(savedInstanceState);       
		GameActivity.activity = this;
		//if (savedInstanceState == null) {
			setContentView(R.layout.activity_game);
			if(sdkBelow11){
				getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
	        }else{
	        	getActionBar().setDisplayShowCustomEnabled(true);
	        	getActionBar().setCustomView(R.layout.custom_title);
	        }
			initialize();
		//}
	}

	private void initialize() {
		player1 = (TextView)findViewById(R.id.playerOneName);
		player2 = (TextView)findViewById(R.id.playerTwoName);

		// Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(MyString.getStringById(R.string.app_name));
        if(!sdkBelow11){
        	mTitle.setTextSize(20);
        }
        mTitle = (TextView) findViewById(R.id.title_right_text);
        
		rollButton = (Button) findViewById(R.id.rollButton);
		BoardPiece redPiece = new BoardPiece(this, R.drawable.ui_icon_piece_two, BoardPiece.RED_PIECE);
		BoardPiece bluePiece = new BoardPiece(this,	R.drawable.ui_icon_piece_one, BoardPiece.BLUE_PIECE);

		currentPiece = bluePiece;
		boardView = (ImageView) findViewById(R.id.boardView);
		// we set the screen with as the image height.
		boardSize = MyViewManager.getScreenWidth(this);
		boardView.getLayoutParams().height = boardSize;

		gameBoard = new GameBoard(boardSize, bluePiece, redPiece);

		/*
		 * sPath[1] = new Path(); sPath[1].moveTo(100, 100);
		 * sPath[1].lineTo(100, 300); sPath[1].lineTo(300, 300);
		 * sPath[1].lineTo(300,100); sPath[1].lineTo(100,0);
		 * 
		 * point = new Point[2]; point[0] = gameBoard.getPointByNumber(36);
		 * point[1] = new Point(100, 0);
		 */
	}

	public void rollTheDice(View v) {
		rollButton.setEnabled(false);
		rollButton.setBackgroundResource(R.drawable.ui_btn_wait);
	}

	protected void moveRedPiece() {
		currentPiece = gameBoard.getRedPiece();
		MyAnimations.movePiece(gameBoard, currentPiece, currentValue, GameActivity.OFFSET_TIME_FOR_RED_PIECE);
	}

	protected void moveBluePiece() {
		currentPiece = gameBoard.getBluePiece();
		MyAnimations.movePiece(gameBoard, currentPiece, currentValue, GameActivity.OFFSET_TIME_FOR_BLUE_PIECE);
	}
}
