package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

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

public class GuessTimeActivity extends AppCompatActivity {
    // on below line we are creating variable
    // for our array list and swipe deck.
    private SwipeDeck cardStack;
    private ImageButton btnSoundGuessTimeOnOff;
    private ImageButton btnGuessTimeBack;
    Boolean getSoundFlag = true;
    private SwipeDeck swipe_deck;
    private ImageView imgViewWallPaper;
    private ImageView imageViewStartGif;
    private ImageView imageViewAgainGif;
    private ImageView imageViewLoaderGif;
    int clickCount = 1;
    int adShowCount = 13;
    private AdView mAdView;
    ImageView imgVwLearnLoader;
    View viewLearnLoader;
    AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    public static ArrayList<GuessTimeItem> guessTimeDataArray;

    //Declare variables
    MediaPlayer player;
    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundLearnActivity = "soundLearnActivityKey";
    DeckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_time);
        btnSoundGuessTimeOnOff = findViewById(R.id.btnSoundGuessTimeOnOff);
        btnGuessTimeBack = findViewById(R.id.btnGuessTimeBack);
        swipe_deck = findViewById(R.id.swipe_deck);
        imageViewStartGif = findViewById(R.id.imageViewStartGif);
        imageViewAgainGif = findViewById(R.id.imageViewAgainGif);
        imageViewLoaderGif = findViewById(R.id.imageViewLoaderGif);
        imgViewWallPaper = findViewById(R.id.guessTimeWallImage);
        imgVwLearnLoader = findViewById(R.id.imgVwGuessTimeLoader);
        viewLearnLoader = findViewById(R.id.viewLoaderGuessTimeBg);
        Glide.with(this).load(R.drawable.loader).into(imgVwLearnLoader);
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(soundLearnActivity)) {
            getSoundFlag = sharedPreferences.getBoolean(soundLearnActivity, false);
            if (getSoundFlag == true) {
                btnSoundGuessTimeOnOff.setImageResource(R.mipmap.sound_on);
            } else {
                btnSoundGuessTimeOnOff.setImageResource(R.mipmap.sound_off);
            }
        } else {
            editor.putBoolean(soundLearnActivity, true);
            editor.commit();
            btnSoundGuessTimeOnOff.setImageResource(R.mipmap.sound_on);
        }

        // on below line we are initializing our array list and swipe deck.
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.swipeTopCardRight(10);
        ViewCompat.setTranslationZ(swipe_deck, 15);

        Glide.with(this).load(R.drawable.starting).into(imageViewStartGif);
        Glide.with(this).load(R.drawable.again).into(imageViewAgainGif);
        Glide.with(this).load(R.drawable.preloader).into(imageViewLoaderGif);

        mAdView = findViewById(R.id.adViewBannerGuessTimeActivity);
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
                System.out.println("Show error####"+adError);
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
        //-----------------------------------------




        // on below line we are creating a variable for our adapter class and passing array list to it.
        adapter = new DeckAdapter(MainActivity.guessTimeFinalArray, this);
        /// cardStack.layoutManager = CardStackLayoutManager();
        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);
        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                 //  Toast.makeText(GuessTimeActivity.this, "", Toast.LENGTH_SHORT).show();
                clickCount = clickCount + 1;
                if (clickCount > adShowCount) {
                    clickCount = 0;
                    showInterstitialAds(false);
                }
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                // Toast.makeText(GuessTimeActivity.this, "", Toast.LENGTH_SHORT).show();
                clickCount = clickCount + 1;
                if (clickCount > adShowCount) {
                    clickCount = 0;
                    showInterstitialAds(false);
                }
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                // Toast.makeText(GuessTimeActivity.this, "", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();

                imageViewAgainGif.setAlpha(1.0f);
                imageViewStartGif.setAlpha(1.0f);
                imageViewLoaderGif.setAlpha(1.0f);

                cardStack.setAlpha(0.0f);
                cardStack.setAdapter(adapter);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardStack.setAlpha(1.0f);
                        imageViewAgainGif.setAlpha(0.0f);
                        imageViewStartGif.setAlpha(0.0f);
                        imageViewLoaderGif.setAlpha(0.0f);
                    }
                }, 2000);

            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                //   Toast.makeText(GuessTimeActivity.this, "CARDS MOVED DOWN", Toast.LENGTH_SHORT).show();

                //  Log.i("TAG", "CARDS MOVED DOWN");


            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                // Log.i("TAG", "CARDS MOVED UP");
            }
        });

        btnGuessTimeBack.setOnTouchListener(new View.OnTouchListener() {
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
                        GuessTimeActivity.super.onBackPressed();
                    }
                }
                return true;
            }
        });

        btnSoundGuessTimeOnOff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);

                        btnGuessTimeBack.setVisibility(View.VISIBLE);
                        if (sharedPreferences.contains(soundLearnActivity)) {
                            getSoundFlag = sharedPreferences.getBoolean(soundLearnActivity, false);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            getSoundFlag = !getSoundFlag;
                            editor.putBoolean(soundLearnActivity, getSoundFlag);
                            editor.commit();
                            if (getSoundFlag == true) {
                                btnSoundGuessTimeOnOff.setImageResource(R.mipmap.sound_on);
                            } else {
                                btnSoundGuessTimeOnOff.setImageResource(R.mipmap.sound_off);
                            }
                        }
                    }
                }
                return true;
            }
        });


    }

    public void swipeRightCardOnCorrectOptionClicked() {
        cardStack.swipeTopCardRight(500);
    }


    public void playSoundGuessTime(String soundName) {
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
            imgVwLearnLoader.setVisibility(View.VISIBLE);
            viewLearnLoader.setVisibility(View.VISIBLE);
        } else {
            imgVwLearnLoader.setVisibility(View.INVISIBLE);
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
                mInterstitialAd.show(GuessTimeActivity.this);

                // Log.i(TAG, "onAdLoaded");
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.i("TAG", "The ad was dismissed.");
                        if (fromHome) {
                            Log.i("playCrad", "The ad was dismissed---if");
                            GuessTimeActivity.super.onBackPressed();
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
