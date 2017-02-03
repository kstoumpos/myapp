package gr.myapp.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantCategoryActivity extends MyActivity {

    private ChildMerchant rowItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_category);


        Bundle extras = getIntent().getExtras();

        HashMap<String,ChildMerchant> hashMap = (HashMap<String,ChildMerchant>) extras.getSerializable("data");
        rowItem  = hashMap.get("children");

        ImageView merchantCatLogo = (ImageView) findViewById(R.id.action_merchant_cat_icon);
        Ion.with(merchantCatLogo).load(rowItem.merchantCategoryIcon);

        ImageView merchantLogo = (ImageView) findViewById(R.id.merchantItemImage);
        Ion.with(merchantLogo).load(rowItem.merchantLogo);

        TextView merchantItemName = (TextView) findViewById(R.id.merchantItemName);
        merchantItemName.setText(rowItem.merchantName);

        TextView merchantItemTitle = (TextView) findViewById(R.id.merchantItemTitle);
        merchantItemTitle.setText(rowItem.merchantTitle);

        TextView merchantItemDesc = (TextView) findViewById(R.id.merchantItemDesc);
        merchantItemDesc.setText(rowItem.merchantDescription);

        ImageView closeActivity = (ImageView) findViewById(R.id.action_merchant_info_close);
        closeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MerchantCategoryActivity.this.finish();

            }
        });


    }



}
