package gr.myapp.app;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);





        final Handler handler=  new Handler();
        final Runnable myRunnable =new Runnable() {

            /*
             * Showing splash screen with a timer.
             */

            @Override
            public void run() {

                nextActivity();
            }
        };

        handler.postDelayed(myRunnable,SPLASH_TIME_OUT);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        ImageView getStarted = (ImageView) findViewById(R.id.get_started_btn);



        getStarted.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        handler.removeCallbacks(myRunnable);

                        nextActivity();

                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        break;
                    }

                }


                return true;
            }
        });



    }

    private void nextActivity(){


                SharedPreferences sharedPref = SplashScreenActivity.this.getSharedPreferences(getString(R.string.local_storage_filename),Context.MODE_PRIVATE);

                int flagSlider = sharedPref.getInt(getString(R.string.ls_have_seen_first_slider), 111);

                // close this activity
                Intent intent;
                if(flagSlider == 1){
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                } else {

                    intent = new Intent(SplashScreenActivity.this, ChooseLanguageActivity.class);
                }
                startActivity(intent);
                finish();


    }



}