/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: NewsDetailActivity.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/29       Create
 */

package com.yongf.smartbeijing.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yongf.smartbeijing.R;


/**
 * 新闻详情的页面
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/29
 * @see
 * @since SmartBeiJing1.0
 */
public class NewsDetailActivity extends AppCompatActivity {

    private ImageButton ib_back;
    private ImageButton ib_setTextSize;
    private ImageButton ib_share;
    private WebView wv_news;
    private ProgressBar pb_loading_news;
    private WebSettings wv_newsSettings;

    /**
     * 字体大小描述
     */
    private String[] textSizeString = {"超大号", "大号", "正常", "小号", "超小号"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        去掉标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

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
        //创建三个按钮共用的监听器
        View.OnClickListener listener = new View.OnClickListener() {

            /**
             * 0 => 超大号
             * 1 => 大号
             * 2 => 正常
             * 3 => 小号
             * 4 => 超小号
             */
            int textSizeIndex = 2;

            AlertDialog dialog;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ib_base_content_back:     //返回键
                        //关闭当前新闻页面
                        finish();

                        break;
                    case R.id.ib_base_content_textsize:     //修改字体大小
                        //通过对话框来修改字体大小（5种大小的字体）
                        showChangeTextSizeDialog();

                        //设置字体的大小   wv_newsSettings.setTextSize(WebSettings.TextSize.);
//                        setTextSize();

                        break;
                    case R.id.ib_base_content_share:        //分享


                        break;
                }
            }

            private void showChangeTextSizeDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsDetailActivity.this);
                builder.setTitle("设置字体大小");
                builder.setSingleChoiceItems(textSizeString, textSizeIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textSizeIndex = which;
                        setTextSize();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }

            /**
             * 设置新闻的字体
             * 此方法可以用数组代替
             */
            private void setTextSize() {
                switch (textSizeIndex) {
                    case 0:     //超大号
                        wv_newsSettings.setTextSize(WebSettings.TextSize.LARGEST);

                        break;
                    case 1:     //大号
                        wv_newsSettings.setTextSize(WebSettings.TextSize.LARGER);

                        break;
                    case 2:     //正常
                        wv_newsSettings.setTextSize(WebSettings.TextSize.NORMAL);

                        break;
                    case 3:     //小号
                        wv_newsSettings.setTextSize(WebSettings.TextSize.SMALLER);

                        break;
                    case 4:     //超小号
                        wv_newsSettings.setTextSize(WebSettings.TextSize.SMALLEST);

                        break;
                }

                //修改字体之后关闭对话框
                dialog.dismiss();
            }
        };
        //给返回键添加点击事件
        ib_back.setOnClickListener(listener);
        ib_share.setOnClickListener(listener);
        ib_setTextSize.setOnClickListener(listener);

        //给webView添加一个新闻加载完成的监听事件
        wv_news.setWebViewClient(new WebViewClient() {

            /**
             * 页面加载完成的事件处理
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                //隐藏进度条
                pb_loading_news.setVisibility(View.GONE);

                super.onPageFinished(view, url);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String newsUrl = getIntent().getStringExtra("news_url");
        if (TextUtils.isEmpty(newsUrl)) {
            Toast.makeText(getApplicationContext(), "链接错误", Toast.LENGTH_SHORT).show();
        } else {
            //有新闻
            //加载新闻
            wv_news.loadUrl(newsUrl);
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.newscenter_newsdetail);

        //设置菜单按钮隐藏
        findViewById(R.id.ib_base_content_menu).setVisibility(View.GONE);

        //隐藏标题
        findViewById(R.id.tv_base_content_title).setVisibility(View.GONE);

        //返回的按钮
        ib_back = (ImageButton) findViewById(R.id.ib_base_content_back);
        ib_back.setVisibility(View.VISIBLE);

        //修改新闻的字体
        ib_setTextSize = (ImageButton) findViewById(R.id.ib_base_content_textsize);
        ib_setTextSize.setVisibility(View.VISIBLE);

        //分享按钮
        ib_share = (ImageButton) findViewById(R.id.ib_base_content_share);
        ib_share.setVisibility(View.VISIBLE);

        //显示新闻
        wv_news = (WebView) findViewById(R.id.wv_newscenter_newsdetail);

        //WebView的显示设置
        wv_newsSettings = wv_news.getSettings();
        wv_newsSettings.setBuiltInZoomControls(true);           //支持屏幕缩放
        wv_newsSettings.setUseWideViewPort(true);       //双击放大缩小
        wv_newsSettings.setJavaScriptEnabled(true);     //支持编译js脚本

        //加载新闻的进度
        pb_loading_news = (ProgressBar) findViewById(R.id.pb_newscenter_newsdetail_loading);
    }
}
