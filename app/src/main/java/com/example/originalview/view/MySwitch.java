package com.example.originalview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.originalview.R;

/**
 * 自定义view流程
 * 1.写类继承View 2.重写onDraw，进行绘制 3.重写onMeasure，修改尺寸 4.在xml布局文件中配置
 *
 * @author ALion
 */
public class MySwitch extends View {

    private Paint mPaint;
    private Bitmap mBitmapBg;
    private Bitmap mBitmapSlide;
    private int mSlideLeft;
    private boolean isOpen;//当前开关状态
    private int MAX_LEFT;
    private int startX;
    private int moveX;
    private boolean isClick;

    private static final String NAME_SPACE = "http://schemas.android.com/apk/res/com.example.originalview";

    public MySwitch(Context context) {
        super(context);
        initView();
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

        //获取属性值
        isOpen = attrs.getAttributeBooleanValue(NAME_SPACE, "checked", false);
        //加载自定义滑块
        int slideId = attrs.getAttributeResourceValue(NAME_SPACE, "slide", -1);//-1表示无效
        int bgId = attrs.getAttributeResourceValue(NAME_SPACE, "bg", -1);
        if (slideId > 0 && bgId > 0) {
            mBitmapSlide = BitmapFactory.decodeResource(getResources(), slideId);
            mBitmapBg = BitmapFactory.decodeResource(getResources(), bgId);
        }
        if (isOpen) {
            mSlideLeft = MAX_LEFT;
        } else {
            mSlideLeft = 0;
        }
        invalidate();
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);//画笔颜色

        //初始化背景Bitmap
        mBitmapBg = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        //初始化滑块Bitmap
        mBitmapSlide = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);

        MAX_LEFT = mBitmapBg.getWidth() - mBitmapSlide.getWidth();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据位移判断是点击事件，还是移动事件
                if (isClick) {
                    if (isOpen) {
                        isOpen = false;
                        mSlideLeft = 0;
                    } else {
                        isOpen = true;
                        mSlideLeft = MAX_LEFT;
                    }
                    invalidate();//重绘view，刷新，重新调用onDraw()。invalidate使无效
                    //回调当前开关状态
                    if (mListener != null) {
                        mListener.onCheckedChanged(MySwitch.this, isOpen);
                    }
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录起始x坐标
                //相对于当前控件的x坐标。getRawX()是获取相对于屏幕的
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.记录移动后的x坐标 3.记录x偏移量 4.根据偏移量更新左边距 5.刷新 6.重新初始化起点坐标
                int endX = (int) event.getX();
                int dX = endX - startX;
                mSlideLeft += dX;
                moveX = Math.abs(dX);//向左向右都要统计下来，所以要用绝对值
                //避免滑块超出边界
                if (mSlideLeft < 0)
                    mSlideLeft = 0;
                if (mSlideLeft > MAX_LEFT)
                    mSlideLeft = MAX_LEFT;

                invalidate();//刷新

                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //根据位移判断是点击事件，还是移动事件
                isClick = moveX < 2;
                moveX = 0;
                if (!isClick) {
                    //根据当前位置，去切换开关状态
                    if (mSlideLeft < MAX_LEFT / 2) {
                        mSlideLeft = 0;
                        isOpen = false;
                    } else {
                        mSlideLeft = MAX_LEFT;
                        isOpen = true;
                    }
                    invalidate();
                    //回调当前开关状态
                    if (mListener != null) {
                        mListener.onCheckedChanged(MySwitch.this, isOpen);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    //绘制过程    measure->layout->draw
    //onMeasure->onLayout->onDraw
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mBitmapBg.getWidth(), mBitmapBg.getHeight());//依照图片大小，来确定控件大小

//        System.out.println("==========onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(0, 0, 300, 300, mPaint);//画一个300x300的矩形

//        System.out.println("==========onDraw");
        canvas.drawBitmap(mBitmapBg, 0, 0, null);//绘制背景图片
        canvas.drawBitmap(mBitmapSlide, mSlideLeft, 0, null);//绘制滑块图片
    }

    private OnCheckedChangListener mListener;
    //设置开关监听
    public void setOnCheckedChangListener(OnCheckedChangListener listener) {
        mListener = listener;
    }

    /**
     * 监听状态的回调接口
     */
    public interface OnCheckedChangListener {
        void onCheckedChanged(View view, boolean isChecked);
    }
}
