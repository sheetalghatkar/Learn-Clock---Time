
package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Range;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class PlayClockActivity extends AppCompatActivity implements NumberPicker.OnScrollListener, NumberPicker.OnValueChangeListener {
    // on below line we are creating variable
    // for our array list and swipe deck.
    private ImageButton btnSoundSetTimeOnOff;
    private ImageButton btnSetTimeBack;
    private ImageButton btnPlayTimeSound;
    Boolean getSoundFlag = true;
    private ImageView imgViewWallPaper;
    int clickCount = 1;
    int adShowCount = 13;
    int digitalHour;
    int digitalMinute;
    int digitalHourSelected;
    int digitalMinuteSelected;
    int prevDigMinuteSelected;
    private AdView mAdView;
    ImageView imgVwSetLoader;
    View viewLearnLoader;
    AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    int hourArrayLength = 0;
    int tempMinAngle;
    CardView card_hour_hand;
    CardView card_minute_hand;
    boolean isTouchOnAnalogClock = false;
    int[] hourArray;
    double clockAngle;
    boolean isTouchHourHand; // to move only Hour hand
    boolean isTouchMinuteHand; // to move only Minute hand
    boolean hourScrollIdle = false;
    boolean minuteScrollIdle = false;
    boolean isTouchHourWhileMoving = false; // to Check hour hand touch while moving
    boolean isTouchMinuteWhileMoving = false; //  to Check minute hand touch while moving
    String[] minuteDigitArray;
    TextView playClockTimeText;
    double centreX = 0.0;
    double centreY = 0.0;
    List<Integer> minuteArrayList;
    int[] minuteArray;
    String[] hourDigitArray;
    ImageView imgVwSetTimeHourHand;
    ImageView imgVwSetTimeMinuteHand;
    int newHourAngle;
    int newMinuteAngle;
    int soundCountTwo = 0;
    //Declare variables
    ImageView imgVwClockDial;
    MediaPlayer player;
    NumberPicker hourPicker;
    NumberPicker minutePicker;
    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundLearnActivity = "soundLearnActivityKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_clock);
        btnSoundSetTimeOnOff = findViewById(R.id.btnSoundPlayClockOnOff);
        card_hour_hand = findViewById(R.id.card_play_clock_hour_hand);
        card_minute_hand = findViewById(R.id.card_play_clock_minute_hand);
        btnSetTimeBack = findViewById(R.id.btnPlayClockBack);
        imgViewWallPaper = findViewById(R.id.playClockWallImage);
        imgVwSetLoader = findViewById(R.id.imgVwPlayClockLoader);
        viewLearnLoader = findViewById(R.id.viewLoaderPlayClockBg);
        imgVwClockDial = findViewById(R.id.imgVwPlayClockClockDial);
        imgVwSetTimeHourHand = findViewById(R.id.imgVwPlayClockHourHand);
        imgVwSetTimeMinuteHand = findViewById(R.id.imgVwPlayClockMinuteHand);
        playClockTimeText = findViewById(R.id.playClockTimeText);
        btnPlayTimeSound = findViewById(R.id.btnPlayTimeSound);
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
        isTouchHourHand = true;
        isTouchMinuteHand = true;

        // on below line we are initializing our array list and swipe deck.
        centreY = 481;     // imgVwClockDial.getHeight()/2;
        centreX = 481;    //   imgVwClockDial.getWidth()/2;
        imgVwClockDial.setOnTouchListener(handleTouch);
        hourArray = new int[]{0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360};

        minuteArrayList = new ArrayList<Integer>();
        hourArrayLength = hourArray.length;
        int iCount = 0;
        do {
            minuteArrayList.add(iCount);
            iCount = iCount + 6;
        } while (iCount < 366);
        hourDigitArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        minuteDigitArray = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};


//        minuteArray = minuteArrayList.toArray(new Integer[0]);

        minuteArray = new int[minuteArrayList.size()];

        for (int i = 0; i < minuteArray.length; i++) {
            minuteArray[i] = minuteArrayList.get(i);
            //     System.out.println("---minuteArray---" + minuteArray[i]);
        }


        tempMinAngle = 60;
        newHourAngle = 300;
        newMinuteAngle = 60;
        card_hour_hand.setRotation((float) newHourAngle);
        card_minute_hand.setRotation((float) newMinuteAngle);
        imgVwSetTimeMinuteHand.setAlpha(0.7f);
        imgVwSetTimeHourHand.setAlpha(0.7f);

//        System.out.println("Hour array&&&&&:"+ nearest_small_value(60));
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
        //-----------------------------------------

        hourPicker = findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(hourDigitArray.length - 1);
        hourPicker.setMinValue(0);
        hourPicker.setWrapSelectorWheel(true);
        hourPicker.setFocusable(false);
        hourPicker.setFocusableInTouchMode(false);
        hourPicker.setOnValueChangedListener(this);
        hourPicker.setOnScrollListener(this);
        hourPicker.setDisplayedValues(hourDigitArray);
        hourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //-----------------------------------------

        minutePicker = findViewById(R.id.minutePicker);
        minutePicker.setMaxValue(minuteDigitArray.length - 1);
        minutePicker.setOnScrollListener(this);
        minutePicker.setMinValue(0);
        minutePicker.setFocusable(false);
        minutePicker.setFocusableInTouchMode(false);
        minutePicker.setWrapSelectorWheel(true);
        minutePicker.setDisplayedValues(minuteDigitArray);
        minutePicker.setOnValueChangedListener(this);
        minutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //-----------------------------------------
        digitalHourSelected = Integer.parseInt(hourDigitArray[9]);
        digitalMinuteSelected = Integer.parseInt(minuteDigitArray[10]);
        prevDigMinuteSelected = digitalMinuteSelected;
        hourPicker.setValue(9);
        minutePicker.setValue(10);
        setTimeText();
        //-----------------------------------------

        imgVwSetTimeHourHand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        System.out.println("Inside hour hand ACTION_DOWN");
                        isTouchHourWhileMoving = true;
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        System.out.println("Inside hour hand ACTION_UP");
                        isTouchHourHand = false;
                        isTouchHourWhileMoving = false;
                        imgVwSetTimeHourHand.setAlpha(1.0f);
                        imgVwSetTimeMinuteHand.setAlpha(0.7f);
                        break;
                    }
                }
                return isTouchHourHand;
            }
        });

        //-----------------------------------------
        imgVwSetTimeMinuteHand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("imgVwSetTimeMinuteHand touch****");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        isTouchMinuteWhileMoving = true;
                        System.out.println("Inside minute hand ACTION_DOWN");
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        System.out.println("Inside minute hand ACTION_UP");
                        isTouchMinuteHand = false;
                        isTouchMinuteWhileMoving = false;
                        imgVwSetTimeHourHand.setAlpha(0.7f);
                        imgVwSetTimeMinuteHand.setAlpha(1.0f);
                        break;
                    }
                }
                return isTouchMinuteHand;
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
                        PlayClockActivity.super.onBackPressed();
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

        btnPlayTimeSound.setOnTouchListener(new View.OnTouchListener() {
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
                        if (getSoundFlag) {
                            if (digitalMinuteSelected == 0) {
                                playSoundSetTime(1, "oclock", 0, digitalHourSelected);
                            } else if (digitalMinuteSelected == 15) {
                                playSoundSetTime(2, "quarter_past", 0, digitalHourSelected);
                            } else if (digitalMinuteSelected == 30) {
                                playSoundSetTime(2, "half_past", 0, digitalHourSelected);
                            } else if (digitalMinuteSelected == 45) {
                                if (digitalHourSelected != 12) {
                                    playSoundSetTime(2, "quarter_to", 0, (digitalHourSelected + 1));
                                } else {
                                    playSoundSetTime(2, "quarter_to", 0, 1);
                                }
                            } else {
                                if (digitalMinuteSelected < 30) {
                                    if (digitalMinuteSelected == 1) {
                                        playSoundSetTime(3, "minute_past", digitalMinuteSelected, digitalHourSelected);
                                    } else {
                                        playSoundSetTime(3, "minutes_past", digitalMinuteSelected, digitalHourSelected);
                                    }
                                } else {
                                    String strMin = "minutes_to";
                                    if ((60 - digitalMinuteSelected) == 1) {
                                        strMin = "minute_to";
                                    }
                                    if (digitalHourSelected != 12) {
                                        playSoundSetTime(3, strMin, (60 - digitalMinuteSelected), (digitalHourSelected + 1));
                                    } else {
                                        playSoundSetTime(3, strMin, (60 - digitalMinuteSelected), 1);
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
        });
    }

    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        System.out.println("oldVal---------" + oldVal);
        System.out.println("newVal---------" + newVal);
        if (picker == hourPicker) {
//            if (hourScrollIdle) {
//                hourScrollIdle = false;
//                if (Integer.parseInt(hourDigitArray[newVal]) != digitalHourSelected) {
//                    digitalHourSelected = Integer.parseInt(hourDigitArray[newVal]);
//                    setTimeText();
//                }
//            }
//            System.out.println("digitalHourSelected" + digitalHourSelected);
//            System.out.println("**HourPicker onValueChange**" + hourDigitArray[newVal]);
        } else {
            // System.out.println("val---");
//            if (minuteScrollIdle) {
//              //  System.out.println("**MinutePicker onValueChange**" + minuteDigitArray[newVal]);
//                minutePicker.setValue(newVal);
//                minuteScrollIdle = false;
//                digitalMinuteSelected = Integer.parseInt(minuteDigitArray[newVal]);
//              //  if (Integer.parseInt(minuteDigitArray[newVal]) != digitalMinuteSelected) {
////                    digitalMinuteSelected = Integer.parseInt(minuteDigitArray[newVal]);
//                    if (digitalMinuteSelected == 0) {
//                        System.out.println("digitalMinuteSelected***"+digitalMinuteSelected);
//                        tempMinAngle = 360;
//                        if (prevDigMinuteSelected>30) {
//                            System.out.println("oldVal greater than new" + newVal);
//                            if (digitalHourSelected == 12) {
//                                digitalHourSelected = 1;
//                                hourPicker.setValue(0);
//                            } else {
//                              //  System.out.println("else------&&&&" + digitalHourSelected);
//                                digitalHourSelected = Integer.parseInt(hourDigitArray[digitalHourSelected]);
//                              //  System.out.println("else--then----&&&&" + digitalHourSelected);
//                                hourPicker.setValue(digitalHourSelected);
//                            }
//                        }
//                    } else {
//                        if ((digitalMinuteSelected < prevDigMinuteSelected) && (prevDigMinuteSelected - digitalMinuteSelected)> 30) {
//                            System.out.println("1st else if***"+digitalMinuteSelected);
//                            if (digitalHourSelected == 12) {
//                                digitalHourSelected = 1;
//                                hourPicker.setValue(0);
//                            } else {
//                                //  System.out.println("else------&&&&" + digitalHourSelected);
//                                digitalHourSelected = Integer.parseInt(hourDigitArray[digitalHourSelected]);
//                                //  System.out.println("else--then----&&&&" + digitalHourSelected);
//                                hourPicker.setValue(digitalHourSelected);
//                            }
//                        } else  if ((prevDigMinuteSelected < digitalMinuteSelected) && (digitalMinuteSelected - prevDigMinuteSelected)> 30) {
//                            System.out.println("2nnd else if***"+digitalMinuteSelected);
//                            if (digitalHourSelected == 1) {
//                                digitalHourSelected = 12;
//                                hourPicker.setValue(11);
//                            } else {
//                                //  System.out.println("else------&&&&" + digitalHourSelected);
//                                digitalHourSelected = digitalHourSelected - 2;
//                                digitalHourSelected = Integer.parseInt(hourDigitArray[digitalHourSelected]);
//                                //  System.out.println("else--then----&&&&" + digitalHourSelected);
//                                hourPicker.setValue(digitalHourSelected);
//                            }
//                        }
//                        tempMinAngle = digitalMinuteSelected * 6;
//                    }
//                    card_minute_hand.setRotation((float) tempMinAngle);
//              //  }
//                setTimeText();
//            }
        }

//        if (scrollState==0){
//            update();
//        }
    }

    public void onScrollStateChange(NumberPicker view, int scrollState) {
        if (view == hourPicker) {
            switch (scrollState) {
                case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                    break;
                case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
//                    digitalHourSelected = view.getValue();
                    hourScrollIdle = true;
                    System.out.println("--idle---");
                    //     System.out.println("**HourPicker idle**" + hourDigitArray[view.getValue()]);

                    view.postDelayed(new Runnable() {
                        public void run() {
                            int newVal = view.getValue();
                            hourPicker.setValue(newVal);
                            digitalHourSelected = Integer.parseInt(hourDigitArray[newVal]);
                            if (digitalMinuteSelected == 0) {
                                if (digitalHourSelected == 12) {
                                    newHourAngle = 0;
                                } else {
                                    newHourAngle = (digitalHourSelected * 30);
                                }
                            } else {
                                if (digitalHourSelected == 12) {
                                    newHourAngle = (digitalMinuteSelected * 6) / 12;
                                } else {
                                    newHourAngle = (digitalHourSelected * 30) + (digitalMinuteSelected * 6) / 12;
                                }
                            }
                            card_hour_hand.setRotation((float) newHourAngle);
                            setTimeText();
                        }
                    }, 500);
                    break;
                case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    break;
            }
        } else {
            switch (scrollState) {
                case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                    break;
                case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                    view.postDelayed(new Runnable() {
                        public void run() {
                            int newVal = view.getValue();
                            minuteScrollIdle = true;
                            System.out.println("--idle---");
                            //    System.out.println("**MinutePicker idle**" + minuteDigitArray[view.getValue()]);
                            // {
                            //  System.out.println("**MinutePicker onValueChange**" + minuteDigitArray[newVal]);
                            minutePicker.setValue(newVal);
                            minuteScrollIdle = false;
                            digitalMinuteSelected = Integer.parseInt(minuteDigitArray[newVal]);
                            //  if (Integer.parseInt(minuteDigitArray[newVal]) != digitalMinuteSelected) {
//                    digitalMinuteSelected = Integer.parseInt(minuteDigitArray[newVal]);
                            if (digitalMinuteSelected == 0) {
                                System.out.println("digitalMinuteSelected***" + digitalMinuteSelected);
                                tempMinAngle = 360;
                                if (prevDigMinuteSelected > 30) {
                                    System.out.println("oldVal greater than new" + newVal);
                                    if (digitalHourSelected == 12) {
                                        digitalHourSelected = 1;
                                        hourPicker.setValue(0);
                                    } else {
                                        //  System.out.println("else------&&&&" + digitalHourSelected);
                                        digitalHourSelected = Integer.parseInt(hourDigitArray[digitalHourSelected]);
                                        //  System.out.println("else--then----&&&&" + digitalHourSelected);
                                        hourPicker.setValue(digitalHourSelected);
                                    }
                                }
                            } else {
                                if ((digitalMinuteSelected < prevDigMinuteSelected) && (prevDigMinuteSelected - digitalMinuteSelected) > 30) {
                                    System.out.println("1st else if***" + digitalMinuteSelected);
                                    if (digitalHourSelected == 12) {
                                        digitalHourSelected = 1;
                                        hourPicker.setValue(0);
                                    } else {
                                        //  System.out.println("else------&&&&" + digitalHourSelected);
                                        digitalHourSelected = Integer.parseInt(hourDigitArray[digitalHourSelected]);
                                        //  System.out.println("else--then----&&&&" + digitalHourSelected);
                                        hourPicker.setValue(digitalHourSelected);
                                    }
                                } else if ((prevDigMinuteSelected < digitalMinuteSelected) && (digitalMinuteSelected - prevDigMinuteSelected) > 30) {
                                    System.out.println("2nnd else if***" + digitalMinuteSelected);
                                    if (digitalHourSelected == 1) {
                                        digitalHourSelected = 12;
                                        hourPicker.setValue(11);
                                    } else {
                                        //  System.out.println("else------&&&&" + digitalHourSelected);
                                        digitalHourSelected = digitalHourSelected - 2;
                                        System.out.println("2nnd else if else***" + digitalHourSelected);

                                        digitalHourSelected = Integer.parseInt(hourDigitArray[digitalHourSelected]);
                                        //  System.out.println("else--then----&&&&" + digitalHourSelected);
                                        hourPicker.setValue(digitalHourSelected);
                                    }
                                }
                                tempMinAngle = digitalMinuteSelected * 6;
                            }
                            card_minute_hand.setRotation((float) tempMinAngle);
                            //  }
                            setTimeText();
                            // }
//                      }
                        }
                    }, 500);
                    break;
                case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    break;
            }
        }

    }

    public void playSoundSetTime(int soundCount, String soundString, int firstDigit, int secondDigit) {
        int setSound = 0;
        btnPlayTimeSound.setClickable(false);
        btnPlayTimeSound.setAlpha((float) 0.5);

        if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
            int idSoundBg = 0;
            if (soundCount == 1) {
                soundCountTwo = 1;
                idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(secondDigit), null, null);
            } else if (soundCount == 2) {
                soundCountTwo = 1;
                idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + soundString, null, null);
            } else {
                idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(firstDigit), null, null);
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
                if (soundCount == 1) {
                    int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + soundString, null, null);
                    player = MediaPlayer.create(getBaseContext(), idSoundBg);
                    player.start();
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            btnPlayTimeSound.setAlpha((float) 1.0);
                            btnPlayTimeSound.setClickable(true);
                        }
                    });
                } else if (soundCount == 2) {
                    int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(secondDigit), null, null);
                    player = MediaPlayer.create(getBaseContext(), idSoundBg);
                    player.start();
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            btnPlayTimeSound.setAlpha((float) 1.0);
                            btnPlayTimeSound.setClickable(true);
                        }
                    });

                } else if (soundCount == 3) {
                    int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + soundString, null, null);
                    player = MediaPlayer.create(getBaseContext(), idSoundBg);
                    player.start();
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            player.release();
                            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "digit_" + String.valueOf(secondDigit), null, null);
                            player = MediaPlayer.create(getBaseContext(), idSoundBg);
                            player.start();
                            btnPlayTimeSound.setAlpha((float) 1.0);
                            btnPlayTimeSound.setClickable(true);
                        }
                    });
                }
            }
        });


    }

    void setTimeText() {
//        System.out.println("digitalHourSelected" + digitalHourSelected);
//        System.out.println("digitalMinuteSelected" + digitalMinuteSelected);

        String regex = "^0+(?!$)";
        if (digitalMinuteSelected == 00) {
            playClockTimeText.setText(String.valueOf(digitalHourSelected).replaceAll(regex, "") + " " + " o'clock");
        } else if (digitalMinuteSelected == 15) {
            playClockTimeText.setText("quarter past " + String.valueOf(digitalHourSelected).replaceAll(regex, ""));
        } else if (digitalMinuteSelected == 30) {
            playClockTimeText.setText("half past " + String.valueOf(digitalHourSelected).replaceAll(regex, ""));
        } else if (digitalMinuteSelected == 45) {
            if (digitalHourSelected == 12) {
                playClockTimeText.setText("quarter to " + String.valueOf(1).replaceAll(regex, ""));
            } else {
                playClockTimeText.setText("quarter to " + String.valueOf(digitalHourSelected + 1).replaceAll(regex, ""));
            }
        } else {
            if (digitalMinuteSelected < 30) {
                String minuteStr = " minutes past ";
                if (digitalMinuteSelected == 1) {
                    minuteStr = " minute past ";
                }
                playClockTimeText.setText(String.valueOf(digitalMinuteSelected).replaceAll(regex, "") + minuteStr + String.valueOf(digitalHourSelected).replaceAll(regex, ""));
            } else {
                int getHour = digitalHourSelected;
                if (getHour == 12) {
                    getHour = 1;
                } else {
                    getHour = getHour + 1;
                }
                int getMinute = 60 - digitalMinuteSelected;
                String minuteStr = " minutes to ";
                if (getMinute == 1) {
                    minuteStr = " minute to ";
                }
                playClockTimeText.setText(String.valueOf(getMinute).replaceAll(regex, "") + minuteStr + String.valueOf(getHour).replaceAll(regex, ""));
            }
        }
        prevDigMinuteSelected = digitalMinuteSelected;
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

    public static int usingBinarySearch(int value, int[] a) {
        if (value <= a[0]) {
            return a[0];
        }
        if (value >= a[a.length - 1]) {
            return a[a.length - 1];
        }

        int result = Arrays.binarySearch(a, value);
        if (result >= 0) {
            return a[result];
        }

        int insertionPoint = -result - 1;
        return (a[insertionPoint] - value) < (value - a[insertionPoint - 1]) ?
                a[insertionPoint] : a[insertionPoint - 1];
    }


    private View.OnTouchListener handleTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("***parent Down ***");
                    break;

                case MotionEvent.ACTION_MOVE:
                    float tapLocationX = (float) event.getX();
                    float tapLocationY = (float) event.getY();
                    double theta = Math.atan2(tapLocationY - centreY, tapLocationX - centreX);
                    theta += Math.PI / 2;
                    clockAngle = Math.toDegrees(theta);
                    if (clockAngle < 0) {
                        clockAngle += 360;
                    }
                    if (!isTouchHourHand && isTouchHourWhileMoving) {
                        isTouchMinuteHand = false;
                        card_hour_hand.setRotation((float) clockAngle);
                        System.out.println("***parent Move hour hand***" + clockAngle);
                    } else if (!isTouchMinuteHand && isTouchMinuteWhileMoving) {
                        if ((int) clockAngle == 0) {
                            clockAngle = 360;
                        }
                        System.out.println("***parent Move minute hand tempAngle***" + tempMinAngle);
                        System.out.println("***parent Move minute hand clockAngle***" + clockAngle);
                        clockAngle = usingBinarySearch((int) clockAngle, minuteArray);
                        card_minute_hand.setRotation((float) clockAngle);
                        if (!(tempMinAngle == (int) clockAngle)) {
                            if ((((int) clockAngle > tempMinAngle) && (((int) clockAngle) - tempMinAngle) > 300) || ((tempMinAngle == 360) && (tempMinAngle - ((int) clockAngle)) < 60)) {
                                if (newHourAngle == 0) {
                                    newHourAngle = 360;
                                }
                                newHourAngle = newHourAngle - 30;

                                System.out.println("Fisrt If" + newHourAngle);
                            } else if ((tempMinAngle != 360) && (tempMinAngle > (int) clockAngle) && (tempMinAngle - ((int) clockAngle)) > 300) {
                                if (newHourAngle == 360) {
                                    newHourAngle = 0;
                                }
                                newHourAngle = newHourAngle + 30;
                                System.out.println("second If" + newHourAngle);
                            }
                            tempMinAngle = (int) clockAngle;
                            System.out.println("*** newHourAngle" + newHourAngle);
                            newHourAngle = nearest_small_value(newHourAngle);
                            if (newHourAngle == 360) {
                                newHourAngle = 0;
                            }
                            //--------------------------------
                            //to set digital clock hour
                            if (newHourAngle == 0) {
                                if ((int) tempMinAngle == 360) {
                                    digitalHourSelected = Integer.parseInt(hourDigitArray[0]);
                                    hourPicker.setValue(0);
                                } else {
                                    digitalHourSelected = Integer.parseInt(hourDigitArray[11]);
                                    hourPicker.setValue(11);
                                }
                            } else {
                                if ((int) tempMinAngle == 360) {
                                    digitalHourSelected = Integer.parseInt(hourDigitArray[(newHourAngle) / 30]);
                                    hourPicker.setValue((newHourAngle) / 30);
                                } else {
                                    digitalHourSelected = Integer.parseInt(hourDigitArray[(newHourAngle - 30) / 30]);
                                    hourPicker.setValue((newHourAngle - 30) / 30);
                                }
                            }
                            setTimeText();
                            //--------------------------------
                            newHourAngle = newHourAngle + (tempMinAngle) / 12;
                            card_hour_hand.setRotation((float) newHourAngle);
                        }
                        //--------------------------------
                        //to set digital clock minute
                        if ((int) tempMinAngle == 360) {
                            digitalMinuteSelected = Integer.parseInt(minuteDigitArray[0]);
                        } else {
                            digitalMinuteSelected = Integer.parseInt(minuteDigitArray[((int) tempMinAngle) / 6]);
                        }
                        minutePicker.setValue(digitalMinuteSelected);
                        setTimeText();
                        //--------------------------------
                        System.out.println("***updated newHourAngle" + newHourAngle);
                        System.out.println("***updated parent Move minute hand***" + tempMinAngle);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    System.out.println("***parent up ***");
                    if (!isTouchHourHand && isTouchHourWhileMoving) {
//                        newHourAngle = usingBinarySearch((int) clockAngle, hourArray);
                        System.out.println("***parent up tempMinAngle***" + tempMinAngle);

                        if (tempMinAngle != 360 && tempMinAngle != 0) {
                            newHourAngle = nearest_small_value((int) clockAngle);
                        } else {
                            System.out.println("***parent up temp angle 360***");
                            newHourAngle = usingBinarySearch((int) clockAngle, hourArray);
                        }
                        if (newHourAngle == 360) {
                            newHourAngle = 0;
                        }
                        //--------------------------------
                        //to set digital clock hour
                        if (newHourAngle == 0) {
                            digitalHourSelected = Integer.parseInt(hourDigitArray[11]);
                            hourPicker.setValue(11);
                        } else {
                            System.out.println("digitalHourSelected----hou hand" + newHourAngle);
                            System.out.println("digitalHourSelected----new" + (newHourAngle - 30) / 30);

                            digitalHourSelected = Integer.parseInt(hourDigitArray[(newHourAngle - 30) / 30]);
                            hourPicker.setValue((newHourAngle - 30) / 30);
                            System.out.println("digitalHourSelected----actual" + digitalHourSelected);
                        }
                        setTimeText();
                        //--------------------------------
                        if (tempMinAngle != 360) {
                            newHourAngle = newHourAngle + (tempMinAngle) / 12;
                        }
                        card_hour_hand.setRotation((float) newHourAngle);
                        System.out.println("***parent Up hour hand***" + newHourAngle);
                    }
                    isTouchMinuteHand = true;
                    isTouchHourHand = true;
                    isTouchHourWhileMoving = false;
                    isTouchMinuteWhileMoving = false;
                    imgVwSetTimeMinuteHand.setAlpha(0.7f);
                    imgVwSetTimeHourHand.setAlpha(0.7f);

                    break;
            }
            return true;
        }
    };

    int nearest_small_value(int x) {
        int low = 0, high = hourArrayLength - 1, ans = x;

        // Continue until low is less
        // than or equals to high
        while (low <= high) {

            // Find mid
            int mid = (low + high) / 2;

            // If element at mid is less than
            // or equals to searching element
            if (hourArray[mid] <= ans) {

                // If mid is equals
                // to searching element
                if (hourArray[mid] == ans) {

                    // Increment searching element
                    // Make high as N - 1
                    high = hourArrayLength - 1;
                }

                // Make low as mid + 1
                low = mid + 1;
            }

            // Make high as mid - 1
            else
                high = mid - 1;
        }

        // Return the next greater element


        if ((low - 1) >= 0) {
            low = low - 1;
        }

        System.out.println("Value:" + x);
        System.out.println("index value:" + hourArray[low]);
        return hourArray[low];
    }

    public void playSoundSetTime(String soundName) {
        System.out.println("playSound clicked ---------" + soundName);
        if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
//            if (player != null) {
//
//                Log.i("bbbbbwer",  "pp"+player.getCurrentPosition());
//                if (player.getCurrentPosition() != 0) {
//                    if (player.isPlaying()) {
//                        player.stop();
//                    }
//                } else {
//                    player.release();
//                }
//
//            }
            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + soundName, null, null);
            //   player.setVolume(0.0f, 0.0f);
            try {
                player = MediaPlayer.create(getBaseContext(), idSoundBg);
//                player.setVolume(1.0f, 1.0f);

//                player.start();
            } catch (Exception e) {
                Log.e("Music Exception", "catch button click sound play");
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
                mInterstitialAd.show(PlayClockActivity.this);

                // Log.i(TAG, "onAdLoaded");
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.i("TAG", "The ad was dismissed.");
                        if (fromHome) {
                            Log.i("playCrad", "The ad was dismissed---if");
                            PlayClockActivity.super.onBackPressed();
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
