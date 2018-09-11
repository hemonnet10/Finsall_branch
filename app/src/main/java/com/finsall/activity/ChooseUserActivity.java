package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.finsall.R;

import org.json.JSONObject;

public class ChooseUserActivity extends BaseActivity {

    private EditText etMobileNo;
   // RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       // rg= (RadioGroup)findViewById(R.id.radioUser);
        //rg.setOnCheckedChangeListener(new RadioChangeClicker());
        etMobileNo=(EditText)findViewById(R.id.etMobileNo);
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {


        Intent intent =null;
        if(getJsonTag(success,"status").equals("0")) {

            intent = new Intent(this, CustomerDetailActivity.class);
            intent.putExtra("mobileNo", etMobileNo.getText().toString());
            intent.putExtra("ROLE",getJsonTagString(success,"roles"));
            intent.putExtra("REQUEST_TYPE","NEW_CUSTOMER");
            CustomerDetailActivity.userJSON=null;
            //intent.putExtra("USER_DETAIL",success);
        }
        else{
            intent = new Intent(this, OTPActivity.class);
            intent.putExtra("mobileNo", etMobileNo.getText().toString());
            intent.putExtra("ROLE",getJsonTagString(success,"roles"));
            intent.putExtra("REQUEST_TYPE","OLD_CUSTOMER");
            intent.putExtra("userId",getJsonTagString(success,"userId"));
            CustomerDetailActivity.userJSON=success;

        }
        //intent.putExtra("isNewUser",rg.getCheckedRadioButtonId()==R.id.radioNewUser);
        //if(rg.getCheckedRadioButtonId()==R.id.radioNewUser)
          //  intent.putExtra("mobileNo",etMobileNo.getText().toString());



        startActivity(intent);
    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBoardCustomer(View view) {
        if(isNotValidRequired(etMobileNo))
            return;
        JSONObject jsonObject = getBaseJSONRequestObj("UserService","getUserDetails");
        addJsonTag(jsonObject,"mobileNo", etMobileNo.getText().toString() );
        addJsonTag(jsonObject,"roles", "customer" );
        sendRequestToServer(jsonObject,null,false);
        }

    class RadioChangeClicker implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
//            if(rg.getCheckedRadioButtonId()==R.id.radioNewUser) {
//                etMobileNo.setEnabled(true);
//            }
//            else {
//                etMobileNo.setEnabled(false);
//
//            }

        }
    }

}
