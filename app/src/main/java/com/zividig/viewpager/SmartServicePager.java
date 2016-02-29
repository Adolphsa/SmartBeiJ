package com.zividig.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-02-29.
 */
public class SmartServicePager extends BasePager {

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("SmartServicePagerr", "初始化智慧服务数据");
        title.setText("智慧服务");
        setSlidingMenuEnable(true);

        TextView text = new TextView(mActivity);
        text.setText("智慧服务");
        text.setTextSize(20);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
