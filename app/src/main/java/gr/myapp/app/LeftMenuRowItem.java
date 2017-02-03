package gr.myapp.app;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Sevenlabs on 04/07/16.
 */
public class LeftMenuRowItem {

    private String mText1;
    private Drawable iconURL;

    public ArrayList<Merchant> myData;


    LeftMenuRowItem (String text1, ArrayList<Merchant> myDataParam,Drawable iconURL){
        mText1 = text1;
        this.iconURL = iconURL;

        myData = myDataParam;
    }

    public String getLeftMenuText1() {

        return mText1;
    }


    public Drawable getLeftMenuIcon() {

        return this.iconURL;
    }


}
