package gr.myapp.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ConnectivityCheckReceiver extends BroadcastReceiver {

    protected SharedPreferences sharedPref;

    public ConnectivityCheckReceiver() {

    }


    @Override
    public void onReceive(final Context context, Intent intent) {


        for(String key : intent.getExtras().keySet()){
        }


        final Context cntx = context;

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        sharedPref = context.getSharedPreferences(context.getString(R.string.local_storage_filename),context.MODE_PRIVATE);
        boolean alertShowed = sharedPref.getBoolean(context.getString(R.string.ls_already_alert_connection),false);



        if (!isConnected && !alertShowed) {


            sharedPref.edit().putBoolean(context.getString(R.string.ls_already_alert_connection),true).commit();

            final AlertDialog.Builder alertD = new AlertDialog.Builder(context)
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
                                    (ConnectivityManager) cntx.getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo activeNetwork2 = cm_again.getActiveNetworkInfo();
                            boolean isConnected2 = activeNetwork2 != null &&
                                    activeNetwork2.isConnectedOrConnecting();


                            if (isConnected2) {
                                 sharedPref.edit().putBoolean(context.getString(R.string.ls_already_alert_connection),false).commit();
                                Intent intentFragment = new Intent();
                                intentFragment.setAction("FragmentRefresh");
                                context.sendBroadcast(intentFragment);
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
                            context.startActivity(intent);


                        }
                    });

                }
            });


            alertDialog.show();


        }


    }


}
