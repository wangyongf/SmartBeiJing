/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: RefreshListView						
 * 描述: 自定义刷新头和尾部的ListView
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/7       Create
 *  1.1         Scott Wang     2016/3/9       listview头和尾的加载，隐藏listview的头和尾
 */

package com.yongf.smartbeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yongf.smartbeijing.R;
import com.yongf.smartbeijing.utils.DateUtils;

import java.util.Date;

/**
 * 自定义刷新头和尾部的ListView
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/7
 * @see
 * @since SmartBeiJing1.0
 */
public class RefreshListView extends ListView {

    private static final String TAG = "RefreshListView";

    //三个刷新的状态

    private final int PULL_DOWN = 1;        //下拉刷新的状态
    private final int RELEASE = 2;              //松开刷新
    private final int REFRESHING = 3;       //正在刷新

    private int currentState = PULL_DOWN;           //当前的状态

    private View foot;          //加载更多数据的尾部组件
    private LinearLayout head;          //listview刷新数据的头部组件

    //listview刷新头的根布局

    private LinearLayout ll_refresh_head_root;
    private int ll_refresh_head_root_height;
    private float downY;
    private View carousel;
    private int listViewOffsetY;                //listview在屏幕中的纵坐标
    private TextView tv_state_desc;
    private TextView tv_refresh_time;
    private ImageView iv_arrow;
    private ProgressBar pb_loading;
    private RotateAnimation upward_ra;
    private RotateAnimation downward_ra;

    private boolean isPullRefreshEnabled = false;

    private OnRefreshDataListener listener;         //刷新数据的监听回调

    private boolean isLoadingMore;          //是否是加载更多数据的操作

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        initEvent();
    }

    private void initEvent() {
        //添加当前listview的滑动事件
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //状态停止，如果listview显示最后一条，显示-加载更多数据
                //是否最后一条数据显示
                if (getLastVisiblePosition() == getAdapter().getCount() - 1 && !isLoadingMore) {
                    //最后一条数据，显示加载更多的组件
                    foot.setPadding(0, 0, 0, 0);        //显示加载更多数据
                    setSelection(getAdapter().getCount());

                    //加载更多数据

                    isLoadingMore = true;
                    if (listener != null) {
                        listener.loadingMore();     //实现该接口的组件去完成数据的加载
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 完成自己的事件处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //需要我们的功能，屏蔽掉父类的touch事件
        //下拉拖动（当listview显示第一条数据的时候，处理自己的事件，不让listview原生的拖动时间生效）

        //如果没有启用下拉刷新，不做任何操作
        if (!isPullRefreshEnabled) {
            return super.onTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:       //按下
                downY = ev.getY();      //按下时的y轴坐标

                break;
            case MotionEvent.ACTION_MOVE:       //移动


                //如果轮播图没有完全显示，响应的是listview的事件
                //判断轮播图是否完全显示
                //取出listview在屏幕中的坐标，和轮播图在屏幕中的坐标进行比较

                if (!isCarouselFullDisplay()) {
                    //轮播图没有完全显示
                    break;
                }

                //防止按下的时候没有获取坐标
                if (downY == -1) {
                    downY = ev.getY();
                }

                //获取移动位置的坐标
                float moveY = ev.getY();

                //移动的距离
                float dy = moveY - downY;

                if (dy > 0 && getFirstVisiblePosition() == 0) {

                    //当前paddingTop的参数值
                    float viewOffsetY = -ll_refresh_head_root_height + dy;

                    if (viewOffsetY < 0 && currentState != PULL_DOWN) {
                        //刷新头没有完全显示
                        //下拉刷新的状态
                        currentState = PULL_DOWN;       //目的只执行一次
                        refreshState();
                    } else if (viewOffsetY >= 0 && currentState != RELEASE) {
                        //松开刷新的状态
                        currentState = RELEASE;     //记录松开刷新状态，只设置一次
                        refreshState();
                    }
                    ll_refresh_head_root.setPadding(0, Math.round(viewOffsetY), 0, 0);

                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:     //松开
                downY = -1;
                //判断状态
                //如果是PULL_DOWN，松开恢复原状
                if (currentState == PULL_DOWN) {
                    ll_refresh_head_root.setPadding(0, -ll_refresh_head_root_height, 0, 0);
                } else if (currentState == RELEASE) {
                    //刷新数据
                    ll_refresh_head_root.setPadding(0, 0, 0, 0);
                    currentState = REFRESHING;      //改变状态为正在刷新数据的状态
                    refreshState();     //刷新界面
                    //真正地刷新数据
                    if (listener != null) {
                        listener.refreshData();
                    }
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnRefreshDataListener(OnRefreshDataListener listener) {
        this.listener = listener;
    }

    private void initAnimation() {
        upward_ra = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upward_ra.setDuration(500);
        upward_ra.setFillAfter(true);       //停留在动画结束的状态

        downward_ra = new RotateAnimation(-180, -360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downward_ra.setDuration(500);
        downward_ra.setFillAfter(true);       //停留在动画结束的状态
    }

    private void refreshState() {
        switch (currentState) {
            case PULL_DOWN:     //下拉刷新
                //改变文字
                tv_state_desc.setText("下拉刷新");
                iv_arrow.startAnimation(downward_ra);

                break;
            case RELEASE:       //松开刷新
                tv_state_desc.setText("松开刷新");
                iv_arrow.startAnimation(upward_ra);

                break;
            case REFRESHING:        //正在刷新
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(View.GONE);     //隐藏箭头
                pb_loading.setVisibility(View.VISIBLE);     //显示进度条
                tv_state_desc.setText("正在刷新");

                break;
        }
    }

    /**
     * 刷新数据成功，进行相应处理
     */
    public void refreshFinish() {
        //下拉刷新

        if (isLoadingMore) {
            //加载更多数据
            isLoadingMore = false;

            //隐藏加载更多数据的组件
            foot.setPadding(0, -ll_refresh_head_root_height, 0, 0);
        } else {
            //改变文字
            tv_state_desc.setText("下拉刷新");
            iv_arrow.setVisibility(View.VISIBLE);           //显示箭头
            pb_loading.setVisibility(View.INVISIBLE);       //隐藏进度条

            //设置刷新时间为当前事件
            tv_refresh_time.setText("最近刷新时间:" + DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss", new Date()));

            //隐藏刷新头布局
            ll_refresh_head_root.setPadding(0, -ll_refresh_head_root_height, 0, 0);

            //初始化为下拉刷新的状态
            currentState = PULL_DOWN;
        }

    }

    private boolean isCarouselFullDisplay() {
        int[] location = new int[2];

        if (listViewOffsetY == 0) {
            this.getLocationOnScreen(location);
            //获取listview在屏幕中的y轴坐标
            listViewOffsetY = location[1];
        }

        //轮播图在屏幕中的坐标
        carousel.getLocationOnScreen(location);
        //判断
        if (location[1] < listViewOffsetY) {
            //轮播图没有完全显示
            //继续响应listview的事件
            return false;
        }
        return true;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initFoot();
        initHead();

        initAnimation();
    }

    /**
     * 初始化尾部的组件
     */
    private void initFoot() {
        //listview的尾部
        foot = View.inflate(getContext(), R.layout.listview_refresh_foot, null);

        //测量尾部组件的高度

        foot.measure(0, 0);

        //listview尾部组件的高度
        int ll_refresh_foot_height = foot.getMeasuredHeight();

        foot.setPadding(0, -ll_refresh_head_root_height, 0, 0);

        //加载到listview中
        addFooterView(foot);
    }

    /**
     * 用户自己选择是否启用下拉刷新的功能
     *
     * @param isPullRefreshEnabled true-启用;false-禁用下拉刷新
     */
    public void setRefreshHeadEnable(boolean isPullRefreshEnabled) {
        this.isPullRefreshEnabled = isPullRefreshEnabled;
    }

    /**
     * 加载轮播图view
     *
     * @param view
     */
    public void addCarouselHeadView(View view) {
        //判断，如果使用了下拉刷新，就把头布局加入下拉刷新的容器中，否则加载在原来的listview中
        if (isPullRefreshEnabled) {
            //启用下拉刷新
            //轮播图的组件
            carousel = view;
            head.addView(view);
        } else {
            //使用原生的ListView
            super.addHeaderView(view);
        }
    }

    /**
     * 初始化头部的组件
     */
    private void initHead() {
        head = (LinearLayout) View.inflate(getContext(), R.layout.listview_header_container, null);
        ll_refresh_head_root = (LinearLayout) head.findViewById(R.id.ll_listview_head_root);

        //获取刷新头布局的子组件

        //刷新状态的文字描述
        tv_state_desc = (TextView) head.findViewById(R.id.tv_listview_header_state_desc);
        //上次刷新时间
        tv_refresh_time = (TextView) head.findViewById(R.id.tv_listview_header_refresh_time);
        //下拉刷新的箭头
        iv_arrow = (ImageView) head.findViewById(R.id.iv_listview_header_arrow);
        //下拉刷新的进度条
        pb_loading = (ProgressBar) head.findViewById(R.id.pb_listview_header_loading);

        //隐藏刷新头的根布局，轮播图还要显示

        //获取刷新头组件的高度
        ll_refresh_head_root.measure(0, 0);

        //获取测量的高度
        ll_refresh_head_root_height = ll_refresh_head_root.getMeasuredHeight();

        ll_refresh_head_root.setPadding(0, -ll_refresh_head_root_height, 0, 0);

        addHeaderView(head);
    }

    public interface OnRefreshDataListener {
        void refreshData();

        void loadingMore();
    }
}
