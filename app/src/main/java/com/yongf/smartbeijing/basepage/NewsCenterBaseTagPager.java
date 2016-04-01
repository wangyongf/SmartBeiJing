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
 *  1.3         Scott Wang     2016/3/6       优化，先获取本地缓存数据，然后获取网络数据
 */

package com.yongf.smartbeijing.basepage;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.smartbeijing.domain.NewsCenterData;
import com.yongf.smartbeijing.newscenterpage.BaseNewsCenterPager;
import com.yongf.smartbeijing.newscenterpage.InteractBaseNewsCenterPager;
import com.yongf.smartbeijing.newscenterpage.NewsBaseNewsCenterPager;
import com.yongf.smartbeijing.newscenterpage.PhotosBaseNewsCenterPager;
import com.yongf.smartbeijing.newscenterpage.TopicBaseNewsCenterPager;
import com.yongf.smartbeijing.ui.MainActivity;
import com.yongf.smartbeijing.utils.MyConstants;
import com.yongf.smartbeijing.utils.SpTools;
import com.yongf.smartbeijing.view.LeftMenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心基本布局
 *
 * @author Scott Wang
 * @version 1.3, 2016/3/1
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsCenterBaseTagPager extends BaseTagPager {

    private static final String TAG = "NewsCenterBaseTagPager";

    /**
     * 新闻中心要显示的四个页面
     */
    private List<BaseNewsCenterPager> newsCenterPages = new ArrayList<>();

    private NewsCenterData newsCenterData;
    private Gson gson;

    public NewsCenterBaseTagPager(MainActivity context) {
        super(context);
    }

    @Override
    public void initData() {
        //0. 先获取本地数据
        getCacheData();

        //1. 获取网络数据
        getNetworkData();


        super.initData();
    }

    private void getNetworkData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.NEWS_CENTER_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //访问数据成功
                String jsonData = responseInfo.result;
                //保存到背地
//                Log.i(TAG, jsonData);
                SpTools.setString(mainActivity, MyConstants.NEWS_CENTER_URL, jsonData);

                //2. 解析json数据
                parseData(jsonData);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //访问数据失败
                Log.i(TAG, "网络请求数据失败" + e);
            }
        });
    }

    private void getCacheData() {
        String jsonCache = SpTools.getString(mainActivity, MyConstants.NEWS_CENTER_URL, "");
        if (TextUtils.isEmpty(jsonCache)) {
            //没有本地数据

        } else {
            //有本地数据
            //从本地取数据
            parseData(jsonCache);
        }
    }

    /**
     * 解析json数据
     *
     * @param jsonData 从网络服务器获取到的json数据
     */
    private void parseData(String jsonData) {
        //google提供的json解析器
        if (gson == null) {
            gson = new Gson();
        }

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

        for (NewsCenterData.NewsData newsData : newsCenterData.data) {
            BaseNewsCenterPager newsPage = null;
            //遍历四个新闻中心页面
            switch (newsData.type) {
                case 1:     //新闻页面
                    newsPage = new NewsBaseNewsCenterPager(mainActivity, newsCenterData.data.get(0).children);

                    break;
                case 10:     //专题
                    newsPage = new TopicBaseNewsCenterPager(mainActivity);

                    break;
                case 2:     //组图
                    newsPage = new PhotosBaseNewsCenterPager(mainActivity);

                    break;
                case 3:     //互动
                    newsPage = new InteractBaseNewsCenterPager(mainActivity);

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
        BaseNewsCenterPager baseNewsCenterPager = newsCenterPages.get(position);

        //显示数据
        //设置标题
        tv_title.setText(newsCenterData.data.get(position).title);

        //移除掉之前所有的view
        fl_content.removeAllViews();

        //初始化数据
        baseNewsCenterPager.initData();

        //判断，如果是组图listgrid切换的按钮进行显示
        if (baseNewsCenterPager instanceof PhotosBaseNewsCenterPager) {
            //组图
            //显示listgrid切换的按钮显示
            ib_listOrGrid.setVisibility(View.VISIBLE);
            //设置点击事件，做list和grid的切换
            ib_listOrGrid.setTag(baseNewsCenterPager);
            ib_listOrGrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PhotosBaseNewsCenterPager) ib_listOrGrid.getTag()).switchListViewOrGridView(ib_listOrGrid);
                }
            });
        } else {
            ib_listOrGrid.setVisibility(View.GONE);
        }

        //替换掉白纸
        fl_content.addView(baseNewsCenterPager.getRoot());     //添加自己的内容到白纸上
    }
}
