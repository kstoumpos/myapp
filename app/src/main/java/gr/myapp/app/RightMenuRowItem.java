package gr.myapp.app;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.net.URL;

/**
 * Created by Sevenlabs on 04/07/16.
 */
public class RightMenuRowItem {

    private String mText1;
    private Drawable iconURL;

    RightMenuRowItem (String text1){
        mText1 = text1;
    }

    RightMenuRowItem (String text1, Drawable url){
        mText1 = text1;
        iconURL = url;
    }

    public String getRightMenuText1() {

        return mText1;
    }


    public Drawable getRightMenuIcon() {

        return this.iconURL;
    }





}
