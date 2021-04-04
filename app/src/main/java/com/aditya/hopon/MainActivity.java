package com.aditya.hopon;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS="sharedPrefs";
    private boolean darkmodetoggle;
    private int noofpatternscreated;
    private DBManager dbManager;
    private LinearLayout patternmainlayout;
    private String ssid = null;
    final String FineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private CardView custompatterncard,wificonnectioncard,communitycard;
    private ImageView wificonnectionic;
    private TextView connectionstatustxt,noofpatternscreatedtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        if(darkmodetoggle) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (checkSelfPermission(FineLocation) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
        dbManager = new DBManager(this);
        dbManager.open();
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        boolean firstStart=sharedPreferences.getBoolean("firstStart",true);
        if(firstStart){
            dbManager.insert("Regular","122426182A2C",1);
            dbManager.insert("Single Jumps","1214181C",2);
            dbManager.insert("Multiplex","212315172A2C",1);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }
        Toolbar toolbar=findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        patternmainlayout=findViewById(R.id.pattern_main_layout);
        custompatterncard=findViewById(R.id.custompatterncard);
        communitycard=findViewById(R.id.communitycard);
        wificonnectioncard=findViewById(R.id.wificonnectioncard);
        wificonnectionic=findViewById(R.id.wificonnectionic);
        connectionstatustxt=findViewById(R.id.connectionstatustxt);
        noofpatternscreatedtxt=findViewById(R.id.noofpatternscreatedtxt);
        patternmainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,patternsActivity.class);
                startActivity(intent);
            }
        });
        communitycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, communityActivity.class);
                startActivity(intent);
            }
        });
        custompatterncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,UserGenActivity.class);
                startActivity(intent);
            }
        });
        wificonnectioncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Connect to The HopOn board").setIcon(R.drawable.ic_baseline_wifi_24).setMessage("Select the Hopon SSID and enter the password provided in the instruction manual in the next screen");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        ConnectivityManager connManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final WifiManager wifiManager = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
        final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            ssid = connectionInfo.getSSID();
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        //Toast.makeText(MainActivity.this,ssid,Toast.LENGTH_LONG).show();
        if (ssid.equals("Hopon")) {
            wificonnectionic.clearColorFilter();
            connectionstatustxt.setText("Connected");
        } else {
            wificonnectionic.setColorFilter(Color.RED);
            connectionstatustxt.setText("Disconnected");
        }
        noofpatternscreatedtxt.setText(String.valueOf(noofpatternscreated));
        loctionstatusCheck();
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
        switch (item.getItemId()){
            case R.id.enable_dark_mode_button:
                if(item.isChecked()){
                    item.setChecked(false);
                    darkmodetoggle=false;
                    saveData();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else{
                    item.setChecked(true);
                    darkmodetoggle=true;
                    saveData();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
    public void saveData(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("enabledarkmode",darkmodetoggle);
        editor.putInt("noofpatternscreated",noofpatternscreated);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        darkmodetoggle=sharedPreferences.getBoolean("enabledarkmode",false);
        noofpatternscreated=sharedPreferences.getInt("noofpatternscreated",3);
    }
    public void loctionstatusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Location seems to be disabled,It is needed for connection to the Hopon board")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

}