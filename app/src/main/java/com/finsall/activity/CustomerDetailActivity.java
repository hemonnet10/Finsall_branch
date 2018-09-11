package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.finsall.R;

import org.json.JSONObject;

public class CustomerDetailActivity extends BaseActivity {


    private EditText etFirstName;
    private EditText etLastName;
    private EditText etMobileNo;
    private EditText etEmailId;
    private RadioGroup rg;
    private EditText etAdhaarVID;
    private EditText etCIN;
    private EditText etPAN;
    private EditText etTAN;
    private RelativeLayout rlIndividual;
    private RelativeLayout rlCorporate;

    String requestFrom;
    String role;
    static JSONObject userJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuatomer_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etAdhaarVID = (EditText) findViewById(R.id.etAdhaarOrVID);
        etCIN = (EditText) findViewById(R.id.etCIN);
        etPAN = (EditText) findViewById(R.id.etPan);
        etTAN = (EditText) findViewById(R.id.etTAN);

        rg = (RadioGroup) findViewById(R.id.radioUserType);
        rg.setOnCheckedChangeListener(new CustomerDetailActivity.ChooseUserTypeListener());

        rlCorporate = (RelativeLayout) findViewById(R.id.rlCorporate);
        rlIndividual = (RelativeLayout) findViewById(R.id.rlIndividual);


        Intent intent = getIntent();
        requestFrom = intent.getStringExtra("REQUEST_TYPE");

        role = intent.getStringExtra("ROLE");

        etMobileNo.setText(intent.getStringExtra("mobileNo"));
        etMobileNo.setEnabled(false);
        if ("OLD_CUSTOMER".equals(requestFrom)) {
            //  ((Button) findViewById(R.id.buttonValidateOTP)).setText("SUBMIT");
            etMobileNo.setText(getJsonTagString(userJSON, "mobileNo"));
            etFirstName.setText(getJsonTagString(userJSON, "firstName"));
            etLastName.setText(getJsonTagString(userJSON, "lastName"));
            etEmailId.setText(getJsonTagString(userJSON, "emailId"));
            etAdhaarVID.setText(getJsonTagString(userJSON, "aadhar"));
            etPAN.setText(getJsonTagString(userJSON, "pan"));
            etCIN.setText(getJsonTagString(userJSON, "cin"));
            etTAN.setText(getJsonTagString(userJSON, "tan"));
            if (getJsonTagString(userJSON, "isCorporate").equals("Y")) {
                ((RadioButton) findViewById(R.id.radioCorporate)).setChecked(true);
            } else {
                ((RadioButton) findViewById(R.id.radioIndividual)).setChecked(true);
            }

        } else if ("NEW_CUSTOMER".equals(requestFrom)) {

        }


    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {

        String errorMsg = (String) getJsonTag(success, "errorMessage");
        if (errorMsg != null && !errorMsg.isEmpty()) {
            if(getJsonTagString(success,"statusCode").equals("FUK Indi")){
                showAlert(errorMsg,  true,new Intent(this,HomeActivity.class));
            }
            else
                showAlert(errorMsg, false, null);
            return;
        }

        Intent intent = null;
        // intent.putExtra("REQUEST_TYPE","CUSTOMER_DETAIL");

        if ("NEW_CUSTOMER".equals(requestFrom)) {
            intent = new Intent(this, OTPActivity.class);
            intent.putExtra("mobileNo", etMobileNo.getText().toString());
            intent.putExtra("ROLE", role);
            intent.putExtra("userId", getJsonTagString(success, "userId"));
            intent.putExtra("REQUEST_TYPE", "NEW_CUSTOMER");
            startActivity(intent);

        } else if ("checkCbill".equals(requestType)) {
            intent = new Intent(this, KYCCustomerDetailActivity.class);
            intent.putExtra("ROLE", role);
            intent.putExtra("mobileNo", etMobileNo.getText().toString());
            startActivity(intent);

        } else {
            checkCBillScore(getJsonTagString(success, "userId"));
        }

    }

    private void checkCBillScore(String userId) {

        JSONObject jsonObject = getBaseJSONRequestObj("UserService", "checkCibilOrKyc");
        addJsonTag(jsonObject, "loggedInUserId", getData("userId"));
        addJsonTag(jsonObject, "userId", userId);
        addJsonTag(jsonObject, "roles", "customer");
        sendRequestToServer(jsonObject, "checkCbill", false);

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showOTPPage(View view) {

        if (rg.getCheckedRadioButtonId() == -1) {
            ((RadioButton) findViewById(R.id.radioCorporate)).setError(getString(R.string.error_field_required));
            return;
        }

        if (isNotValidRequired(etMobileNo))
            return;
        if (isNotValidRequired(etFirstName))
            return;
        if (isNotValidRequired(etLastName))
            return;
        if (isNotValidRequired(etMobileNo))
            return;
        if (isNotValidRequired(etEmailId))
            return;

        if (rg.getCheckedRadioButtonId() == R.id.radioCorporate) {
            if (isNotValidRequired(etTAN))
                return;
            if (isNotValidRequired(etCIN))
                return;
        } else {
            if (isNotValidRequired(etPAN))
                return;
            if (isNotValidRequired(etAdhaarVID))
                return;
        }

        JSONObject jsonObject = getBaseJSONRequestObj("UserService", "saveOrupdateUser");
        addJsonTag(jsonObject, "mobileNo", etMobileNo.getText().toString());
        addJsonTag(jsonObject, "roles", role);
        addJsonTag(jsonObject, "loggedInUserId", getData("userId"));
        addJsonTag(jsonObject, "firstName", etFirstName.getText().toString());
        addJsonTag(jsonObject, "lastName", etLastName.getText().toString());
        addJsonTag(jsonObject, "emailId", etEmailId.getText().toString());

        if (((RadioButton) findViewById(R.id.radioCorporate)).isChecked()) {
            addJsonTag(jsonObject, "cin", etCIN.getText().toString());
            addJsonTag(jsonObject, "tan", etTAN.getText().toString());
            addJsonTag(jsonObject, "isCorporate", "Y");
        } else {
            addJsonTag(jsonObject, "aadhar", etAdhaarVID.getText().toString());
            addJsonTag(jsonObject, "pan", etPAN.getText().toString());
            addJsonTag(jsonObject, "isCorporate", "N");
        }
        sendRequestToServer(jsonObject, null, false);

    }

    public void updateCustomerDetailFromKYC(View view) {
    }


    class ChooseUserTypeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (rg.getCheckedRadioButtonId() == R.id.radioIndividual) {
                rlIndividual.setVisibility(View.VISIBLE);
                rlCorporate.setVisibility(View.GONE);

            } else {
                rlIndividual.setVisibility(View.GONE);
                rlCorporate.setVisibility(View.VISIBLE);
            }

        }
    }

}
