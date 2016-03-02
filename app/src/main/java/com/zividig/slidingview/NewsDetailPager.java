package com.zividig.slidingview;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.zividig.data.MenuTitleData;
import com.zividig.newspager.BaseNewsTabPager;
import com.zividig.smartbeij.R;

import java.util.ArrayList;

/**
 * 新闻详情页
 * Created by Administrator on 2016-03-01.
 */
public class NewsDetailPager extends BaseDetailPager {

    private ArrayList<BaseNewsTabPager> mArrayList;
    private ViewPager mViewPager;
    private ArrayList<MenuTitleData.MainMenuData> data;

    public NewsDetailPager(Activity activity) {
        super(activity);
    }

    public NewsDetailPager(Activity activity, ArrayList<MenuTitleData.MainMenuData> children) {
        super(activity);

        data = children;
    }

    @Override
    public View initView() {

        View view = View.inflate(mActivity, R.layout.activity_base_news_pager,null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_newsTab);

        return  view;
    }

    @Override
    public void initData() {

        mArrayList = new ArrayList<BaseNewsTabPager>();

        for (int i=0; i<data.size(); i++){
            BaseNewsTabPager pager = new BaseNewsTabPager(mActivity,data.get(i));
            mArrayList.add(pager);
        }

        mViewPager.setAdapter(new NewsTabAdapter());
    }

    class NewsTabAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView((mArrayList.get(position).rootView));
            mArrayList.get(position).initData();
            return mArrayList.get(position).rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
