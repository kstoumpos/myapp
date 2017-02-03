package gr.myapp.app;

import java.io.Serializable;

/**
 * Created by Sevenlabs on 7/10/2016.
 */

public class ChildMerchant implements Serializable {

    public String merchantOrder;
    public String merchantName;
    public String merchantUrl;
    public String merchantLogo;
    public String merchantTitle;
    public String merchantDescription;
    public double merchantCorsLat;
    public double merchantCorsLng;
    public String merchantCategoryIcon;




    ChildMerchant(String merchant_order,String merchant_name,String merchant_url,String merchant_logo,String merchant_title,String merchant_description,String merchant_cors, String merchantCategoryIcon){

                           merchantOrder =         merchant_order;
                           merchantName  =         merchant_name;
                           merchantUrl  =         merchant_url;
                           merchantLogo =         merchant_logo;
                           merchantTitle =        merchant_title;
                           merchantDescription =   merchant_description;
                            this.merchantCategoryIcon = merchantCategoryIcon;
                            setLngLat(merchant_cors);

    }



    private void setLngLat(String mercant_cors){

        String split[] = mercant_cors.split(",");

        this.merchantCorsLat = Double.parseDouble(split[0]);
        this.merchantCorsLng = Double.parseDouble(split[1]);


    }




}
