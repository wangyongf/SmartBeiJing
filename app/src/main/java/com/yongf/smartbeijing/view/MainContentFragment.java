/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: MainContentFragment.java
 * 描述: 主界面的Fragment
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/29       Create
 *  1.1         Scott Wang     2016/3/2         ViewPager的懒加载
 */

package com.yongf.smartbeijing.view;


import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.basepage.BaseTagPager;
import com.yongf.smartbeijing.basepage.GovAffairsBaseTagPager;
import com.yongf.smartbeijing.basepage.HomeBaseTagPager;
import com.yongf.smartbeijing.basepage.NewsCenterBaseTagPager;
import com.yongf.smartbeijing.basepage.SettingCenterBaseTagPager;
import com.yongf.smartbeijing.basepage.SmartServiceBaseTagPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面的Fragment
 *
 * @author Scott Wang
 * @version 1.1, 2016/2/29
 * @see
 * @since SmartBeiJing1.0
 */
public class MainContentFragment extends BaseFragment {

    private static final String TAG = "MainContentFragment";
    @ViewInject(R.id.vp_main_content_pages)
    private MyViewPager viewPager;

    @ViewInject(R.id.rg_content_radios)
    private RadioGroup rg_radios;

    private List<BaseTagPager> pages = new ArrayList<>();

    /**
     * 记录选中的页面编号
     */
    private int selectIndex;

    /**
     * 选中的页面
     */
    private int[] isChecked = {
            R.id.rb_main_content_home,
            R.id.rb_main_content_newscenter,
            R.id.rb_main_content_smartservice,
            R.id.rb_main_content_govaffairs,
            R.id.rb_main_content_settingcenter
    };

    /**
     * 添加自己的事件
     */
    @Override
    public void initEvent() {
        //单选按钮的切换事件
        rg_radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //五个单选按钮
                switch (checkedId) {    //判断点击了哪个单选按钮
                    case R.id.rb_main_content_home:     //首页
                        selectIndex = 0;

                        break;
                    case R.id.rb_main_content_newscenter:       //新闻中心
                        selectIndex = 1;

                        break;
                    case R.id.rb_main_content_smartservice:     //智慧服务
                        selectIndex = 2;

                        break;
                    case R.id.rb_main_content_govaffairs:       //政务
                        selectIndex = 3;

                        break;
                    case R.id.rb_main_content_settingcenter:        //设置中心
                        selectIndex = 4;

                        break;
                }   // end switch

                switchPage();
            }
        });
    }

    /**
     * 左侧菜单点击，让主界面切换不同的页面
     * @param subSelectionIndex
     */
    public void leftMenuClickSwitchPage(int subSelectionIndex) {
        BaseTagPager baseTagPager = pages.get(selectIndex);
        baseTagPager.switchPage(subSelectionIndex);
    }

    /**
     * 设置选中的页面
     */
    protected void switchPage() {
//        BaseTagPager currentPage = pages.get(selectIndex);
        //设置ViewPager显示的页面
        viewPager.setCurrentItem(selectIndex);

        //如果是第一个或者最后一个，不让左侧菜单滑动出来
        if (selectIndex == 0 || selectIndex == pages.size() - 1) {
            //不让左侧菜单滑动
            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        } else {
            //可以滑动左侧菜单，屏幕任何位置都可以滑动出来
            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }

        //设置该页面按钮被选中
        rg_radios.check(isChecked[selectIndex]);
    }

    @Override
    public View initView() {
        View root = View.inflate(mainActivity, R.layout.fragment_content_view, null);

        //XUtils动态注入View
        ViewUtils.inject(this, root);

        return root;
    }

    @Override
    public void initData() {
        super.initData();

        //首页
        pages.add(new HomeBaseTagPager(mainActivity));

        //新闻中心
        pages.add(new NewsCenterBaseTagPager(mainActivity));

        //智慧服务
        pages.add(new SmartServiceBaseTagPager(mainActivity));

        //政务
        pages.add(new GovAffairsBaseTagPager(mainActivity));

        //设置中心
        pages.add(new SettingCenterBaseTagPager(mainActivity));

        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);

//        viewPager.setOffscreenPageLimit(2);     //设置预加载为：前后各2个页面

        //设置默认选择首页
        switchPage();
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i(TAG, "init:" + position);

            BaseTagPager baseTagPager = pages.get(position);
            View root = baseTagPager.getRoot();
            container.addView(root);

            //加载数据
            baseTagPager.initData();

            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i(TAG, "destroy:" + position);

            container.removeView((View) object);
        }
    }
}
