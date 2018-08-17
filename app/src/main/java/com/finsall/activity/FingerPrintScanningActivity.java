package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.finsall.R;

import org.json.JSONObject;

public class FingerPrintScanningActivity extends BaseActivity {


    private TextView tvRejectionResason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_scanning);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        tvRejectionResason=(TextView)findViewById(R.id.tvLoanRejectedReason);
//
//        tvRejectionResason.setText("Rejection Reason...");
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }


    public void goHome(View view) {

        Intent intent=new Intent(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
