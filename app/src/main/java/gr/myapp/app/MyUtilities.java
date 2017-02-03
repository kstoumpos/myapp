package gr.myapp.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Sevenlabs on 4/7/2016.
 */

public class MyUtilities {

/*

 */
public static void hideSoftKey(AppCompatActivity activity,View v) {


    if (!(v instanceof EditText)) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }
}
    public static Boolean checkInternet(Context context) {
        //check connection
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }





}
