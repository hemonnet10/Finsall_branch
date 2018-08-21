package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.finsall.R;

import org.json.JSONObject;

public class KYCCustomerDetailActivity extends BaseActivity {


    //Mandetory fields
    private EditText etName;
    private EditText etMobileNo;
    private EditText etEmailId;
    private EditText etPAN;
    private EditText etAdhaarVID;

    // non mandetory fields

    private EditText etFatherName;
    private EditText etDOB;
    private EditText etAddressLine1;
    private EditText etAddressLine2;
    private EditText etDistrict;
    private Spinner spinnerState;
    private EditText etPincode;

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

        etFatherName=(EditText)findViewById(R.id.etFatherName);
        etDOB=(EditText)findViewById(R.id.etDOB);
        etAddressLine1=(EditText)findViewById(R.id.etAddressLine1);
        etAddressLine2=(EditText)findViewById(R.id.etAddressLine2);
        etDistrict=(EditText)findViewById(R.id.etDistrict);
        spinnerState=(Spinner) findViewById(R.id.spinnerState);
        etPincode=(EditText)findViewById(R.id.etPinCode);


        //populate Fields
//        etName.setText();
//        etMobileNo.setText();
//        etEmailId.setText();
//        etAdhaarVID.setText();
//        etPAN.setText();
//
//        etFatherName.setText();
//        etDOB.setText();
//        etAddressLine1.setText();
//        etAddressLine2.setText();
//        etDistrict.setText();
//        spinnerState.setAdapter();
//        etPincode.setText();




    }

    @Override
    protected void handleSuccessResult(JSONObject success) {


    }

    @Override
    protected void handleErrorResult(String error) {

    }


    public void onSelected(View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateCustomerDetailFromKYC(View view) {

        if(isNotValidRequired(etName))
            return;
        if( isNotValidRequired(etMobileNo))
            return;
        if(isNotValidRequired(etEmailId))
            return;
        if(isNotValidRequired(etAdhaarVID))
            return;
        if(isNotValidRequired(etPAN))
            return;

        if(!TextUtils.isEmpty(etDOB.getText()) && isNotValidDOB(etDOB))
            return;

        Intent intent=new Intent(this,FingerPrintScanningActivity.class);
        startActivity(intent);
    }
}
