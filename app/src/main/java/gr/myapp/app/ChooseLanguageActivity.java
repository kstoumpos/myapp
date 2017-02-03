package gr.myapp.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseLanguageActivity extends AppCompatActivity {

    String chosenLang = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        PrefManager pr = new PrefManager(this.getBaseContext());
        chosenLang = pr.getLang();
        if(chosenLang.equals("lang")){
            chosenLang = "en";
        }
        final TextView langEl = (TextView) findViewById(R.id.language_greek);
        final TextView langEn = (TextView) findViewById(R.id.language_english);


        if(pr.getLang().equals("el") || pr.getLang().equals("en")){

           nextActivity();

        }


        if(chosenLang.equals("el")){

            langEl.setTextColor(Color.parseColor("#4559FD"));
            langEl.setTextSize(38);

        }else{
            langEn.setTextColor(Color.parseColor("#4559FD"));
            langEn.setTextSize(38);

        }

        LocalizationHelper.setLocale(ChooseLanguageActivity.this, chosenLang);
        pr.setLang(chosenLang);

        langEl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:{
                        chosenLang = "el";

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


        ImageView proceedBtn = (ImageView) findViewById(R.id.proceed_btn);

        proceedBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {




                nextActivity();

                return false;
            }
        });

    }

    private void nextActivity(){

        PrefManager pr = new PrefManager(this);

        LocalizationHelper.setLocale(ChooseLanguageActivity.this, chosenLang);
        pr.setLang(chosenLang);

        Intent intent = new Intent(ChooseLanguageActivity.this, MyAppIntro.class);
        startActivity(intent);
        finish();


    }
}
