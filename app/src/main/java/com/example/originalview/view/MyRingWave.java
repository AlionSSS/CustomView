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

import java.util.ArrayList;
import java.util.Random;

/**
 * 绘制波浪效果的环
 *
 * 开发流程：
 * 1.重写onTouchEvent，ACTION_DOWN ACTION_MOVE
 * 2.封装Wave对象
 * 3.圆环集合mWaveList
 * 4.addPoint(x, y)，第一次进入，启动绘制
 * 5.在Handler接收消息
 * 6.flushData，刷新集合中所有的圆环的属性
 * 7.invalidate，刷新界面，继续发消息，形成内循环
 * 8.onDraw，绘制所有圆环
 * 9.调整圆环的间距、透明度变化速度、宽度变化速度
 *
 * @author ALion
 */
public class MyRingWave extends View {

    private static final int MINI_DIS = 10;//圆环最小间距

    private ArrayList<Wave> mWaveList = new ArrayList<>();//各种圆环的集合
    private int[] mColors = new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            flushData();
            invalidate();

            if (!mWaveList.isEmpty())   //圆环集合不为空，继续发消息
                mHandler.sendEmptyMessageDelayed(0, 50);
        }
    };

    //刷新数据
    private void flushData() {
        //遍历圆环集合，修改每个圆环属性
        ArrayList<Wave> removeList = new ArrayList<>();//需要移除的集合
        for (Wave wave : mWaveList) {
            //1.半径变大 2.宽度变大 3.透明度变化
            wave.radius += 5;
            wave.paint.setStrokeWidth(wave.radius / 3);

            //检查透明度是否为0，如果是，就移除该对象
            if (wave.paint.getAlpha() <= 0) {
//                mWaveList.remove(wave);//并发修改异常
                removeList.add(wave);
                continue;
            }

            int alpha = wave.paint.getAlpha();
            alpha -= 7;
            if (alpha < 0)
                alpha = 0;
            wave.paint.setAlpha(alpha);
        }
        mWaveList.removeAll(removeList);//从原始集合中移除alpha为0的对象
    }

    public MyRingWave(Context context) {
        super(context);
    }

    public MyRingWave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRingWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //遍历集合，绘制每一个圆环
        for (Wave wave : mWaveList) {
            canvas.drawCircle(wave.cx, wave.cy, wave.radius, wave.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                addPoint((int) event.getX(), (int) event.getY());
                break;
        }

        return true;
    }

    private void addPoint(int x, int y) {
        if (mWaveList.isEmpty()) { //第一次进来
            addWave(x, y);//添加一个波浪
            mHandler.sendEmptyMessage(0);//发消息开始绘制圆环
        } else {
            //先获取集合中最后一个圆
            Wave lastWave = mWaveList.get(mWaveList.size() - 1);
            //判断将要添加的圆和最后一个圆的距离是否达到一定值。这样圆环之间就不会太密集
            if (Math.abs(x - lastWave.cx) > MINI_DIS || Math.abs(y - lastWave.cy) > MINI_DIS)
                addWave(x, y);

        }
        //不能像下面这样写。因为addWave(x, y)会让mWaveList不为空，从而导致第一次进来不会发消息
//        addWave(x, y);
//        if (mWaveList.isEmpty())
//            mHandler.sendEmptyMessage(0);
    }

    private void addWave(int x, int y) {
        Wave wave = new Wave();
        wave.cx = x;
        wave.cy = y;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(wave.radius / 3);
        paint.setAlpha(255);//设置透明度0-255，255表示完全不透明
        //设置随机色
//        Random random = new Random();
//        random.nextInt(4);
        int colorIndex = (int) (Math.random() * 4);
        paint.setColor(mColors[colorIndex]);

        wave.paint = paint;

        mWaveList.add(wave);
    }

    class Wave {
        public int cx;
        public int cy;
        public int radius;

        public Paint paint;
    }
}
