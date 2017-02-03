package gr.myapp.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 */
public class MapActivity extends AppCompatActivity
        implements GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback {


    ArrayList<ChildMerchant> merchants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        HashMap<String,ArrayList<ChildMerchant>> hashMap = (HashMap<String,ArrayList<ChildMerchant>>) extras.getSerializable("data");
        merchants  = hashMap.get("children");

        ChildMerchant rowItem = merchants.get(0);

        ImageView merchantCatLogo = (ImageView) findViewById(R.id.action_merchant_cat_icon);
        Ion.with(merchantCatLogo).load(rowItem.merchantCategoryIcon);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView closeActivity = (ImageView) findViewById(R.id.action_merchant_info_close);
        closeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MapActivity.this.finish();

            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng newLatLng = new LatLng(37.9745068,23.7330711); ;
        Iterator<ChildMerchant> iter = merchants.iterator();
        while(iter.hasNext()) {
            ChildMerchant merchant = iter.next();
             newLatLng = new LatLng(merchant.merchantCorsLat,merchant.merchantCorsLng);


            Marker marker = googleMap.addMarker(new MarkerOptions().position(newLatLng)
                    .title(merchant.merchantName));
            marker.setTag(merchant);
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.custom_marker_purple));

        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 16.0f));
        googleMap.setOnInfoWindowClickListener(this);


    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        ChildMerchant rowItem = (ChildMerchant)marker.getTag();
        String url = rowItem.merchantUrl;

        HashMap<String, String> webData = new HashMap<String, String>();
        webData.put("url", url);

        Intent intent = new Intent(this.getApplicationContext(), WebViewActivity.class);
        intent.putExtra("data", webData);

        // info activity data
        HashMap<String, ChildMerchant> data = new HashMap<>();
        data.put("children", rowItem);
        intent.putExtra("infodata", data);
        // info activity



        MapActivity.this.startActivity(intent);


    }
}
