package com.finsall.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.finsall.R;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.homeDrawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(this);


        NavigationView drawerNavigation = (NavigationView) findViewById(R.id.drawerNavigation);

        drawerNavigation.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        getUserName();
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShifting(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            //Timber.e(e, "Unable to change value of shift mode");
        }
    }
    @Override
    protected void handleSuccessResult(JSONObject success, String requestType) {


    }

    @Override
    protected void handleErrorResult(String error, String requestType) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        else if( item.getItemId()== R.id.action_settings) {
            removeData("user");
            Intent  intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finsall, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = null;
        switch (menuItem.getItemId()) {
            case R.id.below_home:
                return false;
            case R.id.below_information:
                intent = new Intent(this, InformationActivity.class);
                startActivity(intent);
                return false;
            case R.id.below_calculator:
                intent = new Intent(this, CalculateActivity.class);
                startActivity(intent);
                return false;
            case R.id.below_profile:
                intent = new Intent(this, PersonalActivity.class);
                startActivity(intent);
                return false;
            case R.id.drawerMyProfile:
                intent = new Intent(this, BAProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.drawerNotification:
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                return true;
            case R.id.drawerHome:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }

}
