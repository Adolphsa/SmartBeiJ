package com.zividig.newspager;


import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import com.zividig.customviewpager.RefreshListView;
import com.zividig.data.MenuTitleData;
import com.zividig.data.NewsTabData;
import com.zividig.slidingview.BaseDetailPager;
import com.zividig.smartbeij.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import comzividig.utils.GlobalURL;
import comzividig.utils.SharedPrefUtil;

/**
 * 填充NewsDetilPager的页面
 * Created by Administrator on 2016-03-02.
 */
public class BaseNewsTabPager extends BaseDetailPager{

    private MenuTitleData.MainMenuData data;
    private ViewPager newsImageViewPager;

    private CirclePageIndicator indicator;

    private String topImgUrl;
    public  NewsTabData newsTabData; //数据类
    private TextView topNewTitle; //图片左边的标题

    private ArrayList<NewsTabData.TopnewsData> topNewsList; //顶部图片集合
    private ArrayList<NewsTabData.NewsData> newsList;

    //listView新闻标题相关
    private RefreshListView newsCotentListView;
    private String mMoreURL; //更多的URL
    private NewsAdapter newsAdapter;


    public BaseNewsTabPager(Activity activity, MenuTitleData.MainMenuData mainMenuData) {
        super(activity);
        data = mainMenuData;
        Log.d("小孩数据",data.toString());
        topImgUrl = GlobalURL.hostURL + data.url;
    }

    @Override
    public View initView() {

        View view = View.inflate(mActivity,R.layout.layout_newsimage_viewpager,null);
        newsCotentListView = (RefreshListView) view.findViewById(R.id.lv_news_content);

        //顶部图片
        View headerView = View.inflate(mActivity,R.layout.layout_news_header_listview,null);
        newsImageViewPager = (ViewPager) headerView.findViewById(R.id.vp_newsImage); //顶部图片的图片
        indicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator); //滚动的小圆点
        topNewTitle = (TextView)headerView.findViewById(R.id.tv_topImgText); //图片标题

        newsCotentListView.addHeaderView(headerView); //给listView增加图片头布局


        //新闻下拉上载
        newsCotentListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFormInternet();
            }

            @Override
            public void onLoadMore() {
                if (mMoreURL != null) {
                    getMoreFormInternet();
                } else {
                    Toast.makeText(mActivity, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                    newsCotentListView.onRefreshComplete(false);
                }
            }
        });

        //监听新闻条目的点击事件
        newsCotentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("条目被点击了" + position);

                //本地记录已读状态
                String ids = SharedPrefUtil.getString(mActivity,"read_ids","");
                String id_read = newsList.get(position).id;
                if (!ids.contains(id_read)){ //新闻ID重复的不添加在后面
                    ids = ids + newsList.get(position).id + ",";
                    SharedPrefUtil.setString(mActivity,"read_ids",ids);
                }

                changeReadState(view);

            }
        });

        return view;

    }

    public void changeReadState(View view){
        TextView newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
        newsTitle.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {

        getDataFormInternet();

    }

    //从服务器获取数据
    public void getDataFormInternet(){
        RequestParams params = new RequestParams(topImgUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                parseData(result,false); //解析数据
                newsCotentListView.onRefreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.print(ex);
                Toast.makeText(mActivity, "顶部图片加载失败", Toast.LENGTH_SHORT).show();
                newsCotentListView.onRefreshComplete(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void getMoreFormInternet(){

        RequestParams params = new RequestParams(mMoreURL);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                parseData(result,true); //解析数据
                newsCotentListView.onRefreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("更多新闻加载失败");
                newsCotentListView.onRefreshComplete(false);
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
    protected void parseData(String result,boolean isMore){
        Gson gson = new Gson();
        newsTabData = gson.fromJson(result,NewsTabData.class);
        Log.d("parseData", "解析成功");

        //加载更多的链接
        String more = newsTabData.data.more;
        if (!TextUtils.isEmpty(more)){
            mMoreURL = GlobalURL.hostURL + more;

        }else {
            mMoreURL = null;
        }

        if (!isMore){
            topNewsList = newsTabData.data.topnews; //图片数据集合
            newsList = newsTabData.data.news; //新闻集合

            //解析完成后才有数据
            if (topNewsList != null) {
                newsImageViewPager.setAdapter(new newsImageAdapter());
                indicator.setViewPager(newsImageViewPager);
                indicator.setOnPageChangeListener(new TopImageListener());
                indicator.setSnap(true);
                indicator.onPageSelected(0);

                topNewTitle.setText(topNewsList.get(0).title);
            }

            if (newsList != null) {
                newsAdapter = new NewsAdapter();
                newsCotentListView.setAdapter(newsAdapter);
            }
        }else { //加载更多
            ArrayList<NewsTabData.NewsData> moreNews = newsTabData.data.news;
            newsList.addAll(moreNews); //将更多新闻追加在第一页的新闻下面
            newsAdapter.notifyDataSetChanged();
        }


    }

    //新闻类
    class NewsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public NewsTabData.NewsData getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){

                convertView = View.inflate(mActivity,R.layout.layout_news_content_listview,null);
                holder = new ViewHolder();
                holder.newsContentImage = (ImageView)convertView.findViewById(R.id.img_left_image);
                holder.newsContentTitle = (TextView)convertView.findViewById(R.id.tv_news_title);
                holder.newsContentData = (TextView)convertView.findViewById(R.id.tv_news_data);

                convertView.setTag(holder);
            }else {

                holder = (ViewHolder) convertView.getTag();
            }

            //图片
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setLoadingDrawableId(R.mipmap.news_pic_default)
                    .build();
            NewsTabData.NewsData newsData = newsList.get(position);
            x.image().bind(holder.newsContentImage,newsData.listimage,imageOptions); //设置图片的url并加载显示
            //标题
            holder.newsContentTitle.setText(newsData.title);
            holder.newsContentData.setText(newsData.pubdate);

            //判断是否是已读新闻
            String ids = SharedPrefUtil.getString(mActivity,"read_ids","");
            if (ids.contains(getItem(position).id)){
                holder.newsContentTitle.setTextColor(Color.GRAY);
            }else {
                holder.newsContentTitle.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }

    static class ViewHolder{
        public ImageView newsContentImage;
        public TextView newsContentTitle;
        public TextView newsContentData;
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
                    .setLoadingDrawableId(R.mipmap.topnews_item_default)
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
