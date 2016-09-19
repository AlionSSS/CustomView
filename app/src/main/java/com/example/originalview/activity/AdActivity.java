package com.example.originalview.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.originalview.R;

/**
 * 广告栏 Activity
 *
 * @author ALion
 */
public class AdActivity extends BaseActivity {

    private ViewPager vpPager;

    // 图片集合
    private int[] mImageIds = new int[]{
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
    // 图片标题集合
    private final String[] mImageDes = {"巩俐不低俗，我就不能低俗", "朴树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀"};
    private TextView tvTitle;
    private LinearLayout llContainer;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = vpPager.getCurrentItem();//拿到当前页面位置
            vpPager.setCurrentItem(++currentItem);

            mHandler.sendEmptyMessageDelayed(0, 3000);//继续发送延时2秒的消息，形成类似递归的效果，使广告一直循环
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_ad);

        vpPager = (ViewPager) findViewById(R.id.vp_pager);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
    }

    @Override
    public void initListener() {
        //设置滑动监听
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int pos = position % mImageIds.length;

                tvTitle.setText(mImageDes[pos]);

                for (int i = 0; i < mImageDes.length; i++)
                    llContainer.getChildAt(i).setEnabled(i == pos);
            }

            //某个页面被选中
            @Override
            public void onPageSelected(int position) {

            }

            //滑动状态改变
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //手指按下
                        mHandler.removeCallbacksAndMessages(null);//清除所有消息和Runnable对象，停止轮播
                        break;
                    case MotionEvent.ACTION_UP:     //手指抬起
                        mHandler.sendEmptyMessageDelayed(0, 3000);//继续轮播
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void initData() {
        vpPager.setAdapter(new MyAdapter());
        vpPager.setCurrentItem(mImageIds.length * 10000);//设置初始化的位置

        tvTitle.setText(mImageDes[0]);//初始化新闻标题

        //动态添加小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setImageResource(R.drawable.selector_point);
            view.setEnabled(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0)
                params.leftMargin = 10;//从第二个圆点开始设置间距
            params.topMargin = 5;
            view.setLayoutParams(params);
            llContainer.addView(view);
        }

        mHandler.sendEmptyMessageDelayed(0, 3000);//延时2秒更新广告的消息
    }

    @Override
    public void processClick(View v) {

    }

    class MyAdapter extends PagerAdapter {

        //返回item的个数
        @Override
        public int getCount() {
//            return mImageIds.length;
            return Integer.MAX_VALUE;
        }

        //判断当前要展现的view和返回的object是否是一个对象，如果是，才展现
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //类似listView的getView方法，初始化每个item的布局.viewPager默认自动加载前一张和后一张，保证始终保持3张图片，其余的都要销毁
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % mImageIds.length;

            ImageView view = new ImageView(AdActivity.this);
            view.setBackgroundResource(mImageIds[pos]);

            //将item布局添加给容器
            container.addView(view);
//            System.out.println("初始化item..." + position);

            return view;//返回item的布局对象
        }

        //item销毁的回调方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //从容器中移除布局对象
            container.removeView((View) object);
//            super.destroyItem(container, position, object);
//            System.out.println("销毁item..." + position);
        }
    }

}
