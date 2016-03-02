package com.zividig.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Chronometer;

import java.util.jar.Attributes;

/**
 * 自定义ViewPager   不能滑动
 * Created by Administrator on 2016-02-29.
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    //不拦截事件的传递
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }

    //重写onTouchEvent让viewPager不滚动
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
