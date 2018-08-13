package 云华.智慧校园.自定义控件;

import yh.app.tool.DpPx;

import com.yhkj.cqgyxy.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;

@SuppressLint(
        {
                "ClickableViewAccessibility", "RtlHardcoded"
        })
public class MapCampusPopwindow {
    private static PopupWindow popupWindow;

    private View clickView;
    private Context context;
    private ViewGroup layout;

    public MapCampusPopwindow(View clickView, Context context, ViewGroup layout, ViewGroup parent) {
        this.clickView = clickView;
        this.context = context;
        this.layout = layout;
    }

    public void doit() {
        if (layout == null)
            return;
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, getHeigth() / 3, true);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.touming));
        try {
            popupWindow.showAsDropDown(clickView, -new DpPx(context).getDpToPx(40 - 24), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHeigth() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static PopupWindow getPopupWindow() {
        return popupWindow;
    }
}
