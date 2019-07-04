package com.example.wireless;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     String no;
    String mes;
    private ListView lvSMS;
    private final static int REQUEST_CODE_PERMISSION_READ_SMS=456;
    ArrayList<String> smsMsgList=new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    public static MainActivity instance;
    private View view;

    public static  MainActivity Instance(){
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        lvSMS=(ListView) findViewById(R.id.lvsms);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,smsMsgList);
        lvSMS.setAdapter(arrayAdapter);
        if(checkPermission(Manifest.permission.READ_SMS)){
            refreshInbox();

        }else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_SMS},REQUEST_CODE_PERMISSION_READ_SMS);

        }

    }
    private boolean checkPermission(String readSms) {
        int checkPermission= ContextCompat.checkSelfPermission(this,readSms);
        return  checkPermission==getPackageManager().PERMISSION_GRANTED;
    }

    public void refreshInbox() {
        this.view = view;
        ContentResolver cResolver=getContentResolver();
        Cursor smsInboxCursor=cResolver.query(Uri.parse("content://sms/inbox"),null,null,null,null);
        int indoxBody=smsInboxCursor.getColumnIndex("body");
        int indexAddress=smsInboxCursor.getColumnIndex("address");
        if(indoxBody<0 || !smsInboxCursor.moveToFirst()){

            return;
        }

        no=smsInboxCursor.getString(indexAddress);
        mes=smsInboxCursor.getString(indoxBody);

        arrayAdapter.clear();
        do{
            String str ="SMS From : "+smsInboxCursor.getString(indexAddress)+"\n";
            str+=smsInboxCursor.getString(indoxBody);
            arrayAdapter.add(str);
        }while(smsInboxCursor.moveToNext());
    }
    public void updateList(final String smsMsg){

        arrayAdapter.insert(smsMsg,0);
        arrayAdapter.notifyDataSetChanged();
        refreshInbox();
    }

}