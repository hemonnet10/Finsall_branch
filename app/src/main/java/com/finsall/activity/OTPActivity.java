package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.finsall.R;

import org.json.JSONObject;

public class OTPActivity extends BaseActivity {

    String requestFrom;
    EditText editTextOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent= getIntent();
        requestFrom=intent.getStringExtra("REQUEST_TYPE");
        if("CUSTOMER_DETAIL".equals(requestFrom)){
            setTitle("Proceed To Loan");
            ((Button)findViewById(R.id.buttonValidateOTP)).setText("SUBMIT");
        }

        editTextOTP=(EditText)findViewById(R.id.editTextOTP);
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

        if("CUSTOMER_DETAIL".equals(requestFrom)) {

            if(!editTextOTP.getText().equals("1234")){// to be replaced by server resp
                Intent intent = new Intent(this, IndividualKYCActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, CustomerRejectionActivity.class);
                startActivity(intent);

            }

        }
        else if(requestFrom==null) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        }

    @Override
    protected void handleErrorResult(String error) {

    }


    public void verifyOTP(View view) {

        handleSuccessResult(null);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
