package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.finsall.R;

import org.json.JSONObject;

public class IndividualKYCActivity extends BaseActivity {

    private EditText etAadharNo;
    private EditText etAadharOTP;
    RelativeLayout adhaarRL;
    RelativeLayout otpRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_kyc);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        adhaarRL=(RelativeLayout)findViewById(R.id.rlAadhaar);
        otpRL=(RelativeLayout)findViewById(R.id.rlOTP);
        etAadharNo=(EditText)findViewById(R.id.etAdhaar);
        etAadharOTP=(EditText)findViewById(R.id.etOTP);
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }


    public void onSelected(View view) {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void validateOTP(View view) {

        Intent intent= new Intent(this,CustomerDetailActivity.class);

        intent.putExtra("isNewUser","");
        startActivity(intent);

    }

    public void sendOTP(View view) {
        adhaarRL.setVisibility(View.GONE);
        otpRL.setVisibility(View.VISIBLE);
    }



}
