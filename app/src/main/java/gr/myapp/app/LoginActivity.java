package gr.myapp.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;



public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    @Bind(R.id.scrollLayoutLogin) ScrollView _scrollLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyUtilities.hideSoftKey(LoginActivity.this,v);

                try {
                    login();
                } catch (JSONException e) {
                    onLoginError();
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        _scrollLayout.setOnTouchListener(this);










    }

    public void login() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }


        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();


        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JSONObject jsonObj = new JSONObject();

        jsonObj.put("userId",email);
        jsonObj.put("password",password);

        final String tag_login_request = "login_json_req";
        String url = "http://52.10.158.66/test/RestController.php?view=single";
        JsonObjectRequest loginJsonObject = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObj,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();

                        try {
                            Map<String, Object> checkResult = checkReturnRequest("JSON",response);

                            if(checkResult.get("login").equals("false")) {
                                onLoginFailed();
                            } else {

                                onLoginSuccess(checkResult);
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            onLoginError();
                        }

                    }
                },
                new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        queue.add(loginJsonObject);




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);

        finish();
    }

    public void onLoginSuccess(Map<String,Object> data) {
        _loginButton.setEnabled(true);

        JSONObject dataJson = (JSONObject) data.get("data");

        EventBus.getDefault().post( new LoginEvent(dataJson));

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Under Construction", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }
    public void onLoginError() {
        Toast.makeText(getBaseContext(), "Network Error! Please try again later.", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public Map<String,Object> checkReturnRequest(String type,JSONObject result ) throws JSONException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(type =="JSON") {

            JSONObject TransRequest = result.getJSONObject("TransactionRequest");
            if(TransRequest.get("ResponseDescription").equals("Login Success")) {
                resultMap.put("login","true");
                resultMap.put("data",TransRequest);



            } else {
                resultMap.put("login","false");





                return resultMap;
            }




        }

        return resultMap;

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        MyUtilities.hideSoftKey(LoginActivity.this,v);




        return true;



    }


}