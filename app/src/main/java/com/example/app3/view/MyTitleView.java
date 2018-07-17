package com.example.app3.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yh.app.appstart.lg.R;
import yh.app.logTool.Log;


/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class MyTitleView extends RelativeLayout {
    private TextView iv_titlebar_left;
    private TextView iv_titlebar_right;
    private TextView tv_titlebar_title;
    private RelativeLayout iv_titlebar_leftRel;
    private RelativeLayout iv_titlebar_rightRel;
    private RelativeLayout layout_titlebar_rootlayout;

    private int left_iamge;
    private int right_iamge;

    private int mColor = Color.BLUE;
    private int mTextColor = Color.WHITE;
    private int title_height;
    private int title_text_size;
    private String titlename;

    public MyTitleView(Context context) {
        super(context);

    }

    public MyTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mColor = mTypedArray.getColor(R.styleable.TitleBar_title_bg, Color.BLUE);
        mTextColor = mTypedArray.getColor(R.styleable.TitleBar_title_text_color, Color.WHITE);
        titlename = mTypedArray.getString(R.styleable.TitleBar_title_text);
        left_iamge = mTypedArray.getResourceId(R.styleable.TitleBar_title_left_image, 0);
        right_iamge = mTypedArray.getResourceId(R.styleable.TitleBar_title_right_image, 0);
        title_height = (int) mTypedArray.getDimension(R.styleable.TitleBar_title_height, dip2px(context, 50));
        title_text_size = (int) mTypedArray.getDimension(R.styleable.TitleBar_title_text_size, 18);
        //获取资源后要及时回收
        mTypedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_customtitle, this, true);
        iv_titlebar_left = (TextView) findViewById(R.id.iv_titlebar_left);
        iv_titlebar_right = (TextView) findViewById(R.id.iv_titlebar_right);
        tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
        layout_titlebar_rootlayout = (RelativeLayout) findViewById(R.id.layout_titlebar_rootlayout);
        iv_titlebar_leftRel = (RelativeLayout) findViewById(R.id.iv_titlebar_leftRel);
        iv_titlebar_rightRel = (RelativeLayout) findViewById(R.id.iv_titlebar_rightRel);
        //设置背景颜色
        layout_titlebar_rootlayout.setBackgroundColor(mColor);
        //设置标题文字颜色
        tv_titlebar_title.setTextColor(mTextColor);


        //设置控件高度
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, title_height);
        params.height = title_height;
        layout_titlebar_rootlayout.setLayoutParams(params);

        LayoutParams left_params = new LayoutParams(LayoutParams.MATCH_PARENT, title_height);
        left_params.height = title_height;
        left_params.width = title_height;
        iv_titlebar_leftRel.setLayoutParams(left_params);

        LayoutParams right_params = new LayoutParams(LayoutParams.MATCH_PARENT, title_height);
        right_params.height = title_height;
        right_params.width = title_height;
        right_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        iv_titlebar_rightRel.setLayoutParams(right_params);

        LayoutParams image_params = new LayoutParams(LayoutParams.MATCH_PARENT, title_height);
        image_params.height = title_height / 3;
        image_params.width = (int) (title_height / 4.5);
        image_params.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_titlebar_left.setLayoutParams(image_params);

        setLeftImage(left_iamge);
        setRightImage(right_iamge);

        //设置文字
        setTitle(titlename, context);
    }

    public void setTitle(String titlename, Context context) {
        if (!TextUtils.isEmpty(titlename)) {
            tv_titlebar_title.setText(titlename);
            tv_titlebar_title.setTextSize(px2dip(context, title_text_size) + 5);
        }
    }

    /**
     * 设置左边图片
     */
    public void setLeftImage(int left_iamge) {
        iv_titlebar_left.setBackgroundResource(left_iamge);
        LayoutParams image_params = new LayoutParams(LayoutParams.MATCH_PARENT, title_height);
        image_params.height = title_height / 3;
        image_params.width = (int) (title_height / 4.5);
        image_params.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_titlebar_left.setLayoutParams(image_params);
    }

    /**
     * 设置右边图片
     */
    public void setRightImage(int right_iamge) {
        iv_titlebar_right.setBackgroundResource(right_iamge);
        LayoutParams image_params1 = new LayoutParams(LayoutParams.MATCH_PARENT, title_height);
        image_params1.height = title_height / 3;
        image_params1.width = title_height / 3;
        image_params1.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_titlebar_right.setLayoutParams(image_params1);
    }

    public String getTitle() {
        return titlename;
    }

    public void setLeftListener(OnClickListener onClickListener) {
        iv_titlebar_leftRel.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        iv_titlebar_rightRel.setOnClickListener(onClickListener);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取字体高度
     */
    public double getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 获取字体宽度
     */
    public float getTxtWidth(String s) {
        Rect rect = new Rect();
        Paint p = new Paint();
        p.getTextBounds(s, 0, s.length(), rect);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        float width = rect.width();//字符串的宽度
        return width;
    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


}
