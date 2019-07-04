package com.example.wireless;
import  android.location.LocationManager;
import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.jar.Attributes;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SMSR extends BroadcastReceiver {
    String msgAddress;
    String msgBody;
    String Name;
   private Location location;

    protected void onCreate(Bundle savedInstanceState) {
    }

    public static final String SMS_BUNDLE = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")) {
            if (bundle != null) {
                Object[] sms = (Object[]) bundle.get(SMS_BUNDLE);
                String smsMsg = "";
                SmsMessage smsMessage;


                for (int i = 0; i < sms.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);
                    } else {

                        smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                    }
                    msgBody = smsMessage.getMessageBody().toString();
                    msgAddress = smsMessage.getDisplayOriginatingAddress();
                    smsMsg += "SMS From:" + msgAddress;
                    smsMsg += msgBody + "\n";
                    msgBody =msgBody.toUpperCase();

                    String parts[] = msgBody.split(" ");
                    System.out.println("String converted to String array");
                    for(int x=0; i < parts.length; i++){
                        System.out.println(parts[i]);
                    }
                    if (parts[0].equals("1234")) {
                        switch (parts[1]) {
                            case "WIFION": {
                                WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                wifi.setWifiEnabled(true);
//                           ONOFF o=new ONOFF();
//                           o.ON();
                                break;
                            }
                            case "WIFIOFF": {
                                WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                wifi.setWifiEnabled(false);
//                            ONOFF o=new ONOFF();
//                            o.OFF();
                                break;
                            }
                            case ("BTON"): {
                                BluetoothAdapter mBluetoothAdapter;
                                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                mBluetoothAdapter.enable();
                                break;
                            }
                            case "BTOFF": {
                                BluetoothAdapter mBluetoothAdapter;
                                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                mBluetoothAdapter.disable();
                                break;

                            }
                            case "VM": {
                                context = context.getApplicationContext();
                                AudioManager audiomanager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                audiomanager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                break;

                            }
                            case "RM": {
                                context = context.getApplicationContext();
                                AudioManager audiomanager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                audiomanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                break;

                            }
                            case "ST": {
                                context = context.getApplicationContext();
                                AudioManager audiomanager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                audiomanager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                                break;
                            }
                            case "SEND": {
                                AddContact o = new AddContact();
                                Name = o.FindName(parts[2]);
                                int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);

                                if (permissionCheck == PERMISSION_GRANTED) {
                                    MyMessage(Name);
                                } else {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, 0);
                                }
                                break;
                            }
                            case "HSOFF": {

                                WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                wifi.setWifiEnabled(true);
                                WifiManager wi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                                wi.setWifiEnabled(false);
                                break;
                            }
                            case "BATTERY": {
                                IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                                Intent batteryStatus = context.registerReceiver(null, iFilter);

                                int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
                                int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

                                float batteryPct = level / (float) scale;
                                float ans = batteryPct * 100;
                                String remaining = Float.toString(ans);
                                MyMessage(remaining);
                                break;
                            }

                        }
                    }
                }
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 0:
                if (grantResults.length >= 0 && grantResults[0]== PERMISSION_GRANTED) {
                    MyMessage(Name);
                }
                else{

                }
                break;

        }
    }
    private void MyMessage(String no) {
        SmsManager smsManager = SmsManager.getDefault();
            String phoneNumber=msgAddress;
            String Message= Name;
            smsManager.sendTextMessage(phoneNumber,null,Message,null,null);
        }



    }