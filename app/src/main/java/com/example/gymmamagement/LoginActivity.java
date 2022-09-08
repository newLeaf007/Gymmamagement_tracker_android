package com.example.gymmamagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gymmamagement.modals.Login;
import com.example.gymmamagement.modals.ResponseToken;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;

import org.json.JSONObject;
import org.json.JSONStringer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout loginMain;
    private Animation fadeIn;
    private EditText phone,password;
    private Button login;
    private ImageView logo;
    UserService userService;
    SharedPreferences mPreferences =null;
    Login loginM = new Login();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utils.blackIconStatusBar(LoginActivity.this,R.color.light_background);

        loginMain = findViewById(R.id.loginMain);

        fadeIn = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.fade_in);
        fadeIn.setDuration(1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginMain.setVisibility(View.VISIBLE);

                loginMain.setAnimation(fadeIn);
            }
        },1000);
        logo = findViewById(R.id.logo);
        phone = findViewById(R.id.phone);
        password= findViewById(R.id.password);
        login = findViewById(R.id.login);
        userService = AppUtils.getUserService();
        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                String phoneTxt = phone.getText().toString();
                String passwordTxt = password.getText().toString();
                //validate form
                Log.i(" log info value from the activities---------------------------- ",phoneTxt+","+passwordTxt );
                if(validateLogin(phoneTxt,passwordTxt)){

                    loginM.setUsername(phoneTxt);
                    loginM.setPassword(passwordTxt);
                    doLogin(loginM);
                }else{

                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void doLogin(Login login) {
        Log.i(" log info inside the doLogin before calling function","username="+login.getUsername());
        Call<ResponseToken> call = userService.login(login);
        call.enqueue(new Callback<ResponseToken>() {
            @Override
            public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                if(response.isSuccessful()){
                    ResponseToken token =  response.body();
                    if(token.getToken()!=null && token.getToken().length()!=0){
                        //send to the member list
                        //Toast.makeText(LoginActivity.this,"Login success" ,Toast.LENGTH_LONG).show();

                       // saveToken(token.getToken());
                       Intent intent = new Intent(LoginActivity.this,NavActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"the username/phone number or password is worng",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"something is error here",Toast.LENGTH_LONG).show();
                }
            }

            public void saveToken(String token) {
                mPreferences = context.getSharedPreferences("myPref",0);
                SharedPreferences.Editor  editor = mPreferences.edit();
                editor.putString("token", token);
                editor.apply();;
            }

            @Override
            public void onFailure(Call<ResponseToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLogin(String phoneTxt, String passwordTxt) {
        if(phoneTxt == null || phoneTxt.trim().length()==0){
            Toast.makeText(this,"Phone number is required as username",Toast.LENGTH_LONG).show();
            return false;
        }
        if(passwordTxt ==null || passwordTxt.trim().length()==0){
            Toast.makeText(this,"password field must have some value",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}