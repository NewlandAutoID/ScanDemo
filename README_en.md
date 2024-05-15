# ScanDemo说明

[TOC]

------

| 日期       | 说明        |
| ---------- | ----------- |
| 2024-05-15 | Init Commit |
|            |             |
|            |             |

> 该用例为简单的读码API应用使用说明

一、注册监听解码数据广播	

```java
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
                final String scanResult_1=intent.getStringExtra("SCAN_BARCODE1"); // Scan Result
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
```

二、配置读码参数

```java
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
```

> 可根据配置自行调整设置参数

三、启动读码

```java
/*
 Start Scanner Broadcast
 Button [Start Scan]
 Action Name : [nlscan.action.SCANNER_TRIG]
 Param : [SCAN_TIMEOUT] value: int, 1-9; unit: second
*/
Intent intentstart = new Intent("nlscan.action.SCANNER_TRIG");
intentstart.putExtra("SCAN_TIMEOUT", 9);
mContext.sendBroadcast(intentstart);
```

四、停止读码

```java
/*
 Start Scanner Broadcast
 Button [Start Stop]
 Action Name : [nlscan.action.STOP_SCAN]
*/
Intent intentstop = new Intent("nlscan.action.STOP_SCAN");
mContext.sendBroadcast(intentstop);
```

