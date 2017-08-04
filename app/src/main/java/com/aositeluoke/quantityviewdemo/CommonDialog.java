package com.aositeluoke.quantityviewdemo;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * 类描述:公共的对话框
 * 使用场景：应用中只有一个页面会用到这个UI的对话框
 * 初始化对话框时，提供一个对话框布局，一个主题(提供常用的两种，1、在屏幕中间透明度渐变显示 2、从下往上进入，从上往下退出)
 * 作者:xues
 * 时间:2017年07月17日
 */

public class CommonDialog extends AppCompatDialog {
    private volatile View mRootView;
    /**
     * 取消点击事件，关闭对话框
     */
    private final View.OnClickListener mCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    /**
     * @param context       Activity上下文对象，非Application
     * @param dialogThemeId 对话框主题
     * @param layoutId      布局id
     */
    public CommonDialog(Context context, @StyleRes int dialogThemeId, @LayoutRes int layoutId) {
        super(context, dialogThemeId);
        this.mRootView = LayoutInflater.from(context).inflate(layoutId, null);
        this.setContentView(this.mRootView);
    }

    /**
     * @param context       Activity上下文对象，非Application
     * @param dialogThemeId 对话框主题
     * @param layoutId      布局id
     * @param inBottom      是否在窗体的下方
     */
    public CommonDialog(Context context, @StyleRes int dialogThemeId, @LayoutRes int layoutId, boolean inBottom) {
        super(context, dialogThemeId);
        this.mRootView = LayoutInflater.from(context).inflate(layoutId, null);
        this.setContentView(this.mRootView);
//        设置对话框显示在窗体的最下方
        if (inBottom) {
            Window window = getWindow();
            window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
            DisplayMetrics displayMetrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            window.setLayout(displayMetrics.widthPixels, window.getAttributes().height);
        }
    }


    /**
     * 设置显示的文字
     *
     * @param viewID  控件id
     * @param content 字符串
     */
    public CommonDialog setText(@IdRes int viewID, String content) {
        try {
            TextView view = findViewByRootView(viewID);
            view.setText(content);
        } catch (Exception e) {
            throw new NullPointerException();
        }
        return this;
    }

    /**
     * 设置点击事件
     *
     * @param viewID        控件id
     * @param clickListener 点击事件
     */
    public CommonDialog setOnClickListener(@IdRes int viewID, View.OnClickListener clickListener) {
        View view = findViewByRootView(viewID);
        if (view == null) {
            throw new NullPointerException();
        } else {
            view.setOnClickListener(clickListener);
        }
        return this;
    }


    /**
     * 设置关闭对话框点击事件
     *
     * @param viewID 控件id
     */
    public CommonDialog setCloseDialogListener(@IdRes int viewID) {
        View view = findViewByRootView(viewID);
        if (view == null) {
            throw new NullPointerException();
        } else {
            view.setOnClickListener(mCancelClickListener);
        }
        return this;
    }


    /**
     * @param id  mRootView布局中的控件id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewByRootView(@IdRes int id) {
        return (T) mRootView.findViewById(id);
    }
}
