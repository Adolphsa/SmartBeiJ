package com.zividig.newspager;


import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zividig.data.MenuTitleData;
import com.zividig.slidingview.BaseDetailPager;

/**
 * 填充NewsDetilPager的页面
 * Created by Administrator on 2016-03-02.
 */
public class BaseNewsTabPager extends BaseDetailPager {

    private TextView newsText;
    private MenuTitleData.MainMenuData data;

    public BaseNewsTabPager(Activity activity, MenuTitleData.MainMenuData mainMenuData) {
        super(activity);
        data = mainMenuData;
        Log.d("小孩数据",data.toString());
    }

    @Override
    public View initView() {

        newsText = new TextView(mActivity);
        newsText.setText("新添加的");
        newsText.setTextSize(20);
        newsText.setTextColor(Color.RED);
        newsText.setGravity(Gravity.CENTER);

        return newsText;
    }

    @Override
    public void initData() {
        newsText.setText(data.title);
        Log.d("设置的标题",data.title);
    }
}
