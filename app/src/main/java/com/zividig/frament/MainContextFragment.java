package com.zividig.frament;

import android.view.View;

import com.zividig.smartbeij.R;

/**
 * 主页面
 * Created by Administrator on 2016-02-26.
 */
public class MainContextFragment extends BaseFragment {
    @Override
    public View initView() {
        View mainConTextView = View.inflate(mActivity, R.layout.frament_maincontext,null);
        return mainConTextView;
    }
}
