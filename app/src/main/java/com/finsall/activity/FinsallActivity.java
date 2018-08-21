package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

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

                    intent = new Intent(FinsallActivity.this, HomeActivity.class);
                }
                else{
                    intent = new Intent(FinsallActivity.this, HomeActivity.class);
                    //intent = new Intent(FinsallActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                // close this activity

                finish();

            }

        }, 2*1000); // wait for 5 seconds

    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }

}
