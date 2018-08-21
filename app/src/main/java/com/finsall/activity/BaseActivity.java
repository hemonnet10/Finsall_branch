package com.finsall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.finsall.R;
import com.finsall.util.FinsallRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public abstract class BaseActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup layout = (ViewGroup) ((Activity) this).findViewById(android.R.id.content)
                .getRootView();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        mProgressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(this);

        rl.setGravity(Gravity.CENTER);
        rl.addView(mProgressBar);

        layout.addView(rl, params);
        hide();
    }



    protected boolean isNotValidRequired(EditText et) {
        boolean isValid= !TextUtils.isEmpty(et.getText().toString());
        if(!isValid) {
            et.setError(getString(R.string.error_field_required));
            et.requestFocus();
        }
        return !isValid;
    }


    protected boolean isNotValidDOB(EditText et) {
        boolean isValid= (!TextUtils.isEmpty(et.getText().toString()) && Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\\\d\\\\d)").matcher(et.getText().toString()).matches());
        if(!isValid){
            et.setError(getString(R.string.error_invalid_date));
            et.requestFocus();
        }
        return !isValid;

    }

    protected boolean isNotValidEmail(EditText et) {
        boolean isValid= (!TextUtils.isEmpty(et.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches());
        if(!isValid){
            et.setError(getString(R.string.error_invalid_email));
            et.requestFocus();
        }
        return !isValid;

    }

    protected void sendRequestToServer(JSONObject jsonObject) {
        show();
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://momarkrewards.com:3030/FinsAllServer/api/",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hide();
                        try {
                            handleSuccessResult(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide();
                handleErrorResult("Network Error.");
            }

        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("authentication-is-public", "Y");
                return headers;
            }
        };
        FinsallRequestUtil.getInstance(this).addRequestQueue(request);


    }

    protected JSONObject getBaseJSONRequestObj(String serviceName, String serviceMethod) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("serviceName", serviceName);
            jsonObject.put("serviceMethod", serviceMethod);
            jsonObject.put("clientId", "2017");
            jsonObject.put("version", "1.0.0");
            if(getData("userId")!=null)
            jsonObject.put("loggedInUserId", getData("userId"));

        } catch (JSONException e) {

        }
        return jsonObject;
    }

    protected abstract void handleSuccessResult(JSONObject success) throws JSONException;

    protected abstract void handleErrorResult(String error);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void show() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    protected void getUserName() {
        TextView textViewUserName = (TextView) findViewById(R.id.textViewTextUserName);
        textViewUserName.setText("Hello " + "Ram" + ",");//getData("userName")

    }

    public void openActivity(View view) {

        String TAG = view.getTag().toString();
        Class c = null;
        try {
            c = Class.forName("com.finsall.activity." + TAG);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (c != null) {
            Intent intent = new Intent(this, c);
            startActivity(intent);
        }
    }

    protected void saveData(String key, String value) {
        SharedPreferences sf = getSharedPreferences("MyPref",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String getData(String key) {
        SharedPreferences sf = getSharedPreferences("MyPref",
                Context.MODE_PRIVATE);
        return sf.getString(key, null);
    }
}
