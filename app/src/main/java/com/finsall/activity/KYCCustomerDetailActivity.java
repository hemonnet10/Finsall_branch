package com.finsall.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.finsall.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KYCCustomerDetailActivity extends BaseActivity {


    //Mandetory fields

    private EditText etMobileNo;
    private EditText etEmailId;
    private EditText etPAN;
    private EditText etAdhaarVID;
    private EditText etFirstName;
    private EditText etLastName;

    // non mandetory fields

    private EditText etFatherName;
    private EditText etDOB;
    private EditText etAddressLine1;
    private EditText etAddressLine2;
    private EditText etDistrict;
    private Spinner spinnerState;
    private EditText etPincode;

    private Calendar mcalendar;
    private int day, month, year;
    private ArrayList<String> stateList;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_customer_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etFirstName=(EditText)findViewById(R.id.etFirstName);
        etLastName=(EditText)findViewById(R.id.etLastName);

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etAdhaarVID = (EditText) findViewById(R.id.etAdhaarOrVID);
        etPAN = (EditText) findViewById(R.id.etPan);

        etFatherName = (EditText) findViewById(R.id.etFatherName);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etAddressLine1 = (EditText) findViewById(R.id.etAddressLine1);
        etAddressLine2 = (EditText) findViewById(R.id.etAddressLine2);
        etDistrict = (EditText) findViewById(R.id.etDistrict);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        etPincode = (EditText) findViewById(R.id.etPinCode);


        mcalendar = Calendar.getInstance();
        etDOB.setOnClickListener(mClickListener);
        day = mcalendar.get(Calendar.DAY_OF_MONTH);
        year = mcalendar.get(Calendar.YEAR);
        month = mcalendar.get(Calendar.MONTH);


        Intent intent = getIntent();
        
        role = intent.getStringExtra("ROLE");

        etMobileNo.setText(intent.getStringExtra("mobileNo"));
        sendRequestToServer(getBaseJSONRequestObj("FinsAllService", "getState"), "getAllState", true);
        etMobileNo.setEnabled(false);

    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {
        if (success == null)
            return;
        if (requestType.equals("getAllState")) {
            populateState(success.getJSONArray("stateList"));
            JSONObject jsonObject = getBaseJSONRequestObj("UserService", "getUserDetails");
            addJsonTag(jsonObject, "mobileNo", etMobileNo.getText().toString());
            addJsonTag(jsonObject, "roles", "customer");
            sendRequestToServer(jsonObject, "getUserDetails", false);

        } else if (requestType.equals("getUserDetails")) {
            //populate Fields

            etMobileNo.setText(getJsonTagString(success, "mobileNo"));
            etFirstName.setText(getJsonTagString(success, "firstName"));
            etLastName.setText(getJsonTagString(success, "lastName"));
            etEmailId.setText(getJsonTagString(success, "emailId"));
            etAdhaarVID.setText(getJsonTagString(success, "aadhar"));
            etPAN.setText(getJsonTagString(success, "pan"));
            //etCIN.setText(getJsonTagString(success,"cin"));
            //etTAN.setText(getJsonTagString(success,"tan"));
            etFatherName.setText(getJsonTagString(success, "fatherName"));
            String dateString = getJsonTagString(success, "dob");
            if (dateString != null) {
               // Date date = new Date(Long.parseLong(dateString));
                etDOB.setText(getJsonTagString(success, "dob"));
            }
            etAddressLine1.setText(getJsonTagString(success, "addressLine1"));
            etAddressLine2.setText(getJsonTagString(success, "addressLine2"));
            etDistrict.setText(getJsonTagString(success, "district"));
            String state = getJsonTagString(success, "state");
            if (state != null && stateList != null) {
                spinnerState.setSelection(stateList.indexOf(state));
            }
            etPincode.setText(getJsonTagString(success, "pinCode"));


        } else if (requestType.equals("SAVE_KYC_DETAIL")) {
            Intent intent = new Intent(this, FingerPrintScanningActivity.class);
            intent.putExtra("mobileNo", etMobileNo.getText().toString());
            startActivity(intent);
        }
    }

    void populateState(JSONArray array) {
        try {
            stateList = new ArrayList<>();
            //Add countries

            for (int i = 0; i < array.length(); i++) {
                stateList.add(array.getJSONObject(i).getString("stateName"));
            }
            //fill data in spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_dropdown_item, stateList);
            spinnerState.setAdapter(adapter);

        } catch (JSONException e) {
        }
    }

    @Override
    protected void handleErrorResult(String error, String requestType) {
        showAlert(error,false,null);
    }


    public void onSelected(View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateCustomerDetailFromKYC(View view) {

        if (isNotValidRequired(etFirstName))
            return;
        if (isNotValidRequired(etLastName))
            return;
        if (isNotValidRequired(etMobileNo))
            return;
        if (isNotValidRequired(etEmailId))
            return;
        if (isNotValidRequired(etAdhaarVID))
            return;
        if (isNotValidRequired(etPAN))
            return;

        if (!TextUtils.isEmpty(etDOB.getText()) && isNotValidDOB(etDOB))
            return;
        JSONObject jsonObject = getBaseJSONRequestObj("UserService","saveOrupdateUser");

        addJsonTag(jsonObject, "mobileNo", etMobileNo.getText().toString());
        addJsonTag(jsonObject,"roles", role );
        addJsonTag(jsonObject,"loggedInUserId", getData("userId") );
        addJsonTag(jsonObject, "firstName", etFirstName.getText().toString());
        addJsonTag(jsonObject, "lastName", etLastName.getText().toString());
        addJsonTag(jsonObject, "emailId", etEmailId.getText().toString());
        addJsonTag(jsonObject, "aadhar", etAdhaarVID.getText().toString());
        //addJsonTag(jsonObject,"cin", etCin.getText().toString() );
        addJsonTag(jsonObject, "pan", etPAN.getText().toString());
        //addJsonTag(jsonObject,"tan", etMobileNo.getText().toString() );


        addJsonTag(jsonObject, "fatherName", etFatherName.getText().toString());

        addJsonTag(jsonObject, "dob", etDOB.getText().toString());

        addJsonTag(jsonObject, "addressLine1", etAddressLine1.getText().toString());
        addJsonTag(jsonObject, "addressLine2", etAddressLine2.getText().toString());

        addJsonTag(jsonObject, "district", etDistrict.getText().toString());
        addJsonTag(jsonObject, "state", spinnerState.getSelectedItem().toString());

        addJsonTag(jsonObject, "pinCode", etPincode.getText().toString());



        sendRequestToServer(jsonObject, "SAVE_KYC_DETAIL", false);


    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateDialog();
        }
    };

    public void DateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
             String monthString=String.valueOf(monthOfYear);
                String dayString=String.valueOf(monthOfYear);
                if (monthOfYear < 10)
                    monthString ="0"+monthString;
                if (dayOfMonth < 10)
                    dayString ="0"+dayString;

                    etDOB.setText(year + "-" + monthString + "-" + dayString);
            }
        };
        DatePickerDialog dpDialog = new DatePickerDialog(KYCCustomerDetailActivity.this, listener, year, month, day);
        dpDialog.show();
    }
}
