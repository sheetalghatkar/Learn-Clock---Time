
package com.mobiapps360.LearnClockTime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.util.Range;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bumptech.glide.Glide;
import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SetTimeActivity extends AppCompatActivity {
    // on below line we are creating variable
    // for our array list and swipe deck.
    private ImageButton btnSoundSetTimeOnOff;
    private ImageButton btnSetTimeBack;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnSetTimeSound;
    Boolean getSoundFlag = true;
    private ImageView imgViewWallPaper;
    TextView txtViewStrTimeSetTime;
    private AdView mAdView;
    ImageView imgVwSetLoader;
    View viewLearnLoader;
    AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    MediaPlayer player;
    CardView cardViewDoneButton;
    private RecyclerView recycleViewSetTime;
    SetTimeAdapter setTimeAdapter;
    ImageButton buttonSetTimeDone;
    //Declare variables
    int currentIndex = 0;
    int clickCount = 1;
    int adShowCount = 13;


    int soundCountTwo = 0;
    int soundCountThree = 0;

    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundLearnActivity = "soundLearnActivityKey";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        btnSoundSetTimeOnOff = findViewById(R.id.btnSoundSetTimeOnOff);
        btnSetTimeBack = findViewById(R.id.btnSetTimeBack);
        imgViewWallPaper = findViewById(R.id.setTimeWallImage);
        imgVwSetLoader = findViewById(R.id.imgVwSetTimeLoader);
        viewLearnLoader = findViewById(R.id.viewLoaderSetTimeBg);
        recycleViewSetTime = findViewById(R.id.recycleViewSetTime);
        btnForward = findViewById(R.id.btnSetTimeForward);
        btnBackward = findViewById(R.id.btnSetTimeBackward);
        btnSetTimeSound = findViewById(R.id.btnSetTimeSound);
        txtViewStrTimeSetTime = findViewById(R.id.txtViewStrTimeSetTime);
        buttonSetTimeDone = findViewById(R.id.buttonSetTimeDone);
        cardViewDoneButton = findViewById(R.id.cardViewDoneButton);
        cardViewDoneButton.setBackgroundResource(R.drawable.card_view_border);
        GradientDrawable gradientDrawable = (GradientDrawable) cardViewDoneButton.getBackground();
        gradientDrawable.setStroke(4, Color.parseColor("#1e90ff"));
        gradientDrawable.setColor(getResources().getColor(R.color.offwhite_done));


        Glide.with(this).load(R.drawable.loader).into(imgVwSetLoader);
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(soundLearnActivity)) {
            getSoundFlag = sharedPreferences.getBoolean(soundLearnActivity, false);
            if (getSoundFlag == true) {
                btnSoundSetTimeOnOff.setImageResource(R.mipmap.sound_on);
            } else {
                btnSoundSetTimeOnOff.setImageResource(R.mipmap.sound_off);
            }
        } else {
            editor.putBoolean(soundLearnActivity, true);
            editor.commit();
            btnSoundSetTimeOnOff.setImageResource(R.mipmap.sound_on);
        }

        txtViewStrTimeSetTime.setText(Html.fromHtml(Constant.setTimeItemList[currentIndex].timeString));
        playSoundSetTime(Constant.setTimeItemList[currentIndex].soundCount);

       /* mAdView = findViewById(R.id.adViewBannerSetTimeActivity);
//        adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

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
        });*/


        setTimeAdapter = new SetTimeAdapter(this);
        setTimeAdapter.setListMenuItem(Constant.setTimeItemList);
        recycleViewSetTime.setAdapter(setTimeAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycleViewSetTime.suppressLayout(true);

        SnapHelper snapHelper = new PagerSnapHelper();
        recycleViewSetTime.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recycleViewSetTime);
        recycleViewSetTime
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int offset = recycleViewSetTime.computeHorizontalScrollOffset();
                        if (offset % recycleViewSetTime.getWidth() == 0) {
                            int position = offset / recycleViewSetTime.getWidth();
//                            int idSwipeImg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/swipe", null, null);
                            currentIndex = position;

                            if (currentIndex == Constant.setTimeItemList.length - 1) {
                                btnForward.setImageResource(R.drawable.reload);
                                btnBackward.setVisibility(View.VISIBLE);
                            } else if (currentIndex == 0) {
                                btnForward.setImageResource(R.drawable.next_question);
                                btnBackward.setVisibility(View.INVISIBLE);
                            } else {
                                btnForward.setImageResource(R.drawable.next_question);
                                btnBackward.setVisibility(View.VISIBLE);
                            }
                            txtViewStrTimeSetTime.setText(Html.fromHtml(Constant.setTimeItemList[currentIndex].timeString));

//                            System.out.println("I m here" + clickCount);
                            clickCount = clickCount + 1;
//                            if (clickCount > adShowCount) {
//                                clickCount = 0;
//                                if (player != null) {
//                                    player.release();
//                                }
//                                //  showInterstitialAds(false);
//                            } else
                            if (getSoundFlag == true) {
                                if (player != null) {
                                    player.release();
                                }
                                playSoundSetTime(Constant.setTimeItemList[currentIndex].soundCount);
                            }
                            GradientDrawable gradientDrawable = (GradientDrawable) cardViewDoneButton.getBackground();
                            gradientDrawable.setStroke(4, Color.parseColor("#1e90ff"));
                            gradientDrawable.setColor(getResources().getColor(R.color.offwhite_done));
                        }
                    }
                });
        //------------------------------------------
        btnSetTimeBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        if (player != null) {
                            player.release();
                        }
                        SetTimeActivity.super.onBackPressed();
                    }
                }
                return true;
            }
        });

        btnSoundSetTimeOnOff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);

                        btnSetTimeBack.setVisibility(View.VISIBLE);
                        if (sharedPreferences.contains(soundLearnActivity)) {
                            getSoundFlag = sharedPreferences.getBoolean(soundLearnActivity, false);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            getSoundFlag = !getSoundFlag;
                            editor.putBoolean(soundLearnActivity, getSoundFlag);
                            editor.commit();
                            if (getSoundFlag == true) {
                                btnSoundSetTimeOnOff.setImageResource(R.mipmap.sound_on);
                            } else {
                                btnSoundSetTimeOnOff.setImageResource(R.mipmap.sound_off);
                            }
                        }
                    }
                }
                return true;
            }
        });
        btnForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        btnBackward.setVisibility(View.VISIBLE);
                        if (currentIndex == Constant.setTimeItemList.length - 1) {
                            btnForward.setImageResource(R.drawable.next_question);
                            recycleViewSetTime.getLayoutManager().smoothScrollToPosition(recycleViewSetTime, new RecyclerView.State(), 0);
                        } else {
                            recycleViewSetTime.getLayoutManager().smoothScrollToPosition(recycleViewSetTime, new RecyclerView.State(), currentIndex + 1);
                        }
                    }
                }
                return true;
            }
        });
        btnBackward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        if (currentIndex == 0) {
                            btnBackward.setVisibility(View.INVISIBLE);
                        } else {
                            btnBackward.setVisibility(View.VISIBLE);
                            recycleViewSetTime.getLayoutManager().smoothScrollToPosition(recycleViewSetTime, new RecyclerView.State(), currentIndex - 1);
                        }
                    }
                }
                return true;
            }
        });
        buttonSetTimeDone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                       // View getCurrentRecycleView = recycleViewSetTime.findViewHolderForItemId(setTimeAdapter.getItemId(currentIndex)).itemView;

                        View getCurrentRecycleView = recycleViewSetTime.findViewHolderForAdapterPosition(currentIndex).itemView;
                        //-------------------------------------------
                        //Get hand angles set by user.
                        TextView getUpdatedHour = getCurrentRecycleView.findViewById(R.id.txtHourHide);
                        TextView getUpdatedMinute = getCurrentRecycleView.findViewById(R.id.txtMinuteHide);
//                        SetTimeAdapter.ViewHolder holder = recycleViewSetTime.findViewHolderForAdapterPosition(currentIndex);
                        int set_hour_angle = (Integer.parseInt(getUpdatedHour.getText().toString()));
                        int set_minute_angle = Integer.parseInt(getUpdatedMinute.getText().toString());
                        if (set_minute_angle == 360) {
                            set_minute_angle = 0;
                        }
                        //-------------------------------------------
                        int calculate_minute_angle = (Constant.setTimeItemList[currentIndex].getMinutes()) * 6;
                        int calculat_hour_angle = (Constant.setTimeItemList[currentIndex].getHour()) * 30;
                        calculat_hour_angle = calculat_hour_angle + (calculate_minute_angle) / 12;

                        //-------------------------------------------
                        System.out.println("set_hour_angle#####:"+set_hour_angle);
                        System.out.println("set_minute_angle#####:"+set_minute_angle);
                        System.out.println("calculat_hour_angle#####:"+calculat_hour_angle);
                        System.out.println("calculate_minute_angle#####:"+calculate_minute_angle);
                        //-------------------------------------------
                        if ((set_hour_angle == calculat_hour_angle) && (set_minute_angle == calculate_minute_angle)) {
                            GradientDrawable gradientDrawable = (GradientDrawable) cardViewDoneButton.getBackground();
                            gradientDrawable.setStroke(4, Color.parseColor("#006400"));
                            gradientDrawable.setColor(getResources().getColor(R.color.green_done));
//                            ColorDrawable colorDrawable = (ColorDrawable) cardViewDoneButton.getBackground();
//                            colorDrawable.setColor(Color.parseColor("#3DFF33"));

                            // gradientDrawable.setStroke(4, Color.parseColor("#1e90ff"));
                          //  gradientDrawable.setColor(Color.parseColor("#3DFF33"));

                          //  cardViewDoneButton.setCardBackgroundColor(Color.parseColor("#3DFF33"));
                        } else {
                            GradientDrawable gradientDrawable = (GradientDrawable) cardViewDoneButton.getBackground();
                            gradientDrawable.setStroke(4, Color.parseColor("#8B0000"));
                            gradientDrawable.setColor(getResources().getColor(R.color.red_done));
//                            gradientDrawable.setColor(Color.parseColor("#D2042D"));
//                            ColorDrawable colorDrawable = (ColorDrawable) cardViewDoneButton.getBackground();
//
//                            colorDrawable.setColor(ContextCompat.getColor(getBaseContext(),R.id.));

//                            ColorDrawable colorDrawable = (ColorDrawable) cardViewDoneButton.getBackground();
//                            colorDrawable.setColor(Color.parseColor("#D2042D"));
//                            gradientDrawable.setColor(Color.parseColor("#D2042D"));
                           // gradientDrawable.setStroke(4, Color.parseColor("#1e90ff"));
                          //  img.setCardBackgroundColor(Color.parseColor("#D2042D"));
                        }
                    }
                }
                return true;
            }
        });

        btnSetTimeSound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        playSoundSetTime(Constant.setTimeItemList[currentIndex].soundCount);
                    }
                }
                return true;
            }
        });

    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void screenTapped(View view) {
        // Your code here
    }

    public void playSoundSetTime(int soundCount) {
        if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
            int idSoundBg = 0;
            if ((soundCount == 1) || (soundCount == 2)) {
                soundCountTwo = 1;
                idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + Constant.setTimeItemList[currentIndex].getSoundString(), null, null);
            } else {
                idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(Constant.setTimeItemList[currentIndex].getMinutesSound()), null, null);
            }
            try {
                player = MediaPlayer.create(getBaseContext(), idSoundBg);
            } catch (Exception e) {
                System.out.println("Medi player exception:--" + e);
                Log.e("Music Exception", "catch button click sound play");
            }
        }
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });


//        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                player.release();
//            }
//        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                player.release();
                if (soundCount == 2) {
//                    if (soundCountTwo == 1) {
//                        soundCountTwo = 0;
                        int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(Constant.setTimeItemList[currentIndex].getHourSound()), null, null);
                        player = MediaPlayer.create(getBaseContext(), idSoundBg);
                        player.start();
                   // }
                } else if (soundCount == 3) {
                    int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + Constant.setTimeItemList[currentIndex].getSoundString(), null, null);
                    player = MediaPlayer.create(getBaseContext(), idSoundBg);
                    player.start();

                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            player.release();
                            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(Constant.setTimeItemList[currentIndex].getHourSound()), null, null);
                            player = MediaPlayer.create(getBaseContext(), idSoundBg);
                            player.start();
                        }
                    });
                }
            }
        });
    }

    //Show interstitial Ads
    public void showHideLoader(boolean adFlag) {
        if (adFlag) {
            imgVwSetLoader.setVisibility(View.VISIBLE);
            viewLearnLoader.setVisibility(View.VISIBLE);
        } else {
            imgVwSetLoader.setVisibility(View.INVISIBLE);
            viewLearnLoader.setVisibility(View.INVISIBLE);
        }
    }

    public void showInterstitialAds(Boolean fromHome) {
        System.out.println("Inside showInterstitialAds---");
        showHideLoader(true);
        InterstitialAd.load(this, Constant.INTERSTITIAL_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(SetTimeActivity.this);

                // Log.i(TAG, "onAdLoaded");
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.i("TAG", "The ad was dismissed.");
                        if (fromHome) {
                            Log.i("playCrad", "The ad was dismissed---if");
                            SetTimeActivity.super.onBackPressed();
                            showHideLoader(false);
                        } else {
                            Log.i("playCrad", "The ad was dismissed-----else.");
                            showHideLoader(false);
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        showHideLoader(false);
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        //showHideLoader(false);
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        // Log.d("TAG", "The ad was shown.");
                    }
                });

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                showHideLoader(false);
                mInterstitialAd = null;
            }
        });
    }
}
