package gr.myapp.app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantListActivity extends AppCompatActivity {

    private ArrayList<ChildMerchant> childMerchantArraylst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_list);

        Bundle extras = getIntent().getExtras();

        HashMap<String,ArrayList<ChildMerchant>> hashMap = (HashMap<String,ArrayList<ChildMerchant>>) extras.getSerializable("data");
        childMerchantArraylst  = hashMap.get("children");

        ChildMerchant rowItem = childMerchantArraylst.get(0);


        ImageView merchantCatLogo = (ImageView) findViewById(R.id.action_merchant_cat_icon);
        Ion.with(merchantCatLogo).load(rowItem.merchantCategoryIcon);


         GridView lst = (GridView) findViewById(R.id.listViewMerchantList);
        if(childMerchantArraylst.size()>0) {

            MerchantAdapter adapter = new MerchantAdapter(this,R.layout.merchant_category_item,childMerchantArraylst);
            lst.setAdapter(adapter);

        }



        ImageView closeActivity = (ImageView) findViewById(R.id.action_merchant_info_close);
        closeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MerchantListActivity.this.finish();

            }
        });
    }



    private class MerchantAdapter extends ArrayAdapter<ChildMerchant> {

        Context context;
        public MerchantAdapter(Context context, int resourceId,
                               List<ChildMerchant> items) {
            super(context, resourceId, items);
            this.context = context;
        }

        /*private view holder class*/
        private class ViewHolder {
            ImageView imageView;
            TextView txtTitle1;
            TextView txtTitle2;
            TextView txtTitle3;
            ImageView btn1;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            ChildMerchant rowItem = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.merchant_category_item, null);
                holder = new ViewHolder();
                holder.txtTitle1 = (TextView) convertView.findViewById(R.id.merchantItemName);
                holder.btn1 = (ImageView) convertView.findViewById(R.id.action_merchant_info);
                holder.imageView = (ImageView) convertView.findViewById(R.id.merchantItemImage);




                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            holder.txtTitle1.setText(rowItem.merchantName);

            Ion.with(holder.imageView).load(rowItem.merchantLogo);

            convertView.setOnTouchListener(new ViewListeners(context). new MerchantListItemButton(rowItem));


            return convertView;

        }

    }

}
