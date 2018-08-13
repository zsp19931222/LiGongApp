package 云华.智慧校园.工具;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import com.yhkj.cqgyxy.R;
@SuppressLint("ClickableViewAccessibility")
public class PopupWindowTools
{
	private PopupWindow popupWindow;

	public void showAsDropDown(Context context, View contentView, View clickView, int w, int h, int offx, int offy)
	{
		popupWindow = new PopupWindow(contentView, w, h, true);
		popupWindow.setTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.setTouchInterceptor(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return false;
			}
		});
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.touming));
		try
		{
			popupWindow.showAsDropDown(clickView, offx, offy);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void showAtLocation(Context context, View contentView, ViewGroup parent, int w, int h, int offx, int offy, int gravity)
	{
		popupWindow = new PopupWindow(contentView, w, h, true);
		popupWindow.setTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.setTouchInterceptor(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				popupWindow.dismiss();
				return false;
			}
		});
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green));
		try
		{
			popupWindow.showAtLocation(parent, gravity, offx, offy);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getHeigth(Context context)
	{
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}
}
