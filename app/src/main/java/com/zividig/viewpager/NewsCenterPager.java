package com.zividig.viewpager;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zividig.data.MenuTitleData;
import com.zividig.fragment.LeftMenuFragment;
import com.zividig.slidingview.BaseDetailPager;
import com.zividig.slidingview.ImgDetailPager;
import com.zividig.slidingview.InteractDetailPager;
import com.zividig.slidingview.NewsDetailPager;
import com.zividig.slidingview.TopicsDetailPager;
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
    public ArrayList<BaseDetailPager> detailList;

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
    protected void parseData(String result){
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

        detailList = new ArrayList<>();
        detailList.add(new NewsDetailPager(mActivity, menuTitleData.data.get(0).children));
        detailList.add(new TopicsDetailPager(mActivity));
        detailList.add(new ImgDetailPager(mActivity));
        detailList.add(new InteractDetailPager(mActivity));

        setDetailPager(0);
    }

    //设置侧边栏的页面
    public void setDetailPager(int position){

        BaseDetailPager pager = detailList.get(position);
        flContent.removeAllViews();
        flContent.addView(pager.rootView);

        //设置侧边栏的标题
        MenuTitleData.SlidingMenuData slidingMenuData = menuTitleData.data.get(position);
        title.setText(slidingMenuData.title);

        pager.initData();
    }

}
