/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: MyConstants						
 * 描述: 存放项目中用到的常量
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/28       Create
 *  1.1         Scott Wang     2016/3/2         增加了新闻中心的数据来源URL常量（发布APK时必须修改！）
 */

package com.yongf.smartbeijing.utils;

/**
 * 项目中的常量
 *
 * @author Scott Wang
 * @version 1.1, 2016/2/28
 * @see
 * @since SmartBeiJing1.0
 */
public interface MyConstants {

    /**
     * sp的文件名
     */
    String CONFIG = "cachevalue";

    /**
     * 向导界面是否设置过数据
     */
    String IS_SETUP = "issetup";

    /**
     * 新闻中心的数据来源
     * apk发布修改该ip ip 或者 域名（最好用域名?? www.54yongf.com/zhbj/categories.json）
     */
    String NEWS_CENTER_URL = "http://10.0.2.2/zhbj/categories.json";
}
