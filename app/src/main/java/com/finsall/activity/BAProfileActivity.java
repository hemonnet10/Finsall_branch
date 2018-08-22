package com.finsall.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.finsall.R;
import com.finsall.dto.InsuranceCompany;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BAProfileActivity extends BaseCameraActivity {

    public ImageView mProfilePic;
    private Spinner spinnerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baprofile);
        setTitle(getData("userName"));


        spinnerTitle = (Spinner) findViewById(R.id.spinnerTitle);
        String titleList[] = {"Mr", "Mrs", "Miss", "Ms"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_spinner_dropdown_item, titleList);
        spinnerTitle.setAdapter(arrayAdapter);


        mProfilePic = (ImageView) findViewById(R.id.imageViewPhoto);

    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }


    public void changeProfilePic(View v) {
        Toast.makeText(this, "Change Photo Pressed", Toast.LENGTH_SHORT).show();
        startCapturingImage(mProfilePic);
    }

}
