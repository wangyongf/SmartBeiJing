/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: MyConstants						
 * 描述: 存放项目中用到的常量
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/28       Create
 *  1.1         Scott Wang     2016/3/2         增加了新闻中心的数据来源URL常量（发布APK时必须修改！）
 *  1.2         Scott Wang     2016/3/6         使用小米3进行真机调试，更改服务器ip
 */

package com.yongf.smartbeijing.utils;

/**
 * 项目中的常量
 *
 * @author Scott Wang
 * @version 1.2, 2016/2/28
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
     * 服务器地址
     */
    String SERVER_URL = "http://192.168.1.103/zhbj3/";

    /**
     * 新闻中心的数据来源
     * apk发布修改该ip ip 或者 域名（最好用域名?? www.54yongf.com/zhbj/categories.json）
     */
    String NEWS_CENTER_URL = SERVER_URL + "categories.json";

    /**
     * 已读新闻的ID  key
     */
    String READ_NEWS_ID = "read_news_id";

    /**
     * 组图的URL
     */
    String PHOTOS_URL = SERVER_URL + "photos/photos_1.json";
}
