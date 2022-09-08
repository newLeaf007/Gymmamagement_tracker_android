package com.example.gymmamagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.modals.PaymentMember;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity{

    UserService userService;
    List<PaymentMember> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Utils.blackIconStatusBar(PaymentActivity.this,R.color.light_background);
        userService = AppUtils.getUserService();

        getAllPayment("");
    }

    private void getAllPayment(String filter) {
        Call<List<PaymentMember>> call = userService.gettAllPayment();
        call.enqueue(new Callback<List<PaymentMember>>() {
            @Override
            public void onResponse(Call<List<PaymentMember>> call, Response<List<PaymentMember>> response) {
                {
                    if(response.isSuccessful()){
                        list = response.body();
                        if(list!=null && list.size()>=0){

                            Toast.makeText(PaymentActivity.this,"get all the list",Toast.LENGTH_LONG).show();
                            RecyclerView recyclerView = findViewById(R.id.recyclePayment);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            CustomPaymentAdaptor customAdaptor =new CustomPaymentAdaptor();


                                customAdaptor = new CustomPaymentAdaptor(list,filter,PaymentActivity.this);
                                Toast.makeText(PaymentActivity.this, filter, Toast.LENGTH_SHORT).show();
                                recyclerView.setAdapter(customAdaptor);


                        }else{
                            Toast.makeText(PaymentActivity.this,"list is empty or null",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(PaymentActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PaymentMember>> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}