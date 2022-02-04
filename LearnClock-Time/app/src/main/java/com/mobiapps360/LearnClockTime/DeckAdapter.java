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

        guessClockView.setHour = guessTimeData.get(position).hour;
        guessClockView.setMinute = guessTimeData.get(position).minutes;


        txtViewOption1.setText(guessTimeData.get(position).arrayOption.get(0));
        txtViewOption2.setText(guessTimeData.get(position).arrayOption.get(1));
        txtViewOption3.setText(guessTimeData.get(position).arrayOption.get(2));
        txtViewOption4.setText(guessTimeData.get(position).arrayOption.get(3));


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
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 500);
                            break;
                        } else {
                            //buttonGuessOption1.setImageResource(R.drawable.red_bubble);
                            buttonGuessOption1.setBackgroundResource(R.drawable.border_learn_view);

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
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 500);
                            break;
                        } else {
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
                            buttonGuessOption3.setImageResource(R.drawable.green_bubble);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 500);
                            break;
                        } else {
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
                            buttonGuessOption4.setImageResource(R.drawable.green_bubble);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GuessTimeActivity activity = (GuessTimeActivity) context;
                                    activity.swipeRightCardOnCorrectOptionClicked();
                                }
                            }, 500);
                            break;
                        } else {
                            buttonGuessOption4.setImageResource(R.drawable.red_bubble);
                        }
                    }
                }
                return true;
            }
        });
        return v;
    }
}

