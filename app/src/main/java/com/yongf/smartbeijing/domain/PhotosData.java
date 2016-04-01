/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: PhotosData						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/30       Create	
 */

package com.yongf.smartbeijing.domain;

import java.util.List;

/**
 * 组图的数据
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/30
 * @see
 * @since SmartBeiJing1.0
 */
public class PhotosData {

    private static final String TAG = "PhotosData";

    public int retcode;

    public PhotoData_Data data;

    public class PhotoData_Data {
        public String countcommenturl;
        public String more;
        public String title;

        public List<PhotosNews> news;

        public class PhotosNews {
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public int id;
            public String largeimage;
            public String listimage;
            public String pubdate;
            public String smallimage;
            public String title;
            public String type;
            public String url;
        }
    }

}
