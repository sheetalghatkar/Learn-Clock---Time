
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
import android.sax.Element;
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
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.io.IOException;
import java.sql.SQLOutput;
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
    SetTimeItem[] setTimeItemList;
    boolean sameCellDoneClicked = false;
    int soundCountTwo = 0;
    int soundCountThree = 0;
    String correctSoundStr;
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
        correctSoundStr = "well_done";
        setTimeItemList = new SetTimeItem[]{
                new SetTimeItem(4, 0, 4, 0, "4 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_4", 1, 300, 60, 0),
                new SetTimeItem(6, 0, 6, 0, "6 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_6", 1, 300, 60, 0),
                new SetTimeItem(9, 0, 9, 0, "9 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_9", 1, 300, 60, 0),
                new SetTimeItem(2, 0, 2, 0, "2 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_2", 1, 300, 60, 0),
                new SetTimeItem(5, 0, 5, 0, "5 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_5", 1, 300, 60, 0),
                new SetTimeItem(11, 0, 11, 0, "11 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_11", 1, 300, 60, 0),
                new SetTimeItem(1, 0, 1, 0, "1 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_1", 1, 300, 60, 0),
                new SetTimeItem(8, 0, 8, 0, "8 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_8", 1, 300, 60, 0),
                new SetTimeItem(5, 0, 5, 0, "5 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_5", 1, 300, 60, 0),
                new SetTimeItem(3, 0, 3, 0, "3 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_3", 1, 300, 60, 0),
                new SetTimeItem(12, 0, 12, 0, "12 " + "<b>" + "o'clock" + "</b>", "learndesc_clock_12", 1, 300, 60, 0),
                new SetTimeItem(4, 15, 4, 15, "<b>" + "quarter past" + "</b>" + " 4", "quarter_past", 2, 300, 60, 0),
                new SetTimeItem(5, 45, 6, 15, "<b>" + "quarter to" + "</b>" + " 6", "quarter_to", 2, 300, 60, 0),
                new SetTimeItem(9, 30, 9, 30, "<b>" + "half past" + "</b>" + " 9", "half_past", 2, 300, 60, 0),
                new SetTimeItem(11, 45, 12, 15, "<b>" + "quarter to" + "</b>" + " 12", "quarter_to", 2, 300, 60, 0),
                new SetTimeItem(6, 30, 6, 30, "<b>" + "half past" + "</b>" + " 6", "half_past", 2, 300, 60, 0),
                new SetTimeItem(8, 15, 8, 15, "<b>" + "quarter past" + "</b>" + " 8", "quarter_past", 2, 300, 60, 0),
                new SetTimeItem(1, 45, 2, 15, "<b>" + "quarter to" + "</b>" + " 2", "quarter_to", 2, 300, 60, 0),
                new SetTimeItem(12, 30, 12, 30, "<b>" + "half past" + "</b>" + " 12", "half_past", 2, 300, 60, 0),
                new SetTimeItem(12, 45, 1, 15, "<b>" + "quarter to" + "</b>" + " 1", "quarter_to", 2, 300, 60, 0),
                new SetTimeItem(9, 15, 9, 15, "<b>" + "quarter past" + "</b>" + " 9", "quarter_past", 2, 300, 60, 0),
                new SetTimeItem(5, 30, 5, 30, "<b>" + "half past" + "</b>" + " 5", "half_past", 2, 300, 60, 0),
                new SetTimeItem(3, 45, 4, 15, "<b>" + "quarter to" + "</b>" + " 4", "quarter_to", 2, 300, 60, 0),
                new SetTimeItem(10, 15, 10, 15, "<b>" + "quarter past" + "</b>" + " 10", "quarter_past", 2, 300, 60, 0),
                new SetTimeItem(2, 30, 2, 30, "<b>" + "half past" + "</b>" + " 2", "half_past", 2, 300, 60, 0),
                new SetTimeItem(11, 30, 11, 30, "<b>" + "half past" + "</b>" + " 11", "half_past", 2, 300, 60, 0),
                new SetTimeItem(4, 20, 4, 20, "20 minutes " + "<b>" + "past" + "</b>" + " 4", "minutes_past", 3, 300, 60, 0),
                new SetTimeItem(12, 10, 12, 10, "10 minutes " + "<b>" + "past" + "</b>" + " 12", "minutes_past", 3, 300, 60, 0),
                new SetTimeItem(9, 25, 9, 25, "25 minutes " + "<b>" + "past" + "</b>" + " 9", "minutes_past", 3, 300, 60, 0),
                new SetTimeItem(10, 40, 11, 20, "20 " + "minutes " + "<b>" + "to" + "</b>" + " 11", "minutes_to", 3, 300, 60, 0),
                new SetTimeItem(2, 50, 3, 10, "10 minutes " + "<b>" + "to" + "</b>" + " 3", "minutes_to", 3, 300, 60, 0),
                new SetTimeItem(6, 35, 7, 25, "25 minutes " + "<b>" + "to" + "</b>" + " 7", "minutes_to", 3, 300, 60, 0),
                new SetTimeItem(5, 5, 5, 5, "5 minutes " + "<b>" + "past" + "</b>" + " 5", "minutes_past", 3, 300, 60, 0),
                new SetTimeItem(7, 10, 7, 10, "10 minutes " + "<b>" + "past" + "</b>" + " 7", "minutes_past", 3, 300, 60, 0),
                new SetTimeItem(2, 35, 3, 25, "25 minutes " + "<b>" + "to" + "</b>" + " 3", "minutes_to", 3, 300, 60, 0),
                new SetTimeItem(8, 55, 9, 5, "5 minutes " + "<b>" + "to" + "</b>" + " 9", "minutes_to", 3, 300, 60, 0),
                new SetTimeItem(5, 10, 5, 10, "10 minutes " + "<b>" + "past" + "</b>" + " 5", "minutes_past", 3, 300, 60, 0),
                new SetTimeItem(10, 35, 11, 25, "25 minutes " + "<b>" + "to" + "</b>" + " 11", "minutes_to", 3, 300, 60, 0),
                new SetTimeItem(11, 5, 11, 5, "5 minutes " + "<b>" + "past" + "</b>" + " 11", "minutes_past", 3, 300, 60, 0),
                new SetTimeItem(1, 40, 2, 20, "20 minutes " + "<b>" + "to" + "</b>" + " 2", "minutes_to", 3, 300, 60, 0),
                new SetTimeItem(4, 25, 4, 25, "25 minutes " + "<b>" + "past" + "</b>" + " 4", "minutes_past", 3, 300, 60, 0),
        };
        txtViewStrTimeSetTime.setText(Html.fromHtml(setTimeItemList[currentIndex].timeString));
        if (getSoundFlag == true) {
            playSoundSetTime(setTimeItemList[currentIndex].soundCount);
        }
        mAdView = findViewById(R.id.adViewBannerSetTimeActivity);
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


        setTimeAdapter = new SetTimeAdapter(this);
        setTimeAdapter.setListMenuItem(setTimeItemList);
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

                            if (currentIndex == setTimeItemList.length - 1) {
                                btnForward.setImageResource(R.drawable.reload);
                                btnBackward.setVisibility(View.VISIBLE);
                            } else if (currentIndex == 0) {
                                btnForward.setImageResource(R.drawable.next_question);
                                btnBackward.setVisibility(View.INVISIBLE);
                            } else {
                                btnForward.setImageResource(R.drawable.next_question);
                                btnBackward.setVisibility(View.VISIBLE);
                            }
                            txtViewStrTimeSetTime.setText(Html.fromHtml(setTimeItemList[currentIndex].timeString));
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
                        setTimeAdapter.notifyDataSetChanged();
                        if (currentIndex == setTimeItemList.length - 1) {
                            btnForward.setImageResource(R.drawable.next_question);
                            if (player != null) {
                                player.release();
                            }
                            recycleViewSetTime.getLayoutManager().smoothScrollToPosition(recycleViewSetTime, new RecyclerView.State(), 0);
                        } else {
                            recycleViewSetTime.getLayoutManager().smoothScrollToPosition(recycleViewSetTime, new RecyclerView.State(), currentIndex + 1);
                            increaseCount_playSound();
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
                        setTimeAdapter.notifyDataSetChanged();
                        if (currentIndex == 0) {
                            btnBackward.setVisibility(View.INVISIBLE);
                        } else {
                            btnBackward.setVisibility(View.VISIBLE);
                            recycleViewSetTime.getLayoutManager().smoothScrollToPosition(recycleViewSetTime, new RecyclerView.State(), currentIndex - 1);
                        }
                        increaseCount_playSound();
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

//                        System.out.println("getUpdatedHour#####:" + getUpdatedHour.getText());
//                        System.out.println("getUpdatedMinute#####:" + getUpdatedMinute.getText());
//                        SetTimeAdapter.ViewHolder holder = recycleViewSetTime.findViewHolderForAdapterPosition(currentIndex);
                        int set_hour_angle = (Integer.parseInt(getUpdatedHour.getText().toString()));
                        int set_minute_angle = (Integer.parseInt(getUpdatedMinute.getText().toString()));
                        if (set_hour_angle == 360) {
                            set_hour_angle = 0;
                        }
                        if (set_minute_angle == 360) {
                            set_minute_angle = 0;
                        }
                        //-------------------------------------------
                        int calculate_minute_angle = (setTimeItemList[currentIndex].getMinutes()) * 6;
                        int calculat_hour_angle = (setTimeItemList[currentIndex].getHour()) * 30;
                        if (calculat_hour_angle == 360) {
                            calculat_hour_angle = 0;
                        }
                        calculat_hour_angle = calculat_hour_angle + (calculate_minute_angle) / 12;

                        //-------------------------------------------
//                        System.out.println("set_hour_angle#####:" + set_hour_angle);
//                        System.out.println("set_minute_angle#####:" + set_minute_angle);
//                        System.out.println("calculat_hour_angle#####:" + calculat_hour_angle);
//                        System.out.println("calculate_minute_angle#####:" + calculate_minute_angle);
                        //-------------------------------------------
                        List<SetTimeItem> tempArraylist = Arrays.asList(setTimeItemList);
                        SetTimeItem tempSetTimeItem = tempArraylist.get(currentIndex);//Get current object from original list
                        tempSetTimeItem.setHourHand = (Integer.parseInt(getUpdatedHour.getText().toString()));
                        tempSetTimeItem.setMinuteHand = Integer.parseInt(getUpdatedMinute.getText().toString());
                        //-------------------------------------------
                        if ((set_hour_angle == calculat_hour_angle) && (set_minute_angle == calculate_minute_angle)) {
                            GradientDrawable gradientDrawable = (GradientDrawable) cardViewDoneButton.getBackground();
                            gradientDrawable.setStroke(4, Color.parseColor("#006400"));
                            gradientDrawable.setColor(getResources().getColor(R.color.green_done));
                            tempSetTimeItem.result = 1;
                            if (!sameCellDoneClicked) {
                                if (correctSoundStr == "well_done") {
                                    correctSoundStr = "perfect";
                                } else if (correctSoundStr == "perfect") {
                                    correctSoundStr = "excellent";
                                } else if (correctSoundStr == "excellent") {
                                    correctSoundStr = "great_job";
                                } else if (correctSoundStr == "great_job") {
                                    correctSoundStr = "well_done";
                                }
                            }
                            playResultSound(correctSoundStr);
                        } else {
                            GradientDrawable gradientDrawable = (GradientDrawable) cardViewDoneButton.getBackground();
                            gradientDrawable.setStroke(4, Color.parseColor("#8B0000"));
                            gradientDrawable.setColor(getResources().getColor(R.color.red_done));
                            tempSetTimeItem.result = 2;
                            playResultSound("wrong_beep");
                        }
                        //-------------------------------------------
                        tempArraylist.set(currentIndex, tempSetTimeItem);
                        setTimeItemList = tempArraylist.toArray(setTimeItemList);
//                        System.out.println("---Current list object hour***" + setTimeItemList[currentIndex].setHourHand);
//                        System.out.println("---Current list object minute***" + setTimeItemList[currentIndex].setMinuteHand);
                        sameCellDoneClicked = true;
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
                        if (getSoundFlag) {
                            playSoundSetTime(setTimeItemList[currentIndex].soundCount);
                        } else {
                            ((ImageButton) v).setAlpha((float) 1.0);
                        }
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
       // System.out.println("--onBackPressed--");
        if (player != null) {
            player.release();
        }
        super.onBackPressed();
    }

    public void increaseCount_playSound() {
        clickCount = clickCount + 1;

        System.out.println("---clickCount****" + clickCount);
        if (clickCount > adShowCount) {
            clickCount = 0;
            if (player != null) {
                player.release();
            }
            showInterstitialAds(false);
        } else if (getSoundFlag == true) {
            if (player != null) {
                player.release();
            }
            playSoundSetTime(setTimeItemList[currentIndex].soundCount);
        }
        GradientDrawable gradientDrawable = (GradientDrawable) cardViewDoneButton.getBackground();
        if (setTimeItemList[currentIndex].result == 0) {
            gradientDrawable.setStroke(4, Color.parseColor("#1e90ff"));
            gradientDrawable.setColor(getResources().getColor(R.color.offwhite_done));
        } else if (setTimeItemList[currentIndex].result == 1) {
            gradientDrawable.setStroke(4, Color.parseColor("#006400"));
            gradientDrawable.setColor(getResources().getColor(R.color.green_done));
        } else {
            gradientDrawable.setStroke(4, Color.parseColor("#8B0000"));
            gradientDrawable.setColor(getResources().getColor(R.color.red_done));
        }
        sameCellDoneClicked = false;
    }

//    public static int pxToDp(int px) {
//        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
//    }
//
//    public static int dpToPx(int dp) {
//        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
//    }
//
//    public void screenTapped(View view) {
//        // Your code here
//    }

    public void playSoundSetTime(int soundCount) {
        int setSound = 0;
        btnSetTimeSound.setClickable(false);
        btnSetTimeSound.setAlpha((float) 0.5);
        if (setTimeItemList[currentIndex].getMinutes() == 0) {
            setSound = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "set_1", null, null);
        } else {
            setSound = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "set", null, null);
        }
        player = MediaPlayer.create(getBaseContext(), setSound);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.release();
                if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
                    int idSoundBg = 0;
                    if ((soundCount == 1) || (soundCount == 2)) {
                        soundCountTwo = 1;
                        idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + setTimeItemList[currentIndex].getSoundString(), null, null);
                    } else {
                        idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(setTimeItemList[currentIndex].getMinutesSound()), null, null);
                    }
                    try {
                        player = MediaPlayer.create(getBaseContext(), idSoundBg);
                    } catch (Exception e) {
                        //  System.out.println("Medi player exception:--" + e);
                        Log.e("Music Exception", "catch button click sound play");
                    }
                }
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                    }
                });
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        player.release();
                        btnSetTimeSound.setAlpha((float) 1.0);
                        btnSetTimeSound.setClickable(true);
                        if (soundCount == 2) {
                            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(setTimeItemList[currentIndex].getHourSound()), null, null);
                            player = MediaPlayer.create(getBaseContext(), idSoundBg);
                            player.start();
                        } else if (soundCount == 3) {
                            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + setTimeItemList[currentIndex].getSoundString(), null, null);
                            player = MediaPlayer.create(getBaseContext(), idSoundBg);
                            player.start();

                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    player.release();
                                    int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(setTimeItemList[currentIndex].getHourSound()), null, null);
                                    player = MediaPlayer.create(getBaseContext(), idSoundBg);
                                    player.start();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void playResultSound(String soundResultStr) {
        if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + soundResultStr, null, null);
            try {
                player = MediaPlayer.create(getBaseContext(), idSoundBg);
            } catch (Exception e) {
                // System.out.println("Medi player exception:--" + e);
                Log.e("Music Exception", "catch button click sound play");
            }
        }
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.release();
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
//                        if (fromHome) {
//                            Log.i("playCrad", "The ad was dismissed---if");
//                            SetTimeActivity.super.onBackPressed();
//                            showHideLoader(false);
//                        } else {
                        Log.i("playCrad", "The ad was dismissed-----else.");
                        showHideLoader(false);
                        if (getSoundFlag == true) {
                            if (player != null) {
                                player.release();
                            }
                            playSoundSetTime(setTimeItemList[currentIndex].soundCount);
                        }
                        //   }
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
