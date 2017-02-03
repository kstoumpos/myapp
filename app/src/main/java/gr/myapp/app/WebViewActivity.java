package gr.myapp.app;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

public class WebViewActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        Bundle extras = getIntent().getExtras();

        HashMap<String, Object> data = (HashMap <String, Object>) extras.getSerializable("data");
        HashMap<String, Object> infoData = (HashMap <String, Object>) extras.getSerializable("infodata");

        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();

        myWebView.setWebViewClient(new WebViewClient());

        myWebView.loadUrl(String.valueOf(data.get("url")));


        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        ImageView backBtn = (ImageView) findViewById(R.id.action_back_from_site);

        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(event.getAction() == MotionEvent.ACTION_UP){

                    WebViewActivity.this.finish();

                }



                return false;
            }
        });

        ChildMerchant childMerchantArraylst = (ChildMerchant) infoData.get("children");



        ImageView btn1 = (ImageView) findViewById(R.id.action_merchant_info);
        btn1.setOnTouchListener(new ViewListeners(this.getApplicationContext()). new MerchantCategoryButton(childMerchantArraylst));


        final ProgressBar prBar = (ProgressBar) findViewById(R.id.progrees_webview);

        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                WebViewActivity.this.setProgress(progress * 100);

                if(progress == 100){
                    prBar.setVisibility(View.GONE);
                }
            }
        });

        myWebView.canGoBackOrForward(3);


    }
}
