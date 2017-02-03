package gr.myapp.app;

import java.net.URL;

/**
 * Created by Sevenlabs on 02/08/16.
 */

public class Merchant implements java.io.Serializable{

    public String webUrl;
    public String imgUrl;
    public String label;

    public Merchant(String webUrl, String imgUrl,String label ){


        this.webUrl = webUrl;
        this.imgUrl= imgUrl;
        this.label = label;


    }



}
