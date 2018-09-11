package com.finsall.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.finsall.R;
import com.finsall.dto.InsuranceCompany;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BAProfileActivity extends BaseCameraActivity {

    public ImageView mProfilePic;
    private Spinner spinnerTitle;
    private Spinner spinnerState;


    //Mandetory fields
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etMobileNo;
    private EditText etEmailId;

    // non mandetory fields

    private EditText etAddressLine1;
    private EditText etAddressLine2;
    private EditText etDistrict;
    private EditText etPincode;

    private Calendar mcalendar;
    private int day, month, year;
    private ArrayList<String> stateList;
    private String role;
    List<String> titleList;
    private EditText etBankIFSC;
    private EditText etBA;
    private EditText etHNo;
    private EditText etLandLine;
    private EditText etPan;
    private EditText etAdhaar;
    private EditText etBankAccountNumber;
    private EditText etBankName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baprofile);
        setTitle(getData("userName"));

        spinnerState = (Spinner) findViewById(R.id.spinnerState);

        spinnerTitle = (Spinner) findViewById(R.id.spinnerTitle);
         titleList = Arrays.asList("Mr", "Mrs", "Miss", "Ms");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_spinner_dropdown_item, titleList);
        spinnerTitle.setAdapter(arrayAdapter);
        mProfilePic = (ImageView) findViewById(R.id.imageViewPhoto);


        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etEmailId = (EditText) findViewById(R.id.etEmailId);

        etAddressLine1 = (EditText) findViewById(R.id.etAddressLine1);
        etAddressLine2 = (EditText) findViewById(R.id.etAddressLine2);
        etDistrict = (EditText) findViewById(R.id.etDistrict);
        etPincode = (EditText) findViewById(R.id.etPinCode);

        etBA = (EditText) findViewById(R.id.etBA);
        etHNo = (EditText) findViewById(R.id.etHNo);
        etLandLine = (EditText) findViewById(R.id.etLandLine);
        etPan = (EditText) findViewById(R.id.etPan);
        etAdhaar = (EditText) findViewById(R.id.etAdhaar);
        etBankAccountNumber = (EditText) findViewById(R.id.etBankAccountNumber);
        etBankName = (EditText) findViewById(R.id.etBankName);
        etBankIFSC = (EditText) findViewById(R.id.etBankIFSC);


//        mcalendar = Calendar.getInstance();
//        day = mcalendar.get(Calendar.DAY_OF_MONTH);
//        year = mcalendar.get(Calendar.YEAR);
//        month = mcalendar.get(Calendar.MONTH);


        sendRequestToServer(getBaseJSONRequestObj("FinsAllService", "getState"), "getAllState", true);

    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {

        if (requestType.equals("getAllState")) {
            populateState(success.getJSONArray("stateList"));
            JSONObject jsonObject = getBaseJSONRequestObj("UserService", "getUserDetails");
            addJsonTag(jsonObject, "userId", getData("userId"));
            addJsonTag(jsonObject, "roles", "business associate");
            sendRequestToServer(jsonObject, "getUserDetails", false);
        } else if (requestType.equals("getUserDetails")) {
            etMobileNo.setText(getJsonTagString(success, "mobileNo"));
            etFirstName.setText(getJsonTagString(success, "firstName"));
            etLastName.setText(getJsonTagString(success, "lastName"));
            etEmailId.setText(getJsonTagString(success, "emailId"));
            String dateString = getJsonTagString(success, "dob");
            etAddressLine1.setText(getJsonTagString(success, "addressLine1"));
            etAddressLine2.setText(getJsonTagString(success, "addressLine2"));
            etDistrict.setText(getJsonTagString(success, "district"));
            String state = getJsonTagString(success, "state");
            if (state != null && stateList != null) {
                spinnerState.setSelection(stateList.indexOf(state));
            }
            etPincode.setText(getJsonTagString(success, "pinCode"));
            String title = getJsonTagString(success, "title");
            if (title != null) {
                spinnerTitle.setSelection(titleList.indexOf(state));
            }

            etBA.setText(getJsonTagString(success, "userName"));
            etHNo.setText(getJsonTagString(success, "houseNumber"));
            etLandLine.setText(getJsonTagString(success, "landline"));
            etPan.setText(getJsonTagString(success, "pan"));
            etAdhaar.setText(getJsonTagString(success, "aadhar"));
            if(success.getJSONArray("userBanksDTOSet")!=null && success.getJSONArray("userBanksDTOSet").length()>0) {
                etBankAccountNumber.setText(getJsonTagString(success.getJSONArray("userBanksDTOSet").getJSONObject(0), "accountNumber"));
                etBankName.setText(getJsonTagString(success.getJSONArray("userBanksDTOSet").getJSONObject(0), "bankName"));
                etBankIFSC.setText(getJsonTagString(success.getJSONArray("userBanksDTOSet").getJSONObject(0), "ifscCode"));
            }

            String imageData=getJsonTagString(success,"image");
            if(imageData!=null) {
                byte[] byteArray =  Base64.decode(imageData, Base64.DEFAULT) ;
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                mProfilePic.setImageBitmap(Bitmap.createScaledBitmap(bmp,mProfilePic.getWidth(),
                        mProfilePic.getHeight(), false));

            }


        } else if (requestType.equals("SAVE_DETAIL"))

        {
            showAlert("Profile Updated Successfully.", true, null);
        }
    }


    @Override
    protected void handleErrorResult(String error, String requestType) {

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

    public void changeProfilePic(View v) {
        Toast.makeText(this, "Change Photo Pressed", Toast.LENGTH_SHORT).show();
        startCapturingImage(mProfilePic);
    }

    public void updateBAProfile(View view) {

        if (isNotValidRequired(etFirstName))
            return;
        if (isNotValidRequired(etMobileNo))
            return;
        if (isNotValidRequired(etEmailId))
            return;
        if (isNotValidRequired(etAddressLine1))
            return;
        if (isNotValidRequired(etDistrict))
            return;

        JSONObject jsonObject = getBaseJSONRequestObj("UserService", "saveOrupdateUser");

        addJsonTag(jsonObject, "mobileNo", etMobileNo.getText().toString());
        addJsonTag(jsonObject,"roles", "business associate" );
        addJsonTag(jsonObject, "firstName", etFirstName.getText().toString());
        addJsonTag(jsonObject, "emailId", etEmailId.getText().toString());
        addJsonTag(jsonObject, "addressLine1", etAddressLine1.getText().toString());
        addJsonTag(jsonObject, "addressLine2", etAddressLine2.getText().toString());
        addJsonTag(jsonObject, "district", etDistrict.getText().toString());
        addJsonTag(jsonObject, "state", spinnerState.getSelectedItem().toString());
        addJsonTag(jsonObject, "pinCode", etPincode.getText().toString());


        Bitmap image = ((BitmapDrawable) mProfilePic.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


        addJsonTag(jsonObject, "image", encodedImage);

        sendRequestToServer(jsonObject, "SAVE_DETAIL", false);


    }
}
