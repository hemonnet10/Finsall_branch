package com.finsall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.finsall.R;
import com.finsall.dto.InsuranceCompany;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MotorLoanActivity extends BaseActivity {


    public static final String MOTOR_LOAN_CALCULATION = "Motor Loan Calculation";
    private Spinner insuranceCompanySpinner;
    private Spinner policyTypeSpinner;
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
    String pageName="Motor Loan Calculation";

    TextView textViewType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_locan);
        insuranceCompanySpinner = (Spinner) findViewById(R.id.spinnerInsuranceCompany);
        policyTypeSpinner = (Spinner) findViewById(R.id.spinnerPolicyType);
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
        textViewType=(TextView)findViewById(R.id.textViewType);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            String pn = extras.getString("pageName");
            if (pn != null && !pn.isEmpty())
                pageName = pn;
        }
        if(pageName.equals(MOTOR_LOAN_CALCULATION)) {
            JSONObject jsonObject = getBaseJSONRequestObj("FinsAllService", "getInsuranceCompany");
            sendRequestToServer(jsonObject, "GET_INSURANCE_COMPANY", false);
        }
        else{
            setTitle(pageName);
            textViewType.setText("Policy Type");
            insuranceCompanySpinner.setVisibility(View.GONE);
            JSONObject jsonObject = getBaseJSONRequestObj("FinsAllService", "getInsuranceTypeById");
            addJsonTag(jsonObject, "insuranceTypeId", "2");
            sendRequestToServer(jsonObject, "GET_INSURANCE_TYPE", false);
            editTextOtherPremium.setVisibility(View.GONE);
            ((TextView)findViewById(R.id.textViewOtherText)).setVisibility(View.GONE);
        }

    }


    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {

        if ("GET_INSURANCE_COMPANY".equals(requestType)) {
            JSONArray array=success.getJSONArray("externalEntityNameDTOList");
            populateInsuranceCompany(array);

        }
        else if ("GET_INSURANCE_TYPE".equals(requestType)) {
            JSONArray array=success.getJSONArray("externalEntityTypeDTOList");
            populatePolicyType(array);

        }
        else if ("CALCULATE".equals(requestType)) {
            //edit=success.getString("premium_total_prenium");

                editTextTotalPremium.setText(success.getString("premium_total_premium"));
            eTGST.setText(getJsonTagString(success,"premium_gst"));
            eTTotalAmount.setText(success.getString("premium_total_amount"));
            editTextOtherAmount.setText(getJsonTagString(success,"premium_other_premium"));
            etEligibleTPPremium.setText(success.getString("eligible_tp_premium"));
            eTEligibleOtherPremium.setText(getJsonTagString(success,"eligible_other_premium"));
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
    protected void handleErrorResult(String error, String requestType) {

    }

    public void processLoan(View view) {
        startActivity(new Intent(this, ChooseUserActivity.class));
    }

    public void calculateLoan(View view) throws JSONException {

        if (isNotValidRequired(editTextTPPremium))
            return;
        if (pageName.equals(MOTOR_LOAN_CALCULATION)&& isNotValidRequired(editTextOtherPremium))
            return;

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        JSONObject jsonObject = getBaseJSONRequestObj("EMICalculationService", "loanCalculation");
        addJsonTag(jsonObject, "tp_premium", editTextTPPremium.getText().toString());
        if(pageName.equals(MOTOR_LOAN_CALCULATION)) {
            addJsonTag(jsonObject, "other_premium", editTextOtherPremium.getText().toString());
            jsonObject.put("insurance_company_id", insuranceCompanySpinner.getSelectedItemId());
            jsonObject.put("insurance_type_id", 1);

        }
        else{
            jsonObject.put("insurance_type_id", 2);
            jsonObject.put("policy_type_id", policyTypeSpinner.getSelectedItemId());
        }

        sendRequestToServer(jsonObject, "CALCULATE", false);
    }

    void populateInsuranceCompany(JSONArray array) {
        try {
            ArrayList<InsuranceCompany> insuranceCompanyList = new ArrayList<>();
            //Add countries

            for (int i = 0; i < array.length(); i++) {
                insuranceCompanyList.add(new InsuranceCompany(array.getJSONObject(i).getInt("externalEntityNameId"),
                        array.getJSONObject(i).getString("entityName")));
            }
            //fill data in spinner
            ArrayAdapter<InsuranceCompany> adapter = new ArrayAdapter<InsuranceCompany>
                    (this, android.R.layout.simple_spinner_dropdown_item, insuranceCompanyList);
            insuranceCompanySpinner.setAdapter(adapter);

        }
        catch (JSONException e){}
    }

    void populatePolicyType(JSONArray array) {
        try {
            ArrayList<InsuranceCompany> list = new ArrayList<>();
            //Add countries

            for (int i = 0; i < array.length(); i++) {
                list.add(new InsuranceCompany(array.getJSONObject(i).getInt("externalEntityTypeId"),
                        array.getJSONObject(i).getString("entityType")));
            }
            //fill data in spinner
            ArrayAdapter<InsuranceCompany> adapter = new ArrayAdapter<InsuranceCompany>
                    (this, android.R.layout.simple_spinner_dropdown_item, list);
            policyTypeSpinner.setAdapter(adapter);


        }
        catch (JSONException e){}
    }

}
