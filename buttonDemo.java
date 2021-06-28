package com.example.robocart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.reeman.nerves.RobotActionProvider;
import com.rsc.impl.OnROSListener;
import com.rsc.impl.RscServiceConnectionImpl;
import com.rsc.reemanclient.ConnectServer;

import android.service.reeman.IReemanService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.mbutton);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mbuttonText();
            }
        });
    }

    private void mbuttonText() {
        mButton.setText("Bring it ON");
    }
}
