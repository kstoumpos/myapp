package gr.myapp.app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


import android.graphics.Typeface;
import android.widget.BaseExpandableListAdapter;

import com.koushikdutta.ion.Ion;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private HashMap<String,HeaderAggregate> _listAggregate;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData,
                                 HashMap<String,HeaderAggregate> listAggregate
    ) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._listAggregate = listAggregate;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.left_menu_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.left_menu_item_merchant_name);

        txtListChild.setText(childText);
        txtListChild.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));


        HeaderAggregate currentHeader =_listAggregate.get((String)getGroup(groupPosition));

        if(currentHeader.children != null) {

            ChildAggregate childCat = currentHeader.children.get(childPosition);
            txtListChild.setOnTouchListener(new ViewListeners(_context).new ChildAggregateButton(childCat.message, childCat.childrenMerchants, childCat.map));
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if( this._listDataChild.get(this._listDataHeader.get(groupPosition)) != null ) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.left_menu_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.left_menu_item_cat_name);

        lblListHeader.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        lblListHeader.setText(headerTitle);


        HeaderAggregate currentHeader =_listAggregate.get(headerTitle);



        ImageView imgListheader = (ImageView) convertView.findViewById(R.id.left_menu_item_icon);


        Ion.with(convertView.getContext())
                .load(currentHeader.iconUrl)
                .withBitmap()
                .intoImageView(imgListheader)
        ;






        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
