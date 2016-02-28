/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: GuideActivity.java
 * 描述: 智慧北京向导界面
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/28       Create
 *  1.1         Scott Wang     2016/2/28       完成整个向导界面，以及跳转到主页
 */

package com.yongf.smartbeijing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.utils.DensityUtils;
import com.yongf.smartbeijing.utils.MyConstants;
import com.yongf.smartbeijing.utils.SpTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置向导界面
 * 采用ViewPager进行界面的切换
 *
 * @author Scott Wang
 * @version 1.1, 2016/2/28
 * @see
 * @since SmartBeiJing1.0
 */
public class GuideActivity extends AppCompatActivity {

    private static final String TAG = "GuideActivity";

    /**
     * ViewPager组件
     */
    private ViewPager mVpGuidePages;

    /**
     * 动态加点容器
     */
    private LinearLayout mLlGuidePoints;

    /**
     * 红点
     */
    private View mVGuideRedpoint;

    /**
     * 开始体验的按钮
     */
    private Button mBtnGuideStartexp;

    /**
     * ViewPager使用的容器
     */
    private List<ImageView> mGuides;

    /**
     * ViewPager数据适配器
     */
    private MyPagerAdapter mAdapter;

    /**
     * 点与点之间的距离
     */
    private int disPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //初始化界面
        initView();

        //初始化数据
        initData();

        //初始化组件的事件
        initEvent();
    }

    /**
     * 初始化组件的事件
     */
    private void initEvent() {

        //监听布局完成，触发的结果
        mVGuideRedpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //取消注册 界面变化而发生的回调结果
                mVGuideRedpoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                disPoints = mLlGuidePoints.getChildAt(1).getLeft() - mLlGuidePoints.getChildAt(0).getLeft();
            }
        });

        mBtnGuideStartexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存设置的状态
                SpTools.setBoolean(getApplicationContext(), MyConstants.IS_SETUP, true);        //保存设置完成的状态
                //进入主界面
                Intent main = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(main);    //启动主界面
                //关闭自己
                finish();
            }
        });

        //给ViewPager添加页码改变的事件
        mVpGuidePages.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 在页面滑动过程中触发的事件
             * @param position  当前ViewPager停留的位置
             * @param positionOffset    偏移的比例值
             * @param positionOffsetPixels  偏移的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //计算红点的左边距
                float mLeftMargin = disPoints * (position + positionOffset);

                //设置红点的左边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVGuideRedpoint.getLayoutParams();
                params.leftMargin = Math.round(mLeftMargin);    //对float类型四舍五入

                //重新设置布局
                mVGuideRedpoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                //当前ViewPager显示的页面
                //如果ViewPager滑动到最后一个页面，显示button
                if (position == mGuides.size() - 1) {
                    mBtnGuideStartexp.setVisibility(View.VISIBLE);      //设置按钮的显示
                } else {
                    mBtnGuideStartexp.setVisibility(View.GONE);     //不是最后一页，隐藏该button按钮
                }
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
        //ViewPager Adapter List
        //图片的数据
        int[] pics = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

        //定义ViewPager使用的容器
        mGuides = new ArrayList<>();

        //初始化容器中的数据
        for (int i = 0; i < pics.length; i++) {
            ImageView mIvTemp = new ImageView(getApplicationContext());
            mIvTemp.setBackgroundResource(pics[i]);

            mGuides.add(mIvTemp);

            //给点的容器LinearLayout初始化灰点
            View mVPoint = new View(getApplicationContext());
            mVPoint.setBackgroundResource(R.drawable.gray_point);
            int dip = 10;
            //设置灰色点的参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(getApplicationContext(), dip),
                    DensityUtils.dip2px(getApplicationContext(), dip));       //注意单位是px 不是dp

            //设置点之间的空隙，过滤第一个点
            if (i != 0) {
                params.leftMargin = 10;
            }
            mVPoint.setLayoutParams(params);

            //添加灰色的点到线性布局中
            mLlGuidePoints.addView(mVPoint);
        }
        //创建ViewPager的适配器
        mAdapter = new MyPagerAdapter();

        //设置适配器
        mVpGuidePages.setAdapter(mAdapter);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mVpGuidePages = (ViewPager) findViewById(R.id.vp_guide_pages);
        mLlGuidePoints = (LinearLayout) findViewById(R.id.ll_guide_points);
        mVGuideRedpoint = findViewById(R.id.v_guide_redpoint);
        mBtnGuideStartexp = (Button) findViewById(R.id.btn_guide_startexp);

    }


    /**
     * 向导界面ViewPager的适配器
     *
     * @author Scott Wang
     * @version 1.0, 2016/2/28
     * @see
     * @since SmartBeiJing1.0
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mGuides != null) {
                return mGuides.size();       //返回数据的个数
            }

            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;      //过滤和缓存的作用
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //container ViewPager
            //获取View
            View child = mGuides.get(position);
            //添加View
            container.addView(child);

            return child;
        }
    }
}
