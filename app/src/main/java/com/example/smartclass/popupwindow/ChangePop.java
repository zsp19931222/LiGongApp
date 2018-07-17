package com.example.smartclass.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.BeanState;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import yh.app.utils.DensityUtil;
import yh.app.utils.GetWindowsWH;
import yh.app.utils.StatusBarUtil;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class ChangePop extends PopupWindow implements View.OnClickListener {

    private View conentView;
    private TextView pop_change_arrive_text;
    private TextView pop_change_leave_text;
    private TextView pop_change_late_text;
    private TextView pop_change_early_text;
    private TextView pop_change_truant_text;
    private View parent;
    private Context context;


    public ChangePop(final Context context, View parent) {
        this.parent = parent;
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_change, null);

        pop_change_arrive_text = conentView.findViewById(R.id.pop_change_arrive_text);
        pop_change_leave_text = conentView.findViewById(R.id.pop_change_leave_text);
        pop_change_late_text = conentView.findViewById(R.id.pop_change_late_text);
        pop_change_early_text = conentView.findViewById(R.id.pop_change_early_text);
        pop_change_truant_text = conentView.findViewById(R.id.pop_change_truant_text);

        pop_change_arrive_text.setOnClickListener(this);
        pop_change_leave_text.setOnClickListener(this);
        pop_change_late_text.setOnClickListener(this);
        pop_change_early_text.setOnClickListener(this);
        pop_change_truant_text.setOnClickListener(this);

        int h = GetWindowsWH.GetH(context) - StatusBarUtil.getStatusBarHeight(context);
        int w = GetWindowsWH.GetW(context);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(DensityUtil.dip2px(context, 180 / 2));
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(DensityUtil.dip2px(context, 160));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, DensityUtil.dip2px(context, 180 / 4), -DensityUtil.dip2px(context, 30));
        } else {
            this.dismiss();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_change_arrive_text:
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChangeState, BeanState.ArriveCondition.DQ));
                break;
            case R.id.pop_change_leave_text:
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChangeState, BeanState.ArriveCondition.QJ));
                break;
            case R.id.pop_change_late_text:
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChangeState, BeanState.ArriveCondition.CD));
                break;
            case R.id.pop_change_early_text:
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChangeState, BeanState.ArriveCondition.ZT));
                break;
            case R.id.pop_change_truant_text:
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChangeState, BeanState.ArriveCondition.KK));
                break;
        }
        dismiss();
    }
}