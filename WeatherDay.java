package com.example.kursova;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class WeatherDay implements Serializable {
    private String date;
    private String maxTemp;
    private String minTemp;
    private String descr;

    public WeatherDay(String date, String maxTemp, String minTemp, String descr) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.descr = descr;
    }

    public WeatherDay(String s){
        String [] arr=s.split(",.");
        this.date=arr[0];
        this.minTemp=arr[1];
        this.maxTemp=arr[2];
        this.descr=arr[3];
    }

    public String getDate() {
        return date;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getDescr() {
        return descr;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "Date: " +this.getDate()+"\nMinimal temperature: "+this.getMinTemp()+"\nMaximal temperature: "+this.getMaxTemp()+"\nDescription: "+this.getDescr()+"\n\n";
        return result;
    }
}
