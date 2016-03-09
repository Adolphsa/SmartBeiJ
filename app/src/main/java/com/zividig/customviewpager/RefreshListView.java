package com.zividig.customviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zividig.smartbeij.R;

/**
 * 下拉刷新
 * Created by Administrator on 2016-03-09.
 */
public class RefreshListView extends ListView{

    private static final int STATE_PULL_REFRESH = 0; //下拉刷新
    private static final int STATE_RELEASED_REFRESH = 1; //释放刷新
    private static final int STATE_REFRESHING = 2; //正在刷新

    private int mCurrentState = STATE_PULL_REFRESH; //当前状态

    private View mHeaderView;
    private int startY = -1;
    private int mHeaderViewHeight;
    private ImageView refreshImg;
    private ProgressBar refreshProgress;
    private TextView refreshTitle;
    private TextView refresh;
    private RotateAnimation imgUp;
    private RotateAnimation imgDown;

    public RefreshListView(Context context) {
        super(context);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        mHeaderView = View.inflate(getContext(), R.layout.layout_news_refresh,null); //下拉刷新布局

        refreshImg = (ImageView) mHeaderView.findViewById(R.id.refresh_img);
        refreshProgress = (ProgressBar) mHeaderView.findViewById(R.id.refresh_progress);
        refreshTitle = (TextView) mHeaderView.findViewById(R.id.refresh_title);
        refresh = (TextView) mHeaderView.findViewById(R.id.refresh_data);
        this.addHeaderView(mHeaderView);

        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0); //一隐藏头布局

        imgAnim();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN: //按下
                startY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE: //移动
                if (startY == -1) {
                    startY = (int) ev.getRawY();
                }
                if (mCurrentState == STATE_REFRESHING){
                    break;
                }
                int endY = (int) ev.getRawY();
                int dy = endY - startY;
                if (dy > 0 && getFirstVisiblePosition() == 0){ //只有是下拉并且是第一个item的时候才允许下拉
                    int padding = dy - mHeaderViewHeight;
                    mHeaderView.setPadding(0,padding,0,0);

                    if (padding > 0 && mCurrentState != STATE_RELEASED_REFRESH){ //改为释放刷新
                        mCurrentState = STATE_RELEASED_REFRESH;
                        refreshState();
                    }else if (padding < 0 && mCurrentState != STATE_PULL_REFRESH){
                        mCurrentState = STATE_PULL_REFRESH;
                        refreshState();
                    }
                    return true;
                }

                break;

            case MotionEvent.ACTION_UP: //释放
                startY =-1;
                if (mCurrentState == STATE_RELEASED_REFRESH){
                    mCurrentState = STATE_REFRESHING; //正在刷新
                    mHeaderView.setPadding(0,0,0,0); //显示
                    refreshState();
                }else if (mCurrentState == STATE_PULL_REFRESH){
                    mHeaderView.setPadding(0,-mHeaderViewHeight,0,0); //隐藏
                }
                break;
        }


        return super.onTouchEvent(ev);
    }

    public void refreshState(){ //根据状态的改变刷新控件的布局
        switch (mCurrentState){
            case STATE_PULL_REFRESH:
                refreshTitle.setText("下拉刷新");
                refreshImg.setVisibility(VISIBLE);
                refreshProgress.setVisibility(INVISIBLE);
                refreshImg.setAnimation(imgDown); //箭头向下
                break;

            case STATE_RELEASED_REFRESH:
                refreshTitle.setText("释放刷新");
                refreshImg.setVisibility(VISIBLE);
                refreshProgress.setVisibility(INVISIBLE);
                refreshImg.setAnimation(imgUp); //箭头向上
                break;

            case STATE_REFRESHING:
                refreshTitle.setText("正在刷新");
                refreshImg.clearAnimation(); //必须先清除动画，才能将箭头设置隐藏
                refreshImg.setVisibility(INVISIBLE);
                refreshProgress.setVisibility(VISIBLE);
                break;
        }
    }

    public void imgAnim(){

        //箭头向上
        imgUp = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        imgUp.setDuration(200);
        imgUp.setFillAfter(true);

        //箭头向下
        imgDown = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        imgDown.setDuration(200);
        imgDown.setFillAfter(true);
    }
}
