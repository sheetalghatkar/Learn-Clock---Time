package com.mobiapps360.LearnClockTime;

public class OtherAppsItem {
    String appIcon;
    String appName;
    String appLink;

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppLink() {
        return appLink;
    }

    public void setAppLink(String appLink) {
        this.appLink = appLink;
    }

    public OtherAppsItem(String appIcon, String appName, String appLink) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.appLink = appLink;
    }
}
