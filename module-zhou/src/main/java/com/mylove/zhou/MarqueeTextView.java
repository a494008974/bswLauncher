package com.mylove.zhou;


import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MarqueeTextView extends AppCompatTextView {
	private boolean flag = true;
	public MarqueeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	  @Override
      public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
          if(flag)
              super.onFocusChanged(flag, direction, previouslyFocusedRect);
      }
      @Override
      public void onWindowFocusChanged(boolean focused) {
          if(flag)
              super.onWindowFocusChanged(flag);
      } 
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return flag;
	}

	public void setRoll(boolean flag) {
		// TODO Auto-generated method stub
		this.flag = flag;
	}
}
