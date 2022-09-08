package com.example.gymmamagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    UserService userService;
    List<Member> list=new ArrayList<>();
    private EditText search;
    private ImageView searchIcon;
    public String filter="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        //get refernce of recycle view
        Utils.blackIconStatusBar(MemberActivity.this,R.color.light_background);
        userService = AppUtils.getUserService();
        getAllMember("");

        search = findViewById(R.id.search);
        searchIcon=findViewById(R.id.searchIcon);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = search.getText().toString();
                getAllMember(filter);

            }
        });


    }



    @SuppressLint("LongLogTag")
    private void getAllMember(String filter) {
        Log.i("log info function start to get information of member","value is empty now");
        Call<List<Member>> call = userService.getMember();
        call.enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if(response.isSuccessful()){
                     list = response.body();
                    if(list!=null && list.size()>=0){

                        Toast.makeText(MemberActivity.this,"get all the list",Toast.LENGTH_LONG).show();
                        RecyclerView recyclerView = findViewById(R.id.recycleMember);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        CustomAdaptor customAdaptor =new CustomAdaptor();
                            if(filter.equals("")){

                                customAdaptor = new CustomAdaptor(list,MemberActivity.this,filter);
                                Toast.makeText(MemberActivity.this, filter, Toast.LENGTH_SHORT).show();
                            }else{
                                List<Member> filterList = new ArrayList<>();
                                for(int i=0;i<list.size();i++){
                                    if(filter.trim().equals(list.get(i).getName())){
                                        Member currentMember = new Member();
                                        currentMember.setName(list.get(i).getName());
                                        currentMember.setPhone(list.get(i).getPhone());
                                        currentMember.setAddress(list.get(i).getAddress());
                                        filterList.add(currentMember);
                                    }
                                }
                                customAdaptor = new CustomAdaptor(filterList,MemberActivity.this,filter);
                                Toast.makeText(MemberActivity.this, "filter is applied", Toast.LENGTH_SHORT).show();
                            }



                        recyclerView.setAdapter(customAdaptor);


                    }else{
                        Toast.makeText(MemberActivity.this,"list is empty or null",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MemberActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                Toast.makeText(MemberActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}