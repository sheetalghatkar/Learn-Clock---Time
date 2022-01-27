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

public class ClockLearnActivity extends AppCompatActivity {
    private ImageView imgViewLearnClockGif;
    private TextView txtViewDescTxt;
    private ImageView imgViewDesc;
    private CardView card_desc;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnLearnClockSound;
    private ImageButton btnSoundOnOffLearn;
    RecyclerView clockLearnRecView;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_clock);
        XyzModel[] xyzModelList = new XyzModel[]{
             new XyzModel("Gardian if Galaxies"),
             new XyzModel("End game"),
             new XyzModel("Captain America")
        };

        imgViewLearnClockGif = findViewById(R.id.imgViewLearnClockGif);
        clockLearnRecView = findViewById(R.id.clockLearnRecView);
        txtViewDescTxt = findViewById(R.id.txtViewDescTxt);
        imgViewDesc = findViewById(R.id.imgViewDesc);
        card_desc = findViewById(R.id.card_desc);
        btnForward = findViewById(R.id.btnLearnClockForward);
        btnBackward = findViewById(R.id.btnLearnClockBackward);
        btnSoundOnOffLearn = findViewById(R.id.btnSoundOnOffLearn);
        btnLearnClockSound = findViewById(R.id.btnLearnClockSound);
        Constant.learnClockDataArray.add(new ClockLearnItem("➤ This is a " + "<b>" + "clock dial" + "</b>" +".","learn_clock_1"));
        Glide.with(this).load(R.drawable.learn_clock_1).into(imgViewLearnClockGif);
//        txtViewDescTxt.setText(Html.fromHtml(Constant.learnClockDataArray[0]));
        txtViewDescTxt.setText(Constant.learnClockDataArray.get(0).getStrClockDesc());
        System.out.println("@@@@@@@@@@@@@@@learnClockForwardClicked clicked@@@@@@@@@@@"+Constant.learnClockDataArray.size());
        if (clockLearnRecView == null) {
            System.out.println("Object is Null");
        }
        ClockLearnAdapterActivity  clockLearnAdapterActivity = new ClockLearnAdapterActivity(xyzModelList);
        clockLearnRecView.setHasFixedSize(true);
        clockLearnRecView.setLayoutManager(new LinearLayoutManager(this));
        clockLearnRecView.setAdapter(clockLearnAdapterActivity);
        btnLearnClockSound.setEnabled(false);
        btnLearnClockSound.setAlpha(0.5f);
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(soundLearnActivity)) {
            getSoundFlag = sharedPreferences.getBoolean(soundLearnActivity, false);
            if (getSoundFlag == true) {
                playSound("screen_0_1");
                btnSoundOnOffLearn.setImageResource(R.mipmap.sound_on);
            } else {
                btnSoundOnOffLearn.setImageResource(R.mipmap.sound_off);
            }
        } else {
            editor.putBoolean(soundLearnActivity, true);
            editor.commit();
            btnSoundOnOffLearn.setImageResource(R.mipmap.sound_on);
            playSound("screen_0_1");
        }
        if (cardNumber == 0) {
            btnBackward.setVisibility(View.GONE);
        } else {
            btnBackward.setVisibility(View.VISIBLE);
        }
        ViewCompat.setTranslationZ(txtViewDescTxt, 15);

//        if (clockLearnAdapter == null) {
//            System.out.println("Object is Null11");
//        }
//

    }

    public void backBtnClicked(View v) {
        if (player != null) {
            player.stop();
            player.release();
        }
        ClockLearnActivity.super.onBackPressed();
    }

    public void soundLearnClockONOffClicked(View v) {
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

    public void learnClockForwardClicked(View v) {
        System.out.println("@@@@@@@@@@@@@@@learnClockForwardClicked clicked@@@@@@@@@@@");
    }

    public void learnClockBackwardClicked(View v) {
        System.out.println("@@@@@@@@@@@@@@@learnClockBackwardClicked clicked@@@@@@@@@@@");
    }


    public void learnClockSoundClicked(View v) {
        System.out.println("@@@@@@@@@@@@@@@learnClockSoundClicked clicked@@@@@@@@@@@");
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
                System.out.println("----setOnCompletionListener-----");
                if (cardNumber == 0) {
                    if (subCardNumber == 1) {
                        subCardNumber = 0;
                        imgViewLearnClockGif.setAlpha(0f);
                        imgViewLearnClockGif.setVisibility(View.VISIBLE);
                        imgViewLearnClockGif.animate()
                                .alpha(0.5f)
                                .setDuration(400)
                                .setListener(null);

                        imgViewLearnClockGif.setAlpha(0.5f);
                        imgViewLearnClockGif.animate()
                                .alpha(0f)
                                .setDuration(400)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        imgViewLearnClockGif.setImageResource(R.drawable.learn_clock_1_2);
                                        imgViewLearnClockGif.setAlpha(0f);
                                        imgViewLearnClockGif.animate()
                                                .alpha(0.5f)
                                                .setDuration(400)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        imgViewLearnClockGif.setVisibility(View.VISIBLE);
                                                        imgViewLearnClockGif.setAlpha(1.0f);
                                                    }
                                                });

                                    }
                                });

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (cardNumber == 0) {
                                    playSound("screen_0_2");
                                }
                            }
                        }, 1500);

                    } else {
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                } else if (cardNumber == 1) {
                    if (subCardNumber == 1) {
                        subCardNumber = 2;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (cardNumber == 1) {
                                    playSound("screen_1_2");
                                }
                            }
                        }, 500);

                    } else if (subCardNumber == 2) {
                        subCardNumber = 0;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (cardNumber == 1) {
                                    playSound("screen_1_3");
                                }
                            }
                        }, 600);
                    } else {
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                } else if (cardNumber == 2) {
                    if (subCardNumber == 1) {
                        subCardNumber = 2;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (cardNumber == 2) {
                                    playSound("screen_2_2");
                                }
                            }
                        }, 600);
                    } else if (subCardNumber == 2) {
                        subCardNumber = 0;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (cardNumber == 2) {
                                    playSound("screen_2_3");
                                }
                            }
                        }, 600);
                    } else {
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                } else if (cardNumber == 3) {
                    if (subCardNumber == 1) {
                        subCardNumber = 0;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (cardNumber == 3) {
                                    playSound("screen_3_2");
                                }
                            }
                        }, 600);
                    } else {
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                } else if (cardNumber == 4) {
                    if (subCardNumber == 1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(ClockLearnActivity.this)
                                        .asGif()
                                        .load(R.drawable.learn_clock_5) //Your gif resource
                                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                        .skipMemoryCache(true)
                                        .listener(new RequestListener<GifDrawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                                resource.setLoopCount(1);
                                                return false;
                                            }
                                        })
                                        .into(imgViewLearnClockGif);
                                if (cardNumber == 4) {
                                    playSound("learndesc_clock_" + subCardNumber);
                                }
                                subCardNumber = 2;
                            }
                        }, 200);
                    } else if (subCardNumber == 2) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 3;
                    } else if (subCardNumber == 3) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 4;
                    } else if (subCardNumber == 4) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 5;
                    } else if (subCardNumber == 5) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 6;
                    } else if (subCardNumber == 6) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 7;
                    } else if (subCardNumber == 7) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 8;
                    } else if (subCardNumber == 8) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 9;
                    } else if (subCardNumber == 9) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 10;
                    } else if (subCardNumber == 10) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 11;
                    } else if (subCardNumber == 11) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 12;
                    } else if (subCardNumber == 12) {
                        if (cardNumber == 4) {
                            playSound("learndesc_clock_" + subCardNumber);
                        }
                        subCardNumber = 0;
                    } else {
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                } else if (cardNumber == 5) {
                    if (subCardNumber == 1) {
                        subCardNumber = 0;
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                } else if (cardNumber == 6) {
                    if (subCardNumber == 1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                subCardNumber = 2;
                                Glide.with(ClockLearnActivity.this).load(R.drawable.learn_clock_7_2).into(imgViewLearnClockGif);
                                if (cardNumber == 6) {
                                    playSound("screen_6_2");
                                }
                            }
                        }, 300);
                    } else if (subCardNumber == 2) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                subCardNumber = 3;
                                if (cardNumber == 6) {
                                    playSound("screen_6_3");
                                }
                            }
                        }, 300);
                    } else if (subCardNumber == 3) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(ClockLearnActivity.this)
                                        .asGif()
                                        .load(R.drawable.learn_clock_7_3) //Your gif resource
                                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                        .skipMemoryCache(true)
                                        .listener(new RequestListener<GifDrawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                                resource.setLoopCount(1);
                                                return false;
                                            }
                                        })
                                        .into(imgViewLearnClockGif);
                                if (cardNumber == 6) {
                                    playSound("screen_6_4");
                                }
                                subCardNumber = 0;
                            }
                        }, 200);
                    } else {
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                } else if (cardNumber == 7) {
                    if (subCardNumber == 1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                subCardNumber = 2;
                                Glide.with(ClockLearnActivity.this).load(R.drawable.learn_clock_8_2).into(imgViewLearnClockGif);
                                if (cardNumber == 7) {
                                    playSound("screen_7_2");
                                }
                            }
                        }, 200); //Time in milisecond
                    } else if (subCardNumber == 2) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                subCardNumber = 3;
                                if (cardNumber == 7) {
                                    playSound("screen_7_3");
                                }
                            }
                        }, 200); //Time in milisecond

                    } else if (subCardNumber == 3) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(ClockLearnActivity.this)
                                        .asGif()
                                        .load(R.drawable.learn_clock_8_3) //Your gif resource
                                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                        .skipMemoryCache(true)
                                        .listener(new RequestListener<GifDrawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                                resource.setLoopCount(1);
                                                return false;
                                            }
                                        })
                                        .into(imgViewLearnClockGif);
                                if (cardNumber == 7) {
                                    playSound("screen_7_4");
                                }
                                subCardNumber = 0;
                            }
                        }, 200);
                    } else {
                        btnLearnClockSound.setEnabled(true);
                        btnLearnClockSound.setAlpha(1.0f);
                    }
                }
            }
        });
    }

    public void stopPlayerSound() {
        if (player != null) {
            player.stop();
        }
    }
}