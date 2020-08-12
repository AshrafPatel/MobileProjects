package com.example.android.projectweather;

public class Logs {
    private String country, city, date, hour, temp;

    public Logs(String country, String city, String date, String hour, String temp) {
        this.country = country;
        this.city = city;
        this.date = date;
        this.hour = hour;
        this.temp = temp;
    }


    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getTemp() {
        return temp;
    }
}
