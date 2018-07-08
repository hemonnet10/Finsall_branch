package com.finsall.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.finsall.R;

import org.json.JSONObject;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.homeDrawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
    }

    @Override
    protected void handleSuccessResult(JSONObject success) {


    }

    @Override
    protected void handleErrorResult(String error) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        /*switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                // mTextMessage.setText(R.string.title_home);
                return true;
            case R.id.navigation_dashboard:
                //mTextMessage.setText(R.string.title_dashboard);
                return true;
            case R.id.navigation_notifications:
                //mTextMessage.setText(R.string.title_notifications);
                return true;
        }*/
        return false;
    }
}
