package gr.myapp.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    PrefManager pr ;
    String chosenLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        pr = new PrefManager(this.getBaseContext());
        chosenLang = pr.getLang();


        final TextView langEl = (TextView) findViewById(R.id.language_greek);
        final TextView langEn = (TextView) findViewById(R.id.language_english);

        if (chosenLang.equals("en")){

            langEn.setTextColor(Color.parseColor("#4559FD"));
            langEn.setTextSize(38);
        }else{
            langEl.setTextColor(Color.parseColor("#4559FD"));
            langEl.setTextSize(38);
        }


        langEl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:{
                        chosenLang = "el";
                        LocalizationHelper.setLocale(SettingsActivity.this, "el");
                        langEl.setTextColor(Color.parseColor("#4559FD"));
                        langEn.setTextColor(Color.parseColor("#A6ABEA"));

                        langEl.setTextSize(38);
                        langEn.setTextSize(30);
                        break;
                    }

                }

                return true;
            }
        });



        langEn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:{
                        chosenLang = "en";
                        LocalizationHelper.setLocale(SettingsActivity.this, "en");

                        langEn.setTextColor(Color.parseColor("#4559FD"));
                        langEl.setTextColor(Color.parseColor("#A6ABEA"));

                        langEn.setTextSize(38);
                        langEl.setTextSize(30);
                        break;
                    }

                }

                return true;
            }
        });

    ImageView closeBtn = (ImageView) findViewById(R.id.action_merchant_info_close);

        closeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:{

                        finish();

                        break;
                    }

                }

                return true;
            }
        });


        ImageView proceedBtn = (ImageView) findViewById(R.id.proceed_btn);

        proceedBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                nextActivity();

                return false;
            }
        });


    }


    public void nextActivity(){



        if(!pr.getLang().equals(chosenLang)){

            pr.setLang(chosenLang);

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("changedLang", true);
            startActivity(intent);
        }

        finish();
    }
}
