package com.mobiapps360.LearnClockTime;

public class SetTimeItem {
    int hour;
    int minutes;

    public int getHourSound() {
        return hourSound;
    }

    public void setHourSound(int hourSound) {
        this.hourSound = hourSound;
    }

    public int getMinutesSound() {
        return minutesSound;
    }

    public void setMinutesSound(int minutesSound) {
        this.minutesSound = minutesSound;
    }

    int hourSound;
    int minutesSound;
    String timeString;
    String soundString;
    int soundCount;

    public int getSetHourHand() {
        return setHourHand;
    }

    public void setSetHourHand(int setHourHand) {
        this.setHourHand = setHourHand;
    }

    public int getSetMinuteHand() {
        return setMinuteHand;
    }

    public void setSetMinuteHand(int setMinuteHand) {
        this.setMinuteHand = setMinuteHand;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    int setHourHand;
    int setMinuteHand;
    int result;  // 0 not set, 1 correct set, 2 incorrect set

    public SetTimeItem(int hour, int minutes, int hourSound, int minutesSound, String timeString, String soundString, int soundCount, int setHourHand, int setMinuteHand, int result) {
        this.hour = hour;
        this.minutes = minutes;
        this.hourSound = hourSound;
        this.minutesSound = minutesSound;
        this.timeString = timeString;
        this.soundString = soundString;
        this.soundCount = soundCount;
        this.setHourHand = setHourHand;
        this.setMinuteHand = setMinuteHand;
        this.result = result;
    }

    public String getSoundString() {
        return soundString;
    }

    public void setSoundString(String soundString) {
        soundString = soundString;
    }

    public int getSoundCount() {
        return soundCount;
    }

    public void setSoundCount(int soundCount) {
        this.soundCount = soundCount;
    }


    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }


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


}
