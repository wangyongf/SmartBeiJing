/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: NewsBaseNewsCenterPage						
 * 描述:　新闻中心的新闻界面
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/2       Create
 *  1.1         Scott Wang     2016/3/6       加入新闻页面的轮播图，在轮播图的第一页才可以打开左侧菜单
 */

package com.yongf.smartbeijing.newscenterpage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.domain.NewsCenterData;
import com.yongf.smartbeijing.newstpipage.TPINewsCenterPager;
import com.yongf.smartbeijing.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心的新闻界面
 *
 * @author Scott Wang
 * @version 1.1, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsBaseNewsCenterPager extends BaseNewsCenterPager {

    private static final String TAG = "NewsBaseNewsCenterPager";

    @ViewInject(R.id.newscenter_vp)
    private ViewPager newscenterVP;

    @ViewInject(R.id.newscenter_tpi)
    private TabPageIndicator newscneterTPI;
    /**
     * 页签的数据
     */
    private List<NewsCenterData.NewsData.ViewTagData> viewTagData = new ArrayList<>();

    public NewsBaseNewsCenterPager(MainActivity mainActivity, List<NewsCenterData.NewsData.ViewTagData> children) {
        super(mainActivity);

        this.viewTagData = children;
    }

    @OnClick(R.id.newscenter_ib_nextpage)
    public void next(View v) {
        //切换到下一个页面
        newscenterVP.setCurrentItem(newscenterVP.getCurrentItem() + 1);
    }

    @Override
    public void initEvent() {
        //添加自己的事件

        //给ViewPager添加页面切换的监听器，当页面位于第一个，可以滑动出左侧菜单；否则不可以

        newscneterTPI.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 监听页面停留的位置
             * @param position  页面停留的位置
             */
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    //第一个
                    //可以滑动出左侧菜单
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    //不可以滑动出左侧菜单
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        super.initEvent();
    }

    @Override
    public View initView() {
        View newsCenterRoot = View.inflate(mainActivity, R.layout.newscenterpage_content, null);

        //XUtils工具注入组件
        ViewUtils.inject(this, newsCenterRoot);

        return newsCenterRoot;
    }

    @Override
    public void initData() {
        //设置数据
        MyAdapter adapter = new MyAdapter();

        //设置ViewPager的适配器
        newscenterVP.setAdapter(adapter);

        //把ViewPager和TabPageIndicator进行关联
        newscneterTPI.setViewPager(newscenterVP);

        super.initData();
    }


    /**
     * 页签对应ViewPage的适配器
     *
     * @author Scott Wang
     * @version 1.0, 2016/3/3
     * @see
     * @since SmartBeiJing1.0
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewTagData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 页签显示数据调用该方法
         *
         * @param position 显示第几张页面
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return viewTagData.get(position).title;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            TextView tv = new TextView(mainActivity);
//            tv.setText(viewTagData.get(position).title);
//            tv.setTextSize(25);
//            tv.setGravity(Gravity.CENTER);
            TPINewsCenterPager tpiPager = new TPINewsCenterPager(mainActivity, viewTagData.get(position));
            Log.i(TAG, viewTagData.get(position).url);
            View rootView = tpiPager.getRootView();

            container.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
