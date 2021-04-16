package com.aditya.hopon;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean darkmodetoggle;
    private int noofpatternscreated;
    private String ssid = null;
    final String FineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private ImageView wificonnectionic;
    private TextView connectionstatustxt;
    private TextView noofpatternscreatedtxt;
    private TextView connectiondesctxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        if (darkmodetoggle) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (checkSelfPermission(FineLocation) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
        DBManager dbManager = new DBManager(this);
        dbManager.open();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);
        if (firstStart) {
            dbManager.insert("Regular", "1426281A2C2E", 1);
            dbManager.insert("Single Jumps", "14161A1E", 2);
            dbManager.insert("Multiplex", "232517192C2E", 1);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.app_name);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        LinearLayout patternmainlayout = findViewById(R.id.pattern_main_layout);
        CardView custompatterncard = findViewById(R.id.custompatterncard);
        CardView communitycard = findViewById(R.id.communitycard);
        CardView wificonnectioncard = findViewById(R.id.wificonnectioncard);
        wificonnectionic = findViewById(R.id.wificonnectionic);
        connectionstatustxt = findViewById(R.id.connectionstatustxt);
        connectiondesctxt = findViewById(R.id.wificonnectiondesctext);
        noofpatternscreatedtxt = findViewById(R.id.noofpatternscreatedtxt);
        patternmainlayout.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, patternsActivity.class);
            startActivity(intent);
        });
        communitycard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, communityActivity.class);
            startActivity(intent);
        });
        custompatterncard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserGenActivity.class);
            startActivity(intent);
        });
        wificonnectioncard.setOnClickListener(view -> {
            if (connectionstatustxt.getText().toString().equals("Disconnected")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Connect to The HopOn board").setIcon(R.drawable.ic_baseline_wifi_24).setMessage("Select the Hopon SSID and enter the password provided in the instruction manual in the next screen");
                builder.setPositiveButton("OK", (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        noofpatternscreatedtxt.setText(String.valueOf(noofpatternscreated));
        wifistatuscheck();
        locationstatusCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        if (darkmodetoggle) {
            menu.findItem(R.id.enable_dark_mode_button).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.enable_dark_mode_button) {
            if (item.isChecked()) {
                item.setChecked(false);
                darkmodetoggle = false;
                saveData();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                item.setChecked(true);
                darkmodetoggle = true;
                saveData();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("enabledarkmode", darkmodetoggle);
        editor.putInt("noofpatternscreated", noofpatternscreated);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        darkmodetoggle = sharedPreferences.getBoolean("enabledarkmode", false);
        noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
    }

    public void locationstatusCheck() {
        LocationRequest mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
        LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        settingsBuilder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(settingsBuilder.build());
        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
            } catch (ApiException ex) {
                switch (ex.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) ex;
                            resolvableApiException.startResolutionForResult(MainActivity.this, 199);
                        } catch (IntentSender.SendIntentException ignored) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    public void wifistatuscheck() {
        final WifiManager wifiManager = (WifiManager) getBaseContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            ssid = connectionInfo.getSSID();
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        if (ssid.equals("Aditya")) {
            wificonnectionic.clearColorFilter();
            connectionstatustxt.setText(R.string.connected);
            connectiondesctxt.setText(R.string.wifi_network);
        } else {
            wificonnectionic.setColorFilter(Color.parseColor("#FF5252"));
            connectionstatustxt.setText(R.string.disconnected);
            connectiondesctxt.setText(R.string.tap_to_connect);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 199) {// Check for the integer request code originally supplied to startResolutionForResult().
            switch (resultCode) {
                case Activity.RESULT_OK:
                    wifistatuscheck();
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}