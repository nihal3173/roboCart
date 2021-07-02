package com.reeman.demo3128;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import com.rsc.impl.OnROSListener;
import com.rsc.reemanclient.ConnectServer;

public class RobotHardSDK {
    private static RobotHardSDK sdk;
    private Application mContext;
    private ConnectServer cs;

    private RobotHardSDK(Application application) {
        mContext = application;
        cs = ConnectServer.getInstance(mContext);
        registerRos();
        registerReceiver();
    }

    public static RobotHardSDK getInstance(Application application) {
        if (sdk == null) {
            return sdk = new RobotHardSDK(application);
        }
        return sdk;
    }

    public void release() {
        cs.registerROSListener(null);
        unregisterReceiver();
        if (cs != null)
            cs.release();
        if (sdk != null)
            sdk = null;
    }

    public void registerRos() {
        if (cs == null)
            return;
        cs.registerROSListener(mRosListener);
    }

    //here, you can receiver some callbacks, such as hostname, robot navigation result...
    private OnROSListener mRosListener = new OnROSListener() {
        @Override
        public void onResult(String data) {
            if (data.startsWith("sys:boot:")) {
                //host name
                Log.w("host name", data.replace("sys:boot:", ""));
            } else if (data.startsWith("nav_res")) {
                //navigation result
            } else if (data.startsWith("point_charge:1")) {
                //can't find charge point
            } else if (data.startsWith("point:1")) {
                //can't find targe point
            } else {
                //please refer to "Reeman ROS document" we provided
            }
        }
    };


    RobotReceiver robotReceiver;

    private void registerReceiver() {
        robotReceiver = new RobotReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("REEMAN_BROADCAST_SCRAMSTATE");
        filter.addAction("AUTOCHARGE_ERROR_DOCKNOTFOUND");
        filter.addAction("AUTOCHARGE_ERROR_DOCKINGFAILURE");
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(robotReceiver, filter);
    }

    private void unregisterReceiver() {
        if (robotReceiver != null) {
            mContext.unregisterReceiver(robotReceiver);
        }
    }

    //here, you can receive some system broadcasts, such as power change, scram state.
    public class RobotReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("REEMAN_BROADCAST_SCRAMSTATE".equals(action)) {
                int stopState = intent.getIntExtra("SCRAM_STATE", -1);
            } else if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int mPlugType = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            } else if ("AUTOCHARGE_ERROR_DOCKNOTFOUND".equals(action)) {
                Log.d("POWER", "DOCKNOTFOUND");
            } else if ("AUTOCHARGE_ERROR_DOCKINGFAILURE".equals(action)) {
                Log.d("POWER", "DOCKINGFAILURE");
            }
        }
    }
}
