package com.example.gymmamagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText name,phone,address;
    private Button register;
    Member member = new Member();
    SharedPreferences mPreferences;
    UserService userService;
    String token="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Utils.blackIconStatusBar(RegisterActivity.this,R.color.light_background);

        userService = AppUtils.getUserService();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        register = findViewById(R.id.register);

        //Log.i("log shared Prefernce", mPreferences.getString("token",null));
        //token = "Bearer "+mPreferences.getString("token",null);

        register.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                String phoneTxt = phone.getText().toString();
                String addressTxt = address.getText().toString();

                Log.i("log shared value of register activites", nameTxt+","+phoneTxt+","+addressTxt);
                if(ValidateData(nameTxt,phoneTxt,addressTxt)){
                    member.setName(nameTxt);
                    member.setAddress(addressTxt);
                    member.setPhone(phoneTxt);
                    doRegister(member);
                }
            }

            private void doRegister(Member member) {
                Call<Member> call = userService.createMember(member);
                call.enqueue(new Callback<Member>() {
                    @Override
                    public void onResponse(Call<Member> call, Response<Member> response) {
                        if(response.isSuccessful()){
                            Member reposneMember = response.body();
                            Log.i("log shared member", reposneMember.toString());
                            Toast.makeText(RegisterActivity.this, "Register is completed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,UploadActivity.class);
                            Bundle bundle = new Bundle();
                            intent.putExtra("id",reposneMember.getId());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegisterActivity.this,"Something is error",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Member> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private boolean ValidateData(String nameTxt, String phoneTxt, String addressTxt) {
                if(nameTxt==null && nameTxt.trim().length()==0){
                    Toast.makeText(RegisterActivity.this,"name should not be null",Toast.LENGTH_LONG).show();
                    return false;
                }
                if(phoneTxt==null && phoneTxt.trim().length()==0){
                    Toast.makeText(RegisterActivity.this,"Phone number should not be null ",Toast.LENGTH_LONG).show();
                    return false;
                }
                if(addressTxt==null && addressTxt.trim().length()==0){
                    Toast.makeText(RegisterActivity.this,"Address should not be null or grater than zero",Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }
        });

    }
}