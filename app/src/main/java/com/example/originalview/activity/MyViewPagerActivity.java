package com.example.originalview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.originalview.R;
import com.example.originalview.view.MyViewPager;

/**
 * 自定义ViewPager Activity
 * @author ALion
 */
public class MyViewPagerActivity extends AppCompatActivity {

    private MyViewPager mViewPager;
    private int[] mImageIds = new int[]{R.drawable.a1, R.drawable.a2,
            R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6};
    private RadioGroup rgGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_pager);

        mViewPager = (MyViewPager) findViewById(R.id.mvp);
        //给自定义ViewPager添加图片
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mViewPager.addView(view);
        }

        //添加测试页面
        View testView = View.inflate(this, R.layout.item_test_list, null);
        mViewPager.addView(testView, 1);//将测试页面添加到第二个位置

        //初始化RadioButton
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        for (int i = 0; i < mImageIds.length + 1; i++) {
            RadioButton view = new RadioButton(this);
            view.setId(i);//以当前位置为id
            view.setChecked(i == 0);//默认第一个被点击

            rgGroup.addView(view);

        }

        //设置页面切换的监听
        mViewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                rgGroup.check(position);//id和position相等
            }
        });

        //RadioButton被选中的监听
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int position = checkedId;//id和position相等
                mViewPager.setCurrentItem(position);
            }
        });

    }

}
