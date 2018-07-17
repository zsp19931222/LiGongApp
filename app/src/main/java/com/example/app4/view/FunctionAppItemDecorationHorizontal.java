package com.example.app4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import yh.app.appstart.lg.R;

import yh.app.utils.DensityUtil;

/**
 * Created by Administrator on 2018/4/19 0019.
 */

public class FunctionAppItemDecorationHorizontal extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Context context;

    public FunctionAppItemDecorationHorizontal(Context context) {
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

            // 根据子视图的位置 & 间隔区域，设置矩形（分割线）的2个顶点坐标(左上 & 右下)

            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            // ItemView的左边界 = RecyclerView 的左边界 + paddingLeft距离 后的位置
            final int left = parent.getPaddingLeft();
            // ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));

            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            // ItemView的右边界 = RecyclerView 的右边界减去 paddingRight 后的坐标位置
            final int right = parent.getWidth() - parent.getPaddingRight();
            // 绘制分割线的下边界 = ItemView的下边界+分割线的高度
            final int bottom = top + mDivider;

            // 通过Canvas绘制矩形（分割线）
            c.drawRect(left, top, right, bottom, mPaint);

        }
    }
}
