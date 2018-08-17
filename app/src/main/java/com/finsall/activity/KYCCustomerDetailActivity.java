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

public class KYCCustomerDetailActivity extends BaseActivity {


    private EditText etName;
    private EditText etMobileNo;
    private EditText etEmailId;
    private EditText etAdhaarVID;
    private EditText etPAN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_customer_detail);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etName=(EditText)findViewById(R.id.etName);
        etMobileNo=(EditText)findViewById(R.id.etMobileNo);
        etEmailId=(EditText)findViewById(R.id.etEmailId);
        etAdhaarVID=(EditText)findViewById(R.id.etAdhaarOrVID);
        etPAN=(EditText)findViewById(R.id.etPan);
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


}
