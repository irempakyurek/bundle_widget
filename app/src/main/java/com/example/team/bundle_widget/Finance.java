package com.example.team.bundle_widget;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS on 5.08.2019.
 */

public class Finance {

    public Symbol symbol;
    public String date;
    public Double price;
    public Double openingPrice;
    public Integer trend;


    public Finance(Symbol symbol, String date, Double price, Double openingPrice, Integer trend){

        this.symbol = symbol;
        this.date = date;
        this.price = price;
        this.openingPrice = openingPrice;
        this.trend = trend;

    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String  getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(Double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public Integer getTrend() {
        return trend;
    }

    public void setTrend(Integer trend) {
        this.trend = trend;
    }


}
