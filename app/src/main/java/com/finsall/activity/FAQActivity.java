package com.finsall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.finsall.R;

import org.json.JSONObject;

public class FAQActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }
}
