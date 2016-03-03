/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: LeftMenuFragment.java
 * 描述: 左侧菜单的Fragment
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/2/29       Create
 *  1.1         Scott Wang     2016/3/2         左侧菜单listview的显示
 */

package com.yongf.smartbeijing.view;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.domain.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * 左侧菜单的Fragment
 *
 * @author Scott Wang
 * @version 1.1, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public class LeftMenuFragment extends BaseFragment {

    /**
     * 新闻中心左侧菜单的数据
     */
    private List<NewsData> data = new ArrayList<>();
    private ListView lv_leftData;
    private MyListViewAdapter adapter;
    /**
     * 选中的位置
     */
    private int selectPositoin;

    private OnSwitchPageListener switchPageListener;

    /**
     * 设置监听回调接口
     *
     * @param listener 回调接口
     */
    public void setOnSwitchPageListener(OnSwitchPageListener listener) {
        this.switchPageListener = listener;
    }

    @Override
    public void initEvent() {
        //设置listview的选择事件
        lv_leftData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //保存选中的位置
                selectPositoin = position;

                //更新界面
                adapter.notifyDataSetChanged();

                //控制新闻中心四个页面的显示
                if (switchPageListener != null) {
                    switchPageListener.switchPage(selectPositoin);
                } else {
                    mainActivity.getMainMenuFragment().leftMenuClickSwitchPage(selectPositoin);
                }

                //切换slidingMenu的开关
                mainActivity.getSlidingMenu().toggle();
            }
        });

        super.initEvent();
    }

    @Override
    public View initView() {

//        TextView tv = new TextView(mainActivity);
//        tv.setText("左侧菜单");
//        tv.setTextSize(25);
//        tv.setGravity(Gravity.CENTER);
        // listview显示左侧菜单
        lv_leftData = new ListView(mainActivity);

        //背景是黑色
        lv_leftData.setBackgroundColor(Color.BLACK);

        //选中拖动的背景色设置成透明色
        lv_leftData.setCacheColorHint(Color.TRANSPARENT);

        //设置选中时为透明背景
        lv_leftData.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //没有分割线
        lv_leftData.setDividerHeight(0);

        //距离顶部为45px
        lv_leftData.setPadding(0, 45, 0, 0);

        return lv_leftData;
    }

    public void setLeftMenuData(List<NewsData> data) {
        this.data = data;

        //设置好数据后，通知界面刷新数据
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        //组织数据
        adapter = new MyListViewAdapter();
        lv_leftData.setAdapter(adapter);

        super.initData();
    }

    public interface OnSwitchPageListener {
        void switchPage(int selectionIndex);
    }

    private class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //显示数据
            TextView tv_currentView;
            if (convertView == null) {
                tv_currentView = (TextView) View.inflate(mainActivity, R.layout.left_menu_list_item, null);
            } else {
                tv_currentView = (TextView) convertView;
            }

            //设置数据
            tv_currentView.setText(data.get(position).title);

            return tv_currentView;
        }
    }
}
