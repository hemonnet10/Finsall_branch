package com.finsall.activity;

import android.os.Bundle;
import android.view.View;

import com.finsall.R;

import org.json.JSONObject;

public class TrackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

    public void track(View view) {
    }
}
