package gr.myapp.app;

import java.util.ArrayList;

/**
 * Created by Sevenlabs on 7/10/2016.
 */

public class HeaderAggregate {


    public String iconUrl;

    public ArrayList<ChildAggregate> children = new ArrayList<ChildAggregate>();



    HeaderAggregate(String icon){

        iconUrl = icon;

    }

    public void addChild(ChildAggregate child){

        children.add(child);


    }

}
