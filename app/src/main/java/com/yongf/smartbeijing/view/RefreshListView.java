/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: RefreshListView						
 * 描述: 自定义刷新头和尾部的ListView
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/7       Create	
 */

package com.yongf.smartbeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义刷新头和尾部的ListView
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/7
 * @see
 * @since SmartBeiJing1.0
 */
public class RefreshListView extends ListView {

    private static final String TAG = "RefreshListView";

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
