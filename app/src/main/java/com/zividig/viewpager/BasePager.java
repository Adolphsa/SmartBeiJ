package com.zividig.viewpager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zividig.smartbeij.MainActivity;
import com.zividig.smartbeij.R;

/**
 * viewPager页面的基类
 * Created by Administrator on 2016-02-29.
 */
public class BasePager{

    public Activity mActivity;
    public View rootViewPager;

    public TextView title;
    public FrameLayout flContent;
    public ImageButton btn_menu;

    public BasePager(Activity activity){
        mActivity = activity;
        initView();
    }

    //初始化布局
    public void initView(){
        //加载viewPager的布局文件
        rootViewPager = View.inflate(mActivity, R.layout.layout_contxet_viewpager,null);

        title = (TextView)rootViewPager.findViewById(R.id.tv_title);
        btn_menu = (ImageButton)rootViewPager.findViewById(R.id.imgBtn_menu);
        flContent = (FrameLayout)rootViewPager.findViewById(R.id.fl_content);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSlidingShow();
            }
        });

    }

    //设置侧边栏关闭的方法
    public void setSlidingShow(){
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu menu = mainActivity.getSlidingMenu();
        menu.toggle();
    }

    //初始化数据
    public void initData(){

    }

    //设置侧边栏开启或关闭
    public void setSlidingMenuEnable(boolean enable){
        MainActivity mainActivity = (MainActivity)mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if (enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
