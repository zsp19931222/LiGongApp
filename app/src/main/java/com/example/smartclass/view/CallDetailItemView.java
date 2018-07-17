package com.example.smartclass.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import yh.app.utils.DensityUtil;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class CallDetailItemView extends View {
    private int viewWidth;//控件宽度
    private int viewHeight;//控件高度
    private Paint paint;
    private int color;
    private Context context;
    private String toOften;//到勤情况

    public CallDetailItemView(Context context) {
        super(context);
    }

    public CallDetailItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(color);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewWidth = canvas.getWidth();
        viewHeight = (int) (viewWidth / 3.8);
        drawRoundRect(canvas, DensityUtil.dip2px(context, 45));
    }

    /**
     * 画圆角矩形
     */
    private void drawRoundRect(Canvas canvas, int radian) {
        canvas.drawRoundRect(new RectF(0, 0, viewWidth, viewHeight), radian, radian, paint);
    }

    /**
     * 画右边到勤情况
     */
    private void drawToOften(Canvas canvas) {
        int ToOften = (int) (viewWidth / 6.3);
        canvas.drawRect(new RectF(0, 0, ToOften, viewHeight), paint);
//        canvas.drawText(toOften,(ToOften-DensityUtil.getTextWidth(paint,toOften))/2,(viewHeight-DensityUtil.getTxtHeight(paint)*2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);
    }
}
