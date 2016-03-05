/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: TPINewsData						
 * 描述: 页签对应的json数据
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/4       Create
 *  1.1         Scott Wang     2016/3/6       修正命名错误，变量命名应该与json严格保持一致（否则用Gson解析出错）
 */

package com.yongf.smartbeijing.domain;

import java.util.List;

/**
 * 页签对应的json数据
 *
 * @author Scott Wang
 * @version 1.1, 2016/3/4
 * @see
 * @since SmartBeiJing1.0
 */
public class TPINewsData {

    public int retcode;

    public Data_TPINewsData data;

    public class Data_TPINewsData {
        public String countcommenturl;
        public String more;
        public String title;
        public List<ListNews_Data_TPINewsData> news;
        public List<Topic_Data_TPINewsData> topic;
        public List<Carousel_Data_TPINewsData> topnews;

        public class ListNews_Data_TPINewsData {
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public String id;
            public String listimage;
            public String pubdate;
            public String title;
            public String type;
            public String url;
        }

        public class Topic_Data_TPINewsData {
            public String description;
            public String listimage;
            public String id;
            public int sort;
            public String title;
            public String url;
        }

        public class Carousel_Data_TPINewsData {
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public String id;
            public String pubdate;
            public String title;
            public String topimage;
            public String type;
            public String url;
        }
    }

}
