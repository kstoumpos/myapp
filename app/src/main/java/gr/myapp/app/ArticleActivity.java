package gr.myapp.app;




import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ArticleActivity extends MyActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;
    private int constructedSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Bundle extras = getIntent().getExtras();


        ImageView backBtnArticle = (ImageView) findViewById(R.id.action_back_from_article);
        ImageView catIconImage = (ImageView) findViewById(R.id.article_category_icon);

        backBtnArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleActivity.this.finish();
            }
        });


        HashMap<String, Object> data = (HashMap<String, Object>) extras.getSerializable("data");

        TextView articleTitle = (TextView) findViewById(R.id.textView3);
        TextView articleContent = (TextView) findViewById(R.id.textView4);
        TextView articleCategory = (TextView) findViewById(R.id.categoryName);


        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        LinkedHashMap<String, String> url_maps = new LinkedHashMap<String, String>();
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        if (data.get("multipleImages") != null) {
            ArrayList<String> images =(ArrayList<String>) data.get("multipleImages");



            url_maps.put("", data.get("urlImage").toString());



            for (String image : images) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description("")
                        .image(image)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(this);

                mDemoSlider.addSlider(textSliderView);
            }
            constructedSize = url_maps.size();
        } else {

            file_maps.put("no_image", R.drawable.img_not_available);

            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);
                mDemoSlider.addSlider(textSliderView);


            }
            mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            constructedSize = file_maps.size();
        }


        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);

        mDemoSlider.stopAutoCycle();
        mDemoSlider.addOnPageChangeListener(this);


        articleTitle.setText(String.valueOf(data.get("title")));

        articleContent.setText(String.valueOf(data.get("content")));
        articleCategory.setText(String.valueOf(data.get("category")).toLowerCase());


        String mDrawableName = "filter_"+String.valueOf(data.get("category")).toLowerCase();
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        if(resID != 0) {
            catIconImage.setImageDrawable(ContextCompat.getDrawable(ArticleActivity.this, resID));
        }


    }



    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }



}
