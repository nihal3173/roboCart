package com.reeman.demo3128;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.reeman.nerves.RobotActionProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RobotHardSDK instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initService();
    }

    private void initService() {
        instance = RobotHardSDK.getInstance(getApplication());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.release();
    }

    private void initView() {
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn5:
                //navigate to point "x1", you should mark x1 in your map in advance.
                RobotActionProvider.getInstance().sendRosCom("point[x1]");
                new Handler().postDelayed(() -> RobotActionProvider.getInstance().sendRosCom("point[x2]"), 30000 );//time in millisecond
                break;

            case R.id.btn6:
                //navigate to charge pile, mark charge pile in map, direct docking.
                RobotActionProvider.getInstance().sendRosCom("point_charge[charging_pile]");
                RobotActionProvider.getInstance().sendRosCom("goal: just_charge");
                //RobotActionProvider.getInstance().sendRosCom("bat: uncharge");
                break;

            case R.id.btn7:
               // RobotActionProvider.getInstance().sendRosCom("point[x3]");
                break;

            case R.id.btn8:
                //RobotActionProvider.getInstance().shutDown();
                break;

            default:

                break;
        }
    }
}
