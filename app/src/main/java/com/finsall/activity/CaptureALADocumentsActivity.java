package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finsall.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CaptureALADocumentsActivity extends BaseCameraActivity {

    private RelativeLayout rlala1;
    private RelativeLayout rlala2;
    private ImageView imageViewPreview;
    Button buttonRetake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_aladocuments);
        setTitle("Customer Approved");
        //setTitle("Scan ALA First Page LCM");
        rlala1=(RelativeLayout)findViewById(R.id.rlala1);
        rlala2=(RelativeLayout)findViewById(R.id.rlala2);
        imageViewPreview=(ImageView)findViewById(R.id.myImageViewPreview);
        buttonRetake=(Button)findViewById(R.id.buttonRetake);
    }


    public void captureImage(View v){


    }
    public void scanDocuments(View v){
        rlala1.setVisibility(View.GONE);
        rlala2.setVisibility(View.VISIBLE);
        startCapturingImage(imageViewPreview);
        ((TextView)findViewById(R.id.myImageViewText)).setVisibility(View.GONE);

        if(getNoOfImagesToTake()==1){
            buttonRetake.setVisibility(View.VISIBLE);
        }
        if(getNoOfImagesToTake()==3){

            ((Button)findViewById(R.id.buttonSubmit)).setVisibility(View.VISIBLE);
            ((ImageView)findViewById(R.id.buttonCamera)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.buttonRetake)).setVisibility(View.GONE);
        }
    }

    public void submitDocuments(View v){
        Intent intent=new Intent(this,HomeActivity.class);
        showAlert("Documents submitted successfully.", true, intent);

    }
    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }
}
