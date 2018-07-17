package com.example.app4.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import java.util.List;

import yh.app.utils.DensityUtil;

/**
 * Created by Administrator on 2017/10/9.
 * <p>
 * 通讯录字母栏
 */

public class LetterView extends LinearLayout {
    private Context mContext;
    private CharacterClickListener mListener;

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        setOrientation(VERTICAL);

//        initView();
    }

    public void initView(List<String> strings) {
//        addView(buildImageLayout());

//        for (char i = 'A'; i <= 'Z'; i++) {
//            final String character = i + "";
//            TextView tv = buildTextLayout(character);
//
//            addView(tv);
//        }
        removeAllViews();
        for (int i = 0; i <strings.size() ; i++) {
            TextView tv = buildTextLayout(strings.get(i));
            addView(tv);
        }

//        addView(buildTextLayout("#"));
    }

    private TextView buildTextLayout(final String character) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(mContext);
        layoutParams.setMargins(0, 9, 0, 0);
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(DensityUtil.dip2px(mContext, 5));
        tv.setGravity(Gravity.CENTER);
        tv.setClickable(true);
        tv.setText(character);
        tv.setTextColor(ContextCompat.getColor(mContext,R.color.color_7));

        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickCharacter(character);
                }
            }
        });
        return tv;
    }

    private ImageView buildImageLayout() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        ImageView iv = new ImageView(mContext);
        layoutParams.height = DensityUtil.dip2px(mContext, 10);
        layoutParams.width = DensityUtil.dip2px(mContext, 10);
        layoutParams.gravity = Gravity.CENTER;
        iv.setLayoutParams(layoutParams);

        iv.setBackgroundResource(R.drawable.letter_search);

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    mListener.clickArrow();
                }
            }
        });
        return iv;
    }

    public void setCharacterListener(CharacterClickListener listener) {
        mListener = listener;
    }

    public interface CharacterClickListener {
        void clickCharacter(String character);

        void clickArrow();
    }
}
