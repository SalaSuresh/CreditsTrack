package com.syntax.creditstrack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Dumadu on 8/2/2016.
 */
public class AddCreditActivity extends AppCompatActivity {
    AutoCompleteTextView textNames;
    RadioButton radioButton_They, radioButton_I;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_credit);
        DatabaseHandler db = new DatabaseHandler(this);
        textNames=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_Name);
        radioButton_They = (RadioButton) findViewById(R.id.radioButton_They);
        radioButton_I = (RadioButton) findViewById(R.id.radioButton_I);

        //TextView Name
        List<Credits> distinctCreditNames = db.getDistinctCreditNames();
        String names[] = new String[distinctCreditNames.size()];
        int i = 0;
        for (Credits dcn : distinctCreditNames) {
            Log.e("credit", "distinctCreditNames: "+dcn.getName());
            names[i] = dcn.getName();
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        textNames.setAdapter(adapter);
        textNames.setThreshold(1);

        //Radio Button They
        radioButton_They.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkOweStatus();
                radioButton_I.setChecked(false);
            }
        });

        //Radio Button I
        radioButton_I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                checkOweStatus();
                radioButton_They.setChecked(false);
            }
        });
    }

    public void checkOweStatus(){
        boolean status_They = radioButton_They.isChecked();
        boolean status_I = radioButton_I.isChecked();
        Toast.makeText(AddCreditActivity.this, "THEY: "+status_They+"\n I: "+status_I, Toast.LENGTH_SHORT).show();
    }
}
