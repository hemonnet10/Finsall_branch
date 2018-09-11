package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.finsall.R;

import org.json.JSONObject;

public class OTPActivity extends BaseActivity {

    String role;
    String requestFrom;
    EditText editTextOTP;
    Parcelable userDetail;
    private String mobileNo;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        requestFrom = intent.getStringExtra("REQUEST_TYPE");

        role = intent.getStringExtra("ROLE");

        userDetail=intent.getParcelableExtra("USER_DETAIL");
        if ("NEW_CUSTOMER".equals(requestFrom) || "OLD_CUSTOMER".equals(requestFrom)) {
            setTitle("Proceed To Loan");
            mobileNo = intent.getStringExtra("mobileNo");
            ((Button) findViewById(R.id.buttonValidateOTP)).setText("SUBMIT");
            userId = intent.getStringExtra("userId");

        }

        editTextOTP = (EditText) findViewById(R.id.editTextOTP);
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
        if ("OLD_CUSTOMER".equals(requestFrom)) {
            if (true) {// to be replaced by server resp
                Intent intent = new Intent(this, CustomerDetailActivity.class);
                intent.putExtra("REQUEST_TYPE","OLD_CUSTOMER");
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, CustomerRejectionActivity.class);
                startActivity(intent);

            }

        }
        else if ("checkCbill".equals(requestType)) {


            Intent intent = new Intent(this, KYCCustomerDetailActivity.class);
            intent.putExtra("ROLE",role);
            intent.putExtra("mobileNo",mobileNo);
            startActivity(intent);


        }
        else if ("NEW_CUSTOMER".equals(requestFrom)) {

            checkCBillScore();

        }
        else if (requestFrom == null) {

            Intent intent = new Intent(this, HomeActivity.class);
            saveData("isVerified","true");
            startActivity(intent);
        }
    }

    private void checkCBillScore() {

        JSONObject jsonObject = getBaseJSONRequestObj("UserService", "checkCibilOrKyc");
        addJsonTag(jsonObject, "loggedInUserId", getData("userId"));
        addJsonTag(jsonObject, "userId", userId);
        addJsonTag(jsonObject, "roles", "customer");
        sendRequestToServer(jsonObject, "checkCbill", false);

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {
        showAlert(error, false, null);
    }


    public void verifyOTP(View view) {

        if (isNotValidRequired(editTextOTP))
            return;

        JSONObject jsonObject = getBaseJSONRequestObj("UserService", "validateOTP");
        addJsonTag(jsonObject, "loggedInUserId", getData("userId"));
        addJsonTag(jsonObject, "userId", getData("userId"));
        addJsonTag(jsonObject, "genericParam", editTextOTP.getText().toString());
        addJsonTag(jsonObject, "roles", role);
        sendRequestToServer(jsonObject, "validateOTP", false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
