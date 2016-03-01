/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: MainActivity.java
 * 描述: 智慧北京主界面
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/28       Create
 *  1.1         Scott Wang     2016/3/2         左侧菜单界面的替换，主界面菜单界面的替换
 */

package com.yongf.smartbeijing.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.utils.DensityUtils;
import com.yongf.smartbeijing.view.LeftMenuFragment;
import com.yongf.smartbeijing.view.MainContentFragment;

/**
 * 智慧北京主界面
 *
 * @author Scott Wang
 * @version 1.1, 2016/2/28
 * @see
 * @since SmartBeiJing1.0
 */
public class MainActivity extends SlidingFragmentActivity {

    private static final String TAG = "MainActivity";
    private static final String LEFT_MENU_TAG = "LEFT_MENU_TAG";
    private static final String MAIN_MENU_TAG = "MAIN_MENU_TAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化view
        initView();

        //初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        //1. 获取事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //2. 完成替换

        //完成左侧菜单界面的替换
        transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),
                LEFT_MENU_TAG);

        //完成主界面菜单界面的替换
        transaction.replace(R.id.fl_main_menu, new MainContentFragment(),
                MAIN_MENU_TAG);

        //3. 提交事务
        transaction.commit();
    }

    private void initView() {
        //1. 设置主界面
        setContentView(R.layout.fragment_content_tag);

        //2. 设置左侧菜单界面
        setBehindContentView(R.layout.fragment_left);

        //3. 设置滑动模式
        SlidingMenu sm = getSlidingMenu();
        sm.setMode(SlidingMenu.LEFT);       //设置左侧可以滑动

        //4. 设置滑动位置为全屏
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //5. 设置主界面左侧滑动后剩余的空间
        int offset = DensityUtils.dip2px(MainActivity.this, 200);
        sm.setBehindOffset(offset);
    }
}
