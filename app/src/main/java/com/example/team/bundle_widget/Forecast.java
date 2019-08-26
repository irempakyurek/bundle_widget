package com.example.team.bundle_widget;

import java.text.SimpleDateFormat;

/**
 * Created by ASUS on 8.08.2019.
 */

public class Forecast {

    public Integer hour;
    public Integer period;
    public String date;
    public Integer temperature;
    public Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Forecast(Integer hour, Integer period, String date, Integer temperature, Symbol symbol){

    }
    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.date = date;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }



}
