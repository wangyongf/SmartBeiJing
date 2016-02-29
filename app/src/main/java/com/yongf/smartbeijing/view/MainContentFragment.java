/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: MainContentFragment.java
 * 描述: 主界面的Fragment
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/29       Create
 */

package com.yongf.smartbeijing.view;


import android.view.View;

import com.yongf.smartbeijing.R;

/**
 * 主界面的Fragment
 *
 * @author Scott Wang
 * @version 1.0, 2016/2/29
 * @see
 * @since SmartBeiJing1.0
 */
public class MainContentFragment extends BaseFragment {

    @Override
    public View initView() {
        View root = View.inflate(mainActivity, R.layout.fragment_content_view, null);

//        TextView tv = new TextView(mainActivity);
//        tv.setText("主界面菜单");
//        tv.setTextSize(25);
//        tv.setGravity(Gravity.CENTER);

        return root;
    }
}
