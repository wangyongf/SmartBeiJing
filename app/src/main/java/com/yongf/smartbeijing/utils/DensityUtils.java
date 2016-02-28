/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: DensityUtils						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/28       Create
 *  1.1         Scott Wang     2016/2/28       增加了dip转px，px转dip的方法
 */

package com.yongf.smartbeijing.utils;

import android.content.Context;

/**
 * 用于屏幕适配的工具类
 *
 * @author Scott Wang
 * @version 1.1, 2016/2/28
 * @see
 * @since SmartBeiJing1.0
 */
public class DensityUtils {

    private static final String TAG = "DensityUtils";

    /**
     * dip转px
     *
     * @param context 上下文
     * @param dpValue dip值
     * @return 转换后的px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dip
     *
     * @param context 上下文
     * @param pxValue px值
     * @return 转换后的dip值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
