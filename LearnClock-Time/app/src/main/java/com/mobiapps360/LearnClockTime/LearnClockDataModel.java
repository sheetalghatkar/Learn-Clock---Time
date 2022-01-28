package com.mobiapps360.LearnClockTime;

public class LearnClockDataModel {
    private String imageName;
    private int imageId;

    public LearnClockDataModel(String imageName, int imageId) {
        this.imageName = imageName;
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
