/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeBaseTagPager						
 * 描述: 新闻中心基本布局
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/1       Create
 *  1.1         Scott Wang     2016/3/2       获取服务器json数据并通过Gson解析
 *  1.2         Scott Wang     2016/3/4       动态显示不同的新闻中心页面
 */

package com.yongf.smartbeijing.basepage;

import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.smartbeijing.domain.NewsCenterData;
import com.yongf.smartbeijing.domain.NewsData;
import com.yongf.smartbeijing.newscenterpage.BaseNewsCenterPage;
import com.yongf.smartbeijing.newscenterpage.InteractBaseNewsCenterPage;
import com.yongf.smartbeijing.newscenterpage.NewsBaseNewsCenterPage;
import com.yongf.smartbeijing.newscenterpage.PhotosBaseNewsCenterPage;
import com.yongf.smartbeijing.newscenterpage.TopicBaseNewsCenterPage;
import com.yongf.smartbeijing.ui.MainActivity;
import com.yongf.smartbeijing.utils.MyConstants;
import com.yongf.smartbeijing.view.LeftMenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心基本布局
 *
 * @author Scott Wang
 * @version 1.2, 2016/3/1
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsCenterBaseTagPager extends BaseTagPage {

    private static final String TAG = "NewsCenterBaseTagPager";

    /**
     * 新闻中心要显示的四个页面
     */
    private List<BaseNewsCenterPage> newsCenterPages = new ArrayList<>();

    private NewsCenterData newsCenterData;

    public NewsCenterBaseTagPager(MainActivity context) {
        super(context);
    }

    @Override
    public void initData() {

        //1. 获取网络数据
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.NEWS_CENTER_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //访问数据成功
                String jsonData = responseInfo.result;

                Log.i(TAG, jsonData);

                //2. 解析json数据
                parseData(jsonData);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //访问数据失败
                Log.i(TAG, "网络请求数据失败" + e);
            }
        });


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

        newsCenterData = gson.fromJson(jsonData, NewsCenterData.class);

//        Log.i(TAG, newsCenterData.data.get(0).children.get(0).title);
        //在这里给左侧菜单设置数据
        mainActivity.getLeftMenuFragment().setLeftMenuData(newsCenterData.data);
        //设置左侧菜单的监听回调
        mainActivity.getLeftMenuFragment().setOnSwitchPageListener(new LeftMenuFragment.OnSwitchPageListener() {
            @Override
            public void switchPage(int selectionIndex) {
                Log.i(TAG, "直接调用自己实现。。。");
                NewsCenterBaseTagPager.this.switchPage(selectionIndex);
            }
        });

        //3. 数据的处理
        //把读取的数据封装到界面容器中，通过左侧菜单点击，显示不同的页面
        //根据服务器的数据，创建四个页面（按顺序）

        for (NewsData newsData : newsCenterData.data) {
            BaseNewsCenterPage newsPage = null;
            //遍历四个新闻中心页面
            switch (newsData.type) {
                case 1:     //新闻页面
                    newsPage = new NewsBaseNewsCenterPage(mainActivity, newsCenterData.data.get(0).children);

                    break;
                case 10:     //专题
                    newsPage = new TopicBaseNewsCenterPage(mainActivity);

                    break;
                case 2:     //组图
                    newsPage = new PhotosBaseNewsCenterPage(mainActivity);

                    break;
                case 3:     //互动
                    newsPage = new InteractBaseNewsCenterPage(mainActivity);

                    break;
            }   //end switch

            //添加新闻中心的页面到容器中
            newsCenterPages.add(newsPage);
        }    //end for

        //控制四个页面的显示，默认选择第一个新闻页面
        switchPage(0);
    }

    /**
     * 根据位置，动态显示不同的新闻中心页面
     *
     * @param position 要显示的页面
     */
    public void switchPage(int position) {
        BaseNewsCenterPage baseNewsCenterPage = newsCenterPages.get(position);

        //显示数据
        //设置标题
        tv_title.setText(newsCenterData.data.get(position).title);

        //移除掉之前所有的view
        fl_content.removeAllViews();

        //初始化数据
        baseNewsCenterPage.initData();

        //替换掉白纸
        fl_content.addView(baseNewsCenterPage.getRoot());     //添加自己的内容到白纸上
    }
}
