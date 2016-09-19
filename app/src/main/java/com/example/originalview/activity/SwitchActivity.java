package com.example.originalview.activity;

import android.view.View;
import android.widget.Toast;

import com.example.originalview.R;
import com.example.originalview.view.MySwitch;

/**
 * 按钮栏 Activity
 *
 * @author ALion
 */
public class SwitchActivity extends BaseActivity {

    private MySwitch msSwitch;

    @Override
    public void initView() {
        setContentView(R.layout.activity_switch);

        msSwitch = (MySwitch) findViewById(R.id.ms_switch);
    }

    @Override
    public void initListener() {
        msSwitch.setOnCheckedChangListener(new MySwitch.OnCheckedChangListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                Toast.makeText(SwitchActivity.this, "当前状态：" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
