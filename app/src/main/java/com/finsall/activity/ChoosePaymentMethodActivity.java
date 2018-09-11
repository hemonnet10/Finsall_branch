package com.finsall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.finsall.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ChoosePaymentMethodActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment_method);
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) throws JSONException {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

    public void processPayment(View view) {
        RadioGroup rg= (RadioGroup)findViewById(R.id.radioGroupPayment);
        int id=rg.getCheckedRadioButtonId();
        if(id==-1){
            showAlert("Choose payment method",false,null);
            return;
        }
        Intent intent=null;
        if(id==R.id.radioCreditCard){
        intent= new Intent(this,CreditCardActivity.class);
        }
        else if(id==R.id.radioCheque){
            intent= new Intent(this,ChequeActivity.class);

        }
        else if(id==R.id.radioRtgsNeft){
            intent= new Intent(this,RTGSActivity.class);

        }
        else if(id==R.id.radioImps){
            intent= new Intent(this,RTGSActivity.class);

        }
        startActivity(intent);
    }
}
