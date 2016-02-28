/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: MainActivity.java
 * 描述: 智慧北京主界面
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/28       Create
 */

package com.yongf.smartbeijing.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yongf.smartbeijing.R;

/**
 * 智慧北京主界面
 *
 * @author Scott Wang
 * @version 1.0, 2016/2/28
 * @see
 * @since SmartBeiJing1.0
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
