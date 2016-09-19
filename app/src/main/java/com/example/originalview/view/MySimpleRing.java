package com.example.originalview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制棋盘
 *
 * @author ALion
 */
public class MySimpleRing extends View {

    private Paint mPaint;
    private Paint mPaintCircle;

    public MySimpleRing(Context context) {
        super(context);
        initView();
    }

    public MySimpleRing(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySimpleRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //直线的画笔
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(5);//设置线条宽度

        //圆的画笔
        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.BLACK);
        mPaintCircle.setStrokeWidth(4);
        mPaintCircle.setStyle(Paint.Style.STROKE);//样式-中空，画空心圆
        mPaintCircle.setAntiAlias(true);//去掉锯齿

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画一个棋盘
        for (int i = 0; i <= 400; i += 40) {
            canvas.drawLine(0, i, 400, i, mPaint);//画多条水平线
            canvas.drawLine(i, 0, i, 400, mPaint);//画多条竖直线
        }

//        for (int y = 20; y < 400; y += 40) {
//            for (int x = 20; x < 400; x += 40) {
//                //画圆，铺满
//                canvas.drawCircle(x, y, 20, mPaintCircle);//arg0, arg1 是圆心坐标  arg2 是半径
//            }
//        }
        //移动画布(前面的棋盘已经画好了，不会管，只会移动后面的)
        canvas.translate(-40, -40);
        //画圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 40, mPaintCircle);//arg0, arg1 是圆心坐标  arg2 是半径


    }
}
