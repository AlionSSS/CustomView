package com.example.originalview.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.originalview.R;

/**
 * 仿YouKu菜单栏 Activity
 *
 * @author ALion
 */
public class YouKuActivity extends BaseActivity {

    private RelativeLayout rlLevel1;
    private ImageView ivHome;
    private ImageView ivMenu;
    private RelativeLayout rlLevel2;
    private ImageView ivSearch;
    private ImageView ivMyyouku;
    private RelativeLayout rlLevel3;

    private boolean isLevel3Show = true;
    private boolean isLevel2Show = true;
    private boolean isLevel1Show = true;

    @Override
    public void initView() {
        setContentView(R.layout.activity_you_ku);

        rlLevel1 = (RelativeLayout) findViewById(R.id.rl_level1);
        ivHome = (ImageView) findViewById(R.id.iv_home);

        rlLevel2 = (RelativeLayout) findViewById(R.id.rl_level2);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivMenu = (ImageView) findViewById(R.id.iv_menu);
        ivMyyouku = (ImageView) findViewById(R.id.iv_myyouku);

        rlLevel3 = (RelativeLayout) findViewById(R.id.rl_level3);
    }

    @Override
    public void initListener() {
        ivHome.setOnClickListener(this);

        ivSearch.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
        ivMyyouku.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home:
                if (isLevel2Show) {
                    Tools.hideView(rlLevel2);
                    isLevel2Show = false;
                    if (isLevel3Show) {     //如果level3正在展现中，也隐藏
                        Tools.hideView(rlLevel3, 200);  //动画延时200
                        isLevel3Show = false;
                    }
                } else {
                    Tools.showView(rlLevel2);
                    isLevel2Show = true;
                }
                break;
            case R.id.iv_search:
                System.out.println("===========iv_search");
                break;
            case R.id.iv_menu:
                if (isLevel3Show) {
                    Tools.hideView(rlLevel3);
                    isLevel3Show = false;
                } else {
                    Tools.showView(rlLevel3);
                    isLevel3Show = true;
                    System.out.println("==========");
                }
                break;
            case R.id.iv_myyouku:
                System.out.println("===========iv_myyouku");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (isLevel1Show) {
                Tools.hideView(rlLevel1);
                isLevel1Show = false;
                if (isLevel2Show) {
                    Tools.hideView(rlLevel2, 200);
                    isLevel2Show = false;
                    if (isLevel3Show) {
                        Tools.hideView(rlLevel3, 300);
                        isLevel3Show = false;
                    }
                }
            } else {
                Tools.showView(rlLevel1);
                isLevel1Show = true;
                Tools.showView(rlLevel2, 200);
                isLevel2Show = true;
            }
//            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
