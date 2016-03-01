package com.zividig.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zividig.data.MenuTitleData;
import com.zividig.smartbeij.MainActivity;
import com.zividig.smartbeij.R;
import com.zividig.viewpager.NewsCenterPager;

import java.util.ArrayList;

/**
 * 侧边栏
 * Created by Administrator on 2016-02-26.
 */
public class LeftMenuFragment extends BaseFragment {

    private ListView slidingMenuTitle;
    private ArrayList<MenuTitleData.SlidingMenuData> mArrayList;
    private SlidingMenuTitleAdapter mAdapter;
    private int mPosition;

    @Override
    public View initView() {

        View leftMenuView = View.inflate(mActivity, R.layout.frament_leftmenu,null);

        slidingMenuTitle = (ListView)leftMenuView.findViewById(R.id.lv_slidingMenu);


        return leftMenuView;
    }

    @Override
    public void initData() {

        //设置listView的监听
        slidingMenuTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                System.out.print(mPosition);
                mAdapter.notifyDataSetChanged();//通知view重新绘制界面

                setDetilPager(position);
            }
        });
    }

    //获取标题数据
    public void getData(MenuTitleData data){
        MenuTitleData titleData = data;
        Log.d("左侧边栏",titleData.toString());
        mArrayList = titleData.data;

        mAdapter = new SlidingMenuTitleAdapter();
        slidingMenuTitle.setAdapter(mAdapter);//先获取到数据再进行设置


    }

    public void setDetilPager(int position){
        MainActivity mainActivity = (MainActivity) mActivity;
        MainContextFragment mainContext = (MainContextFragment)mainActivity.getMainFrament();
        NewsCenterPager newsCenterPager = mainContext.getNewsCenterPager();
        newsCenterPager.setDetilPager(position);
    }

    //侧边栏ListView的适配器Adapter
    class SlidingMenuTitleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public MenuTitleData.SlidingMenuData getItem(int position) {
            return mArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(mActivity,R.layout.listview_sliding_item,null);
            TextView tvSlidingText = (TextView) view.findViewById(R.id.tv_title);
            MenuTitleData.SlidingMenuData data = getItem(position);
            tvSlidingText.setText(data.title);//设置侧边栏的标题

            if (mPosition == position){//判断点击的item和绘制的item是否是同一个
                //红色
                tvSlidingText.setEnabled(true);
                System.out.print("红色");
            }else{
                //白色
                tvSlidingText.setEnabled(false);
            }
            return view;
        }
    }


}