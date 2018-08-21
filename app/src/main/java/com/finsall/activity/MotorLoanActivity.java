package com.finsall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.finsall.R;
import com.finsall.dto.InsuranceCompany;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MotorLoanActivity extends BaseActivity {


    private Spinner insuranceCompanySpinner;
    private String requestType = "GET_INSURANCE_COMPANY";
    EditText editTextTPPremium;
    EditText editTextOtherPremium;

    private EditText editTextTotalPremium;
    private EditText eTGST;
    private EditText eTTotalAmount;
    EditText editTextOtherAmount;

    private EditText etEligibleTPPremium;
    private EditText eTEligibleOtherPremium;
    private EditText eTEligibleTotalPremium;
    private EditText eTEligibleGST ;
    private EditText eTEligibleTotalLoanAmount ;
    private EditText eTPrincipal;
    private EditText eTInterest;
    private EditText eTEMI;
    private EditText eTUpfrontLoanContribution ;
    private EditText et1stEMI;
    private EditText eTTotalContribution;


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


        //input fields for loanemi calculation
        editTextTPPremium = (EditText) findViewById(R.id.editTextTPPremium);
        editTextOtherPremium = (EditText) findViewById(R.id.editTextOtherPremium);

        //to be populated from server

        editTextTotalPremium = (EditText) findViewById(R.id.editTextTotalPremium);
        eTGST = (EditText) findViewById(R.id.eTGST);
        eTTotalAmount = (EditText) findViewById(R.id.eTTotalAmount);
        editTextOtherAmount=(EditText) findViewById(R.id.editTextTotalOtherAmount);
        etEligibleTPPremium = (EditText) findViewById(R.id.etEligibleTPPremium);
        eTEligibleOtherPremium = (EditText) findViewById(R.id.eTEligibleOtherPremium);
        eTEligibleTotalPremium = (EditText) findViewById(R.id.eTEligibleTotalPremium);
        eTEligibleGST = (EditText) findViewById(R.id.eTEligibleGST);
        eTEligibleTotalLoanAmount = (EditText) findViewById(R.id.eTEligibleTotalLoanAmount);
        eTPrincipal = (EditText) findViewById(R.id.eTPrincipal);
        eTInterest = (EditText) findViewById(R.id.eTInterest);
        eTEMI = (EditText) findViewById(R.id.eTEMI);
        eTUpfrontLoanContribution = (EditText) findViewById(R.id.eTUpfrontLoanContribution);
        et1stEMI = (EditText) findViewById(R.id.et1stEMI);
        eTTotalContribution = (EditText) findViewById(R.id.eTTotalContribution);

    }

    @Override
    protected void handleSuccessResult(JSONObject success) throws JSONException {

        if ("GET_INSURANCE_COMPANY".equals(requestType)) {

        } else if ("CALCULATE".equals(requestType)) {
            //edit=success.getString("premium_total_prenium");

            editTextTotalPremium.setText(success.getString("premium_total_prenium"));
            eTGST.setText(success.getString("premium_gst"));
            eTTotalAmount.setText(success.getString("premium_total_amount"));
            editTextOtherAmount.setText(success.getString("premium_other_premium"));
            etEligibleTPPremium.setText(success.getString("eligible_tp_prenium"));
            eTEligibleOtherPremium.setText(success.getString("eligible_other_premium"));
            eTEligibleTotalPremium.setText(success.getString("eligible_total_premium"));
            eTEligibleGST .setText(success.getString("eligible_gst"));
            eTEligibleTotalLoanAmount .setText(success.getString("eligible_total_loan_amount"));
            eTPrincipal.setText(success.getString("monthly_principal"));
            eTInterest.setText(success.getString("monthly_interest"));
            eTEMI.setText(success.getString("monthly_emi"));
            eTUpfrontLoanContribution.setText(success.getString("premium_contribution"));
            et1stEMI.setText(success.getString("first_emi"));
            eTTotalContribution.setText(success.getString("total_contribution"));


                    //"term_in_months":"9"

        }
    }

    @Override
    protected void handleErrorResult(String error) {

    }

    public void processLoan(View view) {
        startActivity(new Intent(this, ChooseUserActivity.class));
    }

    public void calculateLoan(View view) throws JSONException {

        if (isNotValidRequired(editTextTPPremium))
            return;
        if (isNotValidRequired(editTextOtherPremium))
            return;

        JSONObject jsonObject = getBaseJSONRequestObj("EMICalculationService", "loanCalculation");
        jsonObject.put("tp_premium", editTextTPPremium.getText().toString());
        jsonObject.put("other_premium", editTextOtherPremium.getText().toString());
        sendRequestToServer(jsonObject);
        requestType = "CALCULATE";

    }
}
