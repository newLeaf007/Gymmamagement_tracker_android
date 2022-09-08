package com.example.gymmamagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmamagement.modals.PaymentMember;
import com.example.gymmamagement.remote.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomPaymentAdaptor extends RecyclerView.Adapter<CustomPaymentAdaptor.MyViewHolder> {
    List<PaymentMember> list;
    String filter;
    Context context;

    public CustomPaymentAdaptor(List<PaymentMember> list, String filter, Context context) {
        this.list = list;
        this.filter = filter;
        this.context = context;
    }

    public CustomPaymentAdaptor() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymentlayout,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText((String)list.get(position).getName());
        holder.mode.setText((String)list.get(position).getType());
        holder.amount.setText((String)String.valueOf(list.get(position).getAmount()));
        holder.month.setText((String)list.get(position).getMonth());
        Picasso.get().load(AppUtils.base_url+"/member/image/"+list.get(position).getImageName()).into(holder.imagePay);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,amount,mode,month;
        ImageView imagePay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            mode = itemView.findViewById(R.id.mode);
            month =itemView.findViewById(R.id.month);
            imagePay = itemView.findViewById(R.id.imagePay);
        }
    }
}
