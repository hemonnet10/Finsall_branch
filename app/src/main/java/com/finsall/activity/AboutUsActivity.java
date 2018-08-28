package com.finsall.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.finsall.R;

import org.json.JSONObject;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ((TextView)findViewById(R.id.aboutUStext)).setText(getString(R.string.about_us));
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }
}
