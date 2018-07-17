package com.example.app3.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import yh.app.utils.DensityUtil;
import 云华.智慧校园.工具.RHelper;

/**
 * Created by Administrator on 2017/9/21.
 * <p>
 * 动态添加view
 */
public class AddView {
    /**
     * @param context
     * @param layout    布局
     * @param viewHight 高度
     */
    public static View addView(Context context, String layout, int viewHight) {
        // TODO 动态添加布局(xml方式)
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, viewHight));
        LayoutInflater inflater3 = LayoutInflater.from(context);
        View view = inflater3.inflate(new RHelper().getLayout(context, layout), null);
        view.setLayoutParams(lp);
        return view;
    }
}
