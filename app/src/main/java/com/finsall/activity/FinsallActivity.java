package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.finsall.R;

import org.json.JSONObject;

public class FinsallActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finsall);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {
                Intent intent=null;

                if(getData("user")!=null)
                {
                   // intent = new Intent(FinsallActivity.this, CaptureALADocumentsActivity.class);
                    intent = new Intent(FinsallActivity.this, HomeActivity.class);
                }
                else{
                   // intent = new Intent(FinsallActivity.this, CustomerDetailActivity.class);
                    intent = new Intent(FinsallActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                // close this activity

                finish();

            }

        }, 2*1000); // wait for 5 seconds

    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

}
