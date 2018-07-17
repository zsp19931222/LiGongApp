package yh.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/2/24 0024.
 * 获取屏幕宽高
 */

public class GetWindowsWH {
    /**
     * 得到屏幕宽度
     * */
    public static int GetW(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    /**
     * 得到屏幕高度
     * */
    public static int GetH(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    /**
     * 密度
     * */
    public static float GetDensity(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }
    /**
     * 得到控件宽高
     * @param i 0-高，1-宽
     * */
    public static int GetViewWH(final View view, int i){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int view_width =view.getMeasuredWidth();
        int view_heigh =view.getMeasuredHeight();
        if(i==0){
            return view_heigh;
        }else if(i==1) {
            return view_width;
        }else {
            return 0;
        }
    }
}
