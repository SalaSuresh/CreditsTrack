package com.syntax.creditstrack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

/**
 * Created by Dumadu on 8/2/2016.
 */
public class AddCreditActivity extends AppCompatActivity {
    AutoCompleteTextView textNames;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_credit);
        DatabaseHandler db = new DatabaseHandler(this);
        textNames=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView_Name);

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
    }
}
