package com.zividig.newspager;


import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import com.zividig.data.MenuTitleData;
import com.zividig.data.NewsTabData;
import com.zividig.slidingview.BaseDetailPager;
import com.zividig.smartbeij.R;

import org.xmlpull.v1.XmlPullParser;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import comzividig.utils.GlobalURL;

/**
 * 填充NewsDetilPager的页面
 * Created by Administrator on 2016-03-02.
 */
public class BaseNewsTabPager extends BaseDetailPager {

    private MenuTitleData.MainMenuData data;
    private ViewPager newsImageViewPager;

    private CirclePageIndicator indicator;

    private String topImgUrl;
    public  NewsTabData newsTabData;
    private TextView topNewTitle;

    private ArrayList<NewsTabData.TopnewsData> topNewsList; //顶部图片集合

    public BaseNewsTabPager(Activity activity, MenuTitleData.MainMenuData mainMenuData) {
        super(activity);
        data = mainMenuData;
        Log.d("小孩数据",data.toString());
    }

    @Override
    public View initView() {

        View view = View.inflate(mActivity,R.layout.layout_newsimage_viewpager,null);
        newsImageViewPager = (ViewPager) view.findViewById(R.id.vp_newsImage);

        indicator = (CirclePageIndicator)view.findViewById(R.id.indicator); //滚动的小圆点

        topNewTitle = (TextView)view.findViewById(R.id.tv_topImgText);
        return view;

    }

    @Override
    public void initData() {

        getDataFormInternet();


    }

    //从服务器获取数据
    public void getDataFormInternet(){

        topImgUrl = GlobalURL.hostURL + data.url;
        Log.d("顶部图片的URL", topImgUrl);
        RequestParams params = new RequestParams(topImgUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                parseData(result); //解析数据

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.print(ex);
                Toast.makeText(mActivity, "顶部图片加载失败", Toast.LENGTH_SHORT).show();
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
        newsTabData = gson.fromJson(result,NewsTabData.class);
        Log.d("parseData", "解析成功");

        topNewsList = newsTabData.data.topnews; //数据在List中

        //解析完成后才有数据
        newsImageViewPager.setAdapter(new newsImageAdapter());
        indicator.setViewPager(newsImageViewPager);
        indicator.setOnPageChangeListener(new TopImageListener());
        indicator.setSnap(true);
        indicator.onPageSelected(0);

        topNewTitle.setText(topNewsList.get(0).title);
    }

    class TopImageListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            topNewTitle.setText(topNewsList.get(position).title); //设置顶部图片标题

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class newsImageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return newsTabData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setLoadingDrawableId(R.mipmap.news_pic_default)
                    .build();
            NewsTabData.TopnewsData topnews = topNewsList.get(position);
            x.image().bind(image,topnews.topimage,imageOptions); //设置图片的url并加载显示
            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
