package com.mobiapps360.LearnClockTime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class LearnClockActivity extends AppCompatActivity {
    private TextView txtViewDescTxt;
    private ImageView imgViewDesc;
    private CardView card_desc;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnLearnClockSound;
    private ImageButton btnSoundOnOffLearn;
    private ImageButton btnLearnClockBack;
    private RecyclerView recycleViewLearnClock;
    LearnClockAdapter learnClockAdapter;
    LearnClockDataModel[] learnClockDataModelList;
    //Declare variables
    MediaPlayer player;
    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundLearnActivity = "soundLearnActivityKey";
    int cardNumber = 0;
    int subCardNumber = 1;
    int maxVolume = 0;
    int currVolume = 0;
    Boolean getSoundFlag = true;
    int currentIndex = 0;
    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_clock);

        learnClockDataModelList = new LearnClockDataModel[]{
                new LearnClockDataModel("➤ This is a " + "<b>" + "clock dial" + "</b>" + ".", R.drawable.learn_clock_0),
                new LearnClockDataModel("➤ There are three hands, " + "<b>" + "hour hand" + "</b>" + ", " + "<b>" + "minute hand" + "</b>" + " and " + "<b>" + "second hand" + "</b>" + " on the face of a clock.", R.drawable.learn_clock_1),
                new LearnClockDataModel("➤ This is an " + "<b>" + "hour hand" + "</b>" + "." + "<br/><br/>➤ The hour hand is a " + "<b>" + "small" + "</b>" + " hand on a clock which shows " + "<b>" + "hours" + "</b>" + "." + "<br/><br/>➤ When an hour hand completes " + "<b>" + "24 hours" + "</b>" + ", it's called " + "<b>" + "1 day" + "</b>" + ".", R.drawable.learn_clock_2),
                new LearnClockDataModel("➤ This is a " + "<b>" + "minute hand" + "</b>" + "." + "<br/><br/>➤ The minute hand is the " + "<b>" + "long" + "</b>" + " hand on a clock which shows " + "<b>" + "minutes" + "</b>" + "." + "<br/><br/>➤ When a minute hand completes " + "<b>" + "60 minutes" + "</b>" + ", it's called " + "<b>" + "1 hour" + "</b>" + ".", R.drawable.learn_clock_3),
                new LearnClockDataModel(" ➤ This is a " + "<b>" + "second hand" + "</b>" + " on a clock which shows " + "<b>" + "seconds" + "</b>" + "." + "<br/><br/>➤ When the second hand completes " + "<b>" + "60 seconds" + "</b>" + ", it's called " + "<b>" + "1 minute" + "</b>" + ".", R.drawable.learn_clock_4),
                new LearnClockDataModel(" ➤ " + "When the " + "<b>" + "minute hand" + "</b>" + " is at the top of the clock " + "<b>" + "at 12" + "</b>" + ", use the phrase " + "<b>" + "O'clock" + "</b>" + ".", R.drawable.learn_clock_5),
                new LearnClockDataModel("", R.drawable.learn_clock_6),
                new LearnClockDataModel("➤ Use the phrase " + "<b>" + "past" + "</b>" + " when the minute hand completes between " + "<b>" + "one to 30 minutes" + "</b>" + " on the dial.", R.drawable.learn_clock_7),
                new LearnClockDataModel("➤ Use the phrase " + "<b>" + "quarter past" + "</b>" + " when the minute hand completes " + "<b>" + "15 minutes" + "</b>" + " on the dial.", R.drawable.learn_clock_8),
                new LearnClockDataModel("➤ Use the phrase " + "<b>" + "half past" + "</b>" + " when the minute hand completes " + "<b>" + "30 minutes" + "</b>" + " on the dial.", R.drawable.learn_clock_9),
                new LearnClockDataModel("", R.drawable.learn_clock_10),
                new LearnClockDataModel("➤ Use the phrase " + "<b>" + "to" + "</b>" + " when the minute hand completes between " + "<b>" + "31 to 59 minutes" + "</b>" + " on the dial.", R.drawable.learn_clock_11),
                new LearnClockDataModel("➤ Use the phrase " + "<b>" + "quarter to" + "</b>" + " when the minute hand completes " + "<b>" + "45 minutes" + "</b>" + " on the dial.", R.drawable.learn_clock_12),
                new LearnClockDataModel("", R.drawable.learn_clock_13)
        };
        txtViewDescTxt = findViewById(R.id.txtViewDescTxt);
        imgViewDesc = findViewById(R.id.imgViewDesc);
        card_desc = findViewById(R.id.card_desc);
        btnForward = findViewById(R.id.btnLearnClockForward);
        btnBackward = findViewById(R.id.btnLearnClockBackward);
        btnSoundOnOffLearn = findViewById(R.id.btnSoundOnOffLearn);
        btnLearnClockBack = findViewById(R.id.btnLearnClockBack);
        btnLearnClockSound = findViewById(R.id.btnLearnClockSound);
        recycleViewLearnClock = findViewById(R.id.recycleViewLearnClock);
        txtViewDescTxt.setText(Html.fromHtml(learnClockDataModelList[0].getImageName()));
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(soundLearnActivity)) {
            getSoundFlag = sharedPreferences.getBoolean(soundLearnActivity, false);
            if (getSoundFlag == true) {
                btnSoundOnOffLearn.setImageResource(R.mipmap.sound_on);
            } else {
                btnSoundOnOffLearn.setImageResource(R.mipmap.sound_off);
            }
        } else {
            editor.putBoolean(soundLearnActivity, true);
            editor.commit();
            btnSoundOnOffLearn.setImageResource(R.mipmap.sound_on);
        }
        if (cardNumber == 0) {
            btnBackward.setVisibility(View.GONE);
        } else {
            btnBackward.setVisibility(View.VISIBLE);
        }
        ViewCompat.setTranslationZ(txtViewDescTxt, 15);
        ViewCompat.setTranslationZ(btnSoundOnOffLearn, 15);
        ViewCompat.setTranslationZ(btnLearnClockBack, 15);

        learnClockAdapter = new LearnClockAdapter(this);
        learnClockAdapter.setListMenuItem(learnClockDataModelList);

        recycleViewLearnClock.setAdapter(learnClockAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelper = new PagerSnapHelper();
        recycleViewLearnClock.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recycleViewLearnClock);
        recycleViewLearnClock
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int offset = recycleViewLearnClock.computeHorizontalScrollOffset();
                        if (offset % recycleViewLearnClock.getWidth() == 0) {
                            int position = offset / recycleViewLearnClock.getWidth();
//                            int idSwipeImg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/swipe", null, null);
                            currentIndex = position;
                            if ((learnClockDataModelList[position].getImageName() == null) || (learnClockDataModelList[position].getImageName().length() == 0)) {
                                txtViewDescTxt.setVisibility(View.INVISIBLE);
                                card_desc.setVisibility(View.INVISIBLE);
                            } else {
                                txtViewDescTxt.setVisibility(View.VISIBLE);
                                card_desc.setVisibility(View.VISIBLE);
                                txtViewDescTxt.setText(Html.fromHtml(learnClockDataModelList[position].getImageName()));
                            }
                            if (currentIndex == learnClockDataModelList.length - 1) {
                                btnForward.setImageResource(R.drawable.reload);
                                btnBackward.setVisibility(View.VISIBLE);
                            } else if (currentIndex == 0) {
                                btnForward.setImageResource(R.drawable.next_question);
                                btnBackward.setVisibility(View.INVISIBLE);
                            } else {
                                btnForward.setImageResource(R.drawable.next_question);
                                btnBackward.setVisibility(View.VISIBLE);
                            }
//                            clickCount = clickCount + 1;
//                            if (clickCount > 14) {
//                                clickCount = 0;
//                                showInterstitialAds(false);
//                            } else
                            if (getSoundFlag == true) {
                                if (player != null) {
                                    player.stop();
                                }
                                playSound("screen_" + currentIndex);
                            }


                          /*  if (MainActivity.isSoundFunctionality) {
                                MediaPlayer playerSwipe = MediaPlayer.create(getBaseContext(), idSwipeImg);
                                currVolume = 47;
                                float log1 = (float) (Math.log(maxVolume - currVolume) / Math.log(maxVolume));
                                playerSwipe.setVolume(log1, log1); //set volume takes two paramater
                                System.out.println("Current position is" + position);
                                if (!isFirstTime) {
                                    if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
                                        playerSwipe.start();
                                    }
                                } else {
                                    isFirstTime = false;
                                }
                                //int idSoundImg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnNature:raw/" + animalDataArray.get(position).getPictureAnimal(), null, null);
                                // System.out.println("Activity idsound:" + animalDataArray.get(position).getPictureAnimal());
                                // System.out.println("#########Start is#########" + position);
                                clickCount = clickCount + 1;
                                if (clickCount > 14) {
                                    clickCount = 0;
                                    showInterstitialAds(false);
                                } else {
                                    if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
                                        if (playerCard != null) {
                                            playerCard.stop();
                                            //  playerCard.release();
                                        }
                                        playerCard = MediaPlayer.create(getBaseContext(), idSoundImg);
                                        Log.i("playCrad","onscroll");
                                        playerCard.start();
                                    }
                                }
                            }*/

                        }
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
                        if (currentIndex == learnClockDataModelList.length - 1) {
                            btnForward.setImageResource(R.drawable.next_question);
                            recycleViewLearnClock.getLayoutManager().smoothScrollToPosition(recycleViewLearnClock, new RecyclerView.State(), 0);
                        } else {
                            recycleViewLearnClock.getLayoutManager().smoothScrollToPosition(recycleViewLearnClock, new RecyclerView.State(), currentIndex + 1);
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
                            recycleViewLearnClock.getLayoutManager().smoothScrollToPosition(recycleViewLearnClock, new RecyclerView.State(), currentIndex - 1);
                        }
                    }
                }
                return true;
            }
        });
        btnLearnClockSound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        learnClockAdapter.setListMenuItem(learnClockDataModelList);
                        if (getSoundFlag == true) {
                            playSound("screen_" + currentIndex);
                        }
                    }
                }
                return true;
            }
        });
    }

    public void backBtnClicked(View v) {
        if (player != null) {
            player.stop();
            player.release();
        }
        LearnClockActivity.super.onBackPressed();
    }

    public void soundLearnClockONOffClicked(View v) {
        Constant.xx = 15;
        if (sharedPreferences.contains(soundLearnActivity)) {
            getSoundFlag = sharedPreferences.getBoolean(soundLearnActivity, false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            getSoundFlag = !getSoundFlag;
            editor.putBoolean(soundLearnActivity, getSoundFlag);
            editor.commit();
            if (getSoundFlag == true) {
                btnSoundOnOffLearn.setImageResource(R.mipmap.sound_on);
            } else {
                btnSoundOnOffLearn.setImageResource(R.mipmap.sound_off);
                if (player != null) {
                    player.stop();
                }
            }
        }
    }

    public void playSound(String soundName) {
        System.out.println("playSound clicked ---------" + soundName);
        if (MainActivity.sharedPreferences.getBoolean(soundLearnActivity, false)) {
            if (player != null) {
                player.stop();
            }
            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + soundName, null, null);
            player = MediaPlayer.create(getBaseContext(), idSoundBg);
            //   player.setVolume(0.0f, 0.0f);
            player.start();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    }

    public void stopPlayerSound() {
        if (player != null) {
            player.stop();
        }
    }
}
