package com.example.app4.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.tool.Tool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app4.entity.ApplicationClassifyEntity;
import com.example.app4.entity.ApplicationEntity;
import com.example.app4.util.FunctionIntentUtil;
import com.example.entity.MoreEntity;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.eventbus.MessageEvent;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import yh.app.appstart.lg.R;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DensityUtil;
import yh.app.utils.GsonImpl;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class MorePresenter {
    private Context context;

    public MorePresenter(Context context) {
        this.context = context;
    }

    /**
     * 首页应用展示
     * */
    public void loadLocationData(LinearLayout moreLinAppIcon) {
        moreLinAppIcon.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(DensityUtil.dip2px(context, 6), 0, DensityUtil.dip2px(context, 6), 0);
        params.width =  DensityUtil.dip2px(context, 15);
        params.height =  DensityUtil.dip2px(context, 15);
        ApplicationEntity applicationEntity = GsonImpl.get().toObject(new SqliteHelper().rawQuery("select * from homepage_application_json").get(0).get("json"), ApplicationEntity.class);
        if (applicationEntity.getContent() != null) {
            if (applicationEntity.getContent() .size() <= 6) {
                for (int i = 0; i < applicationEntity.getContent() .size(); i++) {//动态添加图片
                    if (!applicationEntity.getContent().get(i).getClass_name().equals("com.example.app4.activity.MoreActivity")) {
                        ImageView imageView = new ImageView(context);
                        imageView.setLayoutParams(params);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 20), DensityUtil.dip2px(context, 20));
                        layoutParams.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0);
                        imageView.setLayoutParams(layoutParams);
                        moreLinAppIcon.addView(imageView);
                        GlideLoadUtils.getInstance().glideLoad(context, applicationEntity.getContent().get(i).getImg(), imageView, R.drawable.ico_load_little);
                    }
                }
            } else {
                for (int i = 0; i < 6; i++) {
                    ImageView imageView = new ImageView(context);
                    imageView.setLayoutParams(params);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 20), DensityUtil.dip2px(context, 20));
                    layoutParams.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0);
                    imageView.setLayoutParams(layoutParams);
                    moreLinAppIcon.addView(imageView);
                    GlideLoadUtils.getInstance().glideLoad(context, applicationEntity.getContent().get(i).getImg(), imageView, R.drawable.ico_load_little);
                }
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ImageView imageView = new ImageView(context);
                params1.width = DensityUtil.dip2px(context, 15);
                params1.height = DensityUtil.dip2px(context, 4);
                params1.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0);
                imageView.setLayoutParams(params1);
                moreLinAppIcon.addView(imageView);
                GlideLoadUtils.getInstance().glideLoadLocal(context, R.drawable.more_more, imageView);
            }
        }
    }

    /**
     * 应用跳转
     */
    public void intentApplication(final Context context, final MoreEntity.AllTagsListBean.TagInfoListBean lxBean) {
        new FunctionIntentUtil<>(lxBean, context).intent2();
    }
}
