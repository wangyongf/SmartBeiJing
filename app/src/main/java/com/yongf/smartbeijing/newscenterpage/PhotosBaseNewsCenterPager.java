/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: PhotosBaseNewsCenterPage						
 * 描述:　新闻中心的组图界面
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/3/2       Create	
 */

package com.yongf.smartbeijing.newscenterpage;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.yongf.smartbeijing.domain.PhotosData;
import com.yongf.smartbeijing.ui.MainActivity;
import com.yongf.smartbeijing.utils.MyConstants;
import com.yongf.smartbeijing.utils.SpTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心的组图界面
 *
 * @author Scott Wang
 * @version 1.0, 2016/3/2
 * @see
 * @since SmartBeiJing1.0
 */
public class PhotosBaseNewsCenterPager extends BaseNewsCenterPager {

    private static final String TAG = "PhotosBaseNewsCenterPager";
    private final BitmapUtils bitmapUtils;
    @ViewInject(R.id.lv_newscenter_photos)
    private ListView lv_photos;
    @ViewInject(R.id.gv_newscenter_photos)
    private GridView gv_photos;
    private MyAdapter adapter;
    private List<PhotosData.PhotoData_Data.PhotosNews> photosNewsList = new ArrayList<>();
    /**
     * 列表方式显示图片
     */
    private boolean isListModel = true;

    public PhotosBaseNewsCenterPager(MainActivity mainActivity) {
        super(mainActivity);

        bitmapUtils = new BitmapUtils(mainActivity);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);
    }

    @Override
    public View initView() {
        View photos_root = View.inflate(mainActivity, R.layout.newscenter_photos, null);

        ViewUtils.inject(this, photos_root);

        return photos_root;
    }

    /**
     * 切换listView和gridView
     *
     * @param ib_listOrGrid
     */
    public void switchListViewOrGridView(ImageButton ib_listOrGrid) {
        if (isListModel) {
            ib_listOrGrid.setImageResource(R.drawable.icon_pic_list_type);
            //换成GridView
            //隐藏
            lv_photos.setVisibility(View.GONE);
            gv_photos.setVisibility(View.VISIBLE);
        } else {
            ib_listOrGrid.setImageResource(R.drawable.icon_pic_grid_type);
            //显示ListView
            lv_photos.setVisibility(View.VISIBLE);
            gv_photos.setVisibility(View.GONE);
        }

        isListModel = !isListModel;
    }

    @Override
    public void initData() {
        //创建适配器
        if (null == adapter) {
            adapter = new MyAdapter();
            lv_photos.setAdapter(adapter);
            gv_photos.setAdapter(adapter);
        }

        //改变浏览模式
        switchBrowseModel();

        //本地取缓存数据
        fetchLocalData();

        //网络取数据
        fetchNetworkData();

        super.initData();
    }

    /**
     * 改变浏览模式（列表 | 网格）
     */
    private void switchBrowseModel() {
        if (isListModel) {
            lv_photos.setVisibility(View.VISIBLE);
            gv_photos.setVisibility(View.GONE);
        } else {
            lv_photos.setVisibility(View.GONE);
            gv_photos.setVisibility(View.VISIBLE);
        }
    }

    private void fetchLocalData() {
        String photosJsonData = SpTools.getString(mainActivity, MyConstants.PHOTOS_URL, null);
        if (!TextUtils.isEmpty(photosJsonData)) {
            //有数据
            PhotosData photosData = parseJson(photosJsonData);

            //处理组图数据
            processPhotosData(photosData);
        }
    }

    private void fetchNetworkData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.PHOTOS_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求数据成功
                //获取组图的json数据
                String jsonData = responseInfo.result;

                //缓存
                SpTools.setString(mainActivity, MyConstants.PHOTOS_URL, jsonData);

                //解析json数据
                PhotosData photosData = parseJson(jsonData);

                //处理组图数据
                processPhotosData(photosData);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //请求数据失败

            }
        });
    }

    /**
     * 处理组图的数据
     *
     * @param photosData 组图的数据
     */
    private void processPhotosData(PhotosData photosData) {
        //获取组图的所有数据
        photosNewsList = photosData.data.news;
        adapter.notifyDataSetChanged();         //通知界面更新数据
    }

    /**
     * 解析json数据
     *
     * @param jsonData 组图的json数据
     * @return 解析完毕的组图的数据
     */
    private PhotosData parseJson(String jsonData) {
        Gson gson = new Gson();
        //组图的所有数据
        PhotosData photosData = gson.fromJson(jsonData, PhotosData.class);

        return photosData;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return photosNewsList.size();
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
            //自定义View显示
            ViewHolder holder;

            //判断是否存在view缓存
            if (convertView == null) {
                //没有界面缓存
                convertView = View.inflate(mainActivity, R.layout.photos_list_item, null);
                holder = new ViewHolder();

                holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_photos_list_item_pic);
                holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_photos_list_item_desc);

                convertView.setTag(holder);
            } else {
                //有界面缓存
                holder = (ViewHolder) convertView.getTag();
            }

            //赋值
            //取当前图片数据
            PhotosData.PhotoData_Data.PhotosNews photosNews = photosNewsList.get(position);
            holder.tv_desc.setText(photosNews.title);

            //设置图片
            bitmapUtils.display(holder.iv_pic, photosNews.listimage);

            return convertView;
        }
    }

    private class ViewHolder {
        ImageView iv_pic;
        TextView tv_desc;
    }
}
