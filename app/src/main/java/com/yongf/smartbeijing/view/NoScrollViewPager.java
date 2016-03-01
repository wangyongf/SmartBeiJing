/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: NoScrollViewPager						
 * 描述: 不可以滚动的ViewPager
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/1       Create	
 */

package com.yongf.smartbeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不可以滚动的ViewPager
 * 而且是懒加载的
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/1
 * @see
 * @since SmartBeiJing1.0
 */
public class NoScrollViewPager extends MyViewPager {

    private static final String TAG = "NoScrollViewPager";

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 不让自己拦截
     *
     * @param ev
     * @return false
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 不让自己拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
