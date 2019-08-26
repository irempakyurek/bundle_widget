package com.example.team.bundle_widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    GetFinanceDataService getFinanceDataService;
    String base_symbol_code = "TRY";
    String symbol_code = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }


}
