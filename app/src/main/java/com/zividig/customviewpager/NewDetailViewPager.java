package com.zividig.customviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 新闻页面中的vieePager   id为vp_newsTab
 * 要重写两个构造方法
 * Created by Administrator on 2016-03-05.
 */
public class NewDetailViewPager extends ViewPager {
    public NewDetailViewPager(Context context) {
        super(context);
    }

    public NewDetailViewPager(Context context,AttributeSet attrs){
        super(context, attrs);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentItem() == 0){ //当为第一个页签时，允许父控件拦截事件
            getParent().requestDisallowInterceptTouchEvent(false);
        }else { //当不为第一个页签时，不允许父控件拦截事件
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.dispatchTouchEvent(ev);
    }
}
