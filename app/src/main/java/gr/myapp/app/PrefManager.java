package gr.myapp.app;

/**
 * Created by appgradesteam on 05/10/16.
 */

import android.content.Context;
import android.content.SharedPreferences;


public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "myappPref";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String LANGUAGE = "lang";
    private static final String ICON_GREY = "catIconGrey";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setLang(String lang) {
        editor.putString(LANGUAGE, lang);
        editor.commit();
    }

    public String getLang() {
        return pref.getString(LANGUAGE, "lang");
    }

    public void setIconGrey(String icon) {
        editor.putString(ICON_GREY, icon);
        editor.commit();
    }

    public String getIconGrey() {
        return pref.getString(ICON_GREY, "");
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}