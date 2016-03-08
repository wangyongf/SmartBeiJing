/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: RefreshListView						
 * 描述: 自定义刷新头和尾部的ListView
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/7       Create
 *  1.1         Scott Wang     2016/3/9       listview头和尾的加载，隐藏listview的头和尾
 */

package com.yongf.smartbeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yongf.smartbeijing.R;

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

    //加载更多数据的尾部组件
    private View foot;

    //listview刷新数据的头部组件
    private LinearLayout head;

    //listview刷新头的根布局
    private LinearLayout ll_refresh_head_root;
    private int ll_refresh_head_root_height;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initRoot();
        initHead();
    }

    /**
     * 初始化尾部的组件
     */
    private void initRoot() {
        //listview的尾部
        foot = View.inflate(getContext(), R.layout.listview_refresh_foot, null);

        //测量尾部组件的高度

        foot.measure(0, 0);

        //listview尾部组件的高度
        int ll_refresh_foot_height = foot.getMeasuredHeight();

        foot.setPadding(0, -ll_refresh_head_root_height, 0, 0);

        //加载到listview中
        addFooterView(foot);
    }

    /**
     * 加载轮播图view
     *
     * @param view
     */
    public void addCarouselHeadView(View view) {
        head.addView(view);
    }

    /**
     * 初始化头部的组件
     */
    private void initHead() {
        head = (LinearLayout) View.inflate(getContext(), R.layout.listview_header_container, null);
        ll_refresh_head_root = (LinearLayout) head.findViewById(R.id.ll_listview_head_root);

        //隐藏刷新头的根布局，轮播图还要显示

        //获取刷新头组件的高度
        ll_refresh_head_root.measure(0, 0);

        //获取测量的高度
        ll_refresh_head_root_height = ll_refresh_head_root.getMeasuredHeight();

        head.setPadding(0, -ll_refresh_head_root_height, 0, 0);

        addHeaderView(head);
    }
}
