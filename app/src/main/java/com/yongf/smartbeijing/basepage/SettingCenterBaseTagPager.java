/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeBaseTagPager						
 * 描述: 设置界面的基本布局
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/1       Create	
 */

package com.yongf.smartbeijing.basepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.ui.MainActivity;

/**
 * 设置界面的基本布局
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/1
 * @see
 * @since SmartBeiJing1.0
 */
public class SettingCenterBaseTagPager extends BaseTagPage {

    private static final String TAG = "SettingCenterBaseTagPager";

    public SettingCenterBaseTagPager(MainActivity context) {
        super(context);
    }

    @Override
    public void initData() {

        //屏蔽菜单按钮
        ib_menu.setVisibility(View.GONE);

        tv_title.setText(R.string.setting_center);

        TextView tv = new TextView(mainActivity);
        tv.setText("设置中心的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        //替换掉白纸
        fl_content.addView(tv);     //添加自己的内容到白纸上

        super.initData();
    }

}
