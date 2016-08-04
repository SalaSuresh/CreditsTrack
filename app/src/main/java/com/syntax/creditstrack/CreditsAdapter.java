package com.syntax.creditstrack;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dumadu on 7/27/2016.
 */
public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.MyViewHolder> {

    private List<Credits> creditsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, balance;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            balance = (TextView) view.findViewById(R.id.balance);
        }
    }


    public CreditsAdapter(List<Credits> moviesList) {
        this.creditsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.credits_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Credits credits = creditsList.get(position);
        holder.name.setText(credits.getName());
        if (credits.getTransactionValue()<0){
            holder.balance.setTextColor(Color.rgb(198, 40, 40));
            holder.balance.setText(""+credits.getTransactionValue());
        }else {
            holder.balance.setTextColor(Color.rgb(46, 125, 50));
            holder.balance.setText(""+credits.getTransactionValue());
        }
    }

    @Override
    public int getItemCount() {
        return creditsList.size();
    }
}