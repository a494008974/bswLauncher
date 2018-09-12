package com.mylove.zhou;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author: wenford.li
 * date:   On 2018/5/2
 * email:  26597925@qq.com
 */

public abstract class BaseView {
    Context mCtx;

    WindowManager wm;
    WindowManager.LayoutParams wmParams;
    View mView;
    Runnable mRunnable;
    boolean mAdd = false;
    Unbinder unbinder;

    public  BaseView(Context ctx) {
        mCtx = ctx;
        wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        ;
        wmParams.gravity = Gravity.RIGHT  | Gravity.BOTTOM;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.format = PixelFormat.RGBA_8888;
        mRunnable = new Runnable() {
            @Override
            public void run() {
                hide();
            }
        };
    }

    public void setContent(int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        mView = inflater.inflate(layoutId, null, false);
        unbinder = ButterKnife.bind(this, mView);
        initView();
    }

    public View getView() {
        return mView ;
    }

    public void display() {
        if(mAdd) {
            if(mView.getHandler() != null)
                mView.getHandler().removeCallbacks(mRunnable);

            mView.setVisibility(View.VISIBLE);
        }else{
            mAdd = true;
            wm.addView(mView, wmParams);
        }
    }

    public void display(long timeout) {
        display();
        hide(timeout);
    }

    public void hide() {
        if(mAdd)
            mView.setVisibility(View.GONE);
    }

    public void hide(long timeout) {
        Handler mhandler = mView.getHandler();
        if(mhandler == null)
            mhandler = new Handler();

        mhandler.postDelayed(mRunnable, timeout);
    }

    public void remove() {
        wm.removeView(mView);
        mAdd = false;
        unbinder.unbind();
    }

    protected abstract void initView();

}
