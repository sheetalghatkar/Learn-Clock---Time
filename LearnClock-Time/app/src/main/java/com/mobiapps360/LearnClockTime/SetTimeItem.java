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

    public SetTimeItem(int hour, int minutes, int hourSound, int minutesSound, String timeString, String soundString, int soundCount) {
        this.hour = hour;
        this.minutes = minutes;
        this.hourSound = hourSound;
        this.minutesSound = minutesSound;
        this.timeString = timeString;
        this.soundString = soundString;
        this.soundCount = soundCount;
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
