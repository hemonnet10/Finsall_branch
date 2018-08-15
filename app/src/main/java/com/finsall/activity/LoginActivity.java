package com.finsall.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.finsall.R;
import com.finsall.dto.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity  {

    private EditText etUserName;
    private EditText etPassword;
    private CheckBox otpChkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Set up the login form.
        etUserName = (EditText) findViewById(R.id.editTextUserName);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        otpChkBox = (CheckBox) findViewById(R.id.checkBox);

        otpChkBox.setOnCheckedChangeListener(new OTPCheckBoxChangeClicker());


    }

    @Override
    protected void handleSuccessResult(JSONObject success) {
        Intent intent=null;
        String mobile=null;
        try {

            mobile=success.getString("mobileNo");
            String userId=success.getString("userId");
            saveData("user",success.toString());
            saveData("userName",success.getString("userName"));
            saveData("mobileNo",success.getString("mobileNo"));
            saveData("userId",success.getString("userId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(etPassword.isEnabled()) {
           intent = new Intent(this, HomeActivity.class);
        }
        else{
            intent = new Intent(this, OTPActivity.class);

        }
        startActivity(intent);
        Toast.makeText(this,mobile,Toast.LENGTH_LONG).show();

    }

    @Override
    protected void handleErrorResult(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }


    public void sendOTP(View view) throws JSONException {

        JSONObject jsonObject = getBaseJSONRequestObj("UserService","login");
        jsonObject.put("genericIdentifier", etUserName.getText().toString() );
        if(etPassword.isEnabled()) {
            jsonObject.put("password", etPassword.getText().toString());
            jsonObject.put("app", false);
        }
        else
        jsonObject.put("app", true);

        sendRequestToServer(jsonObject);
       }





    class OTPCheckBoxChangeClicker implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();
            Button loginBtn=(Button)findViewById(R.id.btnLogin);

            if(isChecked) {
                etPassword.setEnabled(false);
                loginBtn.setText("SEND OTP");
            }
            else {
                etPassword.setEnabled(true);
                loginBtn.setText("LOGIN");
            }
        }
    }
   }

