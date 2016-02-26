package com.zividig.smartbeij;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import comzividig.utils.SharedPrefUtil;

/**
 * 引导页面
 * Created by Administrator on 2016-02-25.
 */
public class GuideActivity extends Activity {

    //图片的ID
    private final static int[] mImagesIds = new int[] {R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3};
    private ViewPager vpGuide;
    private ArrayList<ImageView> mImageView;

    private LinearLayout pointGroup; //灰点集合
    private View redPoint; //红点
    private int pointWidth;

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        vpGuide = (ViewPager)findViewById(R.id.vp_guide);
        pointGroup = (LinearLayout)findViewById(R.id.ll_pointGroup);
        redPoint = (View)findViewById(R.id.red_point);
        btnStart=(Button)findViewById(R.id.btn_start);

        //初始化View
        initViews();

        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.addOnPageChangeListener(new MyPagerChangeListener());
        btnStart.setOnClickListener(new BtnStartListener());

    }

    private void initViews() {
        mImageView = new ArrayList<ImageView>();

        for (int i=0; i<mImagesIds.length; i++){ //加载viewPager的图片
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImagesIds[i]);
            mImageView.add(image);
        }

        for (int i=0; i<mImagesIds.length; i++){  //加载小圆点
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point); //设置引导页默认圆点
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            if (i>0){
                params.leftMargin = 15; //设置圆点间隔
            }
            point.setLayoutParams(params);
            pointGroup.addView(point);
        }

        //获取视图树，对layout进行监听
        pointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            //layout完成后调用此方法
            public void onGlobalLayout() {
                Log.d("视图观察者", "layout完成");
                pointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                pointWidth = pointGroup.getChildAt(1).getLeft() - pointGroup.getChildAt(0).getLeft();
                Log.d("pointWidth---", Integer.toString(pointWidth));
            }
        });

    }

    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() { //返回需要加载的控件数
            return mImagesIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) { //判断是否是同一张图片
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageView.get(position));
            return mImageView.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    /**
     * 对ViewPage的滑动进行监听
     */
    class MyPagerChangeListener implements ViewPager.OnPageChangeListener{


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("SmartBJ",position + "百分比是：" + positionOffset + "滑动的距离是：" + positionOffsetPixels );

            float pointMoveLength = pointWidth * positionOffset + position * pointWidth; //红点移动的距离
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)redPoint.getLayoutParams();
            params.leftMargin = (int)pointMoveLength; //设置红点的leftMargin
            redPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            //当浏览到最后一页的时候开始体验按钮出现
            if (position == mImagesIds.length - 1){
                btnStart.setVisibility(View.VISIBLE);
            }else{
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class BtnStartListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            SharedPrefUtil.setBoolean(getApplicationContext(),"guide_is_show",true);

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }
    }

}
