package gr.myapp.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sevenlabs on 8/7/2016.
 */

public class ViewListeners extends View {
    //0 left menu , 1 right menu
    private int mType;
    private Context context;

    private SlidingMenu mSlidingMenu;
    /* constructor for sliding menu button triggers */
    public ViewListeners(Context context, int type, SlidingMenu slidingMenu){

        super(context);
        mType = type;
        mSlidingMenu = slidingMenu;

        this.context= context;
    }
    /* miscellaneous type Listener */
    public ViewListeners(Context context){

        super(context);

        this.context= context;

    }


    public class NewsTab implements View.OnTouchListener {

        private HashMap <String,Object> mData ;

        public NewsTab(HashMap <String,Object> data){

           mData = data;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                    break;
                }
                case MotionEvent.ACTION_UP:{



                    Intent intent = new Intent(context, ArticleActivity.class);

                    intent.putExtra("data", mData);
                    Activity currentActivity = (Activity) context;
                    currentActivity.startActivity(intent);

                    break;
                }


                case MotionEvent.ACTION_CANCEL: {
                    break;
                }

            }

            return true;
        }

    }

    public class WebViewTap implements View.OnTouchListener {

        private HashMap <String,Object> mData ;

        public WebViewTap(HashMap <String,Object> data){

           mData = data;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                    break;
                }
                case MotionEvent.ACTION_UP:{


                    Intent intent = new Intent(context, WebViewActivity.class);

                    intent.putExtra("data", mData);
                    Activity currentActivity = (Activity) context;
                    currentActivity.startActivity(intent);

                    break;
                }


                case MotionEvent.ACTION_CANCEL: {
                    break;
                }

            }

            return true;
        }

    }

    public class LeftCategoryTap implements View.OnTouchListener {

        private ArrayList<Merchant> mData ;

        public LeftCategoryTap(ArrayList<Merchant> data){

           mData = data;
        }



        @Override
        public boolean onTouch(View v, MotionEvent event) {


            switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN: {

                    break;
                }
                case MotionEvent.ACTION_UP:{

                    Intent intent = new Intent(context, WebViewActivity.class);

                    try {
                        Merchant currentMerch = mData.get(0);

                        HashMap<String, String> webData = new HashMap<String, String>();
                        webData.put("url", currentMerch.webUrl);

                        intent.putExtra("data", webData);
                        Activity currentActivity = (Activity) context;
                        currentActivity.startActivity(intent);
                    }catch (Exception ex){

                        Toast.makeText(context, "There is not any merchant in this category", Toast.LENGTH_LONG).show();

                    }

                    break;
                }


                case MotionEvent.ACTION_CANCEL: {
                    break;
                }

            }

            return true;
        }

    }



    public class TouchMenuBar implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                    ImageView view = (ImageView) v;
                    view.getDrawable().setColorFilter(0x77FF0000, PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP:{


                    ImageView view = (ImageView) v;
                    view.getDrawable().clearColorFilter();
                    view.invalidate();

                    switch(mType){
                        case 0:{
                            mSlidingMenu.showMenu();
                            break;
                        }
                        case 1:{
                            mSlidingMenu.showSecondaryMenu();
                            break;
                        }


                    }

                    break;
                }


                case MotionEvent.ACTION_CANCEL: {
                    ImageView view = (ImageView) v;
                    //clear the overlay
                    view.getDrawable().clearColorFilter();
                    view.invalidate();
                    break;
                }

            }

            return true;
        }

    }




    public class OneFragmentFilters implements View.OnClickListener {

        OneFragment oneFragment;


        OneFragmentFilters(OneFragment oneFragment){
            this.oneFragment = oneFragment;
        }

        @Override
        public void onClick(View v) {

            int id =  v.getId();


            String url = null;


               switch (id){

                case R.id.filter_greece:

                    oneFragment.filterQuery = "greece";

                    oneFragment.fillCardView(0, null, true);

                    break;


                   case R.id.filter_world:

                       oneFragment.filterQuery = "world";

                       oneFragment.fillCardView(0, null, true);

                       break;


                   case R.id.filter_sports:

                       oneFragment.filterQuery = "sports";

                       oneFragment.fillCardView(0, null, true);

                       break;


                   case R.id.filter_woman:

                       oneFragment.filterQuery = "woman";

                       oneFragment.fillCardView(0, null, true);

                       break;


                   case R.id.filter_economy:

                       oneFragment.filterQuery = "economy";

                       oneFragment.fillCardView(0, null, true);

                       break;


                   case R.id.filter_technology:

                       oneFragment.filterQuery = "technology";

                       oneFragment.fillCardView(0, null, true);

                       break;


                   case R.id.filter_newspapers:

                       oneFragment.filterQuery = "newspapers";

                       oneFragment.fillCardView(0, null, true);

                       break;


                default:
                    break;


            }



        }

    }



    public class TwoFragmentFilters implements View.OnClickListener {

        TwoFragment twoFragment;


        TwoFragmentFilters(TwoFragment twoFragment){
            this.twoFragment = twoFragment;
        }

        @Override
        public void onClick(View v) {

           int id =  v.getId();

            String url = null;

            Location loc = null;

            switch (id){

                case R.id.deals_near_me:


                    LocationManager mLocationManager = (LocationManager) twoFragment.getActivity().getSystemService(twoFragment.getActivity().LOCATION_SERVICE);

                    if (ActivityCompat.checkSelfPermission( twoFragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( twoFragment.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions( twoFragment.getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    }else{

                        loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        url = "&sort=geodist()+asc&rows=10&wt=json&indent=true&sfield=locm_field_coors_store&pt=";



                        if(loc != null) {
                            url += loc.getLongitude() + "," + loc.getLatitude() + "&d=1";

                            twoFragment.isGPSSet  = true;
                            twoFragment.filterQuery = url;

                        } else {

                            return;

                        }


                    }



                    break;


                case R.id.deals_food:

                    twoFragment.isGPSSet  = false;
                    twoFragment.filterQuery = "food";
                    break;

                case R.id.deals_transportation:

                    twoFragment.isGPSSet  = false;
                    twoFragment.filterQuery = "transportation";
                    break;

                case R.id.deals_health:

                    twoFragment.isGPSSet  = false;
                    twoFragment.filterQuery = "health";
                    break;

                case R.id.deals_beauty:

                    twoFragment.isGPSSet  = false;
                    twoFragment.filterQuery = "beauty";
                    break;

                case R.id.deals_travel:

                    twoFragment.isGPSSet  = false;
                    twoFragment.filterQuery = "travel";
                    break;


                case R.id.deals_e_shops:

                    twoFragment.isGPSSet  = false;
                    twoFragment.filterQuery = "e_shops";
                    break;

                default:
                    break;


            }


            twoFragment.fillCardView(0, null, true);


        }

    }






    public class ChildAggregateButton implements View.OnTouchListener {

        String mMessage;

        ArrayList<ChildMerchant> childMerchantArrayList;
        String mMap;
        ChildAggregateButton(String message, ArrayList<ChildMerchant> incomeList, String map){

            mMessage = message;
            mMap = map;
            childMerchantArrayList = incomeList;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {



            switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                    break;
                }
                case MotionEvent.ACTION_UP:{




                    HashMap<String, ArrayList<ChildMerchant> > data = new HashMap<String, ArrayList<ChildMerchant> >();


                    data.put("children", childMerchantArrayList);


                    Activity current = ((MyApp)context.getApplicationContext()).getCurrentActivity();
                    Intent intent;

                    if(mMap.equals("1")) {
                        intent = new Intent(context, MapActivity.class);
                        intent.putExtra("data",data );
                    } else {
                        if (childMerchantArrayList.size() > 1) {


                            intent = new Intent(current.getBaseContext(), MerchantListActivity.class);
                            intent.putExtra("data", data);
                        } else {
                            String url = childMerchantArrayList.get(0).merchantUrl;

                            HashMap<String, String> webData = new HashMap<String, String>();
                            webData.put("url", url);

                            intent = new Intent(context, WebViewActivity.class);
                            intent.putExtra("data", webData);

                            // info activity data
                            HashMap<String, ChildMerchant> data2 = new HashMap<>();
                            data2.put("children", childMerchantArrayList.get(0));
                            intent.putExtra("infodata", data2);
                            // info activity
                        }
                    }

                    Activity currentActivity = current;
                    currentActivity.startActivity(intent);

                    if(!currentActivity.getClass().getSimpleName().equals(MainActivity.class.getSimpleName())){
                        currentActivity.finish();
                    }



                    break;

                }


                case MotionEvent.ACTION_CANCEL: {
                    break;
                }

            }

            return true;
        }

    }

    public class MerchantCategoryButton implements View.OnTouchListener {



        ChildMerchant childMerchantArrayList;

        MerchantCategoryButton(ChildMerchant incomeList){



            childMerchantArrayList = incomeList;
        }



        @Override
        public boolean onTouch(View v, MotionEvent event) {


            switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                    break;
                }
                case MotionEvent.ACTION_UP:{

                    HashMap<String, ChildMerchant > data = new HashMap<>();


                    data.put("children", childMerchantArrayList);
                    Activity current = ((MyApp)context.getApplicationContext()).getCurrentActivity();
                    Intent intent = new Intent(current.getBaseContext(), MerchantCategoryActivity.class);
                    intent.putExtra("data", data);
                    Activity currentActivity = current;
                    currentActivity.startActivity(intent);




                    break;

                }


                case MotionEvent.ACTION_CANCEL: {
                    break;
                }

            }

            return true;
        }

    }



public class MerchantListItemButton implements View.OnTouchListener {



        ChildMerchant rowItem;

        MerchantListItemButton(ChildMerchant child){
            rowItem = child;
        }



        @Override
        public boolean onTouch(View v, MotionEvent event) {

           switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                    break;
                }
                case MotionEvent.ACTION_UP:{


                    String url = rowItem.merchantUrl;

                    HashMap<String, String> webData = new HashMap<String, String>();
                    webData.put("url", url);

                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("data", webData);

                    // info activity data
                    HashMap<String, ChildMerchant> data = new HashMap<>();
                    data.put("children", rowItem);
                    intent.putExtra("infodata", data);
                    // info activity

                    Activity current = (MerchantListActivity) context;
                    current.startActivity(intent);


                    break;

                }


                case MotionEvent.ACTION_CANCEL: {
                    break;
                }

            }

            return true;
        }

    }




    public class RightListItemListener implements View.OnTouchListener {

        int mItemPosition = 0;

        RightListItemListener(int itemPosition){
            mItemPosition = itemPosition;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_UP) {
                switch (mItemPosition) {
                    case 0:
                        Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_LONG).show();
                        break;

                    case 2:
                        Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_LONG).show();
                        break;

                    case 3:
                        Activity current = ((MyApp) context.getApplicationContext()).getCurrentActivity();


                        Intent intent = new Intent(current.getBaseContext(), SettingsActivity.class);
                        current.startActivity(intent);
                        break;
                }

            }

            return true;
        }
    }



}
