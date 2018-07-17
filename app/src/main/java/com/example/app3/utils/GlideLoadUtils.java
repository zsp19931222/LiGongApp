package com.example.app3.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.app3.tool.GlideCircleTransform;
import com.example.app3.tool.GlideRoundTransform;

/**
 * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
 */
public class GlideLoadUtils {
    private String TAG = "ImageLoader";

    /**
     * 借助内部类 实现线程安全的单例模式
     * 属于懒汉式单例，因为Java机制规定，内部类SingletonHolder只有在getInstance()
     * 方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的。
     * 内部类加载的时候实例化一次instance。
     */

    //判断Activity是否Destroy
    public static boolean isDestroy(Activity activity) {
        if (activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

    public GlideLoadUtils() {
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideLoadUtils INSTANCE = new GlideLoadUtils();
    }

    public static GlideLoadUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     * @param default_image 图片展示错误的本地图片 id
     */
    public void glideLoad(Context context, Object url, ImageView imageView, int default_image) {
        if (context != null) {
            if (!isDestroy((Activity) context)) {
                Glide.with(context)
                        .load(url)
                        .centerCrop()
                        .error(default_image)
                        .placeholder(default_image)
                        .crossFade()
                        .fitCenter()
                        .into(imageView);
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * 圆图片
     */
    public void glideLoadCircle(Context context, Object url, ImageView imageView, int default_image) {
        if (context != null) {
            if (!isDestroy((Activity) context)) {
                Glide.with(context).load(url).centerCrop().error(default_image).transform(new GlideCircleTransform(context)).placeholder(default_image).crossFade
                        ().into(imageView);
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * 添加圆角
     */
    public void glideLoadBorderRadius(Context context, Object url, ImageView imageView, int default_image, int fillet_value) {
        if (context != null) {
            if (!isDestroy((Activity) context)) {
                Glide.with(context).load(url).centerCrop().error(default_image).transform(new GlideRoundTransform(context, fillet_value)).placeholder(default_image).crossFade
                        ().into(imageView);
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoadLocal(Context context, int image, ImageView imageView) {
        if (context != null) {
            if (!isDestroy((Activity) context)) {

                Glide.with(context).load(image).placeholder(image).centerCrop().crossFade
                        ().into(imageView);
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoadLocal(Context context, Drawable image, ImageView imageView) {
        if (context != null) {
            if (!isDestroy((Activity) context)) {

                Glide.with(context).load(image).error(image).placeholder(image).centerCrop().crossFade
                        ().into(imageView);
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad(Activity activity, String url, ImageView imageView, int default_image) {
        if (!activity.isDestroyed()) {
            if (!isDestroy(activity)) {

                Glide.with(activity).load(url).centerCrop().error(default_image).crossFade
                        ().into(imageView);
            }
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    public void glideLoad(Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment).load(url).centerCrop().error(default_image).crossFade
                    ().into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }

    public void glideLoad(android.app.Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment).load(url).centerCrop().error(default_image).crossFade
                    ().into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,android.app.Fragment is null");
        }
    }
}
