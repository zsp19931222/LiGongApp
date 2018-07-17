package com.example.smartclass.popupwindow;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.example.smartclass.view.VerificationCodeView;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;

import yh.app.utils.GetWindowsWH;
import yh.app.utils.ToastUtils;


/**
 * Created by Administrator on 2017/9/12.
 */

public class SignSuccessPopup extends PopupWindow {

    private View conentView;

    private TextView confirm;

    public SignSuccessPopup(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        conentView = inflater.inflate(R.layout.pop_sign_success, null);
        confirm = conentView.findViewById(R.id.pop_ic_confirm_text);
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
        confirm.setOnClickListener(new View.OnClickListener() {
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