/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: InteractBaseNewsCenterPage						
 * 描述:　新闻中心的互动界面
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/2       Create	
 */

package com.yongf.smartbeijing.newscenterpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yongf.smartbeijing.ui.MainActivity;

/**
 * 新闻中心的互动界面
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public class InteractBaseNewsCenterPager extends BaseNewsCenterPager {

    private static final String TAG = "InteractBaseNewsCenterPager";

    public InteractBaseNewsCenterPager(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mainActivity);
        tv.setText("互动的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }
}
