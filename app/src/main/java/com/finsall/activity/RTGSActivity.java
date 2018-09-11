package com.finsall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.finsall.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RTGSActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtgs);
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

    public void submitRequest(View view) {
        showAlert("Payment successfully done",true,new Intent(this,HomeActivity.class));
    }
}
