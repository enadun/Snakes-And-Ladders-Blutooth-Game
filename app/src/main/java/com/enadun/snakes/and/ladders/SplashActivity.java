package com.enadun.snakes.and.ladders;

import com.enadun.snakes.and.ladders.util.MyAnimations;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	ImageView logo1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		logo1 = (ImageView) findViewById(R.id.logo1);
		AnimationSet set = new AnimationSet(true);

		Animation fadeIn = MyAnimations.fadeInAnimation(this, 0, 2000);
		set.addAnimation(fadeIn);

		Animation fadeOut = MyAnimations.fadeOutAnimation(this, 2000, 2000);
		set.addAnimation(fadeOut);

		logo1.startAnimation(set);

		new Handler().postDelayed(startActivityRunnable, 4000);
	}

	@Override
	public void onBackPressed() {
	}

	private final Runnable startActivityRunnable = new Runnable() {

		@Override
		public void run() {
			// Start main activity
			Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
			SplashActivity.this.startActivity(intent);
			SplashActivity.this.finish();
		}
	};
}
