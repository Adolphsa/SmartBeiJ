package com.zividig.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.zividig.smartbeij.R;
import com.zividig.viewpager.BasePager;
import com.zividig.viewpager.GovAffairsPager;
import com.zividig.viewpager.HomePager;
import com.zividig.viewpager.NewsCenterPager;
import com.zividig.viewpager.SettingPager;
import com.zividig.viewpager.SmartServicePager;

import java.util.ArrayList;

/**
 * 主页面
 * Created by Administrator on 2016-02-26.
 */
public class MainContextFragment extends BaseFragment {

    private RadioGroup radioGroup;
    private ViewPager viewPagerContext;

    private ArrayList<BasePager> viewPagerList;
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.frament_maincontext,null);

        radioGroup = (RadioGroup)view.findViewById(R.id.radioBtn_group);
        viewPagerContext = (ViewPager)view.findViewById(R.id.vp_mainContext);

        return view;
    }

    @Override
    public void initData() {

        radioGroup.check(R.id.radioBtn_home);//默认勾选首页

        viewPagerList = new ArrayList<BasePager>();
        //初始化5个页面
        viewPagerList.add(new HomePager(mActivity));
        viewPagerList.add(new NewsCenterPager(mActivity));
        viewPagerList.add(new SmartServicePager(mActivity));
        viewPagerList.add(new GovAffairsPager(mActivity));
        viewPagerList.add(new SettingPager(mActivity));

        viewPagerContext.setAdapter(new viewPagerContextAdapter());

        //监听radioGroup选中的事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioBtn_home:
                        viewPagerContext.setCurrentItem(0,false);//去掉切换的动画效果
                        break;
                    case R.id.radioBtn_newsCenter:
                        viewPagerContext.setCurrentItem(1,false);
                        break;
                    case R.id.radioBtn_smartService:
                        viewPagerContext.setCurrentItem(2,false);
                        break;
                    case R.id.radioBtn_govAffairs:
                        viewPagerContext.setCurrentItem(3,false);
                        break;
                    case R.id.radioBtn_setting:
                        viewPagerContext.setCurrentItem(4,false);
                        break;
                }

            }
        });

        //监听viewPager状态的改变
        viewPagerContext.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerList.get(position).initData();//页面被选中的时候加载initData方法
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPagerList.get(0).initData();//默认初始化首页数据
    }

    public NewsCenterPager getNewsCenterPager(){
        NewsCenterPager pager = (NewsCenterPager)viewPagerList.get(1);
        return pager;
    }

    class viewPagerContextAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = viewPagerList.get(position);
            container.addView(pager.rootViewPager);
            return pager.rootViewPager;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
