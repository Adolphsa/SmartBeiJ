package com.zividig.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment的基类
 * Created by Administrator on 2016-02-26.
 */
public abstract class BaseFragment extends android.app.Fragment {

    public Activity mActivity;

    //创建fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }

    //处理fragment的布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    //依附的activity创建完成
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    //子类必须实现初始化布局方法
    public abstract View initView();

    //初始化数据
    public void initData(){}
}
