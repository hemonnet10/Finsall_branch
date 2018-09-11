package com.finsall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.finsall.R;
import com.finsall.util.FinsallRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
        boolean isValid = !TextUtils.isEmpty(et.getText().toString());
        if (!isValid) {
            et.setError(getString(R.string.error_field_required));
            et.requestFocus();
        }
        return !isValid;
    }


    protected boolean isNotValidDOB(EditText et) {
        boolean isValid = (!TextUtils.isEmpty(et.getText().toString()) && Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$").matcher(et.getText().toString()).matches());
        if (!isValid) {
            et.setError(getString(R.string.error_invalid_date));
            et.requestFocus();
        }
        return !isValid;

    }

    protected boolean isNotValidEmail(EditText et) {
        boolean isValid = (!TextUtils.isEmpty(et.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches());
        if (!isValid) {
            et.setError(getString(R.string.error_invalid_email));
            et.requestFocus();
        }
        return !isValid;

    }

    protected void sendRequestToServer(JSONObject jsonObject, String requestType, boolean isCachable) {
        if(isCachable) {
            JSONObject cachedResponse=  getDataFromCache(jsonObject);
            try {
                if(cachedResponse!=null) {
                    handleSuccessResult(cachedResponse, requestType);
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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
                            if(response!=null)
                            handleSuccessResult(response, requestType);
                            else
                                showAlert("Server not reachable.",false,null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide();
                handleErrorResult("Network Error.", requestType);
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

    private JSONObject getDataFromCache(JSONObject jsonObject) {
        try {
            String key=jsonObject.getString("serviceName")+"|"+jsonObject.getString("serviceMethod");
            String value=getData(key);
            if(value!=null)
            return new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }

    protected JSONObject getBaseJSONRequestObj(String serviceName, String serviceMethod) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("serviceName", serviceName);
            jsonObject.put("serviceMethod", serviceMethod);
            jsonObject.put("app", true);
            jsonObject.put("clientId", "2017");
            jsonObject.put("version", "1.0.0");
            if (getData("userId") != null)
                jsonObject.put("loggedInUserId", getData("userId"));

        } catch (JSONException e) {

        }
        return jsonObject;
    }

    protected abstract void handleSuccessResult(JSONObject success, String requestType) throws JSONException;

    protected abstract void handleErrorResult(String error, String requestType);

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
        textViewUserName.setText("Hello " + getData("userName") + ",");//getData("userName")

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
    protected void removeData(String key) {
        SharedPreferences sf = getSharedPreferences("MyPref",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.remove(key);
        editor.commit();
    }

    protected String getData(String key) {
        SharedPreferences sf = getSharedPreferences("MyPref",
                Context.MODE_PRIVATE);
        return sf.getString(key, null);
    }


    protected void addJsonTag(JSONObject jsonObject, String key, Object value) {
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Object getJsonTag(JSONObject jsonObject, String key) {
        try {
            return jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected String getJsonTagString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected Double getJsonTagDouble(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void showAlert(String message, boolean isFinishRequest, Intent nextActivityIntent){
        //show dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(nextActivityIntent!=null){
                    nextActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    nextActivityIntent.putExtra("EXIT", true);
                    startActivity(nextActivityIntent);
                }
                if(isFinishRequest){
                    finish();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
