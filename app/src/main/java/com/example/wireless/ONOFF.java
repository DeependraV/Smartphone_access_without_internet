package com.example.wireless;

import android.content.Context;
import android.net.wifi.WifiManager;

public class ONOFF {
    private Context context;

    public void ON() {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
    }

    public void OFF() {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }
}