package com.zividig.smartbeij;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import comzividig.utils.SharedPrefUtil;

/**
 * splash页面
 * Created by Administrator on 2016-02-25.
 */
public class SplashActivity extends Activity{

    LinearLayout llRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        llRoot = (LinearLayout)findViewById(R.id.ll_root);
        statrAnimate();
    }

    private void statrAnimate() {

        AnimationSet set = new AnimationSet(false);

        //旋转
        RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);

        //缩放
        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        //渐变
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) { //动画结束的时候调用此方法

                boolean isShowGuide = SharedPrefUtil.getBoolean(getApplicationContext(),"guide_is_show",false);

                //如果引导页显示过了则不显示，直接跳到主页面
                if (!isShowGuide) {
                    startActivity(new Intent(getApplicationContext(), GuideActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        llRoot.startAnimation(set);
    }
}
