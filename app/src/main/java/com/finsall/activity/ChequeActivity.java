package com.finsall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.finsall.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ChequeActivity extends BaseCameraActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque);
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

    public void scanCheque(View view) {
        ImageView imageView=(ImageView)findViewById(R.id.imageViewCheque);
        startCapturingImage(imageView);

    }

    public void submitRequest(View view) {

    }

}
