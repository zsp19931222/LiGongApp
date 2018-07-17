package com.example.smartclass.popupwindow;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
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

public class InputVerificationCodePopup extends PopupWindow {

    private View conentView;
    private View parentView;

    private VerificationCodeView codeView;
    private TextView timeView;
    private TextView confirm;


    private long time;

    public InputVerificationCodePopup(final Context context, long time) {
        this.time = time;
        LayoutInflater inflater = LayoutInflater.from(context);
        conentView = inflater.inflate(R.layout.pop_input_code, null);
        codeView = conentView.findViewById(R.id.pop_ic_vc);
        showInputMethod(context,codeView);
        timeView = conentView.findViewById(R.id.pop_ic_time_text);
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
                if (codeView.getEtNumber() != 4) {
                    ToastUtils.Toast(context, "请输入4位验证码");
                } else {
                    EventBus.getDefault().post(new MessageEvent(TagUtil.InputCodeTag, codeView.getInputContent()));
                    dismiss();
                }
            }
        });
        new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                String s = "请输入验证码<font color='#55b2f5'>（" + millisUntilFinished / 1000 + "S）</font>";
                timeView.setText(Html.fromHtml(s));
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        }.start();
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
    /**
     * 显示键盘
     *
     * @param context
     * @param view
     */
    public static void showInputMethod(Context context, View view) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view, 0);
    }

}