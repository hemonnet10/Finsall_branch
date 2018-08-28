package com.finsall.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.finsall.R;
import com.finsall.dto.NotificationDTO;
import com.finsall.util.LazyAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends BaseActivity {
    ListView list;
    LazyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        list=(ListView)findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        List<NotificationDTO> notificationList=new ArrayList<NotificationDTO>();
        notificationList.add(new NotificationDTO("Notification 1","2 Min Ago"));
        notificationList.add(new NotificationDTO("Notification 2","4 Min Ago"));
        notificationList.add(new NotificationDTO("Notification 3","2 Day Ago"));
        notificationList.add(new NotificationDTO("Notification 4","3 Day Ago"));
        adapter=new LazyAdapter(this, notificationList);
        list.setAdapter(adapter);
    }

    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {

    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }
}
