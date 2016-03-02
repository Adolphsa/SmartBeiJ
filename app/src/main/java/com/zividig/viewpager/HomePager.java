package com.zividig.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * 首页
 * Created by Administrator on 2016-02-29.
 */
public class HomePager extends BasePager {

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("HomePager","初始化首页数据");
        title.setText("智慧北京");//设置标题
        btn_menu.setVisibility(View.INVISIBLE);//设置按钮不显示
        setSlidingMenuEnable(false);//设置侧边栏关闭

        TextView text = new TextView(mActivity);
        text.setText("主页");
        text.setTextSize(20);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
