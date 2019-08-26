package com.example.team.bundle_widget;

import java.util.ArrayList;

/**
 * Created by ASUS on 8.08.2019.
 */

public class Weather {

    public Data data;
    public Integer responseCode;
    public Object error;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Weather(Data data, Integer responseCode, Object error){

    }

}