/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: NewsData						
 * 描述: 新闻数据类
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/2       Create	
 */

package com.yongf.smartbeijing.domain;

import java.util.List;

/**
 * 新闻数据类
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsData {

    public List<ViewTagData> children;

    public String id;

    public String title;

    public int type;

    public String subUrl;

    public String subUrl1;

    public String subDayUrl;

    public String subExcUrl;

    public String subWeekUrl;
}
