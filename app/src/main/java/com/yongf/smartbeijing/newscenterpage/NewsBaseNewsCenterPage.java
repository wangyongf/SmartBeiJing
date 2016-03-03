/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: NewsBaseNewsCenterPage						
 * 描述:　新闻中心的新闻界面
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/2       Create	
 */

package com.yongf.smartbeijing.newscenterpage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.domain.ViewTagData;
import com.yongf.smartbeijing.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心的新闻界面
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsBaseNewsCenterPage extends BaseNewsCenterPage {

    private static final String TAG = "NewsBaseNewsCenterPage";

    @ViewInject(R.id.newscenter_vp)
    private ViewPager newscenterVP;

    @ViewInject(R.id.newscenter_tpi)
    private TabPageIndicator newscneterTPI;
    /**
     * 页签的数据
     */
    private List<ViewTagData> viewTagData = new ArrayList<>();

    public NewsBaseNewsCenterPage(MainActivity mainActivity, List<ViewTagData> children) {
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

        newscenterVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                    //
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
            TextView tv = new TextView(mainActivity);
            tv.setText(viewTagData.get(position).title);
            tv.setTextSize(25);
            tv.setGravity(Gravity.CENTER);

            container.addView(tv);

            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
