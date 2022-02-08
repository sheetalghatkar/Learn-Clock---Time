package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class DeckAdapter extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<GuessTimeItem> guessTimeData;
    private Context context;

    // on below line we have created constructor for our variables.
    public DeckAdapter(ArrayList<GuessTimeItem> guessTimeData, Context context) {
        this.guessTimeData = guessTimeData;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return guessTimeData.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return guessTimeData.get(position);
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

        TextView txtViewOption1;
        TextView txtViewOption2;
        TextView txtViewOption3;
        TextView txtViewOption4;

        CardView cardViewOption1;
        CardView cardViewOption2;
        CardView cardViewOption3;
        CardView cardViewOption4;

        TextView textViewCount;

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

        txtViewOption1 = v.findViewById(R.id.txtViewOption1);
        txtViewOption2 = v.findViewById(R.id.txtViewOption2);
        txtViewOption3 = v.findViewById(R.id.txtViewOption3);
        txtViewOption4 = v.findViewById(R.id.txtViewOption4);

        cardViewOption1 = v.findViewById(R.id.cardViewOption1);
        cardViewOption2 = v.findViewById(R.id.cardViewOption2);
        cardViewOption3 = v.findViewById(R.id.cardViewOption3);
        cardViewOption4 = v.findViewById(R.id.cardViewOption4);

        textViewCount = v.findViewById(R.id.txtCount);

        guessClockView.setHour = guessTimeData.get(position).hour;
        guessClockView.setMinute = guessTimeData.get(position).minutes;


        txtViewOption1.setText(guessTimeData.get(position).arrayOption.get(0));
        txtViewOption2.setText(guessTimeData.get(position).arrayOption.get(1));
        txtViewOption3.setText(guessTimeData.get(position).arrayOption.get(2));
        txtViewOption4.setText(guessTimeData.get(position).arrayOption.get(3));

        textViewCount.setText(String.valueOf((position)+1)+"/"+guessTimeData.size());

        GuessTimeActivity activity = (GuessTimeActivity) context;

        buttonGuessOption1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
//                        buttonGuessOption1.setImageResource(R.drawable.green_bubble);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        if (guessTimeData.get(position).getAnswer() == 0) {
                            buttonGuessOption1.setImageResource(R.drawable.green_bubble);
                            activity.playSoundGuessTime("excellent");
                            buttonGuessOption2.setEnabled(false);
                            buttonGuessOption3.setEnabled(false);
                            buttonGuessOption4.setEnabled(false);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 1000);
                            break;
                        } else {
                            activity.playSoundGuessTime("wrong_beep");
                            buttonGuessOption1.setImageResource(R.drawable.red_bubble);

//                            buttonGuessOption1.setBackgroundResource(R.drawable.border_learn_view);

                        }
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
                        if (guessTimeData.get(position).getAnswer() == 1) {
                            buttonGuessOption2.setImageResource(R.drawable.green_bubble);
                            activity.playSoundGuessTime("great_job");
                            buttonGuessOption1.setEnabled(false);
                            buttonGuessOption3.setEnabled(false);
                            buttonGuessOption4.setEnabled(false);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 1000);
                            break;
                        } else {
                            activity.playSoundGuessTime("wrong_beep");
                            buttonGuessOption2.setImageResource(R.drawable.red_bubble);
                        }
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
                        if (guessTimeData.get(position).getAnswer() == 2) {
                            buttonGuessOption2.setEnabled(false);
                            buttonGuessOption1.setEnabled(false);
                            buttonGuessOption4.setEnabled(false);
                            buttonGuessOption3.setImageResource(R.drawable.green_bubble);
                            activity.playSoundGuessTime("perfect");
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 1000);
                            break;
                        } else {
                            activity.playSoundGuessTime("wrong_beep");
                            buttonGuessOption3.setImageResource(R.drawable.red_bubble);
                        }
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
                        if (guessTimeData.get(position).getAnswer() == 3) {
                            buttonGuessOption2.setEnabled(false);
                            buttonGuessOption1.setEnabled(false);
                            buttonGuessOption3.setEnabled(false);
                            buttonGuessOption4.setImageResource(R.drawable.green_bubble);
                            activity.playSoundGuessTime("well_done");
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 1000);
                            break;
                        } else {
                            buttonGuessOption4.setImageResource(R.drawable.red_bubble);
                            activity.playSoundGuessTime("wrong_beep");
                        }
                    }
                }
                return true;
            }
        });
        return v;
    }
}

