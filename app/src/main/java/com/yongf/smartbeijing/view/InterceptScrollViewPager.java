/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: InterceptScrollViewPager						
 * 描述: 父控件不拦截的ViewPager，自己来处理touch事件
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/4       Create	
 */

package com.yongf.smartbeijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 父控件不拦截的ViewPager，自己来处理touch事件
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/4
 * @see
 * @since SmartBeiJing1.0
 */
public class InterceptScrollViewPager extends ViewPager {

    private static final String TAG = "InterceptScrollViewPager";
    private float downX;
    private float downY;

    public InterceptScrollViewPager(Context context) {
        this(context, null);
    }

    public InterceptScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //true 申请父控件不拦截我的touch事件
        //false 默认父类先拦截touch事件

        //事件完全由自己处理
        //如果在第一个页面，并且是从左往右滑动，让父控件拦截
        //如果在最后一个页面，并且是从右往左滑动，让父控件拦截
        //否则都不让父类拦截

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:       //按下
                getParent().requestDisallowInterceptTouchEvent(true);

                //记录下按下的位置
                downX = ev.getX();
                downY = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:       //移动
                //获取移动的位置坐标
                float moveX = ev.getX();
                float moveY = ev.getY();

                float dx = moveX - downX;
                float dy = moveY - downY;

                //横向移动
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (getCurrentItem() == 0 && dx > 0) {
                        //由父控件处理该事件
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (getCurrentItem() == getAdapter().getCount() - 1 && dx < 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    //让父类拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
