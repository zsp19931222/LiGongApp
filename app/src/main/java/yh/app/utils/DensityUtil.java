package yh.app.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取字体高度
     */
    public static double getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 获取字体宽度
     */
    public static float getTxtWidth(String s) {
        Rect rect = new Rect();
        Paint p = new Paint();
        p.getTextBounds(s, 0, s.length(), rect);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        float width = rect.width();//字符串的宽度
        return width;
    }
    public static int getTextWidth(Paint paint, String str) {
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
