/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeBaseTagPager						
 * 描述: 首页界面的基本布局
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/1       Create
 *  1.1         Scott Wang     2016/3/2       屏蔽首页的菜单按钮，首页对应的空页面
 */

package com.yongf.smartbeijing.basepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.ui.MainActivity;

/**
 * 首页界面的基本布局
 *
 * @author Scott Wang
 * @version 1.1, 2016/3/1
 * @see
 * @since SmartBeiJing1.0
 */
public class HomeBaseTagPager extends BaseTagPager {

    private static final String TAG = "HomeBaseTagPager";

    public HomeBaseTagPager(MainActivity context) {
        super(context);
    }

    @Override
    public void initData() {

        //屏蔽菜单按钮
        ib_menu.setVisibility(View.GONE);

        //设置page的标题
        tv_title.setText(R.string.home);

        //要展示的内容
        TextView tv = new TextView(mainActivity);
        tv.setText("首页的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        //替换掉白纸
        fl_content.addView(tv);     //添加自己的内容到白纸上

        super.initData();
    }
}
