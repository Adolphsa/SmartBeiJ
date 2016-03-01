package com.zividig.slidingview;

import android.app.Activity;
import android.view.View;

import com.zividig.viewpager.BasePager;

/**
 * 侧边栏切换的页面的基类
 * Created by Administrator on 2016-03-01.
 */
public abstract class BaseDetilPager {

    public Activity mActivity;
    public View rootView;

    public BaseDetilPager(Activity activity){
        mActivity = activity;
        rootView = initView();
    }

    //初始化界面
    public abstract View initView();

    //初始化数据
    public void initData(){

    }
}
