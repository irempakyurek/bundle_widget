package com.example.team.bundle_widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherData {

    public WeatherData(List<Forecast> forecasts){

    }

    public List<Forecast> forecasts = null;

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }


}
