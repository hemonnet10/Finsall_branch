package com.finsall.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.finsall.R;

import org.json.JSONObject;

public class BAAccountActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baaccount);
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {
        //week
        TextView tvLoanDisbursedCountWeek = (TextView) findViewById(R.id.tvLoanDisbursedCountWeek);
        TextView tvLoanDisbursedValueWeek = (TextView) findViewById(R.id.tvLoanDisbursedValueWeek);
        TextView tvRepaymentValueWeek = (TextView) findViewById(R.id.tvRepaymentValueWeek);
        TextView tvDefaultValueWeek = (TextView) findViewById(R.id.tvDefaultValueWeek);
        //year
        TextView tvLoanDisbursedCountYear = (TextView) findViewById(R.id.tvLoanDisbursedCountYear);
        TextView tvLoanDisbursedValueYear = (TextView) findViewById(R.id.tvLoanDisbursedValueYear);
        TextView tvRepaymentValueYear = (TextView) findViewById(R.id.tvRepaymentValueYear);
        TextView tvDefaultValueYear = (TextView) findViewById(R.id.tvDefaultValueYear);
    }


    @Override
    protected void handleErrorResult(String error, String requestType) {

    }
}
