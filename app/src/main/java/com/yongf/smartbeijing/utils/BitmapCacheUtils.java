/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BitmapCacheUtils						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/1       Create	
 */

package com.yongf.smartbeijing.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.yongf.smartbeijing.ui.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Bitmap带缓存功能的图片处理，三级缓存
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/1
 * @see
 * @since SmartBeiJing1.0
 */
public class BitmapCacheUtils {

    private static final String TAG = "BitmapCacheUtils";
    private static final int THREAD_POOL_SIZE = 6;          //屏幕中最多同时显示的图片数量
    private final ExecutorService threadPool;
    //动态获取JVM内存
    private long maxSize = Runtime.getRuntime().freeMemory() / 8;
    //图片的缓存容器
    private LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) maxSize) {

        //计算图片占用多大的内存
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    //保留最后一次访问网络URL的信息
    private Map<ImageView, String> urlImageViewMap = new HashMap<>();

    private File cacheDir;
    private MainActivity mainActivity;

    public BitmapCacheUtils(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.cacheDir = mainActivity.getCacheDir();         //获取当前APP的缓存目录

        //线程池
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void display(ImageView iv, String ivUrl) {
        //1. 先从内存取
        Bitmap bitmap = memCache.get(ivUrl);
        if (null != bitmap) {
            //内存中有图片
            iv.setImageBitmap(bitmap);

            Log.i(TAG, "从内存中获取数据");

            return;
        }

        //2. 再从本地文件中取（程序的缓存）
        bitmap = getCacheFile(ivUrl);
        if (null != bitmap) {
            //本地缓存文件中有图片
            iv.setImageBitmap(bitmap);

            Log.i(TAG, "从本地缓存中获取数据");

            return;
        }

        //3. 从网络取
        getNetworkBitmap(iv, ivUrl);

        //保留最后一次访问网络的URL
        urlImageViewMap.put(iv, ivUrl);

        Log.i(TAG, "从网络获取数据");
    }

    /**
     * 从网络获取图片
     *
     * @param iv    要设置图片的ImageView
     * @param ivUrl 图片的网络路径
     */
    private void getNetworkBitmap(ImageView iv, String ivUrl) {
        //访问网络
//        new Thread(new DownloadUrl(iv, ivUrl)).start();
        //换成线程池的操作
        threadPool.submit(new DownloadUrl(iv, ivUrl));
    }

    /**
     * 获取缓存文件
     *
     * @param ivUrl 当做缓存图片的名字
     * @return
     */
    public Bitmap getCacheFile(String ivUrl) {
        //把ivUrl转换成MD5值，再把MD5值作为文件名
        File file = new File(cacheDir, MD5Utils.getTextMD5Signature(ivUrl));
        if (null != file && file.exists()) {
            //文件存在
            //把文件转换成bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //同时在内存中存该图片
            memCache.put(ivUrl, bitmap);

            return bitmap;
        }

        return null;
    }

    /**
     * 将图片缓存到应用的CacheDir
     *
     * @param bitmap 要缓存的图片
     * @param ivUrl  缓存图片的文件名（以路径命名）
     */
    private void cacheBitmap2CacheDir(Bitmap bitmap, String ivUrl) {
        try {
            File file = new File(cacheDir, MD5Utils.getTextMD5Signature(ivUrl));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class DownloadUrl implements Runnable {

        private String ivUrl;
        private ImageView iv;

        public DownloadUrl(ImageView iv, String ivUrl) {
            this.iv = iv;
            this.ivUrl = ivUrl;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(ivUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);       //设置超时时间
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                if (200 == responseCode) {
                    //请求成功
                    InputStream inputStream = conn.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    //1. 往内存中添加
                    memCache.put(ivUrl, bitmap);
                    //2. 往本地文件中添加
                    cacheBitmap2CacheDir(bitmap, ivUrl);

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //3. 显示数据（图片）
                            //判断URL是否最新的
                            if (ivUrl.equals(urlImageViewMap.get(iv))) {
                                //自己的数据
                                iv.setImageBitmap(bitmap);
                            }
                        }
                    });
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
