package com.example.gymmamagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.modals.Payment;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPaymentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView header;
    private Spinner spinner,spinnerMonth;
    private EditText amount;
    private Button register;
    private ImageView memberImage;
    ArrayAdapter<String> paymentAdaptor;
    ArrayAdapter<String> monthAdaptor;
    String name="";
    Integer id=0;
    String image;
    UserService userService;

    String paymentSelected;
    String monthSelected;

    String[] paymentMode = {"Select the payment mode","Google pay","Phone Pe","Paytm","Cash"};
    String[] monthMode ={"Select the month","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_payment);

        id = getIntent().getExtras().getInt("id");
        name = getIntent().getExtras().getString("name");
        image = getIntent().getExtras().getString("image");

        Toast.makeText(this, "Provide the payment of "+name, Toast.LENGTH_LONG).show();
        intialization();
        register.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);


    }

    private void intialization() {
        header = findViewById(R.id.header);
        header.setText("Payment for "+name);
        memberImage = findViewById(R.id.memberImage);
        amount = findViewById(R.id.amount);
        register = findViewById(R.id.register);
        spinner = findViewById(R.id.spinner);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        Picasso.get().load(AppUtils.base_url+"/member/image/"+image).into(memberImage);
        userService= AppUtils.getUserService();
        paymentAdaptor = new ArrayAdapter<String>(this, soup.neumorphism.R.layout.support_simple_spinner_dropdown_item,paymentMode);
        spinner.setAdapter(paymentAdaptor);

        monthAdaptor = new ArrayAdapter<String>(this, soup.neumorphism.R.layout.support_simple_spinner_dropdown_item,monthMode);
        spinnerMonth.setAdapter(monthAdaptor);


    }

    @Override
    public void onClick(View view) {

        paymentSelected = spinner.getSelectedItem().toString();
        monthSelected = spinnerMonth.getSelectedItem().toString();

        Integer amountTxt = Integer.valueOf(amount.getText().toString());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        String yearTxt = String.valueOf(year);

        Payment payment = new Payment();
        payment.setAmount(amountTxt);
        payment.setMonth(monthSelected);
        payment.setYear(yearTxt);
        payment.setType(paymentSelected);

        doRegisterPayment(payment,id);
        Toast.makeText(this, payment.toString(), Toast.LENGTH_LONG).show();

    }

    private void doRegisterPayment(Payment payment,Integer id) {
        {
            Call<Payment> call = userService.createPayment(payment,id);
            call.enqueue(new Callback<Payment>() {
                @Override
                public void onResponse(Call<Payment> call, Response<Payment> response) {
                    if(response.isSuccessful()){
                        Payment payment1 = response.body();
                        if(payment1!=null){
                            Toast.makeText(RegisterPaymentActivity.this, "Payment entered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterPaymentActivity.this,PaymentActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegisterPaymentActivity.this, "payment has not been made", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterPaymentActivity.this, "something went wrong , Try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Payment> call, Throwable t) {
                    Toast.makeText(RegisterPaymentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}