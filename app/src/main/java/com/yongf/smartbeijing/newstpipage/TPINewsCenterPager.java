/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: TPINewsCenterPage						
 * 描述:　新闻中心页签对应的页面
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/4       Create
 *  1.1         Scott Wang     2016/3/6       新闻数据的缓存，轮播图的显示，图片标题，点的选中效果
 */

package com.yongf.smartbeijing.newstpipage;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.domain.NewsCenterData;
import com.yongf.smartbeijing.domain.TPINewsData;
import com.yongf.smartbeijing.ui.MainActivity;
import com.yongf.smartbeijing.utils.DensityUtils;
import com.yongf.smartbeijing.utils.MyConstants;
import com.yongf.smartbeijing.utils.SpTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心页签对应的页面
 *
 * @author Scott Wang
 * @version 1.1, 2016/3/4
 * @see
 * @since SmartBeiJing1.0
 */
public class TPINewsCenterPager {

    private static final String TAG = "TPINewsCenterPager";

    //所有组件
    private final BitmapUtils bitmapUtils;
    @ViewInject(R.id.vp_tpi_news_carousel)
    private ViewPager vp_carousel;      //轮播图的显示组件
    @ViewInject(R.id.tv_tpi_news_desc)
    private TextView tv_pic_desc;           //图片的描述信息
    @ViewInject(R.id.ll_tpi_news_points)
    private LinearLayout ll_points;         //轮播图每个图对应的点组合
    //数据
    @ViewInject(R.id.lv_tpi_news_listnews)
    private ListView lv_listnews;           //显示列表新闻的组件
    private MainActivity mainActivity;
    private View root;
    /**
     * 页签对应的数据
     */
    private NewsCenterData.NewsData.ViewTagData viewTagData;
    private Gson gson;
    /**
     * 轮播图的数据
     */
    private List<TPINewsData.Data_TPINewsData.Carousel_Data_TPINewsData> carouselData = new ArrayList<>();
    /**
     * 轮播图的适配器
     */
    private CarouselAdapter carouselAdapter;
    private int picSelectIndex;

    public TPINewsCenterPager(MainActivity mainActivity, NewsCenterData.NewsData.ViewTagData viewTagData) {
        this.mainActivity = mainActivity;
        this.viewTagData = viewTagData;

        gson = new Gson();

        //XUtils bitmap组件
        bitmapUtils = new BitmapUtils(mainActivity);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);

        //初始化界面
        initView();

        //初始化数据
        initData();

        //初始化事件
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //给轮播图添加页面切换事件
        vp_carousel.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                picSelectIndex = position;
                setPicDescAndPointSelect(picSelectIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        //轮播图的适配器
        carouselAdapter = new CarouselAdapter();
        //给轮播图
        vp_carousel.setAdapter(carouselAdapter);

        //轮播图的数据
        //新闻列表的数据

        //获取缓存数据
        getCacheData();

        //获取网络数据
        getNetworkData();
    }

    /**
     * 处理数据
     *
     * @param tpiNewsData 需要处理的数据
     */
    private void processData(TPINewsData tpiNewsData) {
        //完成数据的处理

        //1. 设置轮播图的数据
        setCarousel(tpiNewsData);

        //2. 处理轮播图对应的点

        //初始化轮播图的点
        initPoints();

        //3.

        setPicDescAndPointSelect(picSelectIndex);
    }

    /**
     * 设置图片描述和点的选中效果
     *
     * @param picSelectIndex 当前position
     */
    private void setPicDescAndPointSelect(int picSelectIndex) {
        //设置点的描述信息
        tv_pic_desc.setText(carouselData.get(picSelectIndex).title);

        //设置点的选中
        for (int i = 0; i < carouselData.size(); i++) {
            ll_points.getChildAt(i).setEnabled(i == picSelectIndex);
        }
    }

    /**
     * 处理轮播图的点
     */
    private void initPoints() {
        //清空所有之前的点
        ll_points.removeAllViews();

        //轮播图有几张，就加几个点
        for (int i = 0; i < carouselData.size(); i++) {
            View v_point = new View(mainActivity);
            //设置点的背景选择器
            v_point.setBackgroundResource(R.drawable.point_selector);
            v_point.setEnabled(false);      //默认都是灰色的点

            //设置点的大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(mainActivity, 5),
                    DensityUtils.dip2px(mainActivity, 5));
            //设置点之间的间距
            params.leftMargin = DensityUtils.dip2px(mainActivity, 10);

            //设置参数
            v_point.setLayoutParams(params);

            ll_points.addView(v_point);
        }
    }

    /**
     * 设置轮播图的数据
     *
     * @param tpiNewsData 所有的数据（包含轮播图的数据）
     */
    private void setCarousel(TPINewsData tpiNewsData) {
        //获取轮播图的数据
        carouselData = tpiNewsData.data.topnews;

        //更新界面
        carouselAdapter.notifyDataSetChanged();
    }

    /**
     * 获取本地数据
     */
    private void getCacheData() {
        String jsonCache = SpTools.getString(mainActivity, viewTagData.url, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            //有数据，解析数据
            TPINewsData tpiNewsCache = parseJson(jsonCache);
            //处理数据
            processData(tpiNewsCache);
        }
    }

    /**
     * 获取网络数据
     */
    private void getNetworkData() {
        HttpUtils httpUtils = new HttpUtils();

//        System.out.println("viewTagData = " + viewTagData.url);

        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.SERVER_URL + viewTagData.url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求数据成功
                String jsonData = responseInfo.result;

                //保存数据到本地
                SpTools.setString(mainActivity, viewTagData.url, jsonData);

                //解析数据
                TPINewsData tpiNewsData = parseJson(jsonData);

                //处理数据
                processData(tpiNewsData);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //请求数据失败
                Log.i(TAG, "失败了失败了失败了失败了失败了失败了");
            }
        });
    }

    /**
     * 解析json数据
     *
     * @param jsonData 本地或网络上的json数据
     */
    private TPINewsData parseJson(String jsonData) {
        TPINewsData tpiNewsData = gson.fromJson(jsonData, TPINewsData.class);
//        Log.i(TAG, tpiNewsData.data.news.get(0).title);

        return tpiNewsData;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //页签对应页面的根布局
        root = View.inflate(mainActivity, R.layout.tpi_news_content, null);

        ViewUtils.inject(this, root);
    }

    public View getRootView() {
        return root;
    }

    /**
     * 轮播图的适配器
     *
     * @author Scott Wang
     * @version 1.0, 2016/3/4
     * @see
     * @since SmartBeiJing1.0
     */
    private class CarouselAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return carouselData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv_carousel_pic = new ImageView(mainActivity);
            iv_carousel_pic.setScaleType(ImageView.ScaleType.FIT_XY);

            //设置默认的图片-网络缓慢
            iv_carousel_pic.setImageResource(R.drawable.home_scroll_default);

            //给图片添加数据
            TPINewsData.Data_TPINewsData.Carousel_Data_TPINewsData carousel_data_tpiNewsData = carouselData.get(position);

            //图片的url
            String topImageUrl = carousel_data_tpiNewsData.topimage;

            Log.i(TAG, topImageUrl);

            //把URL的图片给ImageView
            //异步加载图片并且显示到组件中
            bitmapUtils.display(iv_carousel_pic, topImageUrl);

            container.addView(iv_carousel_pic);

            return iv_carousel_pic;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
