package com.example.team.bundle_widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class AppWidgetConfigure extends Activity {
    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    GetFinanceDataService getFinanceDataService;
    String base_symbol_code = "TRY";
    String symbol_code = "USD";

    private ProgressBar progressBar;
    private ImageView BtnRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
   /*     final Context context = AppWidgetConfigure.this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.example_appwidget);

        appWidgetManager.updateAppWidget(appWidgetId, views);*/
        getFinanceDataService = RetrofitInstance.getFinanceClient().create(GetFinanceDataService.class);
        TextView usdPriceText = findViewById(R.id.usdPrice);
        ImageView usdLogo = findViewById(R.id.usdLogo);
        Call<List<Finance>> call = getFinanceDataService.getFinanceData(base_symbol_code, symbol_code);

        call.enqueue(new Callback<List<Finance>>() {
            @Override
            public void onResponse(Call<List<Finance>> call, Response<List<Finance>> response) {
                List<Finance> financeList=new ArrayList<>();
                financeList=response.body();
                for (int i=0;i<financeList.size();i++){
                    //System.out.println(""+financeList.get(i).price+"\n");
                    String price = String.format("%.3f", financeList.get(i).price);
                    usdPriceText.setText(price);
                }
            }
            @Override
            public void onFailure(Call<List<Finance>> call, Throwable t) {
            }
        });
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();

    }


}
