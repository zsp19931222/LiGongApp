package com.example.app3;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.app3.activity.PhotoBrowserActivity;
import com.example.app4.util.ShareUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yh.app.tool.QRCodeHelper;
import yh.app.utils.ToastUtils;


/**
 * Created by Administrator on 2017/2/10.
 * web调用方法
 */

public class MJavascriptInterface {
    private Context context;
    private String[] imageUrls;

    public MJavascriptInterface(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    String s = "";

    /**
     * 二维码扫描
     */
    @JavascriptInterface
    public void scan() {
        new RxPermissions((Activity) context).
                request(Manifest.permission.CAMERA).
                subscribe(observer);
    }

    /**
     * 二维码扫描
     */
    @JavascriptInterface
    public void scanBtn() {
        new RxPermissions((Activity) context).
                request(Manifest.permission.CAMERA).
                subscribe(observer1);
    }

    /**
     * 图片存储
     */
    @JavascriptInterface
    public void saveImage(String image) {
        this.image = image;
        new RxPermissions((Activity) context).
                request(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(WRITE_EXTERNAL_STORAGE_Observer);
    }

    private String image;

    /**
     * 分享链接
     */
    @JavascriptInterface
    public void shareHref(String s) {
        new ShareUtil((Activity) context).shareHref(s);
    }

    /**
     * 分享文字
     */
    @JavascriptInterface
    public void shareText(String s) {
//        new ShareUtil((Activity) context).shareHref(s);
    }

    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent();
        imageUrls = new String[]{img};
        intent.putExtra("imageUrls", imageUrls);
        intent.putExtra("curImageUrl", img);
        intent.setClass(context, PhotoBrowserActivity.class);
        context.startActivity(intent);
        for (int i = 0; i < imageUrls.length; i++) {
            Log.e("图片地址" + i, imageUrls[i].toString());
        }
    }


    private Observer<Boolean> WRITE_EXTERNAL_STORAGE_Observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toast(context, "获取权限失败");
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                try {
                    JSONObject object = new JSONObject(image);
                    String imageUrl = object.getString("path");
                    Glide.with(context).load(imageUrl).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                        @Override
                        public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                            BitmapUtil.savaBitmap(context,"yh" + System.currentTimeMillis() + ".png", bytes);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtils.Toast(context, "获取权限失败");
            }
        }
    };

    private Observer<Boolean> observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                QRCodeHelper helper = new QRCodeHelper();
                helper.scanQRCode(context);
            } else {
                Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Observer<Boolean> observer1 = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                QRCodeHelper helper = new QRCodeHelper();
                helper.scanQRCode1(context);
            } else {
                Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
