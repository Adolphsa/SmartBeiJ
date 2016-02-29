package com.zividig.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-02-29.
 */
public class NewsCenterPager extends BasePager {

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("NewsCenterPager", "初始化新闻中心数据");
        title.setText("新闻");
        setSlidingMenuEnable(true);

        TextView text = new TextView(mActivity);
        text.setText("新闻中心");
        text.setTextSize(20);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
