/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: BaseFragment.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/29       Create
 */

package com.yongf.smartbeijing.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongf.smartbeijing.ui.MainActivity;

/**
 * BaseFragment
 *
 * @author Scott Wang
 * @version 1.0, 2016/2/29
 * @see
 * @since SmartBeiJing1.0
 */
public abstract class BaseFragment extends Fragment {

    /**
     * MainActivity上下文
     */
    protected MainActivity mainActivity;

    // XXXDao dao = new XXXDao(getActivity);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取fragment所在的Activity
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = initView(); //View
        return root;
    }

    /**
     * 初始化view
     * 必须覆盖此方法来完成界面的显示
     *
     * @return 初始化的view
     */
    public abstract View initView();

    /**
     * 初始化数据
     * 子类覆盖此方法完成数据的添加
     */
    public void initData() {

    }

    /**
     * 初始化事件
     * 子类覆盖此方法完成事件的添加
     */
    public void initEvent() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //初始化事件和数据
        super.onActivityCreated(savedInstanceState);

        //初始化数据
        initData();

        //初始化事件
        initEvent();
    }
}
