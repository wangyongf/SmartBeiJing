/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: SplashActivity.java
 * 描述: 智慧北京的Splash界面
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/27       Create
 *  1.1         Scott Wang     2016/2/28       初始化Splash加载动画，以及进入主界面 | 向导界面
 */

package com.yongf.smartbeijing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.utils.MyConstants;
import com.yongf.smartbeijing.utils.SpTools;

/**
 * 智慧北京的Splash界面，主要实现了动画的效果
 *
 * @author Scott Wang
 * @version 1.1, 2016/2/28
 * @see
 * @since SmartBeiJing1.0
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    /**
     * Splash界面的背景图片
     */
    ImageView mIvMainview;

    /**
     * Splash界面的动画效果集
     */
    private AnimationSet as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        //去掉标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //初始化界面
        initView();

        //播放动画
        startAnimation();

        //初始化事件
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //1. 监听动画播完的事件，只是一处用的事件，采用匿名类对象；多处用到，声明成成员变量
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //监听动画播完
                //2. 判断进入向导界面还是主界面
                if (SpTools.getBoolean(getApplicationContext(), MyConstants.IS_SETUP, false)) {
                    //true，设置过，直接进入主界面
                    Log.i(TAG, "load main");

                    Intent main = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);

                } else {
                    //false，进入向导界面
                    Log.i(TAG, "guide view");

                    Intent guide = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(guide);
                }

                //关闭自己
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 开始播放动画：旋转，缩放，渐变
     */
    private void startAnimation() {
        //false 代表动画集中每一种动画都采用各自的动画插入器
        as = new AnimationSet(false);

        //旋转动画，锚点
        RotateAnimation ra = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f        //设置图片的锚点为图片的中心点
        );
        //设置动画的播放时间
        ra.setDuration(2000);
        ra.setFillAfter(true);      //动画播放完之后，停留在当前状态

        //添加到动画集
        as.addAnimation(ra);

        //渐变动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);       //由完全透明到不透明
        aa.setDuration(2000);
        aa.setFillAfter(true);

        //添加到动画集
        as.addAnimation(aa);

        //缩放动画
        ScaleAnimation sa = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        //设置动画播放时间
        sa.setDuration(2000);
        sa.setFillAfter(true);

        //添加到动画集
        as.addAnimation(sa);

        //播放动画
        mIvMainview.startAnimation(as);

        //动画播完进入下一个界面 向导界面 | 主界面

    }

    /**
     * 初始化界面
     */
    private void initView() {
        //获取背景图片
        mIvMainview = (ImageView) findViewById(R.id.iv_splash_mainview);
    }
}
