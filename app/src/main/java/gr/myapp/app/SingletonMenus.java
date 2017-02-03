package gr.myapp.app;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sevenlabs on 7/10/2016.
 */

public class SingletonMenus {

    private static SingletonMenus menus;
    private  CustomListViewAdapterRight rightMenu;
    private  ExpandableListAdapter leftMenu;

    private boolean leftMenuLoaded = false;
    private boolean rightMenuLoaded = false;
    private boolean alreadyLoading = false;

    private Context myContext = null;

    private SingletonMenus(Context context){
        this.myContext = context;
    }

    public  synchronized static SingletonMenus getInstance(Context context){


        if(menus == null){

            menus = new SingletonMenus(context);

        }
        return menus;


    }


    public void setLeftMenu(ExpandableListAdapter leftMenu){


        this.leftMenu = leftMenu;

    }

    public void setRightMenu(CustomListViewAdapterRight rightMenu){

        this.rightMenu = rightMenu;

    }
     public ExpandableListAdapter getLeftMenu(){

         if(this.leftMenu == null){

             loadLeftMenuAdapter();
         }

         return this.leftMenu;

    }

    public CustomListViewAdapterRight getRightMenu(){

        if(this.rightMenu == null){

            loadRightMenuAdapter();

        }
        return this.rightMenu;

    }

     private void loadRightMenuAdapter(){

         ArrayList<RightMenuRowItem> rowItemsRight;

         rowItemsRight = new ArrayList<RightMenuRowItem>();

         rowItemsRight.add(new RightMenuRowItem(myContext.getString(R.string.login)));
         rowItemsRight.add(new RightMenuRowItem(myContext.getString(R.string.register)));
         rowItemsRight.add(new RightMenuRowItem(myContext.getString(R.string.terms)));
         rowItemsRight.add(new RightMenuRowItem(myContext.getString(R.string.settings)));

         rightMenu = new CustomListViewAdapterRight(this.myContext, R.layout.right_menu_item, rowItemsRight);


    }



    private void loadLeftMenuAdapter(){

        if(!alreadyLoading) {
            alreadyLoading = true;
            // WebServer Request URL


            final HashMap<String, Object> categoriesAggrTest = new HashMap<String, Object>();


            RequestQueue queue = Volley.newRequestQueue(this.myContext);

            String url = "http://www.my-app.gr/api/left-menu-merchants_cors.json";


            final HashMap<String, HeaderAggregate> categoriesAggr = new HashMap<String, HeaderAggregate>();
            final ArrayList<LeftMenuRowItem> rowItems = new ArrayList<LeftMenuRowItem>();

            final ArrayList<String> listDataHeader = new ArrayList<String>();
            final HashMap<Integer, String> unsortedMapDataHeader = new HashMap<Integer, String>();
            final HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            /****************** Start Parse Response JSON Data *************/
                            DataObject listItem;

                            JSONObject jsonResponse;

                            try {

                                try {
                                    response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                jsonResponse = new JSONObject(response);


                                Iterator<String> responseKeys = jsonResponse.keys();


                                while (responseKeys.hasNext()) {
                                    String currentKey = responseKeys.next();

                                    JSONObject categoryObject = jsonResponse.getJSONObject(currentKey);
                                    int indexOrder = Integer.parseInt(categoryObject.get("order").toString());

                                    unsortedMapDataHeader.put(indexOrder, currentKey);
                                    HeaderAggregate currentHeader = new HeaderAggregate("http://my-app.gr/html/" + categoryObject.get("icon"));

                                    String iconGrey = "http://my-app.gr/html/" + categoryObject.get("icon-grey");

                                    categoriesAggr.put(currentKey, currentHeader);


                                    if (categoryObject.optJSONArray("subcategories") != null) {
                                        JSONArray subcats = categoryObject.optJSONArray("subcategories");
                                        List listMerch = new ArrayList<String>();
                                        for (int i = 0; i < subcats.length(); i++) {
                                            JSONObject subcat = subcats.getJSONObject(i);
                                            JSONObject orderOnline;
                                            orderOnline = subcat.getJSONObject("order_online");
                                            if (orderOnline != null) {


                                                String messageMoto = orderOnline.get("message").toString();
                                                String map = orderOnline.get("map").toString();

                                                listMerch.add(i, messageMoto);


                                                JSONArray merchantsArray = orderOnline.optJSONArray("merchants");

                                                if (merchantsArray != null) {
                                                    ChildAggregate childCategory = new ChildAggregate(messageMoto,map);
                                                    for (int j = 0; j < merchantsArray.length(); j++) {
                                                        JSONObject merchantItem = merchantsArray.getJSONObject(j);
                                                        String merchant_order = merchantItem.getString("merchant_order");
                                                        String merchant_name = merchantItem.getString("merchant_name");
                                                        String merchant_url = merchantItem.getString("merchant_url");
                                                        String merchant_logo = merchantItem.getString("merchant_logo");
                                                        String merchant_title = merchantItem.getString("merchant_title");
                                                        String merchant_description = merchantItem.getString("merchant_description");
                                                        String merchant_cors = merchantItem.getString("merchant_cors");

                                                        ChildMerchant childMerch = new ChildMerchant(merchant_order, merchant_name, merchant_url, merchant_logo, merchant_title, merchant_description,merchant_cors, iconGrey);
                                                        childCategory.addMerchant(childMerch);

                                                    }
                                                    currentHeader.addChild(childCategory);
                                                }
                                            }

                                        }

                                        listDataChild.put(currentKey, listMerch);
                                    }


                                }
                            } catch (Exception ex) {
                            }


                            for (int sortIndex = 0; sortIndex <= unsortedMapDataHeader.size() - 1; sortIndex++) {

                                listDataHeader.add(unsortedMapDataHeader.get(sortIndex));

                            }


                            SingletonMenus.this.leftMenu = new ExpandableListAdapter(SingletonMenus.this.myContext, listDataHeader, listDataChild, categoriesAggr);


                            EventBus.getDefault().post(new FilledLeftListEvent(SingletonMenus.this.leftMenu));

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    alreadyLoading = false;
                }

            });

            queue.add(stringRequest);
        }




}


class FilledLeftListEvent{

    public ExpandableListAdapter leftMenu;

    FilledLeftListEvent(ExpandableListAdapter leftMenu){

        this.leftMenu = leftMenu;

    }

}


public void reset(){
    menus = null;
    rightMenu = null;
    leftMenu = null;

    leftMenuLoaded = false;
    rightMenuLoaded = false;
    alreadyLoading = false;
}





}
