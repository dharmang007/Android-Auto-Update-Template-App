package com.aware.androidupdatetemplateapp;
public class Version{
    private String appName;
    private String number;
    private String location;
    private String os;

    public Version(String appName, String number, String location, String os) {
        this.appName = appName;
        this.number = number;
        this.location = location;
        this.os = os;
    }
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }



}
