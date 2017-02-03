package gr.myapp.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;


import android.widget.ImageView;


import org.json.JSONArray;
import org.json.JSONObject;


public class OneFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    final ArrayList listItems = new ArrayList<DataObject>();
    EndlessRecyclerOnScrollListener onScrollListener;

    private BroadcastReceiver fragmentRefreshReceiver;

    public String filterQuery = null;

    public OneFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity currentActivity = (MainActivity) this.getActivity();


        View kati = inflater.inflate(R.layout.fragment_one, container, false);

        mRecyclerView = (RecyclerView) kati.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(currentActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(listItems, currentActivity.getBaseContext(), "left");


        mRecyclerView.setAdapter(mAdapter);
        onScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page) {

                fillCardView(page - 1,null,false);

            }
        };

            mRecyclerView.addOnScrollListener(onScrollListener);



        fillCardView(0,null,true);


        ImageView filterGreece = (ImageView) kati.findViewById(R.id.filter_greece);
        filterGreece.setOnClickListener(new ViewListeners(this.getContext()).new OneFragmentFilters(OneFragment.this)  ) ;

        ImageView filterWorld = (ImageView) kati.findViewById(R.id.filter_world);
        filterWorld.setOnClickListener(new ViewListeners(this.getContext()).new OneFragmentFilters(OneFragment.this)  ) ;

        ImageView filterSports = (ImageView) kati.findViewById(R.id.filter_sports);
        filterSports.setOnClickListener(new ViewListeners(this.getContext()).new OneFragmentFilters(OneFragment.this)  ) ;

        ImageView filterEconomy = (ImageView) kati.findViewById(R.id.filter_economy);
        filterEconomy.setOnClickListener(new ViewListeners(this.getContext()).new OneFragmentFilters(OneFragment.this)  ) ;

        ImageView filterWoman = (ImageView) kati.findViewById(R.id.filter_woman);
        filterWoman.setOnClickListener(new ViewListeners(this.getContext()).new OneFragmentFilters(OneFragment.this)  ) ;

        ImageView filterTechnology = (ImageView) kati.findViewById(R.id.filter_technology);
        filterTechnology.setOnClickListener(new ViewListeners(this.getContext()).new OneFragmentFilters(OneFragment.this)  ) ;

        ImageView filterNewspapers = (ImageView) kati.findViewById(R.id.filter_newspapers);
        filterNewspapers.setOnClickListener(new ViewListeners(this.getContext()).new OneFragmentFilters(OneFragment.this)  ) ;

        return  kati;
    }

    @Override
    public void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("FragmentRefresh");

        fragmentRefreshReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            }
        };




        this.getActivity().registerReceiver(fragmentRefreshReceiver,filter);



    }

    @Override
    public void onPause(){
        super.onPause();
        this.getActivity().unregisterReceiver(fragmentRefreshReceiver);
    }



   public void fillCardView(int page, String query, final Boolean resetList){



       RequestQueue queue = Volley.newRequestQueue(this.getContext());


       String url ="http://www.my-api.eu:8983/solr/myapp_gr/select?fq=bundle%3Aarticle&wt=json&rows=7&sort=ds_created+desc&start="+ String.valueOf(page*7);

       if(filterQuery != null){
           url += "&q=sm_vid_categorynews:"+filterQuery;
       }else{
           url += "&q=*:*";
       }

       final String testurl = url;

       // Request a string response from the provided URL.
       StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       // Display the first 500 characters of the response string.

                       if(resetList) {
                           listItems.clear();
                           onScrollListener.resetList();
                       }


                       /****************** Start Parse Response JSON Data *************/
                       DataObject listItem;

                       JSONObject jsonResponse;

                       try {

                           /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                           jsonResponse = new JSONObject(response);

                           /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                           /*******  Returns null otherwise.  *******/
                           JSONArray jsonMainNode = jsonResponse.optJSONObject("response").getJSONArray("docs");
                           /*********** Process each JSON Node **************/

                           int lengthJsonArr = jsonMainNode.length();

                           for (int i = 0; i < lengthJsonArr; i++) {

                               try {

                                   /****** Get Object for each JSON node.***********/
                                   JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                                   /******* Fetch node values **********/
                                   String title = jsonChildNode.optString("label").toString();
                                   String teaser = jsonChildNode.optString("teaser").toString();

                                   String articleType = jsonChildNode.optString("bundle").toString();
                                   String articleContent = jsonChildNode.optString("content").toString();
                                   String articleCategory = jsonChildNode.optString("tm_vid_5_names").toString();

                                   String articleIconCategory = jsonChildNode.optString("sm_vid_categorynews").toString();

                                   if( articleType.equals("article") ){
                                       ArrayList<String> listMultipleImages = new ArrayList<>();
                                       String imgArtUrl = null;
                                       try {
                                           JSONArray imgFirstChild = jsonChildNode.getJSONArray("sm_field_mobile_image");

                                           imgArtUrl = imgFirstChild.get(0).toString();
                                           int j;

                                           for(j=0;j<imgFirstChild.length();j++){
                                               listMultipleImages.add(imgFirstChild.getString(j));
                                           }

                                       } catch (Exception e) {
                                       }


                                       listItem = new DataObject(title, teaser, imgArtUrl, articleContent, articleCategory, articleIconCategory,listMultipleImages);
                                       listItems.add(listItem);



                                   }



                               }catch (Exception e){
                               }

                           }

                       }catch(Exception ex){
                       }
                       /****************** End Parse Response JSON Data *************/


                       mAdapter.notifyDataSetChanged();


                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               //Log.d("That didn't work!", "");
           }
       });

// Add the request to the RequestQueue.
       queue.add(stringRequest);


        /*

        End - Request News
         */


   }



public void printSomething(Location location){


    fillCardView(0,null,true);




}



}
