package com.example.originalview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 绘制单个环
 * @author ALion
 */
public class MyRing extends View {

    private int cx;
    private int cy;
    private int radius;
    private Paint mPaint;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //1.半径变大 2.宽度变大 3.透明度变化
            radius += 9;
            mPaint.setStrokeWidth(radius / 3);
            int alpha = mPaint.getAlpha();//获取当前的透明度
            alpha -= 10;
            if (alpha < 0)
                alpha = 0;
            mPaint.setAlpha(alpha);

            invalidate();//刷新

            if (alpha > 0)
                mHandler.sendEmptyMessageDelayed(0, 50);//设置延时50毫秒，继续绘制圆环
        }
    };

    public MyRing(Context context) {
        super(context);
        initView();
    }

    public MyRing(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        radius = 0;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(radius / 3);
        mPaint.setColor(Color.RED);
        mPaint.setAlpha(255);//设置透明度0-255，255表示完全不透明
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cx = (int) event.getX();
                cy = (int) event.getY();

                //重新初始化数据
                initView();

                mHandler.sendEmptyMessage(0);
                break;
        }
        return super.onTouchEvent(event);
    }
}
