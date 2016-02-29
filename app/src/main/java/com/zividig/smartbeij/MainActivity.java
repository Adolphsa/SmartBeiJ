package com.zividig.smartbeij;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.zividig.fragment.LeftMenuFragment;
import com.zividig.fragment.MainContextFragment;

public class MainActivity extends SlidingActivity {

    private final static String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private final static String FRAGMENT_MAIN_CONTEXT = "fragment_main_context";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.activity_leftmenu);

        SlidingMenu menu = getSlidingMenu();//获取侧边栏SlidingMenu对象
        menu.setMode(SlidingMenu.LEFT); //左滑
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); //设置成全屏触摸
        menu.setBehindOffset(500); //设置预留屏幕宽度
        menu.setFadeDegree(0.35f);

        initFragment();
    }

    public void initFragment(){
        android.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.ll_leftMenu,new LeftMenuFragment(),FRAGMENT_LEFT_MENU);
        transaction.replace(R.id.ll_mainContext,new MainContextFragment(),FRAGMENT_MAIN_CONTEXT);

        transaction.commit(); //提交事务
    }
}
