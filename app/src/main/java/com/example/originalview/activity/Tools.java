package com.example.originalview.activity;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * 用于仿YouKu菜单栏
 *
 * @author ALion
 */
public class Tools {

//    //方法的重载
//    public static void hideView(ViewGroup view) {
//        hideView(view, 0);
//    }
//
    //隐藏动画
//    public static void hideView(ViewGroup view, long delay) {
//        RotateAnimation anim = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
//        anim.setDuration(500);
//        anim.setFillAfter(true);//动画后保持状态
//        anim.setStartOffset(delay);//延迟多次时间后再运行动画
//
//        view.startAnimation(anim);
//
//        //隐藏后，封掉该view的子对象按钮
//        int childCount = view.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            view.getChildAt(i).setEnabled(false);
//        }
//    }
//
//    //方法的重载
//    public static void showView(ViewGroup view) {
//        showView(view, 0);
//    }
//
//    //显示动画
//    public static void showView(ViewGroup view, long delay) {
//        RotateAnimation anim = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
//        anim.setDuration(500);
//        anim.setFillAfter(true);
//        anim.setStartOffset(delay);
//
//        view.startAnimation(anim);
//    }


    //属性动画师，可以完全移动view
    //隐藏
    public static void hideView(ViewGroup view, long delay) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
        anim.setDuration(500);
        anim.setStartDelay(delay);
        anim.start();
    }

    //显示
    public static void showView(ViewGroup view, long delay) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", 180, 360);
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
        anim.setDuration(500);
        anim.setStartDelay(delay);
        anim.start();
    }

    //重载
    public static void hideView(ViewGroup view) {
        hideView(view, 0);
    }

    public static void showView(ViewGroup view) {
        showView(view, 0);
    }
}
