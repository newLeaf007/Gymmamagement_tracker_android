package com.example.gymmamagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymmamagement.modals.Dashboard;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;

import java.util.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView totalMember,totalPayment,totalMonthPayment;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Utils.blackIconStatusBar(DashboardActivity.this,R.color.light_background);

        totalMember = findViewById(R.id.totalMember);
        totalPayment = findViewById(R.id.totalPayment);
        totalMonthPayment=findViewById(R.id.totalMonthPayment);

        userService = AppUtils.getUserService();
        initalizeDashBoard();


    }

    private void initalizeDashBoard() {
        Call<Dashboard> call = userService.getDashBoard();
        call.enqueue(new Callback<Dashboard>() {
            @Override
            public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                if(response.isSuccessful()){
                    Dashboard dashboard = response.body();
                    if(dashboard!=null){
                        Log.i("dashboard======",dashboard.toString());
                        totalMember.setText(String.valueOf(dashboard.getTotalMmember()));
                        totalMonthPayment.setText(Currency.getInstance("INR").getSymbol() +String.valueOf(dashboard.getTotalPayment()));
                    }else{
                        Toast.makeText(DashboardActivity.this, "Dashboard is getting null value", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DashboardActivity.this, "Something wnet worong with api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dashboard> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}