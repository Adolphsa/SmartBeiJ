package com.zividig.smartbeij;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.zividig.fragment.LeftMenuFragment;
import com.zividig.fragment.MainContextFragment;

import org.xutils.x;

public class MainActivity extends SlidingActivity {

    private final static String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private final static String FRAGMENT_MAIN_CONTEXT = "fragment_main_context";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //初始化xutils
        x.Ext.init(getApplication());
        x.Ext.setDebug(true);
        x.view().inject(this);

        setBehindContentView(R.layout.activity_leftmenu);//加入侧边栏Fragment
        SlidingMenu menu = getSlidingMenu();//获取侧边栏SlidingMenu对象
        menu.setMode(SlidingMenu.LEFT); //左滑
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); //设置成全屏触摸
        menu.setBehindOffset(500); //设置预留屏幕宽度


        initFragment();
    }

    //向侧边栏和主页中添加布局
    public void initFragment(){
        android.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.ll_leftMenu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
        transaction.replace(R.id.ll_mainContext, new MainContextFragment(), FRAGMENT_MAIN_CONTEXT);

        transaction.commit(); //提交事务
    }

    //获取侧边栏的对象
    public Fragment getLeftFrament(){
        FragmentManager fm = getFragmentManager();
        LeftMenuFragment leftMenuFragment = (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);
        return leftMenuFragment;
    }

    //获取主内容的对象
    public Fragment getMainFrament(){
        FragmentManager fm = getFragmentManager();
        MainContextFragment MainFragment = (MainContextFragment) fm.findFragmentByTag(FRAGMENT_MAIN_CONTEXT);
        return MainFragment;
    }
}
