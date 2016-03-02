package com.zividig.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 政务
 * Created by Administrator on 2016-02-29.
 */
public class GovAffairsPager extends BasePager {

    public GovAffairsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("GovAffairsPager", "初始化政务数据");
        title.setText("政务");
        setSlidingMenuEnable(true);

        TextView text = new TextView(mActivity);
        text.setText("政务");
        text.setTextSize(20);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
