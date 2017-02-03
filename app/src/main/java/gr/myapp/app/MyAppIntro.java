package gr.myapp.app;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAppIntro extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip;
    private PrefManager prefManager;
    ImageView backButton;
    ImageView nextButton;
    int curPosition = 0;
    int previousPosition = 0;
    ArrayList<TextView> introActionsArray = new ArrayList<>();
    ArrayList<ImageView> introIconsArray = new ArrayList<>();
    ArrayList<Drawable> introGreyIconsArray = new ArrayList<>();
    ArrayList<Drawable> introBlackIconsArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        prefManager = new PrefManager(this);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_myapp_intro);


//some sharedPref initializations
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.local_storage_filename),this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.ls_have_seen_first_slider), 1);
        editor.putBoolean(this.getString(R.string.ls_already_alert_connection),false);

        editor.commit();


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btnSkip = (Button) findViewById(R.id.btn_skip);


        btnSkip.setTextColor(ContextCompat.getColor(MyAppIntro.this, R.color.aluminum));

        backButton = (ImageView) findViewById(R.id.introLeftArrow);
        backButton.setVisibility(View.GONE);
        nextButton = (ImageView) findViewById(R.id.introRightArrow);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.drawable.travel_banner,
                R.drawable.beauty_banner,
                R.drawable.needs_banner,
                R.drawable.wallet_banner,
                R.drawable.reward_banner,
                R.drawable.deals_banner
        };




        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOffscreenPageLimit(2);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch(event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {


                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        if (curPosition - 1 >= 0) {
                            viewPager.setCurrentItem(curPosition - 1);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:{



                        break;
                    }
                }
                return true;
            }
        });

        nextButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch(event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {

                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        if (curPosition + 1 <= layouts.length -1 ) {
                            viewPager.setCurrentItem(curPosition + 1);

                        }
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:{



                        break;
                    }
                }
                return true;
            }
        });


        ImageView introAction1 = (ImageView) findViewById(R.id.introAction1);
        final TextView introActionLabel1 = (TextView) findViewById(R.id.introActionLabel1);
        ImageView introAction2 = (ImageView) findViewById(R.id.introAction2);
        final TextView introActionLabel2 = (TextView) findViewById(R.id.introActionLabel2);
        ImageView introAction3 = (ImageView) findViewById(R.id.introAction3);
        final TextView introActionLabel3 = (TextView) findViewById(R.id.introActionlabel3);
        ImageView introAction4 = (ImageView) findViewById(R.id.introAction4);
        final TextView introActionLabel4 = (TextView) findViewById(R.id.introActionLabel4);
        ImageView introAction5 = (ImageView) findViewById(R.id.introAction5);
        final TextView introActionLabel5 = (TextView) findViewById(R.id.introActionLabel5);
        ImageView introAction6 = (ImageView) findViewById(R.id.introAction6);
        final TextView introActionLabel6 = (TextView) findViewById(R.id.introActionLabel6);

        int deviceSize = getResources().getInteger(R.integer.isTablet);
        PrefManager pr = new PrefManager(this.getBaseContext());
        if(pr.getLang().equals("en") && deviceSize != 1){

                introActionLabel1.setTextSize(10);
                introActionLabel2.setTextSize(10);
                introActionLabel3.setTextSize(10);
                introActionLabel4.setTextSize(10);
                introActionLabel5.setTextSize(10);
                introActionLabel6.setTextSize(10);

        }



        introActionsArray.add(introActionLabel1);
        introActionsArray.add(introActionLabel2);
        introActionsArray.add(introActionLabel3);
        introActionsArray.add(introActionLabel4);
        introActionsArray.add(introActionLabel5);
        introActionsArray.add(introActionLabel6);

        introIconsArray.add(introAction1);
        introIconsArray.add(introAction2);
        introIconsArray.add(introAction3);
        introIconsArray.add(introAction4);
        introIconsArray.add(introAction5);
        introIconsArray.add(introAction6);

        introGreyIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_travel_gray));
        introGreyIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_beauty_gray));
        introGreyIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_home_needs_gray));
        introGreyIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_wallet_gray));
        introGreyIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_reward_gray));
        introGreyIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_deals_near_me_gray));

        introBlackIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_travel_black));
        introBlackIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_beauty_black));
        introBlackIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_home_needs_black));
        introBlackIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_wallet_black));
        introBlackIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_reward_black));
        introBlackIconsArray.add(ContextCompat.getDrawable(this, R.drawable.intro_deals_near_me_black));

        introAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        introAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        introAction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        introAction4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

        introAction5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(4);
            }
        });

        introAction6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(5);
            }
        });



    }

    private void highlightSelection(int selectedPosition){

        introActionsArray.get(previousPosition).setVisibility(View.GONE);
        introActionsArray.get(selectedPosition).setVisibility(View.VISIBLE);

        introIconsArray.get(previousPosition).setImageDrawable(introGreyIconsArray.get(previousPosition));
        introIconsArray.get(selectedPosition).setImageDrawable(introBlackIconsArray.get(selectedPosition));

        if(selectedPosition > 0 && selectedPosition < 5 ){
            backButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }else if(selectedPosition == 0){
            backButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }else if(selectedPosition == 5){
            nextButton.setVisibility(View.GONE);
            backButton.setVisibility(View.VISIBLE);
        }

        previousPosition = selectedPosition;

    }



    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(MyAppIntro.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            curPosition = position;
            highlightSelection(curPosition);

            if (position == layouts.length - 1) {
                btnSkip.setVisibility(View.GONE);
            } else {
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {


            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View view = layoutInflater.inflate(R.layout.welcome_slide1, container, false);
            container.addView(view);






            if(position == layouts.length-1){

                ImageView experienceIntroButton = (ImageView) view.findViewById(R.id.experience_intro_button);

                experienceIntroButton.setVisibility(View.VISIBLE);

                experienceIntroButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchHomeScreen();
                    }
                });
            }

            ImageView imageSlide = (ImageView) view.findViewById(R.id.introImg);

            imageSlide.setImageDrawable(ContextCompat.getDrawable(MyAppIntro.this, layouts[position]));



            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}