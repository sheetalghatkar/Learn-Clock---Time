package com.mobiapps360.LearnClockTime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobiapps360.LearnClockTime.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private WebView webViewPrivacy ;
    private TextView txtTitlePrivacyPolicy;
    private ImageView privacyWallImage;
    private  ImageButton btnImgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        webViewPrivacy  = findViewById(R.id.webViewPrivacy);
        txtTitlePrivacyPolicy = findViewById(R.id.txtTitlePrivacyPolicy);
        privacyWallImage = findViewById(R.id.privacyWallImage);
        txtTitlePrivacyPolicy.setText("Privacy Policy");
        btnImgHome = findViewById(R.id.btnHomePrivacyPolicy);

        privacyWallImage.setImageResource(R.mipmap.homebg);


        webViewPrivacy.getSettings().setJavaScriptEnabled(true);
        webViewPrivacy .loadUrl("https://mobiapps360.com/privacy");

        btnImgHome.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        ((ImageButton)v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        ((ImageButton)v).setAlpha((float) 1.0);
                        PrivacyPolicyActivity.super.onBackPressed();
                    }
                }
                return true;
            }

        });


//        webViewPrivacy.getSettings().setJavaScriptEnabled(true); // enable javascript
//        webViewPrivacy.setWebViewClient(new WebViewClient() {
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//               // Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
//            }
//            @TargetApi(android.os.Build.VERSION_CODES.M)
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
//                // Redirect to deprecated method, so you can use it in all SDK versions
//                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
//            }
//        });
//
//        webViewPrivacy .loadUrl("https://mobiapps360.com/");
//        setContentView(webViewPrivacy );

    }
    public void onBackPressed() {
        PrivacyPolicyActivity.super.onBackPressed();
    }
}