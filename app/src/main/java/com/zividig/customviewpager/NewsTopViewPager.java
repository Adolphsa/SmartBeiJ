package com.zividig.customviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016-03-07.
 */
public class NewsTopViewPager extends ViewPager {
    public NewsTopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsTopViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true); //要求父控件不拦截事件
        return super.dispatchTouchEvent(ev);
    }
}
