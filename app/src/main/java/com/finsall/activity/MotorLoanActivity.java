package com.finsall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.finsall.R;
import com.finsall.dto.InsuranceCompany;

import org.json.JSONObject;

import java.util.ArrayList;

public class MotorLoanActivity extends BaseActivity {


    private Spinner insuranceCompanySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_locan);
        insuranceCompanySpinner = (Spinner) findViewById(R.id.spinnerInsuranceCompany);
        ArrayList<InsuranceCompany> insuranceCompanyList = new ArrayList<>();
        //Add countries

        insuranceCompanyList.add(new InsuranceCompany(1, "India"));
        insuranceCompanyList.add(new InsuranceCompany(2, "USA"));
        insuranceCompanyList.add(new InsuranceCompany(3, "China"));
        insuranceCompanyList.add(new InsuranceCompany(4, "UK"));

        //fill data in spinner
        ArrayAdapter<InsuranceCompany> adapter = new ArrayAdapter<InsuranceCompany>
                (this, android.R.layout.simple_spinner_dropdown_item, insuranceCompanyList);
        insuranceCompanySpinner.setAdapter(adapter);
        insuranceCompanySpinner.setAdapter(adapter);
        //insuranceCompanySpinner.a
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {

    }

    @Override
    protected void handleErrorResult(String error) {

    }

    public void processLoan(View view) {
    startActivity(new Intent(this,ChooseUserActivity.class));
    }

    public void calculateLoan(View view) {

    }
}
