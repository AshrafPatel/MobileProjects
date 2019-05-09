package com.example.android.projectweather;

public class Weather {
    private String country, placeName, condition, desc;
    private double temp;
    private int picture;

    public Weather(String country, String placeName, String condition, String desc, double temp) {
        this.country = country;
        this.placeName = placeName;
        this.condition = condition;
        this.desc = desc;
        this.temp = temp;
    }

    public String getCountry() {
        return country;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getMain() {
        return condition;
    }

    public String getDesc() {
        return desc;
    }

    public double getTemp() {
        return temp;
    }

    public void setPicture(String desc) {
        int picture;
        if (desc.contains("rain")) {
            picture = R.drawable.iconfinder_overcast_256;
        }
        else if (desc.toLowerCase().contains("snow")){
            picture = R.drawable.iconfinder_snow_occasional_256;
        }
        else if (desc.toLowerCase().contains("fog") || desc.toLowerCase().contains("clouds")) {
            picture = R.drawable.iconfinder_fog_256;
        }
        else if (desc.toLowerCase().contains("hail")) {
            picture = R.drawable.iconfinder_hail_heavy_256;
        }
        else if (desc.toLowerCase().contains("sleet")) {
            picture = R.drawable.iconfinder_sleet_256;
        }
        else {
            picture = R.drawable.iconfinder_sunny_256;
        }
        this.picture = picture;
    }

    public int getPicture() {
        return picture;
    }
}
