package com.example.app3.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.GlideCacheUtil;
import com.example.app3.tool.HintTool;

import org.greenrobot.eventbus.EventBus;

import com.yhkj.cqgyxy.R;

import yh.app.utils.GetWindowsWH;


/**
 * Created by Administrator on 2017/9/12.
 */

public class HintPopup extends PopupWindow {

    private View conentView;
    private View parentView;

    private TextView textView;
    private Button cancleBtn;
    private Button confirmBtn;
    private ImageView imageView1;
    private ImageView imageView2;

    public HintPopup(final Context context, View parentview, final String hintText) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_hint, null);
        cancleBtn = conentView.findViewById(R.id.hintpop_btn_cancle);
        confirmBtn = conentView.findViewById(R.id.hintpop_btn_confirm);
        imageView1 = conentView.findViewById(R.id.hintpop_Image1);
        imageView2 = conentView.findViewById(R.id.hintpop_Image2);
        textView = conentView.findViewById(R.id.hintpop_text_hint);
        this.parentView = parentView;
        int h = GetWindowsWH.GetH(context);
        int w = GetWindowsWH.GetW(context);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        textView.setText(hintText);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintText.equals(HintTool.CLEARCACHE)) {
                    new GlideCacheUtil().clearImageAllCache(context);
                    EventBus.getDefault().post(new MyEventBus(HintTool.CLEARCACHE));
                } else if (hintText.equals(HintTool.Del_Friend)) {
                    EventBus.getDefault().post(new MyEventBus(HintTool.Del_Friend));
                } else if (hintText.equals(HintTool.LOGINOUT)) {
                    EventBus.getDefault().post(new MyEventBus(HintTool.LOGINOUT));
                }
                dismiss();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();

        }
    }
}
