package com.mobiapps360.LearnClockTime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class OtherAppsActivity extends AppCompatActivity {
    private ImageView imgViewWallPaper;
    private ImageButton btnOtherAppBack;
    private AdView mAdView;
    AdRequest adRequest;
    private RecyclerView recycleViewOtherApp;
    OtherAppsItem[] otherAppsItemList;
    OtherAppsAdapter otherAppsAdapter;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_apps);
        btnOtherAppBack = findViewById(R.id.btnOtherAppBack);
        recycleViewOtherApp = findViewById(R.id.recycleViewOtherApp);
        mAdView = findViewById(R.id.adViewBannerOtherAppsActivity);

        otherAppsItemList = new OtherAppsItem[]{
//                new OtherAppsItem("gk_quiz", "GK Quiz", "com.mobiapps360."),
                new OtherAppsItem("learn_nature", "Learn Nature", "com.mobiapps360.LearnNature"),
                new OtherAppsItem("learn_food", "Learn Natural Food", "com.mobiapps360.LearnNaturalFood"),
                new OtherAppsItem("learn_house", "Learn House Objects", "com.mobiapps360.learnhouseobjects")
        };
        //--------------------------------------------
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                // Toast.makeText(MainActivity.this,"ad loaded",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                System.out.println("Show error####" + adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        //------------------------------------------
        otherAppsAdapter = new OtherAppsAdapter(this);
        otherAppsAdapter.setListMenuItem(otherAppsItemList);
        recycleViewOtherApp.setAdapter(otherAppsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //recycleViewOtherApp.suppressLayout(true);

//        SnapHelper snapHelper = new PagerSnapHelper();
        recycleViewOtherApp.setLayoutManager(layoutManager);
//        snapHelper.attachToRecyclerView(recycleViewOtherApp);
        recycleViewOtherApp
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int offset = recycleViewOtherApp.computeVerticalScrollOffset();
                        if (offset % recycleViewOtherApp.getWidth() == 0) {
                            int position = offset / recycleViewOtherApp.getWidth();
//                            int idSwipeImg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/swipe", null, null);
                            currentIndex = position;

//                            if (currentIndex == otherAppsItemList.length - 1) {
//                                btnForward.setImageResource(R.drawable.reload);
//                                btnBackward.setVisibility(View.VISIBLE);
//                            } else if (currentIndex == 0) {
//                                btnForward.setImageResource(R.drawable.next_question);
//                                btnBackward.setVisibility(View.INVISIBLE);
//                            } else {
//                                btnForward.setImageResource(R.drawable.next_question);
//                                btnBackward.setVisibility(View.VISIBLE);
//                            }
//                            txtViewStrTimeSetTime.setText(Html.fromHtml(otherAppsItemList[currentIndex].timeString));
                        }
                    }
                });
        //------------------------------------------
        btnOtherAppBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        OtherAppsActivity.super.onBackPressed();
                    }
                }
                return true;
            }
        });


        //------------------------------------------
        btnOtherAppBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        OtherAppsActivity.super.onBackPressed();
                    }
                }
                return true;
            }
        });
        //------------------------------------------
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openAppInPlayStore(String appPackageName) {
        System.out.println("i am groot ");
        Intent xyz = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName));
        startActivity(xyz);
    }
}
