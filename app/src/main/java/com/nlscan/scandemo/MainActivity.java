package com.nlscan.scandemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IntentFilter mFilter = null;
    private BroadcastReceiver mReceiver = null;

    private TextView mScanResult = null;
    private Button mScanStart = null;
    private Button mScanStop = null;
    private Boolean ColorRed = false;

    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScanResult = (TextView)findViewById(R.id.ScanResult);
        mScanStart =  (Button)findViewById(R.id.ScanStart);
        mScanStart.setOnClickListener(this);
        mScanStop =  (Button)findViewById(R.id.ScanStop);
        mScanStop.setOnClickListener(this);

        mContext = this;
        /*
        Scanner Result:
        TextView : [ScanResult]
        Broadcase Name :[nlscan.action.SCANNER_RESULT]
        Scanner Result: [SCAN_BARCODE1]
        Scanner Type: [SCAN_BARCODE_TYPE]
        Scanner State: [SCAN_STATE]
         */
        mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                final String scanResult_1=intent.getStringExtra("SCAN_BARCODE1");
                final int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1); // -1:unknown
                final String scanStatus=intent.getStringExtra("SCAN_STATE");
                if("ok".equals(scanStatus)){
                    // You can modify the following functions

                    mScanResult.setText(scanResult_1);
                    if(false == ColorRed)
                    {
                        mScanResult.setTextColor(Color.RED);
                        ColorRed = true;
                    }
                    else
                    {
                        mScanResult.setTextColor(Color.BLUE);
                        ColorRed = false;
                    }
                    // End functions
                }
            }
        };
        mFilter= new IntentFilter("nlscan.action.SCANNER_RESULT");
        registerReceiver(mReceiver, mFilter);
        // End Scanner Result

        /*
        Config Scanner
         */
        Intent intent = new Intent ("ACTION_BAR_SCANCFG");
        // Scanner Enable
        intent.putExtra("EXTRA_SCAN_POWER", 1);
        // Scanner Mode
        intent.putExtra("EXTRA_SCAN_MODE", 3);
        sendBroadcast(intent);
        // End Scanner Config
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.ScanStart:
                /*
                Start Scanner Broadcast
                Button [Start Scan]
                Action Name : [nlscan.action.SCANNER_TRIG]
                Param : [SCAN_TIMEOUT] value: int, 1-9; unit: second
                */
                Intent intentstart = new Intent("nlscan.action.SCANNER_TRIG");
                intentstart.putExtra("SCAN_TIMEOUT", 9);
                mContext.sendBroadcast(intentstart);
                break;

            case R.id.ScanStop:
                /*
                Start Scanner Broadcast
                Button [Start Stop]
                Action Name : [nlscan.action.STOP_SCAN]
                */
                Intent intentstop = new Intent("nlscan.action.STOP_SCAN");
                mContext.sendBroadcast(intentstop);
                break;

            default:
                break;

            }
        }
    }