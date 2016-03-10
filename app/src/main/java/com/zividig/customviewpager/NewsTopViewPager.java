package com.zividig.customviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 新闻顶部图片的viewPager
 * Created by Administrator on 2016-03-07.
 */
public class NewsTopViewPager extends ViewPager {

    private int startX;
    private int startY;

    public NewsTopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsTopViewPager(Context context) {
        super(context);
    }

    /**
     * 事件分发
     * @param ev
     * @return boolean
     * 1.当viewPager的页面是在第一页，并且向右滑动时  需要被父控件拦截
     * 2.当viewPager的页面是在某个页面的最后一页，并且向左滑动时  需要父控件拦截
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch ( ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                getParent().requestDisallowInterceptTouchEvent(true); //父控件不要拦截事件，保证ACTION_MOVE事件的执行

                startX = (int)ev.getRawX(); //开始时点击的X坐标
                startY = (int)ev.getRawY(); //开始时点击的Y坐标

                break;

            case MotionEvent.ACTION_MOVE :

                int endX = (int)ev.getRawX();
                int endY = (int)ev.getRawY();

                if (Math.abs(endX - startX) > Math.abs(endY - startY)) {// 左右滑动
                    if (endX > startX) {// 右划
                        if (getCurrentItem() == 0) {// 第一个页面, 需要父控件拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {// 左划
                        if (getCurrentItem() == getAdapter().getCount() - 1) {// 最后一个页面,
                            // 需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {// 上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
