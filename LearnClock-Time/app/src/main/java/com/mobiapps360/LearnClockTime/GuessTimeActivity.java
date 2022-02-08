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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.daprlabs.cardstack.SwipeDeck;

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
                //   Toast.makeText(GuessTimeActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                // Toast.makeText(GuessTimeActivity.this, "", Toast.LENGTH_SHORT).show();
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
                            if (player.getCurrentPosition() != 0) {
                                if (player.isPlaying()) {
                                    player.stop();
                                    player.release();
                                }
                            } else {
                                player.release();
                            }
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
                                if (player != null) {
                                    if (player.getCurrentPosition() != 0) {
                                        if (player.isPlaying()) {
                                            player.stop();
                                        }
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

}
