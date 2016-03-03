/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseNewsCenterPage						
 * 描述: 新闻中心的基本页面
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/2       Create	
 */

package com.yongf.smartbeijing.newscenterpage;

import android.view.View;

import com.yongf.smartbeijing.ui.MainActivity;

/**
 * 新闻中心的基本页面
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public abstract class BaseNewsCenterPage {

    private static final String TAG = "BaseNewsCenterPage";

    protected MainActivity mainActivity;

    /**
     * 根布局
     */
    protected View root;

    public BaseNewsCenterPage(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        root = initView();

        //初始化事件
        initEvent();
    }

    /**
     * 子类覆盖此方法实现事件的处理
     */
    public void initEvent() {

    }

    /**
     * 子类覆盖此方法来显示自定义的View
     *
     * @return
     */
    public abstract View initView();

    public View getRoot() {
        return root;
    }

    /**
     * 子类覆盖此方法完成数据的显示
     */
    public void initData() {

    }
}
