package com.finsall.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finsall.R;
import com.finsall.dto.NotificationDTO;

public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    List<NotificationDTO> notificationList;
    private static LayoutInflater inflater=null;

    public LazyAdapter(Activity a, List<NotificationDTO> d) {
        activity = a;
        notificationList=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return notificationList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView tvNotification = (TextView)vi.findViewById(R.id.tvNotification);
        TextView tvNotificationTime = (TextView)vi.findViewById(R.id.tvNotificationTime);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        // Setting all values in listview
        tvNotification.setText(notificationList.get(position).getNotificationText());
        tvNotificationTime.setText(notificationList.get(position).getTimeString());

        return vi;
    }
}