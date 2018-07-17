package yh.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anmin on 2017/6/15.
 */

public class Utils {
    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }




    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    
    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    public static String getYareTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月-dd日");
        return format.format(date);
    }



    /**
     *  
     *      * 得到两个整数之间的一个随机数 
     *      * 
     *      * @param start 较小的数 
     *      * @param end   较大的数 
     *      * @return 
     *      
     */
    public static int getRadomNumber(int start, int end) {
        return (int) (start + Math.random() * (end - start));
    }
    
    
    
    /**
     * 确认字符串是否为email格式
     * 
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
    String strPattern = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(strEmail);
    return m.matches();
    }
    
    
	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTodayDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
