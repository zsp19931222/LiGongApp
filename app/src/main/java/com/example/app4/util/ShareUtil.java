package com.example.app4.util;

import android.app.Activity;

import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/6/12 0012.
 * 分享
 */

public class ShareUtil {

    private Activity activity;
    private OnekeyShare oks;

    public ShareUtil(Activity activity) {
        this.activity = activity;
        oks = new OnekeyShare();
    }

    /**
     * 分享链接
     * */
    public void shareHref(String json) {
        try {
            JSONObject object=new JSONObject(json);
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // title标题，微信、QQ和QQ空间等平台使用
            oks.setTitle(object.getString("title"));
            // titleUrl QQ和QQ空间跳转链接
            oks.setTitleUrl(object.getString("url"));
            // text是分享文本，所有平台都需要这个字段
            oks.setText(object.getString("text"));
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImageUrl(object.getString("photo"));
            // url在微信、微博，Facebook等平台中使用
            oks.setUrl(object.getString("url"));
            oks.setCallback(new myPlatformActionListener());
            // 启动分享GUI
            oks.show(activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享文本
     * */
    public void shareText(String json){
        try {
            JSONObject object=new JSONObject(json);
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // title标题，微信、QQ和QQ空间等平台使用
            oks.setTitle(object.getString("title"));
            // titleUrl QQ和QQ空间跳转链接
            oks.setTitleUrl(object.getString("url"));
            // text是分享文本，所有平台都需要这个字段
            oks.setText(object.getString("text"));
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImageUrl(object.getString("photo"));
            // url在微信、微博，Facebook等平台中使用
            oks.setUrl(object.getString("url"));
            oks.setCallback(new myPlatformActionListener());
            // 启动分享GUI
            oks.show(activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class myPlatformActionListener implements PlatformActionListener {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            ToastUtils.Toast(activity, "分享成功");
            EventBus.getDefault().post(new MessageEvent(TagUtil.ShareSuccessTag, ""));
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            ToastUtils.Toast(activity, "分享失败");
            EventBus.getDefault().post(new MessageEvent(TagUtil.ShareErrorTag, ""));

        }

        @Override
        public void onCancel(Platform platform, int i) {
            ToastUtils.Toast(activity, "取消分享");
            EventBus.getDefault().post(new MessageEvent(TagUtil.ShareCancelTag, ""));

        }
    }
}
