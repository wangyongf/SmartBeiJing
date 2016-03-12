/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: DateUtils						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/9       Create	
 */

package com.yongf.smartbeijing.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间处理的工具类
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/9
 * @see
 * @since SmartBeiJing1.0
 */
public class DateUtils {

    private static final String TAG = "DateUtils";

    /**
     * 格式化时间日期
     *
     * @param formatter 时间日期格式
     * @param date      日期时间
     * @return 格式化后的时间日期
     */
    public static String getFormatDate(String formatter, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatter);

        return format.format(date);
    }

}
