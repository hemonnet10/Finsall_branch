package com.finsall.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.finsall.R;

import org.json.JSONException;
import org.json.JSONObject;

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
    protected void handleSuccessResult(JSONObject success, String requestType) {
        Intent intent=null;
        String mobile=null;
        try {
            String errorMsg= (String) getJsonTag(success,"errorMessage");
            if(errorMsg!=null && !errorMsg.isEmpty()){
               showAlert(errorMsg, false, null);
                return;
            }
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
            saveData("isVerified","true");
            intent = new Intent(this, HomeActivity.class);
        }
        else{
            intent = new Intent(this, OTPActivity.class);
            intent.putExtra("ROLE",getJsonTagString(success,"roles"));

        }
        startActivity(intent);
       // Toast.makeText(this,mobile,Toast.LENGTH_LONG).show();

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {
        etPassword.setFocusable(true);
        showAlert(error,false,null);
        //Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }


    public void sendOTP(View view) throws JSONException {

        if(isNotValidRequired(etUserName))
            return;
        if( etPassword.isEnabled() &&isNotValidRequired(etPassword))
            return;

        JSONObject jsonObject = getBaseJSONRequestObj("UserService","login");
        jsonObject.put("genericIdentifier", etUserName.getText().toString() );
        if(etPassword.isEnabled()) {
            jsonObject.put("password", etPassword.getText().toString());
            jsonObject.put("app", false);
        }
        else
        jsonObject.put("app", true);

        sendRequestToServer(jsonObject,"validateOTP",false);
        //remove below two lines
      // Intent intent = new Intent(this, OTPActivity.class);
       //startActivity(intent);

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

