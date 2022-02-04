package com.mobiapps360.LearnClockTime;

import java.util.ArrayList;

public class GuessTimeItem {
    int hour;
    int minutes;
    public ArrayList<String> arrayOption;
    int answer;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public ArrayList<String> getArrayOption() {
        return arrayOption;
    }

    public void setArrayOption(ArrayList<String> arrayOption) {
        this.arrayOption = arrayOption;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public GuessTimeItem(int hour, int minutes, ArrayList<String> arrayOption, int answer) {
        this.hour = hour;
        this.minutes = minutes;
        this.arrayOption = arrayOption;
        this.answer = answer;
    }
}
