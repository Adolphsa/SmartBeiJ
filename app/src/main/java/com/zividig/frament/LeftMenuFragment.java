package com.zividig.frament;

import android.view.View;

import com.zividig.smartbeij.R;

/**
 * 侧边栏
 * Created by Administrator on 2016-02-26.
 */
public class LeftMenuFragment extends BaseFragment {
    @Override
    public View initView() {

        View leftMenuView = View.inflate(mActivity, R.layout.frament_leftmenu,null);
        return leftMenuView;
    }
}
