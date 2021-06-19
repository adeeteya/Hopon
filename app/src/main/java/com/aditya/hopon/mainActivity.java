package com.aditya.hopon;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class mainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean darkModeToggle;
    private int noofpatternscreated, noofcommunitypatterns;
    private String ssid = null;
    final String FineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private ImageView wifiConnectionIcon;
    private TextView connectionStatusTxt;
    private TextView noOfPatternsCreatedTxt;
    private TextView wifiConnectionDescTxt, onlinePatternsCountTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        if (darkModeToggle) {
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
            dbManager.insert("Regular", "1426281A2C2E", 1, null);
            dbManager.insert("Single Jumps", "14161A1E", 2, null);
            dbManager.insert("Multiplex", "232517192C2E", 1, null);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.app_name);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        onlinePatternsCountTxt = findViewById(R.id.onlinePatternCountTxt);
        LinearLayout patternCard = findViewById(R.id.patternCard);
        CardView customPatternCard = findViewById(R.id.customPatternCard);
        CardView communityCard = findViewById(R.id.communityCard);
        CardView wifiConnectionCard = findViewById(R.id.wifiConnectionCard);
        wifiConnectionIcon = findViewById(R.id.wifiConnectionIcon);
        connectionStatusTxt = findViewById(R.id.connectionStatusTxt);
        wifiConnectionDescTxt = findViewById(R.id.wifiConnectionDescTxt);
        noOfPatternsCreatedTxt = findViewById(R.id.noOfPatternsCreatedTxt);
        patternCard.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity.this, patternsActivity.class);
            startActivity(intent);
        });
        communityCard.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity.this, communityActivity.class);
            startActivity(intent);
        });
        customPatternCard.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity.this, userGenActivity.class);
            startActivity(intent);
        });
        wifiConnectionCard.setOnClickListener(view -> {
            if (connectionStatusTxt.getText().toString().equals(getString(R.string.disconnected))) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mainActivity.this);
                builder.setTitle(R.string.wifiAlertDialogTitle).setIcon(R.drawable.ic_baseline_wifi_24).setMessage(R.string.wifiAlertDialogDesc);
                builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
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
        onlinePatternsCountTxt.setText(String.valueOf(noofcommunitypatterns));
        noOfPatternsCreatedTxt.setText(String.valueOf(noofpatternscreated));
        wifiStatusCheck();
        locationStatusCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        if (darkModeToggle) {
            menu.findItem(R.id.enable_dark_mode_button).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.enable_dark_mode_button) {
            if (item.isChecked()) {
                item.setChecked(false);
                darkModeToggle = false;
                saveData();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                item.setChecked(true);
                darkModeToggle = true;
                saveData();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            return true;
        } else if (item.getItemId() == R.id.change_lang_button) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("enabledarkmode", darkModeToggle);
        editor.putInt("noofpatternscreated", noofpatternscreated);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        darkModeToggle = sharedPreferences.getBoolean("enabledarkmode", false);
        noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
        noofcommunitypatterns = sharedPreferences.getInt("noofcommunitypatterns", 50);
    }

    public void locationStatusCheck() {
        if (checkSelfPermission(FineLocation) != PackageManager.PERMISSION_GRANTED) return;
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
                            resolvableApiException.startResolutionForResult(mainActivity.this, 199);
                        } catch (IntentSender.SendIntentException ignored) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    public void wifiStatusCheck() {
        final WifiManager wifiManager = (WifiManager) getBaseContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            ssid = connectionInfo.getSSID();
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        if (ssid.equals("Hopon")) {
            wifiConnectionIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#68B684")));
            connectionStatusTxt.setText(R.string.connected);
            wifiConnectionDescTxt.setHint(R.string.wifi_network);
        } else {
            wifiConnectionIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#E63946")));
            connectionStatusTxt.setText(R.string.disconnected);
            wifiConnectionDescTxt.setHint(R.string.connection_box_bottomText);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 199) {// Check for the integer request code originally supplied to startResolutionForResult().
            switch (resultCode) {
                case Activity.RESULT_OK:
                    wifiStatusCheck();
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}