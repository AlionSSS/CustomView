package com.example.originalview.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.originalview.R;

/**
 * 主页面 Activity
 * @author ALion
 */
public class MainActivity extends BaseActivity {

    private Button btnYouku;
    private Button btnAd;
    private Button btnDrop;
    private Button btnSwitch;
    private Button btnMyVp;
    private Button btnWave;

    public void initView() {
        setContentView(R.layout.activity_main);

        btnYouku = (Button) findViewById(R.id.btn_youku);
        btnAd = (Button) findViewById(R.id.btn_ad);
        btnDrop = (Button) findViewById(R.id.btn_drop);
        btnSwitch = (Button) findViewById(R.id.btn_switch);
        btnMyVp = (Button) findViewById(R.id.btn_my_vp);
        btnWave = (Button) findViewById(R.id.btn_wave);
    }

    public void initListener() {
        btnYouku.setOnClickListener(this);
        btnAd.setOnClickListener(this);
        btnDrop.setOnClickListener(this);
        btnSwitch.setOnClickListener(this);
        btnMyVp.setOnClickListener(this);
        btnWave.setOnClickListener(this);
    }

    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_youku:
                startActivity(new Intent(this, YouKuActivity.class));
                break;
            case R.id.btn_ad:
                startActivity(new Intent(this, AdActivity.class));
                break;
            case R.id.btn_drop:
                startActivity(new Intent(this, DropDownActivity.class));
                break;
            case R.id.btn_switch:
                startActivity(new Intent(this, SwitchActivity.class));
                break;
            case R.id.btn_my_vp:
                startActivity(new Intent(this, MyViewPagerActivity.class));
                break;
            case R.id.btn_wave:
                startActivity(new Intent(this, WaveActivity.class));
                break;
        }
    }

}
