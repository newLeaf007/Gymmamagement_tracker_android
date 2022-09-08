package com.example.gymmamagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavActivity extends AppCompatActivity {



    private TextView memberList,registeredMember,paymentDetails,dashboard;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);



        logo = findViewById(R.id.logo);

        memberList = findViewById(R.id.memberList);
        registeredMember =findViewById(R.id.registeredMember);
        paymentDetails=findViewById(R.id.paymentDetails);
        dashboard =findViewById(R.id.dashboard);


        memberList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavActivity.this,MemberActivity.class);
                startActivity(intent);
            }
        });

        registeredMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavActivity.this,RegisterActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NavActivity.this,
                        Pair.create(logo,"logo"),
                        Pair.create(registeredMember,"register")
                        );
                startActivity(intent,options.toBundle());
            }
        });

        paymentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}