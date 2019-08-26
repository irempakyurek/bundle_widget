package com.example.team.bundle_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.RemoteViews;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 2.08.2019.
 */

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    GetFinanceDataService getFinanceDataService;
    GetWeatherDataService getWeatherDataService;
    String base_symbol_code = "TRY";
    String symbol_code = "USD";

    String countryName = "";
    String cityName = "Istanbul";
    String provinceName = "Istanbul";
    AppWidgetManager appWidgetManager;
    int[] appWidgetIds;

    //MARK: - Life Cycle Methods
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        /*if (appWidgetManager != null) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }*/
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            this.appWidgetIds = appWidgetIds;
            this.appWidgetManager = appWidgetManager;

            Intent intent = new Intent(context, ExampleAppWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            updateWidget(context, pendingIntent, appWidgetId);
        }
    }

    //MARK - State Check and init methods
    private boolean isNetworkConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = (NetworkInfo.State) manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = (NetworkInfo.State) manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        return mobile ==  NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTED;
    }

    private RemoteViews remoteViews(Context context, PendingIntent pendingIntent){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
        views.setOnClickPendingIntent(R.id.refresh_button, pendingIntent);
        views.setViewVisibility(R.id.no_internet_connection, View.GONE);
        views.setViewVisibility(R.id.widget_layout, View.VISIBLE);
        return  views;
    }

    //MARK: - Setup Hide Update Methods
    private void setupFinance(RemoteViews views, int appWidgetId){
        views.setViewVisibility(R.id.finance_active_layout, View.VISIBLE);
        views.setViewVisibility(R.id.finance_no_active_layout, View.GONE);

        getFinanceDataService = RetrofitInstance.getFinanceClient().create(GetFinanceDataService.class);
        Call<List<Finance>> call = getFinanceDataService.getFinanceData(base_symbol_code, symbol_code);

        call.enqueue(new Callback<List<Finance>>() {
            @Override
            public void onResponse(Call<List<Finance>> call, Response<List<Finance>> response) {
                if (response.body() != null){

                    //getting price from service and formatting three digits
                    String price = String.format("%.3f", response.body().get(0).price );
                    //showing price on a textview on the widget
                    views.setTextViewText(R.id.usdPrice, price);
                    //getting trend from service
                    Integer trend = response.body().get(0).trend;
                    //for showing arrows which are related to up or down
                    Integer img = null;
                    if (trend == 1)
                        img = R.drawable.increasing_arrow;
                    else
                        img = R.drawable.decreasing_arrow;
                    //showing trend vector on the widget
                    views.setImageViewResource(R.id.trend_vector, img);

                    //getting date from service
                    String date = response.body().get(0).date;
                    SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

                    //setting timezone
                    oDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date dateObj = null;
                    try {
                        dateObj = oDateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //setting new date format for the date
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM HH:mm" , new Locale("tr"));
                    outputFormat.setTimeZone(TimeZone.getDefault());
                    String strNewDate = outputFormat.format(dateObj);


                    //showing date on the widget
                    views.setTextViewText(R.id.date, strNewDate);

                    //  views.setViewVisibility(R.id.widget_layout, View.VISIBLE);
                    // views.setViewVisibility(R.id.no_internet_connection, View.GONE);
                    //for updating the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views);

                }

            }
            @Override
            public void onFailure(Call<List<Finance>> call, Throwable t) {

            }
        });
    }

    private void hideFinance(RemoteViews views, int appWidgetId){
        views.setViewVisibility(R.id.finance_no_active_layout, View.VISIBLE);
        views.setViewVisibility(R.id.finance_active_layout, View.GONE);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void setupWeather(RemoteViews views, int appWidgetId) {
        views.setViewVisibility(R.id.weather_active_layout, View.VISIBLE);
        views.setViewVisibility(R.id.weather_no_active_layout, View.GONE);

        getWeatherDataService = RetrofitInstance.getWeatherClient().create(GetWeatherDataService.class);
        Call<Weather> callWeather = getWeatherDataService.getWeatherData(countryName, cityName, provinceName);

        callWeather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> callWeather, Response<Weather> response) {
                if (response.body() != null){

                    //getting forecast from service
                    Forecast forecast = response.body().getData().getWeatherData().getForecasts().get(0);
                    //getting temperature from service
                    String  temp = forecast.temperature.toString();
                    views.setTextViewText(R.id.temp, temp);
                    //getting icon name
                    String icon = forecast.getSymbol().imageName;
                    //setting icon acoording to icon name
                    Integer img = null;
                    if (icon.equals("clear_sky"))
                        img = R.drawable.clear_sky;
                    else if (icon.equals("clear_sky_n"))
                        img = R.drawable.clear_sky_n;
                    else if(icon.endsWith("fair_sky"))
                        img = R.drawable.fair_sky;
                    else if(icon.endsWith("fair_sky_n"))
                        img = R.drawable.fair_sky_n;
                    else if(icon.equals("fog"))
                        img = R.drawable.fog;
                    else if(icon.equals("h_rain"))
                        img = R.drawable.h_rain;
                    else if(icon.equals("overcast"))
                        img = R.drawable.overcast;
                    else if(icon.equals("partly_cloudy"))
                        img = R.drawable.partly_cloudy;
                    else if(icon.equals("partly_cloudy_n"))
                        img = R.drawable.partly_cloudy_n;
                    else if(icon.equals("rain"))
                        img = R.drawable.rain;
                    else if(icon.equals("rain_s"))
                        img = R.drawable.rain_s;
                    else if(icon.equals("rain_s_n"))
                        img = R.drawable.rain_s_n;
                    else if(icon.equals("rain_thunder"))
                        img = R.drawable.rain_thunder;
                    else if(icon.equals("sleet"))
                        img = R.drawable.sleet;
                    else if(icon.equals("snow"))
                        img = R.drawable.snow;
                    else if(icon.equals("snow_thunder"))
                        img = R.drawable.snow_thunder;

                    //showing icon on the widget
                    views.setImageViewResource(R.id.weather_img, img);

                    views.setViewVisibility(R.id.widget_layout, View.VISIBLE);
                    views.setViewVisibility(R.id.no_internet_connection, View.GONE);
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

            }
            @Override
            public void onFailure(Call<Weather> callWeather, Throwable t) {

            }
        });
    }

    private void hideWeather(RemoteViews views, int appWidgetId){
        views.setViewVisibility(R.id.weather_no_active_layout, View.VISIBLE);
        views.setViewVisibility(R.id.weather_active_layout, View.GONE);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void updateWidget(Context context, PendingIntent pendingIntent, int appWidgetId){
        RemoteViews views = remoteViews(context, pendingIntent);

        if (!isNetworkConnected(context)){
            views.setViewVisibility(R.id.widget_layout, View.GONE);
            views.setViewVisibility(R.id.no_internet_connection, View.VISIBLE);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            return;
        }

        boolean isFinanceActive = base_symbol_code != null &&
                !base_symbol_code.isEmpty() &&
                symbol_code != null &&
                !symbol_code.isEmpty();

        if(isFinanceActive) {
            setupFinance(views, appWidgetId);
        } else{
            hideFinance(views, appWidgetId);
        }

        boolean isWeatherActive = countryName != null &&
                !countryName.isEmpty() &&
                cityName != null &&
                !cityName.isEmpty() &&
                provinceName != null &&
                !provinceName.isEmpty();

        if(isWeatherActive){
            setupWeather(views, appWidgetId);
        } else{
            hideWeather(views, appWidgetId);
        }

    }

}
