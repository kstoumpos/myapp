package gr.myapp.app;

/**
 * Created by Sevenlabs on 28/06/16.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private Context mContext;
    private String mFragmentType;


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView ArtcleDesc;
        ImageView iconCatArticle;
        ImageView imgArticle;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.titleArticle);
            iconCatArticle = (ImageView) itemView.findViewById(R.id.iconCatArticle);
            imgArticle = (ImageView) itemView.findViewById(R.id.imgArticle);

            ArtcleDesc = (TextView) itemView.findViewById(R.id.textExcerpt);

        }


    }



    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset, Context context, String fragmentType) {
        mDataset = myDataset;
        mContext = context;
        mFragmentType = fragmentType;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_one_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());
        holder.ArtcleDesc.setText(mDataset.get(position).getmArticleContent().substring(0,120) + "...");

        String articleCategory = mDataset.get(position).getmCategory();

        Pattern p = Pattern.compile("\\[\"(.*)\"\\]");
        Matcher m = p.matcher(articleCategory);
        if(m.matches()) {
            articleCategory = m.group(1);
        }


        URL imgUrl = mDataset.get(position).getmImgArt();

        HashMap <String, Object> data = new HashMap <String, Object>();

        data.put("title", mDataset.get(position).getmText1());
        data.put("content", mDataset.get(position).getmArticleContent());
        data.put("category", articleCategory);
        data.put("categoryIcon", mDataset.get(position).getmArticleIconCategory());
        data.put("multipleImages", mDataset.get(position).getmListMultipleImages());


        if(imgUrl != null) {
            data.put("urlImage",imgUrl.toString());
        }
        holder.itemView.setOnTouchListener(new ViewListeners( holder.itemView.getContext() ).new NewsTab(data){

        });

        if(imgUrl != null){
            Ion.with(holder.imgArticle)
                    .load(imgUrl.toString())
                    ;

        }else{
            holder.imgArticle.setImageDrawable(ContextCompat.getDrawable( holder.itemView.getContext(),R.drawable.img_not_available) );
        }


        Pattern p2 = Pattern.compile("\\[\"(.*)\"\\]");
        Matcher m2 = p2.matcher(mDataset.get(position).getmArticleIconCategory());
        String articleIconCategory = "";
        if(m2.matches()) {
           articleIconCategory = m2.group(1).toLowerCase();
        }


        if(mFragmentType.equals("left") ){
            holder.iconCatArticle.setImageResource(mContext.getResources().getIdentifier("filter_"+articleIconCategory, "drawable", mContext.getPackageName()));
        }else{
            holder.iconCatArticle.setImageResource(mContext.getResources().getIdentifier("filter__"+articleIconCategory, "drawable", mContext.getPackageName()));

        }



    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}