package com.zividig.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zividig.data.MenuTitleData;
import com.zividig.fragment.LeftMenuFragment;
import com.zividig.slidingview.BaseDetilPager;
import com.zividig.slidingview.ImgDetilPager;
import com.zividig.slidingview.InteractDetilPager;
import com.zividig.slidingview.NewsDetilPager;
import com.zividig.slidingview.TopicsDetilPager;
import com.zividig.smartbeij.MainActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import comzividig.utils.GlobalURL;

/**
 * 新闻中心页面
 * Created by Administrator on 2016-02-29.
 */
public class NewsCenterPager extends BasePager {

    public MenuTitleData menuTitleData;//服务器的标题数据
    public ArrayList<BaseDetilPager> detilList;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("NewsCenterPager", "初始化新闻中心数据");
        title.setText("新闻");
        setSlidingMenuEnable(true);


        getDataFormService();//获取数据
    }

    //从服务器获取数据
    public void getDataFormService(){
        RequestParams params = new RequestParams(GlobalURL.dataURL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("成功", result);
                parseData(result); //解析数据
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //解析网络数据
    public void parseData(String result){
        Gson gson = new Gson();
        menuTitleData = gson.fromJson(result, MenuTitleData.class);
        Log.d("解析成功了", menuTitleData.toString());

        setSlidingData();
    }

    //将数据传递给LeftFragment
    public void setSlidingData(){

        MainActivity mainActivity = (MainActivity)mActivity;
        LeftMenuFragment lmf = (LeftMenuFragment) mainActivity.getLeftFrament();
        lmf.getData(menuTitleData);

        detilList = new ArrayList<BaseDetilPager>();
        detilList.add(new NewsDetilPager(mActivity));
        detilList.add(new TopicsDetilPager(mActivity));
        detilList.add(new ImgDetilPager(mActivity));
        detilList.add(new InteractDetilPager(mActivity));

        setDetilPager(0);
    }

    //设置侧边栏的页面
    public void setDetilPager(int position){

       View view = detilList.get(position).rootView;
        flContent.removeAllViews();
        flContent.addView(view);

        //设置侧边栏的标题
        MenuTitleData.SlidingMenuData slidingMenuData = menuTitleData.data.get(position);
        title.setText(slidingMenuData.title);
    }

}
