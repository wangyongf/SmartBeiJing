/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeBaseTagPager						
 * 描述: 新闻中心基本布局
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/1       Create	
 */

package com.yongf.smartbeijing.basepage;

import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.domain.NewsCenterData;
import com.yongf.smartbeijing.ui.MainActivity;
import com.yongf.smartbeijing.utils.MyConstants;

/**
 * 新闻中心基本布局
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/1
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsCenterBaseTagPager extends BaseTagPage {

    private static final String TAG = "NewsCenterBaseTagPager";

    public NewsCenterBaseTagPager(MainActivity context) {
        super(context);
    }

    @Override
    public void initData() {

        //获取网络数据
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.NEWS_CENTER_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //访问数据成功
                String jsonData = responseInfo.result;

                Log.i(TAG, jsonData);

                //解析json数据
                parseData(jsonData);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //访问数据失败
                Log.i(TAG, "网络请求数据失败" + e);
            }
        });

        tv_title.setText(R.string.news_center);

        TextView tv = new TextView(mainActivity);
        tv.setText("新闻中心的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        //替换掉白纸
        fl_content.addView(tv);     //添加自己的内容到白纸上

        super.initData();
    }

    /**
     * 解析json数据
     *
     * @param jsonData 从网络服务器获取到的json数据
     */
    private void parseData(String jsonData) {
        //google提供的json解析器
        Gson gson = new Gson();

        NewsCenterData newsCenterData = gson.fromJson(jsonData, NewsCenterData.class);

        Log.i(TAG, newsCenterData.data.get(0).children.get(0).title);
    }
}
