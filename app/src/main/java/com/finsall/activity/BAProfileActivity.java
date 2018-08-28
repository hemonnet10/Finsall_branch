package com.finsall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.finsall.R;
import com.finsall.dto.InsuranceCompany;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BAProfileActivity extends BaseCameraActivity {

    public ImageView mProfilePic;
    private Spinner spinnerTitle;
    private Spinner spinnerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baprofile);
        setTitle(getData("userName"));

        spinnerState = (Spinner) findViewById(R.id.spinnerState);

        spinnerTitle = (Spinner) findViewById(R.id.spinnerTitle);
        String titleList[] = {"Mr", "Mrs", "Miss", "Ms"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_spinner_dropdown_item, titleList);
        spinnerTitle.setAdapter(arrayAdapter);


        mProfilePic = (ImageView) findViewById(R.id.imageViewPhoto);

        sendRequestToServer(getBaseJSONRequestObj("FinsAllService","getState"),"getAllState", false);

    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {

        if(requestType.equals("getAllState")){
            populateState(success.getJSONArray("stateList"));
        }

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

    void populateState(JSONArray array) {
        try {
            ArrayList<String> insuranceCompanyList = new ArrayList<>();
            //Add countries

            for (int i = 0; i < array.length(); i++) {
                insuranceCompanyList.add(array.getJSONObject(i).getString("stateName"));
            }
            //fill data in spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_dropdown_item, insuranceCompanyList);
            spinnerState.setAdapter(adapter);

        }
        catch (JSONException e){}
    }
    public void changeProfilePic(View v) {
        Toast.makeText(this, "Change Photo Pressed", Toast.LENGTH_SHORT).show();
        startCapturingImage(mProfilePic);
    }

    public void updateBAProfile(View view) {
        finish();
    }
}
