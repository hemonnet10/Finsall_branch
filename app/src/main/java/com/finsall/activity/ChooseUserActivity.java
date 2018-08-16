package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.finsall.R;

import org.json.JSONObject;

public class ChooseUserActivity extends BaseActivity {

    private EditText etMobileNo;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        rg= (RadioGroup)findViewById(R.id.radioUser);
        rg.setOnCheckedChangeListener(new RadioChangeClicker());
        etMobileNo=(EditText)findViewById(R.id.etMobileNo);
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }


    public void onSelected(View view) {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBoardCustomer(View view) {
        Intent intent= new Intent(this,CustomerDetailActivity.class);

        intent.putExtra("isNewUser",rg.getCheckedRadioButtonId()==R.id.radioNewUser);
        if(rg.getCheckedRadioButtonId()==R.id.radioNewUser)
        intent.putExtra("mobileNo",etMobileNo.getText().toString());
        startActivity(intent);
    }

    class RadioChangeClicker implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(rg.getCheckedRadioButtonId()==R.id.radioNewUser) {
                etMobileNo.setEnabled(true);
            }
            else {
                etMobileNo.setEnabled(false);

            }

        }
    }

}
