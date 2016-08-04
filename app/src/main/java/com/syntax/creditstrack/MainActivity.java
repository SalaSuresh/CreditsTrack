package com.syntax.creditstrack;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    public String userName = "Suresh Sala";

    private FloatingActionButton mfab_addTransaction;

    private List<Credits> creditsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CreditsAdapter mAdapter;

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(""+db.getUserBalance());
        if (db.getUserBalance()<0)
            collapsingToolbarLayout.setBackgroundColor(Color.rgb(198, 40, 40));
        else
            collapsingToolbarLayout.setBackgroundColor(Color.rgb(46, 125, 50));

        mfab_addTransaction = (FloatingActionButton) findViewById(R.id.fab);
        mfab_addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "this is floating button", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AddCreditActivity.class));
            }
        });



        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.e("credit", "Inserting ..");
        db.addTransaction(new Credits("Ramki", 300, "notcleared", "before vacation"));
        db.addTransaction(new Credits("Syam", -1000, "cleared", "chit amount"));
        db.addTransaction(new Credits("Syam", 300, "notcleared", "personal amount"));
        db.addTransaction(new Credits("Prasanna", -2000, "notcleared", "chit amount"));

        // Reading all contacts
        Log.e("credit", "Reading all contacts..");
        List<Credits> credits = db.getAllCredits();

        for (Credits crd : credits) {
            String log = "Id: "+crd.getID()
                    +" ,Name: " + crd.getName()
                    + " ,Transaction Value: " + crd.getTransactionValue()
                    + " ,Transaction Status: " + crd.getTransactionStatus()
                    + " ,Transaction Note: " + crd.getTransactionNote();
            // Writing Contacts to log
            Log.e("credit", "Name: "+log);
        }

        List<Credits> distinctCreditNames = db.getDistinctCreditNames();
        for (Credits dcn : distinctCreditNames) {
            Log.e("credit", "distinctCreditNames: "+dcn.getName());
        }

        Log.e("credit", "User Balance: "+ db.getUserBalance());

        List<Credits> distinctCreditsSum = db.getDistinctCreditsSum();
        for (Credits dcs : distinctCreditsSum) {
            Log.e("credit", "distinctCreditsSum: "+dcs.getName());
            Log.e("credit", "distinctCreditsSum: "+dcs.getTransactionValue());
        }

        List<Credits> distinctCredits = db.getDistinctCredits("Syam");
        for (Credits dc : distinctCredits) {
            Log.e("credit", "distinctCredits: "+dc.getName());
            Log.e("credit", "distinctCredits: "+dc.getTransactionValue());
        }


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//
//        mAdapter = new CreditsAdapter(distinctCreditsSum);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);

        prepareCreditsData();
    }



    private void prepareCreditsData() {
//        Credits credits = new Credits("Mad Max: Fury Road", 10);
//        creditsList.add(credits);
//
//        credits = new Credits("Inside Out", 10);
//        creditsList.add(credits);
//
//        mAdapter.notifyDataSetChanged();
        DatabaseHandler db = new DatabaseHandler(this);
        List<Credits> distinctCreditsSum = db.getDistinctCreditsSum();
        for (Credits dcs : distinctCreditsSum) {
            Log.e("credit", "distinctCreditsSum-----: "+dcs.getName());
            Log.e("credit", "distinctCreditsSum-----: "+dcs.getTransactionValue());
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new CreditsAdapter(distinctCreditsSum);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

}
