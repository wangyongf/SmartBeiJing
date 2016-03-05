/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: NewsCenterData						
 * 描述: 新闻中心的数据封装
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/2       Create
 *  1.1         Scott Wang     2016/3/6       优化，将其他两个相关的类移到此类文件内部
 */

package com.yongf.smartbeijing.domain;

import java.util.List;

/**
 * 新闻中心的数据封装
 *
 * @author Scott Wang
 * @version 1.1, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsCenterData {

    public int retcode;

    /**
     * 新闻的数据
     */
    public List<NewsData> data;

    public List<String> extend;

    public class NewsData {

        public List<ViewTagData> children;

        public String id;

        public String title;

        public int type;

        public String url;

        public String url1;

        public String dayurl;

        public String excurl;

        public String weekurl;

        public class ViewTagData {

            public String id;

            public String title;

            public int type;

            public String url;
        }
    }
}
