package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.finsall.R;

import org.json.JSONObject;

public class CustomerDetailActivity extends BaseActivity {


    private EditText etName;
    private EditText etMobileNo;
    private EditText etEmailId;
    private RadioGroup rg;
    private EditText etAdhaarVID;
    private EditText etCIN;
    private EditText etPAN;
    private EditText etTAN;
    private RelativeLayout rlIndividual;
    private RelativeLayout rlCorporate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuatomer_detail);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etName=(EditText)findViewById(R.id.etName);
        etMobileNo=(EditText)findViewById(R.id.etMobileNo);
        etEmailId=(EditText)findViewById(R.id.etEmailId);
        etAdhaarVID=(EditText)findViewById(R.id.etAdhaarOrVID);
        etCIN=(EditText)findViewById(R.id.etCIN);
        etPAN=(EditText)findViewById(R.id.etPan);
        etTAN=(EditText)findViewById(R.id.etTAN);

        rg= (RadioGroup)findViewById(R.id.radioUserType);
        rg.setOnCheckedChangeListener(new CustomerDetailActivity.ChooseUserTypeListener());

        rlCorporate=(RelativeLayout)findViewById(R.id.rlCorporate);
        rlIndividual=(RelativeLayout)findViewById(R.id.rlIndividual);

    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

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

    public void showOTPPage(View view) {

        if(rg.getCheckedRadioButtonId()==-1){
            ((RadioButton)findViewById(R.id.radioCorporate)).setError(getString(R.string.error_field_required));
            return;
        }
        Intent intent= new Intent(this,OTPActivity.class);
        intent.putExtra("REQUEST_TYPE","CUSTOMER_DETAIL");

        startActivity(intent);
    }

    public void updateCustomerDetailFromKYC(View view) {
    }


    class ChooseUserTypeListener implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(rg.getCheckedRadioButtonId()==R.id.radioIndividual) {
                rlIndividual.setVisibility(View.VISIBLE);
                rlCorporate.setVisibility(View.GONE);

            }
            else {
                rlIndividual.setVisibility(View.GONE);
                rlCorporate.setVisibility(View.VISIBLE);
            }

        }
    }

}
