/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: SpTools						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/28       Create
 *  1.1         Scott Wang     2016/3/6         增加保存、获取字符串类型数据的方法
 */

package com.yongf.smartbeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的工具类
 *
 * @author Scott Wang
 * @version 1.1, 2016/2/28
 * @see
 * @since SmartBeiJing1.0
 */
public class SpTools {

    private static final String TAG = "SpTools";

    /**
     * 设置布尔常量
     *
     * @param context 上下文
     * @param key     关键字
     * @param value   对应的值
     */
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIG, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();     //提交保存设置
    }

    /**
     * 获取布尔常量
     *
     * @param context  上下文
     * @param key      关键字
     * @param defValue 设置的默认值
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIG, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 设置字符串常量
     *
     * @param context 上下文
     * @param key     关键字
     * @param value   对应的值
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIG, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();     //提交保存设置
    }

    /**
     * 获取字符串常量
     *
     * @param context  上下文
     * @param key      关键字
     * @param defValue 设置的默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIG, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
}
