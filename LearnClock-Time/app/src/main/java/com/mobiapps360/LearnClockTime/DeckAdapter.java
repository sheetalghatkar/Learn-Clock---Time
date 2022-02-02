package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DeckAdapter extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<CourseModal> courseData;
    private Context context;

    // on below line we have created constructor for our variables.
    public DeckAdapter(ArrayList<CourseModal> courseData, Context context) {
        this.courseData = courseData;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return courseData.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return courseData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // in get view method we are inflating our layout on below line.
        View v = convertView;
        GuessClockView guessClockView;
        ImageButton buttonGuessOption1;
        ImageButton buttonGuessOption2;
        ImageButton buttonGuessOption3;
        ImageButton buttonGuessOption4;

        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_rv_item, parent, false);
        }
        // on below line we are initializing our variables and setting data to our variables.
        guessClockView = v.findViewById(R.id.clockViewGuessTime);

        buttonGuessOption1 = v.findViewById(R.id.buttonGuessOption1);
        buttonGuessOption2 = v.findViewById(R.id.buttonGuessOption2);
        buttonGuessOption3 = v.findViewById(R.id.buttonGuessOption3);
        buttonGuessOption4 = v.findViewById(R.id.buttonGuessOption4);
        guessClockView.setHour = 10;
        guessClockView.setMinute = 0;


        buttonGuessOption1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        buttonGuessOption1.setImageResource(R.drawable.green_bubble);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        buttonGuessOption1.setImageResource(R.drawable.green_bubble);
//                        GuessTimeActivity activity = (GuessTimeActivity) context;
//                        activity.swipeRightCardOnCorrectOptionClicked();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                GuessTimeActivity activity = (GuessTimeActivity) context;
                                activity.swipeRightCardOnCorrectOptionClicked();
                            }
                        }, 500);
                        break;
                    }
                }
                return true;
            }
        });
        buttonGuessOption2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        GuessTimeActivity activity = (GuessTimeActivity) context;
                        activity.swipeRightCardOnCorrectOptionClicked();
                    }
                }
                return true;
            }
        });
        buttonGuessOption3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        GuessTimeActivity activity = (GuessTimeActivity) context;
                        activity.swipeRightCardOnCorrectOptionClicked();
                    }
                }
                return true;
            }
        });
        buttonGuessOption4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        GuessTimeActivity activity = (GuessTimeActivity) context;
                        activity.swipeRightCardOnCorrectOptionClicked();
                    }
                }
                return true;
            }
        });
        return v;
    }
}

