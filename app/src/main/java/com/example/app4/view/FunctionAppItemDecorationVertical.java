package com.example.app4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yhkj.cqgyxy.R;

import yh.app.utils.DensityUtil;

/**
 * Created by Administrator on 2018/4/19 0019.
 */

public class FunctionAppItemDecorationVertical extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Context context;

    public FunctionAppItemDecorationVertical(Context context) {
        mPaint = new Paint();
        this.context = context;
        mPaint.setColor(ContextCompat.getColor(context, R.color.color_f6));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();//获取item数目
        for (int i = 0; i < childCount; i++) {
            // 获取每个Item的位置
            final View child = parent.getChildAt(i);
            // 获取布局参数
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            // 设置矩形(分割线)的宽度为10px
            final int mDivider = 2;

            int left = child.getRight();
            int right = left + mDivider;

            int top = 0;
            int bottom = top + child.getBottom();

            // 通过Canvas绘制矩形（分割线）
            c.drawRect(left, top, right, bottom, mPaint);

        }
    }
}
