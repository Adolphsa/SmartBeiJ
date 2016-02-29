package com.zividig.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-02-29.
 */
public class SettingPager extends BasePager {

    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("SettingPager", "初始化设置数据");
        title.setText("设置");
        btn_menu.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);

        TextView text = new TextView(mActivity);
        text.setText("设置");
        text.setTextSize(20);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
