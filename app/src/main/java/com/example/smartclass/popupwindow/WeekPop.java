package com.example.smartclass.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.eventbus.MyEventBus;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import yh.app.utils.GetWindowsWH;
import yh.app.utils.StatusBarUtil;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/1/4 0004.
 * 时间选择弹出框
 */

public class WeekPop extends PopupWindow {

    private View conentView;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private QuickAdapter quickAdapter;
    private List<String> strings = new ArrayList<>();

    private int now_weeks = 1;//当前周
    private int now_choise_weeks = 1;//当前周

    public WeekPop(final Context context, int size, int now_weeks, int now_choise_weeks) {
        this.now_weeks = now_weeks;
        this.now_choise_weeks = now_choise_weeks;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_sc_week, null);
        recyclerView = conentView.findViewById(R.id.rec_sc_pop_week);
        imageView = conentView.findViewById(R.id.image_sc_pop_week);
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
        ColorDrawable dw = new ColorDrawable(00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
//        this.setAnimationStyle(R.style.anim_bottom_pop);//弹出动画
        setRecyclerView(context, size);
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
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    private void setRecyclerView(final Context context, int size) {
        for (int i = 0; i < size; i++) {
            strings.add((i + 1) + "");
        }
        quickAdapter = new QuickAdapter<String>(strings) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_sc_pop_week;
            }

            @Override
            public void convert(VH holder, final String data, int position) {
                if (data.equals(now_weeks + "")) {
                    if (data.equals(now_choise_weeks + "")) {
                        holder.setTextView(R.id.text_item_sc_pop_week, "当前周数").setTextColor(ContextCompat.getColor(context, R.color.white));
                        holder.setImageView(context, R.id.image_item_sc_pop_week, R.drawable.circle_withe).setVisibility(View.VISIBLE);
                    } else {
                        holder.setTextView(R.id.text_item_sc_pop_week, "周数").setTextColor(ContextCompat.getColor(context, R.color.white));
                        holder.setImageView(context, R.id.image_item_sc_pop_week, R.drawable.circle_withe).setVisibility(View.GONE);
                    }
                    holder.setTextView(R.id.text_item_sc_pop_week_num, data).setTextColor(ContextCompat.getColor(context, R.color.white));
                    holder.setRelativeLayout(R.id.rel_item_sc_pop_week).setBackgroundColor(ContextCompat.getColor(context, R.color.color_blue_3da8f5));
                } else {
                    if (data.equals(now_choise_weeks + "")) {
                        holder.setTextView(R.id.text_item_sc_pop_week, "当前周数").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                        holder.setImageView(context, R.id.image_item_sc_pop_week, R.drawable.circle_blue).setVisibility(View.VISIBLE);
                    } else {
                        holder.setTextView(R.id.text_item_sc_pop_week, "周数").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                        holder.setImageView(context, R.id.image_item_sc_pop_week, R.drawable.circle_withe).setVisibility(View.GONE);
                    }
                    holder.setTextView(R.id.text_item_sc_pop_week_num, data).setTextColor(ContextCompat.getColor(context, R.color.color_gray_666666));
                    holder.setRelativeLayout(R.id.rel_item_sc_pop_week).setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                }
                holder.setRelativeLayout(R.id.rel_item_sc_pop_week).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        now_weeks = Integer.valueOf(data);
                        quickAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(TagUtil.ChoiceWeeks, now_weeks));
                        dismiss();

                    }
                });
            }
        };
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        recyclerView.setAdapter(quickAdapter);
    }
}