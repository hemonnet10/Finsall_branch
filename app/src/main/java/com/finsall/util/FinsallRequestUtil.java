package com.finsall.util;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finsall.activity.FinsallActivity;

public class FinsallRequestUtil {
    private static FinsallRequestUtil INSTANCE;
    private RequestQueue requestQueue;
    private static Context context;


    private FinsallRequestUtil() {
    }

    public static synchronized FinsallRequestUtil getInstance(Context mContext) {
        context = mContext;
        if (INSTANCE == null) {
            INSTANCE = new FinsallRequestUtil();
        }
        return INSTANCE;
    }

   /* public RequestQueue getRequestQueue() {


        return requestQueue;
    }*/
    public void addRequestQueue(Request request){
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

}
