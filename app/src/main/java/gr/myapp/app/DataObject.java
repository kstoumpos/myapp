package gr.myapp.app;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

import org.w3c.dom.Text;

/**
 * Created by Sevenlabs on 28/06/16.
 */

public class DataObject {
    private String mText1;
    private String mText2;
    private String imgArtUrl;
    URL imgUrl;

    public String mArticleContent;

    private String mArticleCategory;
    private String mArticleIconCategory;
    private ArrayList<String> mListMultipleImages;

    DataObject (String text1, String text2, String url, String articleContent, String articleCategory, String articleIconCategory, ArrayList<String> listMultipleImages){
        mText1 = text1;
        mText2 = text2;
        imgArtUrl = url;
        mArticleContent = articleContent;
        mArticleCategory = articleCategory;
        mArticleIconCategory = articleIconCategory;
        mListMultipleImages = listMultipleImages;

    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public URL getmImgArt() {
         imgUrl = null;
            try {
                 imgUrl = new URL(imgArtUrl);
            }catch(Exception e){

            }
        return imgUrl;
    }

    public void setmImgArt(URL imgArt) {
        this.imgUrl = imgArt;
    }


    public String getmCategory() {
        return mArticleCategory;
    }


    public String getmArticleContent() {
        return mArticleContent;
    }

    public String getmArticleIconCategory() {
        return mArticleIconCategory;
    }
    public ArrayList<String> getmListMultipleImages() {
        return mListMultipleImages;
    }



}