package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.finsall.R;

import org.json.JSONObject;

public class CalculateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getUserName();
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

    public void openActivity(View view) {

        String TAG = view.getTag().toString();
        Intent intent = new Intent(this, MotorLoanActivity.class);

        if("NonMotorLoanActivity".equals(TAG)){
            intent.putExtra("pageName","Non Motor Calculation");
        }
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
