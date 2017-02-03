package gr.myapp.app;

import java.util.ArrayList;

/**
 * Created by Sevenlabs on 7/10/2016.
 */

public class ChildAggregate {


    public String message;

    public ArrayList<ChildMerchant> childrenMerchants = new ArrayList<ChildMerchant>();

    public String map;

    ChildAggregate(String message, String map){

        this.message = message;
        this.map = map;
    }

    public void addMerchant(ChildMerchant child){


        childrenMerchants.add(child);

    }





}
