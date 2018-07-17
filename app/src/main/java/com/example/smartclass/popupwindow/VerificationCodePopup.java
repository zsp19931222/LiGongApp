package com.example.smartclass.popupwindow;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import yh.app.utils.GetWindowsWH;


/**
 * Created by Administrator on 2017/9/12.
 */

public class VerificationCodePopup extends PopupWindow {

    private View conentView;
    private View parentView;

    private TextView codeView;
    private TextView timeView;

    private View.OnKeyListener mOnKeyListener;


    public VerificationCodePopup(final Context context, View parentview, final String code) {
        LayoutInflater inflater = LayoutInflater.from(context);
        conentView = inflater.inflate(R.layout.popup_verification_code, null);
        codeView = conentView.findViewById(R.id.pop_code_code_text);
        timeView = conentView.findViewById(R.id.pop_code_time_text);
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
        mOnKeyListener = new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return true;
            }
        };
        conentView.setOnKeyListener(mOnKeyListener);
        conentView.setFocusable(true);
        conentView.setFocusableInTouchMode(true);
        this.setFocusable(true);
        codeView.setText(code);
        timer.start();
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

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            String s = "结束<font color='#55b2f5'>（" + millisUntilFinished / 1000 + "s）</font>";
            timeView.setText(Html.fromHtml(s));
        }

        @Override
        public void onFinish() {
            dismiss();
        }
    };


}