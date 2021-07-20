package com.reeman.demo3128;

import android.os.Bundle;
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
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                RobotActionProvider.getInstance().moveFront(20);
                break;
            case R.id.btn2:
                RobotActionProvider.getInstance().moveBack(20, 0);
                break;
            case R.id.btn3:
                RobotActionProvider.getInstance().moveLeft(90, 0);
                break;
            case R.id.btn4:
                RobotActionProvider.getInstance().moveLeft(-90, 0);
                break;
            case R.id.btn5:
                //navigate to point "x1", you should mark x1 in your map in advance.
                RobotActionProvider.getInstance().sendRosCom("point[x1]");
                break;
            case R.id.btn6:
                //navigate to charge pile, you should mark charge pile in your map in advance.
                RobotActionProvider.getInstance().sendRosCom("point_charge[charging_pile]");
                RobotActionProvider.getInstance().sendRosCom("goal: just_charge");
            default:
                //for more command, please refer to our document
                break;
        }
    }
}
