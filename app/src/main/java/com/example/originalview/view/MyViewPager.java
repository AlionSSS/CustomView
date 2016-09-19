package com.example.originalview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义ViewPager
 * 1. 写一个类继承ViewGroup
 * 2. 重写onLayout方法，保证孩纸正常显示（一字排开）
 * 3. 重写onTouchEvent，手势识别器（onScroll）,scrollBy
 * 4. 监听手指抬起事件，根据当前滑动后的位置，确定下一个页面，scrollTo
 * 5. 防止pos过大
 * 6. 平滑移动，Scroller, startScroll->回调computeScroll
 * 7. 动态增加RadioButton，监听自定义ViewPager页面切换（回调接口），更改RadioButton选中位置
 * 8. 监听RadioButton切换事件，更改页面
 * 9. 增加测试页面（ScrollView）
 * 10. 重写onMeasure，测量每个孩子
 * 11. 重写onInterceptTouchEvent，在适当时机（水平滑动），中断事件传递
 *
 * @author ALion
 */
public class MyViewPager extends ViewGroup {

    private GestureDetector mDetector;
    private int pos;
    private Scroller mScroller;

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MyViewPager(Context context) {
        super(context);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //手势识别器
        mDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            //手势滑动
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                if ((pos <= 0 && distanceX < 0) || (pos >= getChildCount() - 1 && distanceX > 0))
                    scrollBy(0, 0);
                else
                    scrollBy((int) distanceX, 0);//滑动偏移一定距离，相对位移

//                scrollTo(x, y);//绝对位移，移动到具体的x, y坐标

                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
        //初始化滑动器
        mScroller = new Scroller(getContext());
    }

    //测量布局宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //手动测量所有子对象的宽高,解决测试页面无法展示的问题
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

//        int size = MeasureSpec.getSize(widthMeasureSpec);
//        System.out.println("======size: " + size);
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
////        MeasureSpec.AT_MOST;  //最大值wrap_content
////        MeasureSpec.EXACTLY;  //确定值，宽高写死，例100dp match_parent
////        MeasureSpec.UNSPECIFIED;//没有指定宽高
//        System.out.println("======mode: " + mode);
    }

    //设置布局位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //手动设置子对象的位置,保证一字排开
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);//委托手势识别器处理

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //1.获取当前滑动位置 2.根据当前位置，确定应该跳到第几个页面 3.跳到确定的页面
                int scrollX = getScrollX();
                //判断跳到第几个页面
                pos = (scrollX + getWidth() / 2) / getWidth();
                //防止pos过大
                if (pos > getChildCount() - 1)
                    pos = getChildCount() - 1;
                //移动到确定页面
                setCurrentItem(pos);
                //页面切换回调
                if (mListener != null) {
                    mListener.onPageSelected(pos);
                }
                break;
        }
        return true;
    }

    //切换到某个具体的页面
    public void setCurrentItem(int pos) {
        //移动到确定页面
        int distance = pos * getWidth() - getScrollX();//目标位置 - 当前位置 = 要滑动的距离
        //此方法不会产生滑动，而是会不断回调computeScroll()，需要在这个方法中处理滑动器
        mScroller.startScroll(getScrollX(), 0, distance, 0, Math.abs(distance));//设定滑动参数，arg4滑动时间=距离绝对值，保证距离越长时间越久
        invalidate();//刷新界面，保证滑动器正常运行
    }

    //当startScroll后，系统会不断回调此方法
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//判断回调回调有没有结束
            int currX = mScroller.getCurrX();//获取当前应该移动到的位置
            scrollTo(currX, 0);//移动到确定位置
            invalidate();
        }
    }

    private OnPageChangeListener mListener;

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    public interface OnPageChangeListener {
        void onPageSelected(int position);
    }

    //事件中断 onInterceptTouchEvent->onTouchEvent
    private int startX;
    private int startY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果左右滑动，需要拦截；如果上下滑动，不需要拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDetector.onTouchEvent(ev);//将ACTION_DOWN事件传递给手势识别器，避免事件丢失（return false的话就不会调用onTouchEvent()方法，而会传递给子对象）
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();

                int dX = endX - startX;
                int dY = endY - startY;

                return Math.abs(dX) > Math.abs(dY);
//                break;
        }
        return false;//true表示要中断事件传递，不允许子对象响应，由父控件处理
    }

    //事件分发  优先顺序: dispatchTouchEvent -> onInterceptTouchEvent -> onTouchEvent
    //                      1.要不要发出去事件  2.要不要拦截     3.怎么处理
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
