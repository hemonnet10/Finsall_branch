package com.finsall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.finsall.R;
import com.finsall.dto.InsuranceCompany;

import org.json.JSONObject;

public class BAProfileActivity extends BaseActivity {


    private Spinner spinnerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baprofile);
        setTitle(getData("userName"));


        spinnerTitle=(Spinner)findViewById(R.id.spinnerTitle);
        String titleList[] = {"Mr", "Mrs", "Miss", "Ms"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_spinner_dropdown_item, titleList);
        spinnerTitle.setAdapter(arrayAdapter);
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }
}
