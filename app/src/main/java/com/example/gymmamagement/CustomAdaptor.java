package com.example.gymmamagement;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.remote.AppUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomAdaptor  extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder> {

    List<Member> list;
    String filter;
    Context context;


    public CustomAdaptor(List<Member> list, Context context,String filter) {
        this.list = list;
        this.filter=filter;
        this.context = context;
    }

    public CustomAdaptor() {

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @NonNull
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)   {

                holder.name.setText((String) list.get(position).getName());
                holder.phone.setText((String) list.get(position).getPhone());
                holder.address.setText((String) list.get(position).getAddress());
                Log.i("image url",AppUtils.base_url+"/member/image/"+list.get(position).getImageName());
                 Picasso.get().load(AppUtils.base_url+"/member/image/"+list.get(position).getImageName()).into(holder.imageMember);

                Integer id = list.get(position).getId();
                String name = list.get(position).getName();
                String image = list.get(position).getImageName();

                Log.i("id and name",id+""+name);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),RegisterPaymentActivity.class);
                        Bundle bundle = new Bundle();

                        intent.putExtra("id",id);
                        intent.putExtra("name",name);
                        intent.putExtra("image",image);
                        intent.putExtras(bundle);
                        view.getContext().startActivity(intent);
                    }
                });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,phone,address;
        ImageView imageMember;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            imageMember = itemView.findViewById(R.id.imageMember);
        }
    }
}
