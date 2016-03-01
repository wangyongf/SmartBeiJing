/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseTagPage						
 * 描述: 5个主界面的基类
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/1       Create
 *  1.1         Scott Wang     2016/3/2       将initData放到加载界面的时候执行
 */

package com.yongf.smartbeijing.basepage;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.ui.MainActivity;

/**
 * 5个主界面的基类
 *
 * @author Scott Wang
 * @version 1.1, 2016/3/1
 * @see
 * @since SmartBeiJing1.0
 */
public class BaseTagPage {

    protected static final String TAG = "BaseTagPage";

    /**
     * 上下文
     */
    protected MainActivity mainActivity;

    /**
     * 界面的根布局
     */
    protected View root;

    /**
     * 按钮ImageButton
     */
    protected ImageButton ib_menu;

    /**
     * 标题
     */
    protected TextView tv_title;

    /**
     * 界面显示的内容
     */
    protected FrameLayout fl_content;

    public BaseTagPage(MainActivity context) {
        this.mainActivity = context;

        //初始化布局
        initView();

        //初始化事件
        initEvent();
    }

    /**
     * 初始化事件
     */
    public void initEvent() {
        //给菜单按钮添加点击事件
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开或者关闭左侧菜单
                mainActivity.getSlidingMenu().toggle();     //左侧菜单的开关
            }
        });
    }

    /**
     * 初始化布局
     */
    public void initView() {
        //界面的根布局
        root = View.inflate(mainActivity, R.layout.fragment_content_base_content, null);

        ib_menu = (ImageButton) root.findViewById(R.id.ib_base_content_menu);
        tv_title = (TextView) root.findViewById(R.id.tv_base_content_title);
        fl_content = (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
    }

    /**
     * 初始化数据
     * 此方法在该页面数据显示的时候再调用
     */
    public void initData() {

    }

    /**
     * 获取界面的根布局
     *
     * @return 界面的根布局
     */
    public View getRoot() {
        return root;
    }

}
