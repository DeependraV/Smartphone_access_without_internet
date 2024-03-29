package com.example.wireless;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BATTERY_STATES {
    Context context;
    public String STATES() {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;
        float ans = batteryPct * 100;
        String remaining = Float.toString(ans);
        //remaining="Your Battery % is "+remaining;
        return  remaining;
    }
}
