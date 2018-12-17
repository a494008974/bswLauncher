package com.mylove.slideanimation.anim;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.mylove.slideanimation.R;

/**
 * TODO: document your custom view class.
 */
public class SlideView extends FrameLayout {
    private EnterAnimLayout enterAnimLayout;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public SlideView(Context context) {
        super(context);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        enterAnimLayout = new EnterAnimLayout(context);
        addView(enterAnimLayout);
    }

    public void showView(View view){
        if(enterAnimLayout == null) return;
        enterAnimLayout.removeAllViews();
        enterAnimLayout.addView(view);
    }

    public void start(){
        if(enterAnimLayout == null) return;
        Anim anim = new AnimBaiYeChuang(enterAnimLayout);
        anim.startAnimation(2500);//开始播放动画，动画播放时长2500ms，默认2000
    }

}
