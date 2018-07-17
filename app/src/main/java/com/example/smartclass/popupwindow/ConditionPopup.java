package com.example.smartclass.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.BeanState;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yh.app.utils.GetWindowsWH;
import yh.app.utils.StatusBarUtil;


/**
 * Created by Administrator on 2017/9/12.
 */

public class ConditionPopup extends PopupWindow {

    private TextView popupConditionCancel;
    private RecyclerView popupConditionRec;
    private ImageView imageView;
    private View conentView;
    private View parentView;
    private List<String> conditionList;

    private QuickAdapter quickAdapter;


    public ConditionPopup(final Context context) {
        conditionList = new ArrayList<>();
        conditionList.add(BeanState.ArriveCondition.DQ);
        conditionList.add(BeanState.ArriveCondition.CD);
        conditionList.add(BeanState.ArriveCondition.ZT);
        conditionList.add(BeanState.ArriveCondition.KK);
        conditionList.add(BeanState.ArriveCondition.QJ);
        LayoutInflater inflater = LayoutInflater.from(context);
        conentView = inflater.inflate(R.layout.popup_condition, null);
        popupConditionCancel = conentView.findViewById(R.id.popup_condition_cancel);
        popupConditionRec = conentView.findViewById(R.id.popup_condition_rec);
        imageView = conentView.findViewById(R.id.popup_condition_image);
        int h = GetWindowsWH.GetH(context) - StatusBarUtil.getStatusBarHeight(context);
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
        final ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        quickAdapter = new QuickAdapter<String>(conditionList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_condition;
            }

            @Override
            public void convert(VH holder, final String data, int position) {
                holder.setTextView(R.id.item_condition_text, data);
                holder.setRelativeLayout(R.id.item_condition_rel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new MessageEvent("修改状态", data));
                        EventBus.getDefault().post(new MessageEvent(TagUtil.ChangeState, data));

                        dismiss();
                    }
                });
            }

        };
        popupConditionRec.setLayoutManager(new LinearLayoutManager(context));
        popupConditionRec.setAdapter(quickAdapter);
        popupConditionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
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