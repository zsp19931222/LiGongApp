package com.example.smartclass.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import yh.app.appstart.lg.R;


/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 */
public class HRRoundProgressBar extends View {
    public static int centre;
    public static int radius;


    public int getViewR() {
        return viewR;
    }

    public void setViewR(int viewR) {
        this.viewR = viewR;
    }

    private int viewR = 0;//控件半径

    /**
     * 画笔对象的引用
     */
    private Paint paint;
    /**
     * 画实心圆
     */
    private Paint solidpaint;
    /**
     * 画BPM
     */
//    private Paint BPMpaint;
    /**
     * 画心率是否正常
     */
    private Paint XLpaint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable = false;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    private Matrix matrix;

    public static final int STROKE = 0;
    public static final int FILL = 1;
    private Context context;

    public HRRoundProgressBar(Context context) {
        this(context, null);
    }

    public HRRoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HRRoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        matrix = new Matrix();
        paint = new Paint();
        solidpaint = new Paint();
        XLpaint = new Paint();
        paint.setAntiAlias(true);
        solidpaint.setAntiAlias(true);
        XLpaint.setAntiAlias(true);
        roundWidth = 5;
        style = STROKE;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        centre = getViewR() / 2; //获取圆心的x坐标
        radius = (int) (centre - roundWidth / 2); //圆环的半径
        paint.setColor(Color.parseColor("#b48ffc")); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿


        canvas.drawCircle(centre, centre, radius - 15, paint); //画出圆环

        /**
         * 画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(Color.parseColor("#f33a6d"));
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        int percent = (int) (((float) progress / (float) max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0

        XLpaint.setTextSize(20);
        XLpaint.setAntiAlias(true);  //消除锯齿
        XLpaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体

        XLpaint.setColor(Color.parseColor("#f33a6d")); //设置BPM的颜色
        /**
         * 画圆弧 ，画圆环的进度
         */

        //设置进度是实心还是空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(ContextCompat.getColor(context,R.color.color_blue_3da8f5));  //设置进度的颜色
        @SuppressLint("DrawAllocation") RectF oval = new RectF(centre - radius + 15, centre - radius + 15, centre
                + radius - 15, centre + radius - 15);  //用于定义的圆弧的形状和大小的界限
        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 270, 360 * progress / max, false, paint);  //根据进度画圆弧
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 270, 360 * progress / max, true, paint);  //根据进度画圆弧
                break;
            }
        }
    }


    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getViewR(), getViewR());
    }

}
