package com.zividig.slidingview;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 新闻详情页
 * Created by Administrator on 2016-03-01.
 */
public class NewsDetilPager extends BaseDetilPager {

    public NewsDetilPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {

        TextView text = new TextView(mActivity);
        text.setText("新闻 -- 详情页");
        text.setTextSize(20);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        return  text;
    }
}
