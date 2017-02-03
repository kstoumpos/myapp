package gr.myapp.app;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Sevenlabs on 7/7/2016.
 */

public class LoginEvent {



    public  String fullName = "";
    public  String balance = "";
    public  String accountType = "";
    public  String loginToken = "";
    public  String responseCode = "";
    public  String phone = "";
    public  String email = "";

    public LoginEvent(HashMap<String,String> data){

            this.fullName = data.get("fullName");


        }
    public LoginEvent(JSONObject data){

            this.fullName = data.optString("UserFullName");
            this.balance = data.optString("UserBalance");
            this.accountType = data.optString("UserAccountType");
            this.loginToken = data.optString("LoginToken");
            this.responseCode = data.optString("ResponseCode");
            this.phone = data.optString("UserPhone");
            this.email = data.optString("UserEmail");


        }

}
