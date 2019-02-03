package com.enadun.snakes.and.ladders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	//When Click the Single Player Button
	public void singlePlay(View v){
		// Start single play activity
		Intent intent = new Intent(MenuActivity.this, SinglePlayActivity.class);
		MenuActivity.this.startActivity(intent);
	}

	//When Click the Multy Player Button
	public void multyPlay(View v){
		// Start multy player activity
		Intent intent = new Intent(MenuActivity.this, MultyPlayerActivity.class);
		MenuActivity.this.startActivity(intent);
	}

	//When Click the Exit Button
	public void exitOnApp(View v){
		onBackPressed();
	}
	
	@Override
	public void onResume(){
	super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
