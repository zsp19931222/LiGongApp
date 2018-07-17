package com.example.smartclass.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import yh.app.utils.DensityUtil;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Context context;

    public MyItemDecoration(Context context) {
        mPaint = new Paint();
        this.context = context;
        mPaint.setColor(Color.RED);
    }

    /**
     * 在子视图上设置绘制范围，并绘制内容
     * 类似平时自定义View时写onDraw()一样
     * 绘制图层在ItemView以下，所以如果绘制区域与ItemView区域相重叠，会被遮挡
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        int childCount = parent.getChildCount();//获取item数目
//        for (int i = 0; i < childCount; i++) {
//            // 获取每个Item的位置
//            final View child = parent.getChildAt(i);
//            int index = parent.getChildAdapterPosition(child);
//            if (index % 2 == 0) {
//                continue;
//            }
//            // 获取布局参数
//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
//                    .getLayoutParams();
//            // 设置矩形(分割线)的宽度为10px
//            final int mDivider = 10;
//
//            // 根据子视图的位置 & 间隔区域，设置矩形（分割线）的2个顶点坐标(左上 & 右下)
//
//            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
//            // ItemView的左边界 = RecyclerView 的左边界 + paddingLeft距离 后的位置
//            final int left = parent.getPaddingLeft()+20;
//            // ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY
//            final int top = child.getBottom() + params.bottomMargin +
//                    Math.round(ViewCompat.getTranslationY(child));
//
//            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
//            // ItemView的右边界 = RecyclerView 的右边界减去 paddingRight 后的坐标位置
//            final int right = parent.getWidth() - parent.getPaddingRight();
//            // 绘制分割线的下边界 = ItemView的下边界+分割线的高度
//            final int bottom = top + mDivider;
//
//
//            // 通过Canvas绘制矩形（分割线）
//            c.drawRect(left, top, right, bottom, mPaint);
//
//        }
    }


    /**
     * 同样是绘制内容，但与onDraw（）的区别是：绘制在图层的最上层
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
//        int childCount = parent.getChildCount();//获取item数目
//        for (int i = 0; i < childCount; i++) {
//            // 获取每个Item的位置
//            final View child = parent.getChildAt(i);
//            int index = parent.getChildAdapterPosition(child);
//            if (index % 2 == 0) {
//                continue;
//            }
//            // 获取布局参数
//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
//                    .getLayoutParams();
//            // 设置矩形(分割线)的宽度为10px
//            final int mDivider = 1;
//
//            // 根据子视图的位置 & 间隔区域，设置矩形（分割线）的2个顶点坐标(左上 & 右下)
//
//            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
//            // ItemView的左边界 = RecyclerView 的左边界 + paddingLeft距离 后的位置
//            final int left = parent.getPaddingLeft()+20;
//            // ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY
//            final int top = child.getBottom() + params.bottomMargin +
//                    Math.round(ViewCompat.getTranslationY(child));
//
//            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
//            // ItemView的右边界 = RecyclerView 的右边界减去 paddingRight 后的坐标位置
//            final int right = parent.getWidth() - parent.getPaddingRight();
//            // 绘制分割线的下边界 = ItemView的下边界+分割线的高度
//            final int bottom = top + mDivider;
//
//
//            // 通过Canvas绘制矩形（分割线）
//            c.drawRect(left, top, right, bottom, mPaint);
//
//        }
    }

    /**
     * 设置ItemView的内嵌偏移长度（inset）
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //获取item的下标
        int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == 0) {
            outRect.set(0, DensityUtil.dip2px(context, 15), 0, 0);
        }
    }
}
