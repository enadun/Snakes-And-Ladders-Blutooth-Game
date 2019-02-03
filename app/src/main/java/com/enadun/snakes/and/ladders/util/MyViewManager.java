package com.enadun.snakes.and.ladders.util;

import com.enadun.snakes.and.ladders.GameActivity;
import com.enadun.snakes.and.ladders.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class MyViewManager {
	public static int getScreenWidth(Context context) {
		return getScreenSize(context).x;
	}

	public static int getScreenHeight(Context context) {
		return getScreenSize(context).y;
	}

	public static Point getScreenSize(Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		Point size = new Point();
		size.x = metrics.widthPixels;
		size.y = metrics.heightPixels;
		return size;
	}

	public static int getValueOfDP(Context context, int vlaue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (vlaue * scale + 0.5f);
	}

	public static void changePosition(View iv, int x, int y) {
		LayoutParams params = (LayoutParams) iv.getLayoutParams();
		params.leftMargin = x;
		params.topMargin = y;
		iv.setLayoutParams(params);
	}

	public static void showGameFinishAlertDialog(final GameBoard gameBoard,
			boolean win, final Button rollButton) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				GameActivity.activity);
		if (win) {
			alertDialogBuilder.setTitle(MyString
					.getStringById(R.string.game_finish_win_title));

			alertDialogBuilder
					.setMessage(
							MyString.getStringById(R.string.game_finish_msg))
					.setCancelable(false)
					.setIcon(R.drawable.cup)
					.setPositiveButton(
							MyString.getStringById(R.string.positive_btn),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									gameBoard.resetgame(rollButton);
									dialog.cancel();
								}

							})
					.setNegativeButton(
							MyString.getStringById(R.string.negative_btn),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									GameActivity.activity.finish();
								}
							});
		} else {
			alertDialogBuilder.setTitle(MyString
					.getStringById(R.string.game_finish_lost_title));

			alertDialogBuilder
					.setMessage(
							MyString.getStringById(R.string.game_finish_msg))
					.setCancelable(false)
					.setIcon(R.drawable.lost)
					.setPositiveButton(
							MyString.getStringById(R.string.positive_btn),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									gameBoard.resetgame(rollButton);
									dialog.cancel();
								}

							})
					.setNegativeButton(
							MyString.getStringById(R.string.negative_btn),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									GameActivity.activity.finish();
								}
							});
		}

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public static void showExitAlertDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				GameActivity.activity);
		alertDialogBuilder.setTitle(MyString
				.getStringById(R.string.game_exit_title));

		alertDialogBuilder
				.setMessage(MyString.getStringById(R.string.game_exit_msg))
				.setCancelable(false)
				.setPositiveButton(
						MyString.getStringById(R.string.positive_btn),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								GameActivity.activity.finish();
							}

						})
				.setNegativeButton(
						MyString.getStringById(R.string.negative_btn),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public static void showNewGameAlertDialog(final GameBoard gameBoard, final Button rollButton) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				GameActivity.activity);
		alertDialogBuilder.setTitle(MyString
				.getStringById(R.string.new_game_title));

		alertDialogBuilder
				.setMessage(MyString.getStringById(R.string.new_game_msg))
				.setCancelable(false)
				.setPositiveButton(
						MyString.getStringById(R.string.positive_btn),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								gameBoard.resetgame(rollButton);
								dialog.cancel();
							}

						})
				.setNegativeButton(
						MyString.getStringById(R.string.negative_btn),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	public static void showCustomToast(String msg, int type, int value) {
		LayoutInflater inflater = GameActivity.activity.getLayoutInflater();
		View bottomLayoutView = GameActivity.activity
				.findViewById(R.id.bottom_layout);

		View view = inflater.inflate(R.layout.toast_layout,
				(ViewGroup) bottomLayoutView, false);
		TextView tv = (TextView) view.findViewById(R.id.toast_msg);
		tv.setText(msg);
		ImageView iv = (ImageView) view.findViewById(R.id.image_view);

		switch(type){
		case 1 :
			tv.setTextColor(GameActivity.activity.getResources().getColor(R.color.blue_piese));
			iv.setImageResource(getToastImageIdByNumber(value));
			break;
		case 2 :
			tv.setTextColor(GameActivity.activity.getResources().getColor(R.color.red_piese));
			iv.setImageResource(getToastImageIdByNumber(value));
			break;
		case 3 :
			tv.setTextColor(GameActivity.activity.getResources().getColor(R.color.blue_piese));
			iv.setImageResource(getToastImageIdByNumber(value));
			break;
		case 4 :
			tv.setTextColor(GameActivity.activity.getResources().getColor(R.color.red_piese));
			iv.setImageResource(getToastImageIdByNumber(value));
			break;
		}
		Toast toast = new Toast(GameActivity.activity);
		toast.setView(view);
		toast.setGravity(
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
				0,
				(bottomLayoutView.getHeight() - getValueOfDP(
						GameActivity.activity, 50)) / 2);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public static int getToastImageIdByNumber(int n) {
		switch (n) {
		case 1:
			return R.drawable.dice_one;
		case 2:
			return R.drawable.dice_two;
		case 3:
			return R.drawable.dice_three;
		case 4:
			return R.drawable.dice_four;
		case 5:
			return R.drawable.dice_five;
		case 6:
			return R.drawable.dice_six;
		case 7:
			return R.drawable.overlapping_blue;
		case 8:
			return R.drawable.overlapping_red;
		case 9:
			return R.drawable.ladder;
		case 10:
			return R.drawable.snake;
			}
		return 0;
	}
}
