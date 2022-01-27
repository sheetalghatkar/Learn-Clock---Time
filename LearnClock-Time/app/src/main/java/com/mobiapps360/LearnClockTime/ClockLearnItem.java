package com.mobiapps360.LearnClockTime;

import android.media.Image;

public class ClockLearnItem {



    public String strClockDesc;

    public String getStrImgName() {
        return strImgName;
    }

    public void setStrImgName(String strImgName) {
        this.strImgName = strImgName;
    }

    public String strImgName;



    public ClockLearnItem(String strClockDesc, String strImgName) {
        this.strClockDesc = strClockDesc;
        this.strImgName = strImgName;
    }

    public String getStrClockDesc() {
        return strClockDesc;
    }

    public void setStrClockDesc(String strClockDesc) {
        this.strClockDesc = strClockDesc;
    }
}
