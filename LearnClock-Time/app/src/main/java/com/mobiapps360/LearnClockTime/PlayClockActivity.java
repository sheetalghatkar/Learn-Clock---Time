
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

public class PlayClockActivity extends AppCompatActivity {
    // on below line we are creating variable
    // for our array list and swipe deck.
    private ImageButton btnSoundSetTimeOnOff;
    private ImageButton btnSetTimeBack;
    Boolean getSoundFlag = true;
    private ImageView imgViewWallPaper;
    int clickCount = 1;
    int adShowCount = 13;
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

    boolean isTouchHourWhileMoving = false; // to Check hour hand touch while moving
    boolean isTouchMinuteWhileMoving = false; //  to Check minute hand touch while moving

    double centreX = 0.0;
    double centreY = 0.0;
    List<Integer> minuteArrayList;
    int[] minuteArray;
    ImageView imgVwSetTimeHourHand;
    ImageView imgVwPlayClockMinuteHand;
    int newHourAngle;
    int newMinuteAngle;
    //Declare variables
    ImageView imgVwClockDial;
    MediaPlayer player;
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
        imgVwPlayClockMinuteHand = findViewById(R.id.imgVwPlayClockMinuteHand);
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
        imgVwPlayClockMinuteHand.setAlpha(0.7f);
        imgVwSetTimeHourHand.setAlpha(0.7f);

        System.out.println("Hour array&&&&&:"+ nearest_small_value(60));
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
                        imgVwPlayClockMinuteHand.setAlpha(0.7f);
                        break;
                    }
                }
                return isTouchHourHand;
            }
        });

        //-----------------------------------------
        imgVwPlayClockMinuteHand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("imgVwPlayClockMinuteHand touch****");
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
                        imgVwPlayClockMinuteHand.setAlpha(1.0f);
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
                        System.out.println("***parent Move hour hand***"+clockAngle);
                    } else if (!isTouchMinuteHand && isTouchMinuteWhileMoving) {
                        if ((int)clockAngle == 0) {
                            clockAngle = 360;
                        }
                        System.out.println("***parent Move minute hand tempAngle***"+tempMinAngle);
                        System.out.println("***parent Move minute hand clockAngle***"+clockAngle);

                        card_minute_hand.setRotation((float) clockAngle);
                        if (!(tempMinAngle == (int) clockAngle)) {
                            if ((((int) clockAngle > tempMinAngle) && (((int) clockAngle) - tempMinAngle) > 300)||((tempMinAngle == 360) && (tempMinAngle - ((int) clockAngle)) < 60)) {
                                if (newHourAngle == 0) {
                                    newHourAngle = 360;
                                }
                                newHourAngle = newHourAngle - 30;

                                System.out.println("Fisrt If"+newHourAngle);
                            } else if((tempMinAngle != 360)&&(tempMinAngle >(int) clockAngle) && (tempMinAngle - ((int) clockAngle)) > 300){
                                if (newHourAngle == 360) {
                                    newHourAngle = 0;
                                }
                                newHourAngle = newHourAngle + 30;
                                System.out.println("second If"+newHourAngle);
                            }
                            tempMinAngle = (int) clockAngle;
                            System.out.println("*** newHourAngle"+newHourAngle);
                            newHourAngle = nearest_small_value(newHourAngle);
                            if (newHourAngle == 360) {
                                newHourAngle = 0;
                            }
                            newHourAngle = newHourAngle + (tempMinAngle) / 12;
                            card_hour_hand.setRotation((float) newHourAngle);
                        }
                        System.out.println("***updated newHourAngle"+newHourAngle);
                        System.out.println("***updated parent Move minute hand***"+tempMinAngle);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    System.out.println("***parent up ***");
                    if (!isTouchHourHand && isTouchHourWhileMoving) {
//                        newHourAngle = usingBinarySearch((int) clockAngle, hourArray);
                        newHourAngle = nearest_small_value((int) clockAngle);
                        if (newHourAngle == 360) {
                            newHourAngle = 0;
                        }
                        if (tempMinAngle != 360) {
                            newHourAngle = newHourAngle + (tempMinAngle) / 12;
                        }

                        card_hour_hand.setRotation((float) newHourAngle);
                        System.out.println("***parent Up hour hand***"+newHourAngle);
                    }
                    isTouchMinuteHand = true;
                    isTouchHourHand = true;
                    isTouchHourWhileMoving = false;
                    isTouchMinuteWhileMoving = false;
                    imgVwPlayClockMinuteHand.setAlpha(0.7f);
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
