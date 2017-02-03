package gr.myapp.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

public class MyActivity extends AppCompatActivity {


    protected ConnectivityCheckReceiver receiver;

    protected IntentFilter filter;

    protected MyApp mMyApp;

    protected SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = (MyApp)this.getApplicationContext();
        sharedPref = this.getSharedPreferences(this.getString(R.string.local_storage_filename),this.MODE_PRIVATE);

        filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        receiver = new ConnectivityCheckReceiver();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);



    }


    @Override
    public void onResume(){
        super.onResume();

        if(!MyUtilities.checkInternet(this.getApplicationContext())){

            sharedPref.edit().putBoolean(this.getString(R.string.ls_already_alert_connection),true).commit();
            dialogConnection();
        }

        mMyApp.setCurrentActivity(this);
        registerReceiver(receiver, filter);
    }


    @Override
    public void onPause(){
        super.onPause();
        clearReferences();
        unregisterReceiver(receiver);
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        clearReferences();


    }

    private void clearReferences(){
        Activity currActivity = mMyApp.getCurrentActivity();
        if (this.equals(currActivity))
            mMyApp.setCurrentActivity(null);
    }


    protected void dialogConnection(){
        final AlertDialog.Builder alertD = new AlertDialog.Builder(MyActivity.this)
                .setTitle("Internet Connection Lost")
                .setMessage("Check your Internet Connection")
                .setPositiveButton("Retry", null)
                .setNegativeButton("Exit", null)
                .setIcon(android.R.drawable.ic_dialog_alert);


        final AlertDialog alertDialog = alertD.create();

        alertDialog.setCancelable(false);


        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button bPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                bPositive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        ConnectivityManager cm_again =
                                (ConnectivityManager) MyActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork2 = cm_again.getActiveNetworkInfo();
                        boolean isConnected2 = activeNetwork2 != null &&
                                activeNetwork2.isConnectedOrConnecting();


                        if (isConnected2) {
                            // put false boolean to indicate that alert has dismissed
                            sharedPref.edit().putBoolean(MyActivity.this.getString(R.string.ls_already_alert_connection),false).commit();
                            // prepare to send broadcast action to refresh foreground fragments
                            Intent intentFragment = new Intent();
                            intentFragment.setAction("FragmentRefresh");
                            MyActivity.this.sendBroadcast(intentFragment);

                            Intent intentRefreshMenu = new Intent();
                            intentFragment.setAction("LeftMenuRefresh");
                            MyActivity.this.sendBroadcast(intentRefreshMenu);
                            EventBus.getDefault().post(new refreshLeftMenu());

                            alertDialog.dismiss();

                        }


                    }
                });


                Button bNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                bNegative.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyActivity.this.startActivity(intent);


                    }
                });

            }
        });


        alertDialog.show();
    }

    class refreshLeftMenu{

    }





}
