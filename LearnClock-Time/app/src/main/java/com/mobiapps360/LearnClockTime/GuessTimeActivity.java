package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.daprlabs.cardstack.SwipeDeck;

public class GuessTimeActivity extends AppCompatActivity {
    // on below line we are creating variable
    // for our array list and swipe deck.
    private SwipeDeck cardStack;
    private ArrayList<CourseModal> courseModalArrayList;
    private ImageButton btnSoundGuessTimeOnOff;
    private ImageButton btnGuessTimeBack;
    Boolean getSoundFlag = true;
    private SwipeDeck swipe_deck;
    private ImageView imgViewWallPaper;

    //Declare variables
    MediaPlayer player;
    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundLearnActivity = "soundLearnActivityKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_time);
        btnSoundGuessTimeOnOff = findViewById(R.id.btnSoundGuessTimeOnOff);
        btnGuessTimeBack = findViewById(R.id.btnGuessTimeBack);
        swipe_deck = findViewById(R.id.swipe_deck);
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
        courseModalArrayList = new ArrayList<>();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.swipeTopCardRight(10);
//        ViewCompat.setTranslationZ(imgViewWallPaper,10);
        ViewCompat.setTranslationZ(swipe_deck, 15);

        // on below line we are adding data to our array list.
        courseModalArrayList.add(new CourseModal("C++", "30 days", "20 Tracks", "C++ Self Paced Course", R.drawable.learn_clock_1));
        courseModalArrayList.add(new CourseModal("Java", "30 days", "20 Tracks", "Java Self Paced Course", R.drawable.learn_clock_1));
        courseModalArrayList.add(new CourseModal("Python", "30 days", "20 Tracks", "Python Self Paced Course", R.drawable.learn_clock_1));
        courseModalArrayList.add(new CourseModal("DSA", "30 days", "20 Tracks", "DSA Self Paced Course", R.drawable.learn_clock_1));
        courseModalArrayList.add(new CourseModal("PHP", "30 days", "20 Tracks", "PHP Self Paced Course", R.drawable.learn_clock_1));

        // on below line we are creating a variable for our adapter class and passing array list to it.
        final DeckAdapter adapter = new DeckAdapter(courseModalArrayList, this);
        /// cardStack.layoutManager = CardStackLayoutManager();
        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);

        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(GuessTimeActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(GuessTimeActivity.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(GuessTimeActivity.this, "No more courses present", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
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
                            player.stop();
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
                                if (player != null) {
                                    player.stop();
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

    public void swipeLeftCardOnCorrectOptionClicked() {

        cardStack.swipeTopCardLeft(500);
    }

}
