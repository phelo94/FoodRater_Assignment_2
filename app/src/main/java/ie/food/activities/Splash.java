package ie.food.activities;

import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import ie.food.R;
import ie.food.registration.LoginActivity;

public class Splash extends Activity {
    // used to know if the back button was pressed in the splash_screen  activity

    private boolean 			mIsBackButtonPressed;
    private static final int 	SPLASH_DURATION = 5000; // 5 seconds
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        ImageView im = findViewById(R.id.imageView);

        try {
            AnimatedImageDrawable foodAnimation;
            foodAnimation = (AnimatedImageDrawable) ImageDecoder.decodeDrawable(
                    ImageDecoder.createSource(getResources(), R.drawable.splashscreenlogo));
            im.setImageDrawable(foodAnimation);
            foodAnimation.start();
        }
        catch(Exception e)
        {

        }
 
        Handler handler = new Handler();
        // run a thread after 5 seconds to start the home screen
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
                // make sure we close the splash_screen  so the user
            	// won't come back to it if back key pressed
                finish();
                 
                if (!mIsBackButtonPressed) {
                    // start the home screen if the back button wasn't pressed already 
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    Splash.this.startActivity(intent);
               }       
            }
        }, SPLASH_DURATION); // time in milliseconds to delay call to run()
    }
 
    @Override
   public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }
}
