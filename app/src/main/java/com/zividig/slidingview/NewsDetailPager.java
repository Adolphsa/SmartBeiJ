package com.zividig.slidingview;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.viewpagerindicator.TabPageIndicator;
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
    private ArrayList<MenuTitleData.MainMenuData> data;

    private ViewPager mViewPager;
    private TabPageIndicator indicator;
    private ImageButton nextPager;  //切换新闻标题的按钮

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

        //初始化控件TabPageIndicator
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        nextPager = (ImageButton) view.findViewById(R.id.btn_pagerChange);

        //设置监听，当按钮被点击后，页面加1
        nextPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(++currentItem);
            }
        });

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
        indicator.setViewPager(mViewPager);
    }



    class NewsTabAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) { //返回当前页面的标题
            return data.get(position).title;
        }

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
            BaseNewsTabPager pager = mArrayList.get(position);
            container.addView((pager.rootView));
            pager.initData();
            return pager.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
